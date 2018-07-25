/**
 * 
 */
package com.mvc.dao;

import java.util.List;

/**
 * 领班查房效率相关的dao层接口
 * 
 * @author zjn
 * @date 2017年1月17日
 */
public interface CheckHouseDao {

	// 获取领班每天查房用时列表
	List<Object> getCheckHouseTime(String startTime, String endTime);

}
