package com.mvc.dao;

import java.util.List;

import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.Equipment;

public interface EquipMainDao {

	Integer countEquipFailNumById(Integer equipId);
	
	List<EquipMain> selectListByEquipId(Integer equipmentId);

	List<Equipment> selectEquipByRoom(List<Integer> roomId);
	
	Integer countEmTotal(String eid,String emr,String proj_id);
	
	List<EquipMain> selectEquipMainListByIR(String eid, String emr, String proj_id ,Integer offset, Integer end);
	
	
}