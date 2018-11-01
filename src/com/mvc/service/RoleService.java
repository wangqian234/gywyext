package com.mvc.service;

import java.util.List;
import com.mvc.entityReport.Role;
import com.mvc.entityReport.User;

/**
 * 角色
 * @author wanghuimin
 * @date 2016年9月18日
 */
public interface RoleService {
	// 根据页数筛选全部角色列表
			List<Role> findRoleAllByPage(Integer offset, Integer end);

		// 查询总条数
		Integer countTotal();

		//添加角色
		boolean save(Role role);

	

}