package com.mvc.dao;

import java.util.List;

import com.mvc.entityReport.Equipment;

public interface EquipmentDao {

	//删除设备信息
	Boolean updateState(Integer equip_id);
	//根据限制条件筛选信息
	List<Equipment> findEquipmentByPage(String eqType, String eqState, Integer offset, Integer limit);
	Integer countTotal(String eqType, String eqState);
	
	// 查询设备信息总条数
	Integer countEqTotal(String searchKey);
	// 根据页数筛选全部设备信息列表
	List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end);








}