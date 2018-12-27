package com.mvc.service;

import java.util.List;
import com.mvc.entityReport.AlarmLog;

import net.sf.json.JSONArray;

public interface AlarmLogService {

	List<AlarmLog> getAlarmListByPage(String proj_id,String searchKey ,Integer offset, Integer end);
	Integer countAlarmTotal(String proj_id,String searchKey);
	List<AlarmLog> getAlarmListByEquipId(String equipmentId);
	JSONArray getProEquipAnalysis(String proj_id);
	boolean save(AlarmLog alarmlog);
}
