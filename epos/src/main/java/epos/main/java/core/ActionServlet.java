package epos.main.java.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import epos.main.java.exception.NonActionForRequstException;
import epos.main.java.exception.ParameterErrorException;
import epos.main.java.transaction.Env;
import epos.main.java.utils.ParamUtils;

public class ActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	Map<String,ActionConfig> configMap = new HashMap<String,ActionConfig>();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req,resp);
	}
	@Override
	public void destroy() {		
		super.destroy();
	}

	
	@Override
	public void init() throws ServletException {
		String filePath = this.getServletContext().getRealPath("");
		String fileName = this.getInitParameter("config");
		String configPath = filePath + fileName;
		
//		初始化数据库连接		
		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new File(configPath));
			Element rootNode = doc.getRootElement();
//			初始化actionConfig		
			this.initializeActionConfig(rootNode);
//			初始化数据库连接
			this.initializeDataSource(rootNode);
			Env.initServiceBeanMap();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response){
		try {
			String actionPath = getActionPathFromRequest(request);
//			设置请求和响应的编码规则
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			
			ActionConfig actionConfig = configMap.get(actionPath);
			if(null == actionConfig){
				throw new NonActionForRequstException("There is no action bean for this request which action name is \"" + actionPath + "\"");			
			}
//			讲处理结果通过json字符串格式返回给客户端
			returnResult(request, response, actionConfig);			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonActionForRequstException e) {
//			直接将错误日志通过response返回给客户端
			e.outPrint(response);
			e.printStackTrace();
		} catch (ParameterErrorException e) {			
			e.outPrint(response);
			e.printStackTrace();
		}
	}
	
	private void returnResult(HttpServletRequest request, HttpServletResponse response, ActionConfig actionConfig) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{		
		String clzName = actionConfig.getActionclass();
		Action action = (Action)Class.forName(clzName).newInstance();
		outPut(response, action.excute(request, response));
	}
	
	private String getActionPathFromRequest(HttpServletRequest request) throws ParameterErrorException{		
		String param = request.getParameter("json");
		if(StringUtils.isBlank(param)){
			throw new ParameterErrorException("There is no parameter named 'json'. ");
		}
		try{	
			JSONObject jsonObj = new JSONObject();
			JSONArray jsonArray = JSONArray.fromObject("["+param+"]");
			jsonObj = jsonArray.getJSONObject(0);
			return jsonObj.getString("action");			
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * ajax result return : success
	 * @param response
	 */
	public void return_out(HttpServletResponse response,String result, String msg){
		outPut(response,"{\"resultCode\":\"" + result + "\",\"msg\":\"" + msg + "\"}");
	}
	
	/**
	 * ajax输出
	 * @param response
	 * @param result
	 */
	public void outPut(HttpServletResponse response,String result){
		try {
			response.getWriter().print(result);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据库连接
	 * @param rootNode
	 */
	private void initializeDataSource(Element rootNode){
		System.out.println("初始化数据库连接开始");
		List<?> dataSourceNodesList = rootNode.selectNodes("data-sources/data-source");
		Element dataSourceNode = (Element)dataSourceNodesList.get(0);
		String className = dataSourceNode.attributeValue("type");
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
			Object dataSource = clazz.newInstance();
//			获取DB参数
			List<?> dataSourcePropertyNodesList = rootNode.selectNodes("data-sources/data-source/property");
			for(Object node : dataSourcePropertyNodesList){
				Element propertyNode = (Element)node;
				String dataSourceProperty = propertyNode.attributeValue("name");
				String dataSourceValue = propertyNode.attributeValue("value");
				Field[] fields = clazz.getDeclaredFields();
				for(Field field : fields){
					if(field.getName().equals(dataSourceProperty)){
						Method method = clazz.getMethod("set" + ParamUtils.upperCaseMethodName(field.getName()), field.getType());
						method.invoke(dataSource, ParamUtils.convertString(dataSourceValue, field.getType()));
					}
				}
			}
//			初始化DB连接
			DataSourceFactory.init((DataSource)dataSource);
			System.out.println("初始化数据库连接结束");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化actionConfig
	 * @param rootNode   config.xml的节点树
	 * @throws DocumentException
	 */
	private void initializeActionConfig(Element rootNode){
		List<?> nodesList = rootNode.selectNodes("action-mapping/action");
		System.out.println("初始化actionConfig开始");
		for(Object node : nodesList){
//			加载actionConfig
			ActionConfig actionConfig = new ActionConfig();
//			设置action的请求路径path,处理类class
			Element actionNode = (Element)node;
			String actionPath = actionNode.attributeValue("path");
			String actionClass = actionNode.attributeValue("class");
			actionConfig.setActionPath(actionPath);
			actionConfig.setActionclass(actionClass);
//			设置action处理完成后的跳转取消，path处理完成后的选择符，value是路径
			List<?> forwardNodeList = actionNode.selectNodes("forward");
			
			ActionForward forward = new ActionForward();
			for(Object fNode : forwardNodeList){
				Element forewardNode = (Element)fNode;
				String path = forewardNode.attributeValue("path");
				String name = forewardNode.attributeValue("name");
				forward.putForwardMap(name, path);
			}
			actionConfig.setActionForward(forward);
			configMap.put(actionPath,actionConfig);
		}
		System.out.println("初始化actionConfig结束");
	}
}
