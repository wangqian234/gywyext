package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.EquipPara;

public interface EquipParaRepository extends JpaRepository<EquipPara, Integer> {	

	//根据ID获取安装位置信息
	@Query("select ep from EquipPara ep where equip_para_id=:equip_para_id ")
	public EquipPara selectEquipParaById(@Param("equip_para_id") Integer equip_para_id);

	//获取安装位置信息
	@Query("select ep from EquipPara ep where equip_para_isdeleted=0")
	List< EquipPara> getEquipParaInfo();

}