package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AlarmLogDao;
import com.mvc.dao.EquipmentDao;
import com.mvc.dao.EquipMainDao;
import com.mvc.service.AlarmLogService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.Equipment;
import com.mvc.repository.AlarmLogRepository;


@Service("AlarmLogServiceImpl")
public class AlarmLogServiceImpl implements AlarmLogService {
	
	@Autowired
	AlarmLogDao alarmLogDao;
	
	@Autowired
	EquipmentDao equipmentDao;

	@Autowired
	EquipMainDao equipMainDao;
	
	@Autowired
	AlarmLogRepository alarmLogRepository;
	
	// 根据页数筛选全部旅游信息列表
	@Override
		public List<AlarmLog> getAlarmListByPage(String proj_id,String searchKey,Integer offset, Integer end) {
			return alarmLogDao.getAlarmListByPage(proj_id,searchKey,offset, end);
		}
	@Override
	public Integer countAlarmTotal(String proj_id,String searchKey) {
		return alarmLogDao.countAlarmTotal(proj_id,searchKey);
	}
	@Override
	public List<AlarmLog> getAlarmListByEquipId(String equipmentId) {
		return alarmLogDao.getAlarmListByEquipId(equipmentId);
	}
	@Override
	public JSONArray getProEquipAnalysis(String proj_id) {
		List<Equipment> list = equipmentDao.selectAllEquipByProId(proj_id);
		JSONArray arr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Integer num = alarmLogDao.countEquipFailNumById(list.get(i).getEquip_id());
			JSONObject obj = new JSONObject();
			if (num == null) {
				num = 0;
			}
			obj.put("value", num);
			obj.put("name", list.get(i).getEquip_name());
			arr.add(obj);
		}
		return arr;
	}

	//添加信息
	public boolean save(AlarmLog alarmlog) {
		AlarmLog result = alarmLogRepository.saveAndFlush(alarmlog);
		if (result.getAlarm_log_id() != null)
			return true;
		else
			return false;
	}
	
	
}
