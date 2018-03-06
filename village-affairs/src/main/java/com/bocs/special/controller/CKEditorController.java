package com.bocs.special.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bocs.special.core.Constant;
import com.bocs.special.core.JavaEEFrameworkBaseController;
import com.bocs.special.model.Information;
import com.bocs.util.ImageUtil;
import com.bocs.util.PropertiesUtils;

import core.exception.ServiceException;

@Controller
@RequestMapping("/ckeditor")
public class CKEditorController  extends JavaEEFrameworkBaseController<Information> implements Constant {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1968515673152031638L;

	@RequiresPermissions(value="file:upload")
	@RequestMapping("/fileUpload")
	public void fileUpload(@RequestParam("upload") MultipartFile upload, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// CKEditor提交的很重要的一个参数
		String callback = request.getParameter("CKEditorFuncNum");
		String projectUrl = PropertiesUtils.getBasicValue("weburl");
		if(! projectUrl.endsWith("/")){
			projectUrl += "/";
		}
		String fileName = upload.getOriginalFilename();
		String newFileName = uploadFile(upload, request).getName();
		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
				+ ",'"+projectUrl+"ckeditor/download?newFileName=" + newFileName +"&fileName="+fileName+"','')");
		out.println("</script>");
	}

	@RequiresPermissions(value="image:upload")
	@RequestMapping("/imgUpload")
	public void imgUpload(@RequestParam("upload") MultipartFile upload, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		// CKEditor提交的很重要的一个参数
		String callback = request.getParameter("CKEditorFuncNum");
		String contentType = upload.getContentType();
		if(isImage(contentType)){
			String projectUrl = PropertiesUtils.getBasicValue("weburl");
			if(! projectUrl.endsWith("/")){
				projectUrl += "/";
			}
			File newFile = uploadFile(upload, request);
			
			//压缩图片
			ImageUtil.resize(newFile, newFile, 1024, 1024, 1);
			
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
					+ ",'"+projectUrl+"attachment/information/" + newFile.getName() +"','')");
			out.println("</script>");
		}else{
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
					+ ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
			out.println("</script>");
		}
	}
	
	
	@RequiresPermissions(value="vedio:upload")
	@RequestMapping("/vedioUpload")
	public void vedioUpload(@RequestParam("upload") MultipartFile upload, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		// CKEditor提交的很重要的一个参数
		String callback = request.getParameter("CKEditorFuncNum");
		String contentType = upload.getContentType();
		System.out.println("contentType:"+contentType);
		String projectUrl = PropertiesUtils.getBasicValue("weburl");
		if(! projectUrl.endsWith("/")){
			projectUrl += "/";
		}
		File newFile = uploadFile(upload, request);
		
		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.CKEDITOR.tools.callFunction(" + callback
				+ ",'"+projectUrl+"attachment/information/" + newFile.getName() +"','')");
		out.println("</script>");
	}
	
	/**
	 * 下载附件
	 * @param fileId
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/download", method = { RequestMethod.POST, RequestMethod.GET })
	public void download(String newFileName, String fileName, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if(StringUtils.isNotBlank(newFileName)){
			String filePath = request.getServletContext().getRealPath("/attachment/information/") + File.separator+ newFileName;
			response.setCharacterEncoding("utf-8");
			response.setContentType(getContentType(fileName));
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(new File(filePath));
				os = response.getOutputStream();
				byte[] b = new byte[2048];
				int length;
				while ((length = is.read(b)) > 0) {
					os.write(b, 0, length);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				 try {
					if(os != null){
						os.close();
					}
					if(is != null){
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}
	}
	
	
	private String getContentType(String fileName) {
		String extentsion = FilenameUtils.getExtension(fileName);
		String contentType = "text/plain";
		if("BMP".equalsIgnoreCase(extentsion)){
			contentType = "image/bmp";
		}else if("GIF".equalsIgnoreCase(extentsion)){
			contentType = "image/gif";
		}else if("JPEG".equalsIgnoreCase(extentsion)){
			contentType = "image/jpeg";
		}else if("TIFF".equalsIgnoreCase(extentsion)){
			contentType = "image/tiff";
		}else if("PDF".equalsIgnoreCase(extentsion)){
			contentType = "application/pdf";
		}else if("doc".equalsIgnoreCase(extentsion) || "docx".equalsIgnoreCase(extentsion)){
			contentType = "application/msword";
		}else if("xls".equalsIgnoreCase(extentsion) || "xlsx".equalsIgnoreCase(extentsion) ){
			contentType = "application/vnd.ms-excel";
		}else if("ppt".equalsIgnoreCase(extentsion) || "pptx".equalsIgnoreCase(extentsion)){
			contentType = "application/vnd.ms-powerpoint";
		}else if("visio".equalsIgnoreCase(extentsion)){
			contentType = "application/vnd.visio";
		}
		// TODO Auto-generated method stub
		return contentType;
	}

	private boolean isImage(String contentType) {
		if("image/pjpeg".equals(contentType)){
			return true;
		}else if("image/jpeg".equals(contentType)){
			return true;
		}else if("image/png".equals(contentType)){
			return true;
		}else if("image/x-png".equals(contentType)){
			return true;
		}else if("image/gif".equals(contentType)){
			return true;
		}else if("image/bmp".equals(contentType)){
			return true;
		}
		return false;
	}

	private File uploadFile(MultipartFile file, HttpServletRequest request) throws ServiceException{
		String root = request.getServletContext().getRealPath("/attachment/information") + File.separator;
		File newFile = null;
		if(! file.isEmpty()){
			String newName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
			newFile = new File(root + newName);
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ServiceException("文件上传失败！");
			}
		}
		return newFile;
	}
}
