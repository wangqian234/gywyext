package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AlarmLogDao;
import com.mvc.service.AlarmLogService;
import com.mvc.entityReport.AlarmLog;


@Service("AlarmLogServiceImpl")
public class AlarmLogServiceImpl implements AlarmLogService {
	
	@Autowired
	AlarmLogDao alarmLogDao;

	// 根据页数筛选全部旅游信息列表
	@Override
		public List<AlarmLog> getAlarmListByPage(String searchKey,Integer offset, Integer end) {
			return alarmLogDao.getAlarmListByPage(searchKey,offset, end);
		}
	@Override
	public Integer countAlarmTotal(String searchKey) {
		return alarmLogDao.countAlarmTotal(searchKey);
	}
}
