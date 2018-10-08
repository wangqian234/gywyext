package com.mvc.service;

import java.util.List;

import com.mvc.entityReport.EquipRoom;

public interface EquipRoomService {

	List<EquipRoom> selectEquipRoomListByproId(Integer proId);

}
