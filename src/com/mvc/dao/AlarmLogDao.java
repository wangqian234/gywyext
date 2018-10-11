package com.mvc.dao;

import java.util.Date;
import java.util.List;

public interface AlarmLogDao {

	Integer countEquipFailNumById(Integer equipId);
	
	
	Integer countEquipFailNumByIdAndDate(Integer equipId,Date date);
	
	
	List<Object>  getEquipFailCountById(Integer equipId,String year);
	
	Integer getEquipAlarmNumByProId(Integer proId);
}