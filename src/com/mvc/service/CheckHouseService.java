package com.mvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mvc.entityReport.CheckHouse;

/**
 * 领班查房效率相关的service层接口
 * 
 * @author zjn
 * @date 2017年1月17日
 */
public interface CheckHouseService {

	// 查询领班查房效率列表
	List<CheckHouse> getCheckHouseList(String startTime, String endTime);

	// 导出领班查房效率列表
	ResponseEntity<byte[]> exportCheckHouseList(Map<String, Object> map);

	String getAnalyseResult(List<CheckHouse> checkHouseList, String writeField);

	// 导出领班查房效率列表
	ResponseEntity<byte[]> exportCheckHouseExcel(Map<String, Object> map);

}
