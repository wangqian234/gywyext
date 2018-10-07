package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

	// 根据ID获取设备信息
	@Query("select tr from Equipment tr where equip_id=:equip_id ")
	public Equipment selectEquipmentById(@Param("equip_id") Integer equip_id);

	// 根据位置获取设备信息
	@Query("select count(tr) from Equipment tr where equip_room=:equip_room ")
	public Integer selectEquipNumByRoomId(@Param("equip_room") Integer equip_room);

	

}