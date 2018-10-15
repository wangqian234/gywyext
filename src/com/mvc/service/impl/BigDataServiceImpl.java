package com.mvc.service.impl;

import java.math.BigInteger;
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
import com.mvc.entityReport.AlarmLog;
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

	// 某个位置所有传感器的故障统计
	@Override
	public JSONArray failureAnalysis(Integer roomId) {
		// TODO Auto-generated method stub
		List<Equipment> list = equipmentDao.selectAllEquipByRoomId(roomId);
		JSONArray arr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Integer num = alarmLogDao.countEquipFailNumById(list.get(i).getEquip_id());
			JSONObject obj = new JSONObject();
			if (num == null) {
				num = 0;
			}
			obj.put("value", num);
			obj.put("name", list.get(i).getEquip_name());
			arr.add(obj);
		}
		return arr;
	}

	// 获取设备健康状况的分析
	@Override
	public JSONArray getEquipRadarById(Integer equipmentId) {
		// TODO Auto-generated method stub
		float reliability = 0;
		float health = 0;
		float residualLife = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		reliability = (float) ((1 - num * 0.001) * 0.98);//可靠性
		health = (float) ((1 - num * 0.001) * 0.95);//健康指数
		Equipment e = new Equipment();
		e = equipmentDao.selectEquipmentById(equipmentId);
		Date now = new Date();
		if (e.getEquip_life() == null) {
			e.setEquip_life(3600);
		}
		int lifeTime = e.getEquip_life();//标准寿命
		int month, monthyear, monthday;
		Date getDate = new Date();
		String nowDate = sdf.format(getDate);
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		try {
			bef.setTime(sdf.parse(nowDate));
			aft.setTime(sdf.parse(sdf.format(e.getEquip_udate())));// useDate
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (aft.get(Calendar.YEAR) < bef.get(Calendar.YEAR)) {
			monthyear = (bef.get(Calendar.YEAR) - aft.get(Calendar.YEAR)) * 12*30;
		} else {
			monthyear = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12*30;
		}
		month = (bef.get(Calendar.MONTH) - aft.get(Calendar.MONTH))*30;
		/*if (aft.get(Calendar.MONTH) < bef.get(Calendar.MONTH)) {
			month = (bef.get(Calendar.MONTH) - aft.get(Calendar.MONTH))*30;
		} else {
			month = (aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH))*30;
		}*/
		monthday = (bef.get(Calendar.DATE) - aft.get(Calendar.DATE)) / 1;
		/*if (aft.get(Calendar.DATE) < bef.get(Calendar.DATE)) {
			monthday = (bef.get(Calendar.DATE) - aft.get(Calendar.DATE)) / 1;
		} else {
			monthday = (aft.get(Calendar.DATE) - bef.get(Calendar.DATE)) / 1;
		}*/
		int useTime = month + monthyear + monthday;
		
		//BigInteger usedDate = now.getTime() - e.getEquip_udate().getTime();//已使用时间
		residualLife = (lifeTime - useTime * reliability) / lifeTime;
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

	// 设备故障预测
	@Override
	public JSONArray getEquipPreById(Integer equipmentId) {
		// TODO Auto-generated method stub
		EquipMain em = new EquipMain();
		Equipment e=new Equipment();
		e=equipmentDao.selectEquipmentById(equipmentId);
		List<EquipMain> emList = equipMainDao.selectListByEquipId(equipmentId);
		Date dt = new Date();
		if (emList.size() == 0) {
			dt = null;
		} else {
			dt = emList.get(0).getEquip_main_date();
		}
		Integer num = alarmLogDao.countEquipFailNumByIdAndDate(equipmentId, dt);
		if (num == null || num == 0) {
			num = 1;
		}

		float dd = (float) (2000.00 / num);
		long intervalTime = (long) (dd * 60 * 60* 1000);
		long nextMainDate;
		if (dt == null) {
			nextMainDate = e.getEquip_udate().getTime() + intervalTime;
		} else {
			nextMainDate = dt.getTime() + intervalTime;
		}
		Date preDate = new Date(nextMainDate);
		
		Date now=new Date();
		
		if(preDate.getTime()>now.getTime()){
			System.out.println("大于当前时间");
		}else{
			System.out.println("小于当前时间，进行整改");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
			preDate=calendar.getTime();
		}
		JSONArray arr = new JSONArray();
		arr.add(dt);
		arr.add(preDate);
		arr.add(e.getEquip_udate());
		return arr;
	}

	// 设备故障按月统计
	@Override
	public JSONObject getEquipFailCountById(Integer equipmentId) {
		// TODO Auto-generated method stub
		JSONObject o = new JSONObject();
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		List<Object> listSource = alarmLogDao.getEquipFailCountById(equipmentId, year);
		Iterator<Object> it = listSource.iterator();
		JSONArray xArr = new JSONArray();
		JSONArray dataArr = new JSONArray();
		Object[] obj = null;
		while (it.hasNext()) {
			obj = (Object[]) it.next();
			xArr.add(obj[0]);
			dataArr.add(obj[1]);
		}
		o.put("xAxis", xArr);
		o.put("data", dataArr);
		return o;
	}

	@Override
	public JSONArray getEquipFailById(String equip_id) {
		JSONArray arr = new JSONArray();
		List <AlarmLog> list = alarmLogDao.getAlarmListByEquipId(equip_id);
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		int num4 = 0;

		for(int i=0;i<list.size();i++){
			switch (list.get(i).getAlarm_log_code()) {
			case "001":
				num1++;
				break;
			case "002":
				num2++;
				break;
			case "003":
				num3++;
				break;
			case "004":
				num4++;
				break;
			default:
				break;
			}
		}
		JSONObject obj1 = new JSONObject();
		obj1.put("value", num1);
		obj1.put("name", "高于上限值");
		arr.add(obj1);
		JSONObject obj2 = new JSONObject();
		obj2.put("value", num2);
		obj2.put("name", "低于下限值");
		arr.add(obj2);
		JSONObject obj3 = new JSONObject();
		obj3.put("value", num3);
		obj3.put("name", "设备损坏");
		arr.add(obj3);
		JSONObject obj4 = new JSONObject();
		obj4.put("value", num4);
		obj4.put("name", "其他");
		arr.add(obj4);
		
		return arr;
	}

}
