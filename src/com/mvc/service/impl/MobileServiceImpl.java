package com.mvc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AlarmLogDao;
import com.mvc.dao.EquipmentDao;
import com.mvc.dao.MobileDao;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;
import com.mvc.repository.AlarmLogRepository;
import com.mvc.repository.EquipMainRepository;
import com.mvc.repository.EquipOperRepository;
import com.mvc.repository.EquipParaRepository;
import com.mvc.repository.EquipRoomRepository;
import com.mvc.service.MobileService;
import com.utils.StringUtil;

@Service("mobileServiceImpl")
public class MobileServiceImpl implements MobileService {

	@Autowired
	AlarmLogRepository alarmLogRepository;
	@Autowired
	EquipOperRepository equipOperRepository;
	@Autowired
	EquipMainRepository equipMainRepository;
	@Autowired
	EquipParaRepository equipParaRepository;
	@Autowired
	EquipRoomRepository equipRoomRepository;
	@Autowired
	MobileDao mobileDao;
	@Autowired
	EquipmentDao equipmentDao;
	@Autowired
	AlarmLogDao alarmLogDao;
	
	
	
	@Override
	public AlarmLog addAlarm(AlarmLog alarmLog) {
		AlarmLog result = alarmLogRepository.saveAndFlush(alarmLog);
		return result;
	}

	@Override
	public EquipOper addOpeartion(EquipOper equipOper) {
		EquipOper result = equipOperRepository.saveAndFlush(equipOper);
		return result;
	}

	@Override
	public List<EquipMain> getmaintenance() {
		List<EquipMain> equipMains = equipMainRepository.getmaintenance();
		return equipMains;
	}

	@Override
	public List<EquipMain> getMaintenanceById(String equip_id) {
		List<EquipMain> equipMains = equipMainRepository.getMaintenanceById(equip_id);
		for(int i=0;i<equipMains.size();i++){
			equipMains.get(i).setEquipment(null);
		}
		return equipMains;
	}

	@Override
	public List<EquipPara> getParaById(String equip_id) {
		List<EquipPara> equipParas = equipParaRepository.selectEquipParaById(Integer.parseInt(equip_id));
		for(int i=0;i<equipParas.size();i++){
			equipParas.get(i).setEquipment(null);
		}
		return equipParas;
	}

	@Override
	public List<EquipRoom> getRoomByProId(String proj_id) {
		List<EquipRoom> equipRoom = equipRoomRepository.getRoomByProId(Integer.parseInt(proj_id));
		return equipRoom;
	}

	@Override
	public List<Equipment> selectEquipByRoomMobile(List<EquipRoom> room) {
		List<Integer> ints = new ArrayList<Integer>();
		List<Equipment> list = null;
		if(room.size()>0){
			for(int i=0;i<room.size();i++){
				if(room.get(i).getEquip_room_id() != null){
					ints.add(room.get(i).getEquip_room_id());
				}
			}
			list = mobileDao.selectEquipByRoomMobile(ints);
		}

		return list;
	}

	//根据维保时间查找设备
	@Override
	public List<Map> selectEquipmentByN(String proj_id) {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
		String timenow = dateFormat.format(date);
		List<Object> list = equipmentDao.selectEquipmentByN(proj_id, timenow);
		Iterator<Object> it = list.iterator();
		List<Map> listGoal = listsourceToListGoalS(it);
		return listGoal;
	}

	//根据健康状态查找设备
	@Override
	public List<Map> selectEquipmentByS(String proj_id) {
		List<Object> list = equipmentDao.selectEquipmentByS(proj_id);
		Iterator<Object> it = list.iterator();
		List<Map> listGoal = listsourceToListGoalS(it);
		return listGoal;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> listsourceToListGoalS(Iterator<Object> it) {
		List<Map> listGoal = new ArrayList<Map>();

		Object[] obj;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			Map m1 = new HashMap();

			m1.put("equip_id", obj[0]);
			m1.put("equip_name", obj[1]);
			m1.put("equip_memo", obj[2]);
			listGoal.add(m1);
		}
		return listGoal;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> selectAlarmByA(String proj_id) {
		List<Object> list = alarmLogDao.selectAlarmByA(proj_id);
		Iterator<Object> it = list.iterator();
		List<Map> listGoal = listsourceToListGoalA(it);
		return listGoal;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> listsourceToListGoalA(Iterator<Object> it) {
		List<Map> listGoal = new ArrayList<Map>();

		Object[] obj;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			Map m1 = new HashMap();

			m1.put("alarm_log_id", obj[0]);
			m1.put("alarm_log_date", obj[1]);
			m1.put("alarm_log_info", obj[2]);
			m1.put("alarm_log_memo", obj[3]);
			m1.put("equip_name", obj[4]);
			listGoal.add(m1);
		}
		return listGoal;
	}

	@Override
	public List<Integer> selectAllNum(String proj_id) {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
		String timenow = dateFormat.format(date);
		Integer ndate = equipmentDao.getNdateNum(proj_id, timenow);
		Integer state = equipmentDao.getStateNum(proj_id);
		Integer alart = alarmLogDao.getAlarmNum(proj_id);
		List<Integer> list = new ArrayList<Integer>();
		list.add(alart);
		list.add(ndate);
		list.add(state);
		return list;
	}

}
