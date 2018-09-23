package com.mvc.service;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipOper;

public interface MobileService {

	//手动添加报警信息
	AlarmLog addAlarm(AlarmLog alarmLog);

	//手动添加运行状态信息
	EquipOper addOpeartion(EquipOper equipOper);

}
