package com.mvc.entityReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="equip_type")
public class EquipType {
	private Integer equip_type_id;//设备分类编号，主键
	private String equip_type_name;//设备类型名称
	private String equip_type_memo;//设备类型备注
	private Integer equip_type_isdeleted;//设备类型是否删除 0：存在 1：已删除
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "equip_type_id",unique=true,nullable=false, length = 11)
	public Integer getEquip_type_id() {
		return equip_type_id;
	}
	public void setEquip_type_id(Integer equip_type_id) {
		this.equip_type_id = equip_type_id;
	}
	
	@Column(name = "equip_type_name", length = 32)
	public String getEquip_type_name() {
		return equip_type_name;
	}
	public void setEquip_type_name(String equip_type_name) {
		this.equip_type_name = equip_type_name;
	}
	
	@Column(name = "equip_type_memo", length = 255)
	public String getEquip_type_memo() {
		return equip_type_memo;
	}
	public void setEquip_type_memo(String equip_type_memo) {
		this.equip_type_memo = equip_type_memo;
	}
	
	@Column(name = "equip_type_isdeleted", length = 1)
	public Integer getEquip_type_isdeleted() {
		return equip_type_isdeleted;
	}
	public void setEquip_type_isdeleted(Integer equip_type_isdeleted) {
		this.equip_type_isdeleted = equip_type_isdeleted;
	}
	
	
	}
	
