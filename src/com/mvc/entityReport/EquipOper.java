package com.mvc.entityReport;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "equip_oper")
public class EquipOper {
	private Integer equip_oper_id;// 设备运行状态编号，主键
	private Equipment equipment;// 设备编号，外键
	private String equip_oper_info;// 运行状态信息
	private Date equip_oper_time;// 运行状态数据时间

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "equip_oper_id", unique = true, nullable = false, length = 11)
	public Integer getEquip_oper_id() {
		return equip_oper_id;
	}

	public void setEquip_oper_id(Integer equip_oper_id) {
		this.equip_oper_id = equip_oper_id;
	}

	@ManyToOne
	@JoinColumn(name = "equip_id")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@Column(name = "equip_oper_info", length = 255)
	public String getEquip_oper_info() {
		return equip_oper_info;
	}

	public void setEquip_oper_info(String equip_oper_info) {
		this.equip_oper_info = equip_oper_info;
	}

	@Column(name = "equip_oper_time")
	public Date getEquip_oper_time() {
		return equip_oper_time;
	}

	public void setEquip_oper_time(Date equip_oper_time) {
		this.equip_oper_time = equip_oper_time;
	}

}
