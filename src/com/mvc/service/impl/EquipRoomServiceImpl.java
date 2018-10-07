package com.mvc.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.mvc.dao.EquipRoomDao;
import com.mvc.dao.EquipmentDao;
import com.mvc.entityReport.User;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Files;
import com.mvc.service.EquipRoomService;
import com.mvc.service.EquipmentService;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipMain;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("equipRoomServiceImpl")
public class EquipRoomServiceImpl implements EquipRoomService {

	@Autowired
	EquipRoomRepository equipRoomRepository;

	
	@Autowired
	EquipRoomDao equipRoomDao;


	@Override
	public List<EquipRoom> selectEquipRoomListByproId(Integer proId) {
		// TODO Auto-generated method stub
		return equipRoomRepository.selectEquipRoomList(proId);
	}

	
}
