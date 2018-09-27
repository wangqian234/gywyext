package com.mvc.service;

import java.text.ParseException;
import java.util.List;

import com.mvc.entityReport.User;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipRoom;

import com.mvc.entityReport.EquipType;

import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipMain;

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
	List<EquipRoom> selectEquipRoomByProj(String searchKey);
	//根据Room获取设备
	List<Equipment> selectEquipByRoom(List<EquipRoom> room, int i, int j);
	
	// 添加设备信息
	Equipment save(Equipment equipment);
	
	// 修改设备基本信息
	boolean updateEquipmentBase(Integer equip_id, JSONObject jsonObject, User user) throws ParseException;
 	// 根据ID获取设备信息
	Equipment selectEquipmentById(Integer equip_id);
	
	// 添加设备安装位置信息
	boolean save(EquipRoom equip_room);
	
/*	// 添加设备分类信息
	boolean save(EquipType equip_type);*/

/*	// 添加设备制造商信息
	boolean save(EquipManu equip_manu);*/

	//获取设备分类信息
	List<EquipType> getEquipTypeInfo();

/*	//获取设备制造商信息
	List<EquipManu> getEquipManuInfo();
*/
    //添加设备特征参数信息
	boolean save(EquipPara equip_para);

	// 根据ID获取用户信息
	//User selectUserById(Integer user_id);

 	// 根据ID获取设备特征参数信息
	//EquipPara selectEquipParaById(Integer equip_para_id);

	// 查询维保信息总条数
	Integer countEmTotal(String searchKey);
	// 根据页数筛选全部设备维保信息列表
	List<EquipMain> selectEquipMainByPage(String searchKey, Integer offset, Integer end);

	//根据设备id查找设备特征参数
	List<EquipPara> getEquipPara(String searchKey);

	void saveParas(List<EquipPara> equipParas);




}
