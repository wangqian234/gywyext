package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipOper;
import com.mvc.repository.AlarmLogRepository;
import com.mvc.repository.EquipMainRepository;
import com.mvc.repository.EquipOperRepository;
import com.mvc.service.MobileService;

@Service("mobileServiceImpl")
public class MobileServiceImpl implements MobileService {

	@Autowired
	AlarmLogRepository alarmLogRepository;
	@Autowired
	EquipOperRepository equipOperRepository;
	@Autowired
	EquipMainRepository equipMainRepository;
	
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

}
