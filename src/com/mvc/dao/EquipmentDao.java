package com.mvc.dao;

import java.util.Date;
import java.util.List;

import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;

public interface EquipmentDao {

	// 删除设备信息
	Boolean updateState(Integer equip_id);

	//根据room，state筛选信息
	List<Equipment> selectEquipmentByRS(List<Integer> roomId ,String eqRoom, String eqState,String searchKey,Integer offset, Integer end);

	// 查询设备总条数
	Integer countEqTotal(List<Integer> roomId,String eqRoom,String eqState,String searchKey);
	
	// 根据页数筛选全部安装位置信息列表
	List<EquipRoom> selectEquipRoomByProj(String searchKey);

	// 根据room查找设备
	List<Equipment> selectEquipByRoom(List<Integer> roomId, int offset, int end);


	/*
	 * 设备维保信息 // 查询设备信息总条数 Integer countEmTotal(String searchKey); //
	 * 根据页数筛选全部设备信息列表 List<EquipMain> selectEquipMainByPage(String searchKey,
	 * Integer offset, Integer end);
	 */

	// 根据设备id查找设备特征参数
	List<EquipPara> getEquipPara(String searchKey);
	
	//根据设备参数id查询设备参数实时数据
	List<EquipOper> getEquipRealData(String searchKey, String startDate);

	// zq
	List<Equipment> findEquipListByRoomId(Integer roomId, Integer offset, Integer limit);

	Integer selectEquipNumByRoomId(Integer roomId);
	
	List<Equipment> selectAllEquipByRoomId(Integer roomId);
	
	Equipment selectEquipmentById(Integer equipmentId);
	
	Integer getEquipMainNumByProId(Integer proId,Date updateDate);
	
	Integer getEquipUnhealthNumByProId(Integer proId);

	//根据维保时间查找设备
	List<Object> selectEquipmentByN(String proj_id, String timenow);

	//根据健康状态查找设备
	List<Object> selectEquipmentByS(String proj_id);

	Integer getNdateNum(String proj_id, String timenow);

	Integer getStateNum(String proj_id);
	
	List<Equipment> selectIndexMainEquipList(Integer proId,Integer offset,Integer limit,Date updateDate);
	
	List<Equipment> selectIndexUnhealthEquip(Integer proId,Integer offset,Integer limit);

	List<Equipment> selectAllEquipByProId(String proj_id);

}