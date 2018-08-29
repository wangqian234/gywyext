package com.mvc.service;

import java.util.List;

import com.mvc.entityReport.Files;


/**
 * 文件管理业务
 * 
 * @author wangrui
 * @date 2016-10-14
 */
public interface FileService {

	// 添加文件
	Boolean addFile(Files file);
}
