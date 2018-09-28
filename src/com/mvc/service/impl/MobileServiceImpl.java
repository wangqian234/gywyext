package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
				int a = room.get(i).getEquip_room_id();
				if(room.get(i).getEquip_room_id() != null){
					ints.add(room.get(i).getEquip_room_id());
				}
			}
			list = mobileDao.selectEquipByRoomMobile(ints);
		}

		return list;
	}

}
