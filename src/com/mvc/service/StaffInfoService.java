package com.mvc.service;

import java.util.List;


import com.mvc.entityReport.User;


public interface StaffInfoService {

	List<User> getStaffInfo();
	
	boolean save(User user);

	// 筛选角色列表
	//List<User> findRoleAlls();
	
	// 根据userAcct查询用户账号是否存在,返回1存在，返回0不存在
		Long isExist(String userAcct);

}
