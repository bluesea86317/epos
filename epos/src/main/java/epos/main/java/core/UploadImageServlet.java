package epos.main.java.core;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import epos.main.java.exception.UploadImageException;

@SuppressWarnings("serial")
public class UploadImageServlet extends HttpServlet {

	private static final String webDirectory = "/images/item";
	private static final String[] fileTypeString = {"png","jpg","jpeg","gif","bmp"};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processUpload(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processUpload(req, resp);
	}
	
	@SuppressWarnings("rawtypes")
	private void processUpload(HttpServletRequest request, HttpServletResponse response) throws IOException{	
		String destinationFileName = "";
		String msg = "上传成功";
//		设置请求和响应的编码规则
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(10*1024 * 1024);
		Return retn = new Return(Return.PROCESS_RESULT_SUCCESS, msg);
		JSONObject jonObj = retn.toJsonObj();
		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
                   throw new UploadImageException("不是有效的文件流请求");
				} else {
					if (!"".equals(item.getName())) {
						if (item.getSize() == 0) {							
							throw new UploadImageException("该请求中没有任何文件");
						} else {
							destinationFileName = writeToFile(this, item);
							if (destinationFileName == null || destinationFileName.trim().length() == 0) {
								throw new UploadImageException("upload error !");
							}
						}
					}
				}
			}
			jonObj.put("imageFileName", destinationFileName);
			response.getWriter().print(jonObj.toString());
		}catch (FileUploadBase.SizeLimitExceededException e) {
			new UploadImageException("上传文件不能超过10M").outPrint(response);
		}catch (FileUploadException e) {
			e.printStackTrace();
			new UploadImageException(e.getMessage()).outPrint(response);
		}catch (UploadImageException e) {			
			e.printStackTrace();
			e.outPrint(response);
		}catch (Exception e){
			new UploadImageException(e.getMessage()).outPrint(response);
		}
		
	}
	
	private String writeToFile(HttpServlet servlet, FileItem item) throws UploadImageException{
		String fileDir = servlet.getServletContext().getRealPath("")+ webDirectory+ "/";
		File targetDir = new File(fileDir);
		String fileName = "";
		try {
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			fileName = item.getName();
			if (fileName != null && !fileName.equals("")) {
				boolean flag = false;
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				for(String fileType : fileTypeString){
					if(fileType.equals(suffix)){
						flag = true;
						break;
					}					
				}
				if(!flag){
					throw new UploadImageException("文件格式不正确, 只能上传图片文件!");
				}
				fileName = System.currentTimeMillis() + "."+ suffix;
				item.write(new File(fileDir, fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UploadImageException(e.getMessage());
		}
		return fileName;
	}
}
