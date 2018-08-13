package com.mvc.entityReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="equip_para")
public class EquipPara {
	private Integer equip_para_id;//特征参数编号，主键
	private Equipment equipment;//设备编号，外键
	private String equip_para_name;//特征参数名称
	private String equip_para_unit;//特征参数单位
	private String equip_para_rate;//特征参数额定值
	private Float equip_para_max;//特征参数报警最大值
	private Float equip_para_min;//特征参数报警最小值
	private String equip_para_memo;//特征参数备注
	private Integer equip_para_isdeleted;//特征参数是否删除 0：存在 1：已删除
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "equip_para_id",unique=true,nullable=false, length = 11)
	public Integer getEquip_para_id() {
		return equip_para_id;
	}
	public void setEquip_para_id(Integer equip_para_id) {
		this.equip_para_id = equip_para_id;
	}
	
	@ManyToOne
	@JoinColumn(name="equip_id")
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	@Column(name = "equip_para_name", length = 64)
	public String getEquip_para_name() {
		return equip_para_name;
	}
	public void setEquip_para_name(String equip_para_name) {
		this.equip_para_name = equip_para_name;
	}
	
	@Column(name = "equip_para_unit", length = 16)
	public String getEquip_para_unit() {
		return equip_para_unit;
	}
	public void setEquip_para_unit(String equip_para_unit) {
		this.equip_para_unit = equip_para_unit;
	}
	
	@Column(name = "equip_para_rate", length = 32)
	public String getEquip_para_rate() {
		return equip_para_rate;
	}
	public void setEquip_para_rate(String equip_para_rate) {
		this.equip_para_rate = equip_para_rate;
	}
	
	@Column(name = "equip_para_max", length = 16)
	public Float getEquip_para_max() {
		return equip_para_max;
	}
	public void setEquip_para_max(Float equip_para_max) {
		this.equip_para_max = equip_para_max;
	}
	
	@Column(name = "equip_para_min", length = 16)
	public Float getEquip_para_min() {
		return equip_para_min;
	}
	public void setEquip_para_min(Float equip_para_min) {
		this.equip_para_min = equip_para_min;
	}
	
	@Column(name = "equip_para_memo", length = 255)
	public String getEquip_para_memo() {
		return equip_para_memo;
	}
	public void setEquip_para_memo(String equip_para_memo) {
		this.equip_para_memo = equip_para_memo;
	}
	
	@Column(name = "equip_para_isdeleted", length = 1,columnDefinition = "INT not null default 0")
	public Integer getEquip_para_isdeleted() {
		return equip_para_isdeleted;
	}
	public void setEquip_para_isdeleted(Integer equip_para_isdeleted) {
		this.equip_para_isdeleted = equip_para_isdeleted;
	}
	
}