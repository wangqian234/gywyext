package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.EquipMain;


public interface EquipMainRepository extends JpaRepository<EquipMain, Integer> {

	@Query("select c from EquipMain c ")
	public List<EquipMain> getmaintenance();

	@Query("select c from EquipMain c where equip_id = :equip_id")
	public List<EquipMain> getMaintenanceById(@Param("equip_id") String equip_id);
}
