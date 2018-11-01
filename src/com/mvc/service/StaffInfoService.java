package com.mvc.service;

import java.text.ParseException;
import java.util.List;

import com.mvc.entityReport.Role;
import com.mvc.entityReport.User;

import net.sf.json.JSONObject;


public interface StaffInfoService {

	
	
	boolean save(User user);
	List<User> getStaffInfo();
	

	// 筛选角色列表
	List<Role> getAllRoleList();
	
	// 根据userAcct查询用户账号是否存在,返回1存在，返回0不存在
	//	Long isExist(String userAcct);
	
	// 根据页数筛选全部用户信息列表
		List<User> findUserByPage(String searchKey, Integer offset, Integer end);

	// 查询总条数
	Integer countTotal(String searchKey);

	//根据ID删除
	boolean deleteIsdelete(Integer user_id);
	//根据ID删除角色
	boolean deleteIsdeleteRole(Integer role_id);
	// 修改用户基本信息
		Boolean updateUserBase(Integer user_id, JSONObject jsonObject, User user) throws ParseException;
		
	// 根据ID获取用户信息
	User selectUserById(Integer user_id);
	// 根据ID获取角色信息
	Role selectRoleById(Integer role_id);	
}
