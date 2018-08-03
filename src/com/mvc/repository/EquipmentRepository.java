package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
	
	// 根据ID查询设备信息
	@Query("select tr from Equipment tr where equip_id = :equip_id")
	public Equipment findEquipmentById(@Param("equip_id") Integer equip_id);

	// 根据ID查询全部设备信息
	@Query("select tr from Equipment tr where equip_isdeleted=0 ")
	public List<Equipment> findEquipmentAlls();

	// 根据id删除
	@Query("update Equipment set equip_isdeleted=1 where equip_id = :equip_id")
	public boolean deleteByEquipmentId(@Param("equip_id") Integer equip_id);

	//根据ID获取旅游信息
	@Query("select tr from Equipment tr where equip_id=:equip_id ")
	public Equipment selectEquipmentById(@Param("equip_id") Integer equip_id);
}