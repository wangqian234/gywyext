package com.mvc.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mvc.entityReport.Equipment;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface BigDataService {

	// zq
	Integer selectEquipNumByRoomId(Integer roomId);
	
	List<Equipment> findEquipListByRoomId(Integer roomId,Integer offset,Integer limit);

	JSONArray failureAnalysis(Integer roomId);
	
	JSONArray getEquipRadarById(Integer equipmentId);
	
	JSONArray getEquipPreById(Integer equipmentId);
	
	JSONObject getEquipFailCountById(Integer equipmentId);
}
