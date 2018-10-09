package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.EquipPara;

public interface EquipParaRepository extends JpaRepository<EquipPara, Integer> {	

	//根据ID获取特征参数信息
	@Query("select ep from EquipPara ep where equip_id=:equip_id ")
	public List<EquipPara> selectEquipParaById(@Param("equip_id") Integer equip_id);

	//获取特征参数信息
	@Query("select ep from EquipPara ep where equip_para_isdeleted=0")
	List<EquipPara> getEquipParaInfo();

}