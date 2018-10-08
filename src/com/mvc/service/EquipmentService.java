package com.mvc.service;

import java.text.ParseException;
import java.util.List;

import com.mvc.entityReport.User;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Project;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.EquipManu;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipMain;

import net.sf.json.JSONObject;

public interface EquipmentService {

	// 根据id删除
	boolean deleteIsdelete(Integer equip_id);

	// 根据room，state筛选信息
	List<Equipment> selectEquipmentByRS(List<EquipRoom> room, String eqRoom, String eqState, String searchKey,Integer offset, Integer end);
	// 查询设备总条数
	Integer countEqTotal(List<EquipRoom> room,String eqRoom,String eqState,String searchKey);
		
	// 根据页数筛选全部设备安装位置列表
	List<EquipRoom> selectEquipRoomByProj(String searchKey);
	//根据Room获取设备
	List<Equipment> selectEquipByRoom(List<EquipRoom> room, int i, int j);
	
	// 添加设备信息
	Equipment save(Equipment equipment);
	
	// 修改设备基本信息
	boolean updateEquipmentBase(Integer equip_id, JSONObject jsonObject) throws ParseException;
 	// 根据ID获取设备信息
	Equipment selectEquipmentById(Integer equip_id);

	//获取设备分类信息
	List<EquipType> getEquipTypeInfo();

	//获取设备分类信息
	List<User> getUserInfo();

	//根据设备id查找设备特征参数
	List<EquipPara> getEquipPara(String searchKey);
	void saveParas(List<EquipPara> equipParas);

}
