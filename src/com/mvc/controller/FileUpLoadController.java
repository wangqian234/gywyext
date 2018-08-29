package com.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.base.constants.PictureConstants;
import com.mvc.entityReport.Files;
import com.mvc.service.FileService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/file")
public class FileUpLoadController {
	/**
	 * 上传文件
	 * 
	 * @param request（file,contId）
	 * @return true,false
	 * @throws IOException
	 */
	
	@Autowired
	FileService fileService;
	
	@RequestMapping("/upload.do")
	public @ResponseBody String upload(HttpServletRequest request, HttpSession session) throws IOException {
		boolean flag = false;
		JSONObject jsonObject = new JSONObject();
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {// 判断是否有文件上传
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 转换成多部分request
			Iterator<String> iter = multiRequest.getFileNames();// request中的所有文件名
			String path = request.getSession().getServletContext().getRealPath(PictureConstants.UPLOAD_PATH);// 上传服务器的路径
			createDir(path);

			Date date = null;
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddhhmmssSSS");// 定义到毫秒
			String nowStr = "";
			Files fileBean = null;

			while (iter.hasNext()) {// 文件存储失败和存入数据库失败，都是失败
				MultipartFile file = multiRequest.getFile(iter.next());// 将要上传的文件
				fileBean = new Files();
				long time = System.currentTimeMillis();
				date = new Date();
				nowStr = dateformat.format(date);
				if (file != null) {
					try {
						String myFileName = file.getOriginalFilename();// 当前上传文件的文件名称
						String filename = myFileName.substring(0, myFileName.lastIndexOf("."));// 去掉后缀的文件名
						String suffix = myFileName.substring(myFileName.lastIndexOf(".") + 1);// 后缀
						if (myFileName.trim() != "") {// 如果名称不为"",说明该文件存在，否则说明该文件不存在
							path += "\\" + filename + nowStr + "." + suffix;// 定义上传路径
							File localFile = new File(path);
							file.transferTo(localFile);
						}
						// 将记录写入数据库
						fileBean.setFile_name(myFileName);// 文件名
						fileBean.setFile_type(suffix);// 文件类型，后缀
						fileBean.setFile_path(path);// 文件路径
						fileBean.setFile_ctime(new Date(time));// 创建时间
						fileBean.setFile_isdelete(0);// 是否删除
						flag = fileService.addFile(fileBean);
						if (flag == false) {
							break;
						}
						jsonObject.put("fileBean", fileBean);
					} catch (Exception e) {
						flag = false;
						jsonObject.put("error", "文件上传失败");
						e.printStackTrace();
					}
				}
			}
		}
		return jsonObject.toString();
	}
	
	/**
	 * 根据路径确定目录，没有目录，则创建目录
	 * 
	 * @param path
	 */
	public void createDir(String path) {
		File fileDir = new File(path);
		if (!fileDir.exists() && !fileDir.isDirectory()) {// 判断/download目录是否存在
			fileDir.mkdir();// 创建目录
		}
	}

}
