package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.RoleDao;
import com.mvc.entityReport.Role;
import com.mvc.entityReport.User;
import com.mvc.repository.RoleRepository;
import com.mvc.service.RoleService;

/**
 * 角色
 * 
 * @author wanghuimin
 * @date 2016年9月18日
 */
@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDao roleDao;
	@Autowired
	RoleRepository roleRepository;

	// 根据页数筛选全部用户信息列表
	
			public List<Role> findRoleAllByPage(Integer offset, Integer end) {
				return  roleDao.findRoleAllByPage(offset, end);
			}
		// 查询总条数
				@Override
				public Integer countTotal() {
					return roleDao.countTotal();
				}
		// 添加角色
		@Override
		public boolean save(Role role) {
			Role result = roleRepository.saveAndFlush(role);
			if (result.getRole_id() != null)
				return true;
			else
				return false;

		}
	}

	

