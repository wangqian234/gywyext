package com.mvc.dao;

import java.util.List;

import com.mvc.entityReport.EquipMain;

public interface EquipMainDao {

	Integer countEquipFailNumById(Integer equipId);
	
	List<EquipMain> selectListByEquipId(Integer equipmentId);
}