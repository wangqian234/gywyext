package com.mvc.entityReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "equip_oper")
public class EquipOper {
	private Integer equip_oper_id;// 设备运行状态编号，主键
	private Integer equip_para_id;// 设备编号，外键
	private String equip_oper_info;// 运行状态信息
	private String equip_oper_time;// 运行状态数据时间
	private Integer equip_oper_flag;//标志位用于判断是否被报警程序轮询

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "equip_oper_id", unique = true, nullable = false, length = 11)
	public Integer getEquip_oper_id() {
		return equip_oper_id;
	}

	public void setEquip_oper_id(Integer equip_oper_id) {
		this.equip_oper_id = equip_oper_id;
	}

	public String getEquip_oper_info() {
		return equip_oper_info;
	}

	public void setEquip_oper_info(String equip_oper_info) {
		this.equip_oper_info = equip_oper_info;
	}

	public String getEquip_oper_time() {
		return equip_oper_time;
	}

	public void setEquip_oper_time(String equip_oper_time) {
		this.equip_oper_time = equip_oper_time;
	}

	public Integer getEquip_para_id() {
		return equip_para_id;
	}

	public void setEquip_para_id(Integer equip_para_id) {
		this.equip_para_id = equip_para_id;
	}

	public Integer getEquip_oper_flag() {
		return equip_oper_flag;
	}

	public void setEquip_oper_flag(Integer equip_oper_flag) {
		this.equip_oper_flag = equip_oper_flag;
	}


}
