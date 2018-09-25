package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mvc.entityReport.EquipMain;


public interface EquipMainRepository extends JpaRepository<EquipMain, Integer> {

	@Query("select c from EquipMain c ")
	public List<EquipMain> getmaintenance();
}
