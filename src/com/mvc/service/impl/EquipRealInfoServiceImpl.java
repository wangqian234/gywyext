package com.mvc.service.impl;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.EquipRealInfoDao;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.Equipment;
import com.mvc.repository.EquipManuRepository;
import com.mvc.repository.EquipParaRepository;
import com.mvc.repository.EquipRoomRepository;
import com.mvc.repository.EquipTypeRepository;
import com.mvc.repository.EquipmentRepository;
import com.mvc.repository.ProjectRepository;
import com.mvc.repository.UserRepository;
import com.mvc.service.EquipRealInfoService;

import net.sf.json.JSONObject;

@Service("equipRealInfoServiceImpl")
public class EquipRealInfoServiceImpl implements EquipRealInfoService {
	@Autowired
	EquipmentRepository equipmentRepository;
	@Autowired
	EquipRoomRepository equipRoomRepository;
	@Autowired
	EquipTypeRepository equipTypeRepository;
	@Autowired
	EquipManuRepository equipManuRepository;
	@Autowired
	EquipParaRepository equipParaRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EquipRealInfoDao equipRealInfoDao;
	
	// 查询设备总条数
		@Override
		public Integer countEqTotal(String searchKey) {
			return equipRealInfoDao.countEqTotal(searchKey);
		}
	// 根据页数筛选全部设备信息列表
		@Override
		public List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end) {
			return equipRealInfoDao.selectEquipmentByPage(searchKey, offset, end);
		}
	//根据设备id查找设备特征参数
			@Override
			public List<EquipPara> getEquipPara(String searchKey) {
				List<EquipPara> list = equipRealInfoDao.getEquipPara(searchKey);
				for(int i=0;i<list.size();i++){
					list.get(i).setEquipment(null);
				}
				return equipRealInfoDao.getEquipPara(searchKey);
			}
	//根据设备参数名字查找设备特征参数信息
			@Override
			public List<EquipPara> getEquipParaByName(String searchKey) {
				List<EquipPara> list = equipRealInfoDao.getEquipParaByName(searchKey);
				for(int i=0;i<list.size();i++){
					list.get(i).setEquipment(null);
				}
				return equipRealInfoDao.getEquipParaByName(searchKey);
			}
	//根据特征参数id，获取设备实时数据
	@Override
	public List<EquipOper> getEquipRealData(String searchKey, String startDate) {
		List<EquipOper> data = equipRealInfoDao.getEquipRealData(searchKey,startDate);
		for(int i=0;i<data.size();i++){
			data.get(i).setEquip_para_id(null);
		}
		return equipRealInfoDao.getEquipRealData(searchKey,startDate);
	}
}
