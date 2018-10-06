package com.mvc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.repository.EquipmentRepository;
import com.mvc.repository.EquipRoomRepository;
import com.mvc.repository.EquipTypeRepository;
import com.mvc.repository.EquipManuRepository;
import com.mvc.repository.EquipParaRepository;
import com.mvc.repository.UserRepository;
import com.mvc.repository.ProjectRepository;
import com.mvc.dao.AlarmLogDao;
import com.mvc.dao.EquipMainDao;
import com.mvc.dao.EquipmentDao;
import com.mvc.entityReport.User;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Files;
import com.mvc.service.BigDataService;
import com.mvc.service.EquipmentService;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipMain;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("bigDataServiceImpl")
public class BigDataServiceImpl implements BigDataService {
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
	EquipmentDao equipmentDao;

	@Autowired
	EquipMainDao equipMainDao;
	@Autowired
	AlarmLogDao alarmLogDao;

	@Override
	public Integer selectEquipNumByRoomId(Integer roomId) {
		// TODO Auto-generated method stub
		return equipmentDao.selectEquipNumByRoomId(roomId);
	}

	@Override
	public List<Equipment> findEquipListByRoomId(Integer roomId, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return equipmentDao.findEquipListByRoomId(roomId, offset, limit);
	}

	@Override
	public JSONArray failureAnalysis(Integer roomId) {
		// TODO Auto-generated method stub
		List<Equipment> list = equipmentDao.selectAllEquipByRoomId(roomId);
		JSONArray arr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Integer num = alarmLogDao.countEquipFailNumById(list.get(i).getEquip_id());
			JSONObject obj = new JSONObject();
			obj.put("value", num);
			obj.put("name", list.get(i).getEquip_name());
			arr.add(obj);
		}
		return arr;
	}

	@Override
	public JSONArray getEquipRadarById(Integer equipmentId) {
		// TODO Auto-generated method stub
		float reliability = 0;
		float health = 0;
		float residualLife = 0;
		EquipMain em = new EquipMain();
		List<EquipMain> emList = equipMainDao.selectListByEquipId(equipmentId);
		Date dt = new Date();
		if (emList.size() == 0) {
			dt = null;
		} else {
			dt = emList.get(0).getEquip_main_date();
		}
		Integer num = alarmLogDao.countEquipFailNumByIdAndDate(equipmentId, dt);
		if (num == null) {
			num = 0;
		}
		reliability = (float) ((1 - num * 0.001) * 0.98);
		health = (float) ((1 - num * 0.001) * 0.95);
		Equipment e = new Equipment();
		e = equipmentDao.selectEquipmentById(equipmentId);
		Date now = new Date();
		if (e.getEquip_life() == null) {
			e.setEquip_life(0);
		}
		long standardDate = e.getEquip_life() * 24 * 60 * 60 * 1000;
		long usedDate = now.getTime() - e.getEquip_udate().getTime();
		long idle = e.getEquip_udate().getTime() - e.getEquip_pdate().getTime();
		residualLife = (standardDate - usedDate * reliability) / (standardDate - idle);
		JSONArray arr = new JSONArray();
		JSONObject o1 = new JSONObject();
		/*
		 * o1.put("name", "可靠度"); o1.put("value", reliability); JSONObject
		 * o2=new JSONObject(); o2.put("name", "健康指数"); o2.put("value", health);
		 * JSONObject o3=new JSONObject(); o3.put("name", "剩余寿命");
		 * o3.put("value", residualLife);
		 */
		arr.add(reliability);
		arr.add(health);
		arr.add(residualLife);
		return arr;
	}

	@Override
	public JSONArray getEquipPreById(Integer equipmentId) {
		// TODO Auto-generated method stub
		EquipMain em = new EquipMain();
		List<EquipMain> emList = equipMainDao.selectListByEquipId(equipmentId);
		Date dt = new Date();
		if (emList.size() == 0) {
			dt = null;
		} else {
			dt = emList.get(0).getEquip_main_date();
		}
		Integer num = alarmLogDao.countEquipFailNumByIdAndDate(equipmentId, dt);
		if (num == null) {
			num = 0;
		}

		if (num == 0) {
			num = 1;
		}
		float dd =(float) (3000.00 / num);
		long intervalTime = (long) (dd * 60 * 1000);
		long nextMainDate;
		Equipment e = new Equipment();
		e = equipmentDao.selectEquipmentById(equipmentId);
		if (dt == null) {
			nextMainDate = e.getEquip_udate().getTime()+intervalTime;
		} else {
			nextMainDate = dt.getTime() + intervalTime;
		}
		Date preDate = new Date(nextMainDate);
		JSONArray arr=new JSONArray();
		arr.add(dt);
		arr.add(preDate);
		return arr;
	}

	@Override
	public JSONObject getEquipFailCountById(Integer equipmentId) {
		// TODO Auto-generated method stub
		JSONObject o=new JSONObject();
		 Calendar date = Calendar.getInstance();
	     String year = String.valueOf(date.get(Calendar.YEAR));
		List<Object> listSource=alarmLogDao.getEquipFailCountById(equipmentId,year);
		Iterator<Object> it = listSource.iterator();
		JSONArray xArr=new JSONArray();
		JSONArray dataArr=new JSONArray();
		Object[] obj = null;
		while(it.hasNext()){
			obj=(Object[]) it.next();
			xArr.add(obj[0]);
			dataArr.add(obj[1]);
		}
		o.put("xAxis", xArr);
		o.put("data", dataArr);
		return o;
	}

}
