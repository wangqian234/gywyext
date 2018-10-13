package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;

public interface MobileService {

	//手动添加报警信息
	AlarmLog addAlarm(AlarmLog alarmLog);

	//手动添加运行状态信息
	EquipOper addOpeartion(EquipOper equipOper);

	//获取维保信息
	List<EquipMain> getmaintenance();

	//根据设备ID获取维保信息
	List<EquipMain> getMaintenanceById(String equip_id);

	//根据设备ID获取设备参数信息
	List<EquipPara> getParaById(String equip_id);

	//根据项目ID获取设备列表
	List<EquipRoom> getRoomByProId(String proj_id);

	List<Equipment> selectEquipByRoomMobile(List<EquipRoom> room);

	//根据维保时间查找设备
	List<Map> selectEquipmentByN(String proj_id);

	//根据健康状态查找设备
	List<Map> selectEquipmentByS(String proj_id);

	//根据项目查找报警
	List<Map> selectAlarmByA(String proj_id);

	//根据proj_id获取数量
	List<Integer> selectAllNum(String proj_id);

}
