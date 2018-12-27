package com.mvc.service;

import java.util.List;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipMain;

public interface EquipMainService {

	List<Equipment> selectEquipByRoom(List<EquipRoom> room);

	Integer countEmTotal(String eid,String emr,String proj_id);
	
	List<EquipMain> selectEquipMainListByIR(String eid, String emr, String proj_id ,Integer offset, Integer end);
}
