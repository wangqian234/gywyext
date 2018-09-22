package com.mvc.service;

import java.util.List;

import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.Equipment;

public interface EquipRealInfoService {

	// 查询设备总条数
	Integer countEqTotal(String searchKey);

	// 根据页数筛选全部设备信息列表
	List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end);

	//根据设备id查找设备特征参数
	List<EquipPara> getEquipPara(String searchKey);

	//根据设备参数id查询设备参数实时数据
	List<EquipOper> getEquipRealData(String searchKey, String start);

	List<EquipPara> getEquipParaByName(String searchKey);

	/*Integer countEDTotal(String searchKey);*/

}
