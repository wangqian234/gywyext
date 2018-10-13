package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AlarmLogDao;
import com.mvc.dao.EquipMainDao;
import com.mvc.dao.EquipmentDao;
import com.mvc.dao.IndexDao;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.Company;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Project;
import com.mvc.service.IndexService;

@Service("indexServiceImpl")
public class IndexServiceImpl implements IndexService {
	@Autowired
	IndexDao indexDao;
	@Autowired
	AlarmLogDao alarmLogDao;
	
	@Autowired
	EquipmentDao equipmentDao;

	@Autowired
	EquipMainDao equipMainDao;
	@Override
	public List<Map> getInitLeft() {
		List<Company> listSource1 = indexDao.getInitLeft1();
		List<Project> listSource2 = indexDao.getInitLeft2();
		
		List<Map> listMap = new ArrayList<Map>();
		
		for(int i = 0 ; i < listSource1.size() ; i++){
			for(int j = 0 ; j < listSource2.size() ; j++){
				if(listSource1.get(i).getComp_id() == listSource2.get(j).getCompany().getComp_id()){
					Map<String, String> map = new HashMap<String, String>();
					map.put("comp_id", listSource1.get(i).getComp_id().toString());
					map.put("comp_name", listSource1.get(i).getComp_name());
					map.put("proj_id", listSource2.get(j).getProj_id().toString());
					map.put("proj_name", listSource2.get(j).getProj_name());
					listMap.add(map);
				}
			}
		}
		
		return listMap;
	}
	@Override
	public Integer getEquipAlarmNumByProId(Integer proId) {
		// TODO Auto-generated method stub
		
		Integer alarmNum=alarmLogDao.getEquipAlarmNumByProId(proId);
		return alarmNum;
	}
	@Override
	public Integer getEquipMainNumByProId(Integer proId,Date updateDate) {
		// TODO Auto-generated method stub
		Integer mainNum=equipmentDao.getEquipMainNumByProId(proId,updateDate);
		return mainNum;
	}
	@Override
	public Integer getEquipUnhealthNumByProId(Integer proId) {
		// TODO Auto-generated method stub
		Integer unhealthNum=equipmentDao.getEquipUnhealthNumByProId(proId);
		return unhealthNum;
	}
	@Override
	public List<AlarmLog> selectIndexAlramLog(Integer proId, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		 List<AlarmLog> list=alarmLogDao.selectIndexAlramLog(Integer.valueOf(proId), offset, limit);
		return list;
	}
	@Override
	public List<Equipment> selectIndexMainEquipList(Integer proId, Integer offset, Integer limit,Date updateDate) {
		// TODO Auto-generated method stub
		List<Equipment> list=equipmentDao.selectIndexMainEquipList(Integer.valueOf(proId), offset, limit,updateDate);
		return list;
	}
	@Override
	public List<Equipment> selectIndexUnhealthEquip(Integer proId, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		
		List<Equipment> list=equipmentDao.selectIndexUnhealthEquip(Integer.valueOf(proId), offset, limit);
		return list;
	}


}
