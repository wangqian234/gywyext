package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.EquipType;

public interface EquipTypeRepository extends JpaRepository<EquipType, Integer> {	

	//根据ID获取分类信息
	@Query("select et from EquipType et where equip_type_id=:equip_type_id ")
	public EquipType selectEquipTypeById(@Param("equip_type_id") Integer equip_type_id);

	//获取设备分类信息
	@Query("select et from EquipType et where equip_type_isdeleted=0")
	List< EquipType> getEquipTypeInfo();
}