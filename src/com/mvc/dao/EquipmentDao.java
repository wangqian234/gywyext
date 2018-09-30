package com.mvc.dao;

import java.util.List;

import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipPara;

public interface EquipmentDao {

	//删除设备信息
	Boolean updateState(Integer equip_id);

	//根据room，state筛选信息
	List<Equipment> selectEquipmentByRS(List<Integer> roomId ,String eqRoom, String eqState,String searchKey,Integer offset, Integer end);

	// 查询设备总条数
	Integer countEqTotal(List<Integer> roomId,String eqRoom,String eqState,String searchKey);
	
	// 根据页数筛选全部安装位置信息列表
	List<EquipRoom> selectEquipRoomByProj(String searchKey);

	//根据room查找设备
	List<Equipment> selectEquipByRoom(List<Integer> roomId, int offset, int end);

	//根据设备id查找设备特征参数
	List<EquipPara> getEquipPara(String searchKey);

}