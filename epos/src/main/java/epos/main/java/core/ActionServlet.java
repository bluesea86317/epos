package epos.main.java.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import epos.main.java.annotation.ActionAuthFilterConfig;
import epos.main.java.exception.NonActionForRequstException;
import epos.main.java.exception.ParameterErrorException;
import epos.main.java.exception.UserNameOrPasswordWrongException;
import epos.main.java.service.UserService;

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
		String contextPath = this.getServletContext().getRealPath("");
		String fileName = this.getInitParameter("config");
		String configPath = contextPath + fileName;
		
//		初始化数据库连接		
		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new File(configPath));
			Element rootNode = doc.getRootElement();
//			初始化actionConfig		
			this.initializeActionConfig(rootNode);
//			初始化数据库连接
//			this.initializeDataSource(rootNode);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response){
		try {
			JSONObject jsonParam = getJsonParam(request);
//			设置请求和响应的编码规则
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			String actionPath = jsonParam.getString("action");
			ActionConfig actionConfig = configMap.get(actionPath);
			if(null == actionConfig){
				throw new NonActionForRequstException("This action name '" + actionPath + "' is wrong.");			
			}
			
//			对需要验权的action进行用户名和密码的正确性验证
			authorizeAction(jsonParam);
			
//			将处理结果通过json字符串格式返回给客户端
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
		} catch (UserNameOrPasswordWrongException e) {
			e.outPrint(response);
			e.printStackTrace();
		}
	}
	
	/**
	 * 调用action, 返回处理的结果, 结果格式为json字符串
	 * @param request
	 * @param response
	 * @param actionConfig
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void returnResult(HttpServletRequest request, HttpServletResponse response, ActionConfig actionConfig) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{		
		String clzName = actionConfig.getActionclass();
		Action action = (Action)Class.forName(clzName).newInstance();
		outPut(response, action.excute(request, response));
	}
	
	/**
	 * 对需要授权的action做用户名密码的合法性验证
	 * @param jsonParam
	 * @throws ClassNotFoundException
	 * @throws UserNameOrPasswordWrongException
	 */
	private void authorizeAction(JSONObject jsonParam) throws ClassNotFoundException, UserNameOrPasswordWrongException{
		ActionConfig actionConfig = configMap.get(jsonParam.getString("action"));
		String clzName = actionConfig.getActionclass();
		ActionAuthFilterConfig annotation  = Class.forName(clzName).getAnnotation(ActionAuthFilterConfig.class);
		if(annotation != null && annotation.needAuthorize()){
			try {
				String userName = jsonParam.getString("userName");
				String password = jsonParam.getString("password");
				UserService userService = Env.getBean("userService");
				if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || !userService.validateUserNameAndPassword(userName, password)){
					throw new UserNameOrPasswordWrongException("Wrong username or password !");
				}				
			} catch (Exception e) {
				throw new UserNameOrPasswordWrongException("Wrong username or password !");
			}
		}
	}
	
	/**
	 * 获取请求的json参数对象
	 * @param request
	 * @return
	 * @throws ParameterErrorException
	 */
	private JSONObject getJsonParam(HttpServletRequest request) throws ParameterErrorException{		
		String param = request.getParameter("json");
		if(StringUtils.isBlank(param)){
			throw new ParameterErrorException("There is no parameter named 'json'. ");
		}
		try{	
			JSONObject jsonObj = new JSONObject();
			JSONArray jsonArray = JSONArray.fromObject("["+param+"]");
			jsonObj = jsonArray.getJSONObject(0);
			return jsonObj;			
		} catch (Exception e) {
			throw new ParameterErrorException("The parameter is not a correct json type. ");
		}
//		return null;
	}
	
	/**
	 * 
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
