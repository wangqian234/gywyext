package com.mvc.service;

import java.util.List;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipOper;

public interface MobileService {

	//手动添加报警信息
	AlarmLog addAlarm(AlarmLog alarmLog);

	//手动添加运行状态信息
	EquipOper addOpeartion(EquipOper equipOper);

	//获取维保信息
	List<EquipMain> getmaintenance();

}
