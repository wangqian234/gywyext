package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.StaffInfoDao;

import com.mvc.entityReport.User;
import com.mvc.repository.StaffInfoRepository;
import com.mvc.service.StaffInfoService;

@Service("staffInfoServiceImpl")
public class StaffInfoServiceImpl implements StaffInfoService {
	
	@Autowired
	StaffInfoRepository staffInfoRepository;
	
	@Autowired
	StaffInfoDao staffInfoDao;
	@Override
	public List<User> getStaffInfo() {
		return staffInfoDao.getStaffInfo();
	}

	/**
	 * 添加、修改旅游信息
	 */
	public boolean save(User user) {
		User result = staffInfoRepository.saveAndFlush(user);
		if (result.getUser_id() != null)
			return true;
		else
			return false;
	}
	// 筛选角色列表
	//	@Override
	//	public List<User> findRoleAlls() {
	//		return staffInfoRepository.findAlls();
	//	}
	// 根据userAcct查询用户账号是否存在,返回1存在，返回0不存在
		public Long isExist(String userAcct) {
			Long result = staffInfoRepository.countByUserAcct(userAcct);
			return result;
		}

}
