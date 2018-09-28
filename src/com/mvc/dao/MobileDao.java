package com.mvc.dao;

import java.util.List;

import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;

public interface MobileDao {

	List<Equipment> selectEquipByRoomMobile(List<Integer> room);

}