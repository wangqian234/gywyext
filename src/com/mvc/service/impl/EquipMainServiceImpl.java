package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.EquipMainDao;
import com.mvc.service.EquipMainService;

import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipMain;

@Service("EquipMainServiceImpl")
public class EquipMainServiceImpl implements EquipMainService {

	@Autowired
	EquipMainDao equipMainDao;
	
	@Override
	public List<Equipment> selectEquipByRoom(List<EquipRoom> room) {
		List<Integer> roomId = new ArrayList();
		for(int k=0;k<room.size();k++){
			System.out.println(room.get(k).getEquip_room_id());
			roomId.add(room.get(k).getEquip_room_id());
		}
		List<Equipment> list = equipMainDao.selectEquipByRoom(roomId);	
		return list;
	}

	@Override
	public Integer countEmTotal(String eid,String emr,String proj_id) {
		return equipMainDao.countEmTotal(eid,emr,proj_id);
	}
	
	@Override
	public List<EquipMain> selectEquipMainListByIR(String eid, String emr, String proj_id ,Integer offset, Integer end) {
		return equipMainDao.selectEquipMainListByIR(eid,emr,proj_id,offset,end);
	}
}
