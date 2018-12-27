package com.mvc.dao;

import java.util.Date;
import java.util.List;

import com.mvc.entityReport.AlarmLog;

public interface AlarmLogDao {

	Integer countEquipFailNumById(Integer equipId);
	
	
	Integer countEquipFailNumByIdAndDate(Integer equipId,Date date);
	
	
	List<Object>  getEquipFailCountById(Integer equipId,String year);

	
	Integer getEquipAlarmNumByProId(Integer proId);

	List<AlarmLog> getAlarmListByPage(String proj_id,String searchKey,Integer offset, Integer end);
	
	Integer countAlarmTotal(String proj_id,String searchKey);



	List<Object> selectAlarmByA(String proj_id);


	Integer getAlarmNum(String proj_id);
	
	List<AlarmLog> selectIndexAlramLog(Integer proId,Integer offset,Integer limit);


	List<AlarmLog> getAlarmListByEquipId(String equipmentId);

}