package com.mvc.dao;

import java.util.Date;
import java.util.List;

import com.mvc.entityReport.AlarmLog;

public interface AlarmLogDao {

	Integer countEquipFailNumById(Integer equipId);
	
	
	Integer countEquipFailNumByIdAndDate(Integer equipId,Date date);
	
	
	List<Object>  getEquipFailCountById(Integer equipId,String year);

	
	Integer getEquipAlarmNumByProId(Integer proId);

	List<AlarmLog> getAlarmListByPage(String searchKey,Integer offset, Integer end);
	
	Integer countAlarmTotal(String searchKey);



	List<Object> selectAlarmByA(String proj_id);


	Integer getAlarmNum(String proj_id);

}