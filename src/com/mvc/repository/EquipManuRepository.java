package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.EquipManu;

public interface EquipManuRepository extends JpaRepository<EquipManu, Integer> {	

	//根据ID获取旅游信息
	@Query("select em from EquipManu em where equip_manu_id=:equip_manu_id ")
	public EquipManu selectEquipManuById(@Param("equip_manu_id") Integer equip_manu_id);

	//获取生产公司信息
	@Query("select em from EquipManu em where equip_manu_isdeleted=0")
	public List< EquipManu> getEquipManuInfo();
}