package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.StaffInfoDao;
import com.mvc.entityReport.User;
import com.mvc.service.StaffInfoService;

@Service("staffInfoServiceImpl")
public class StaffInfoServiceImpl implements StaffInfoService {
	
	@Autowired
	StaffInfoDao staffInfoDao;

	@Override
	public List<User> getStaffInfo() {
		return staffInfoDao.getStaffInfo();
	}

}
