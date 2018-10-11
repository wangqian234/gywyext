package com.mvc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IndexService {

	public List<Map> getInitLeft();
	
	Integer getEquipAlarmNumByProId(Integer proId);
	
	Integer getEquipMainNumByProId(Integer proId,Date updateDate);
	
	Integer getEquipUnhealthNumByProId(Integer proId);
}
