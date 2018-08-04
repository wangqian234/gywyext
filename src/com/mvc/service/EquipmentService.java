package com.mvc.service;

import java.text.ParseException;
import java.util.List;

import com.mvc.entityReport.User;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Project;

import net.sf.json.JSONObject;

public interface EquipmentService {

	// 根据id删除
	boolean deleteIsdelete(Integer equip_id);
	
	/*//根据限制条件筛选信息
	Integer countTotal(String eqType, String eqState);
	List<Equipment> findEquipmentByPage(String eqType, String eqState, Integer offset, Integer limit);*/
	
	// 查询设备总条数
	Integer countEqTotal(String searchKey);
	// 根据页数筛选全部设备信息列表
	List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end);

	// 根据页数筛选全部设备安装位置列表
	List<EquipRoom> selectEquipRoomByPage(String searchKey);
	
	// 查询项目总条数
	Integer countProjTotal(String searchKey);
	// 根据页数筛选全部项目信息列表
	List<Project> selectProjectByPage(String searchKey, Integer offset, Integer end);
	
	// 添加设备信息
	boolean save(Equipment equipment);
	
	// 修改设备基本信息
	boolean updateEquipmentBase(Integer equip_id, JSONObject jsonObject, User user) throws ParseException;
	
	// 根据ID获取设备信息
	Equipment selectEquipmentById(Integer equip_id);

	//根据Room获取设备
	List<Equipment> selectEquipByRoom(List<EquipRoom> room, int i, int j);

}
