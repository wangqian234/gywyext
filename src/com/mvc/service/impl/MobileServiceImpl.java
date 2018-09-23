package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipOper;
import com.mvc.repository.AlarmLogRepository;
import com.mvc.repository.EquipOperRepository;
import com.mvc.service.MobileService;

@Service("mobileServiceImpl")
public class MobileServiceImpl implements MobileService {

	@Autowired
	AlarmLogRepository alarmLogRepository;
	@Autowired
	EquipOperRepository equipOperRepository;
	
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

}
