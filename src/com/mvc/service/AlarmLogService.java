package com.mvc.service;

import java.util.List;
import com.mvc.entityReport.AlarmLog;

import net.sf.json.JSONArray;

public interface AlarmLogService {

	List<AlarmLog> getAlarmListByPage(String searchKey,Integer offset, Integer end);
	Integer countAlarmTotal(String searchKey);
	List<AlarmLog> getAlarmListByEquipId(String equipmentId);
	JSONArray getProEquipAnalysis(String proj_id);
}
