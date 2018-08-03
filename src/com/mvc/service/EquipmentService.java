package com.mvc.service;

import java.text.ParseException;
import java.util.List;

import com.mvc.entityReport.User;
import com.mvc.entityReport.Equipment;

import net.sf.json.JSONObject;

public interface EquipmentService {

	// 根据id删除
	boolean deleteIsdelete(Integer equip_id);
	
	//根据限制条件筛选信息
	Integer countTotal(String eqType, String eqState);
	List<Equipment> findEquipmentByPage(String eqType, String eqState, Integer offset, Integer limit);
	
	// 查询设备总条数
	Integer countEqTotal(String searchKey);
	// 根据页数筛选全部设备信息列表
	List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end);

	// 添加设备信息
	boolean save(Equipment equipment);
	
	// 修改设备基本信息
	boolean updateEquipmentBase(Integer equip_id, JSONObject jsonObject, User user) throws ParseException;
	
	// 根据ID获取设备信息
	Equipment selectEquipmentById(Integer equip_id);

}
