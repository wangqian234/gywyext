package com.mvc.dao;

import java.util.List;

import com.mvc.entityReport.Role;
import com.mvc.entityReport.User;

/**
 * 角色职位
 * 
 * @author wanghuimin
 * @date 2016年9月9日
 */
public interface RoleDao {

	// 根据页数筛选全部角色列表
	List<Role> findRoleAllByPage(Integer offset, Integer end);
				
		// 查询信息总条数
			Integer countTotal();
}
