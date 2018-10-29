package com.mvc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.Equipment;

public interface IndexService {

	public List<Map> getInitLeft();
	
	Integer getEquipAlarmNumByProId(Integer proId);
	
	Integer getEquipMainNumByProId(Integer proId,Date updateDate);
	
	Integer getEquipUnhealthNumByProId(Integer proId);
	
	List<AlarmLog> selectIndexAlramLog(Integer proId,Integer offset,Integer limit);
	
	List<Equipment> selectIndexMainEquipList(Integer proId,Integer offset,Integer limit,Date updateDate);
	
	List<Equipment> selectIndexUnhealthEquip(Integer proId,Integer offset,Integer limit);
	
}
