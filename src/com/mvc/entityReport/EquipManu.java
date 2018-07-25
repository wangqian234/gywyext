package com.mvc.entityReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="equip_manu")
public class EquipManu {
	private Integer equip_manu_id;//设备制造商编号，主键
	private String equip_manu_name;//设备制造商名称
	private Integer equip_manu_tel;//设备制造商联系方式
	private String equip_manu_addr;//设备制造商地址
	private String equip_manu_memo;//设备制造商备注
	private Integer equip_manu_isdeleted;//设备制造商是否删除 0：存在 1：已删除
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "equip_manu_id",unique=true,nullable=false, length = 11)
	public Integer getEquip_manu_id() {
		return equip_manu_id;
	}
	public void setEquip_manu_id(Integer equip_manu_id) {
		this.equip_manu_id = equip_manu_id;
	}
	
	@Column(name = "equip_manu_name", length = 64)
	public String getEquip_manu_name() {
		return equip_manu_name;
	}
	public void setEquip_manu_name(String equip_manu_name) {
		this.equip_manu_name = equip_manu_name;
	}
	
	@Column(name = "equip_manu_tel", length = 11)
	public Integer getEquip_manu_tel() {
		return equip_manu_tel;
	}
	public void setEquip_manu_tel(Integer equip_manu_tel) {
		this.equip_manu_tel = equip_manu_tel;
	}
	
	@Column(name = "equip_manu_addr", length = 255)
	public String getEquip_manu_addr() {
		return equip_manu_addr;
	}
	public void setEquip_manu_addr(String equip_manu_addr) {
		this.equip_manu_addr = equip_manu_addr;
	}
	
	@Column(name = "equip_manu_memo", length = 255)
	public String getEquip_manu_memo() {
		return equip_manu_memo;
	}
	public void setEquip_manu_memo(String equip_manu_memo) {
		this.equip_manu_memo = equip_manu_memo;
	}
	
	@Column(name = "equip_manu_isdeleted", length = 1)
	public Integer getEquip_manu_isdeleted() {
		return equip_manu_isdeleted;
	}
	public void setEquip_manu_isdeleted(Integer equip_manu_isdeleted) {
		this.equip_manu_isdeleted = equip_manu_isdeleted;
	}
	
}