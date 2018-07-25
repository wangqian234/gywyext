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
@Table(name = "equip_main")
public class EquipMain {
	private Integer equip_main_id;// 设备维保信息编号，主键
	private Equipment equipment;// 设备编号，外键
	private String equip_main_info;// 维保内容
	private Date equip_main_date;// 维保时间
	private Integer equip_main_time;// 维保用时
	private String equip_main_worker;// 维保人员
	private Integer equip_main_result;// 维保结果；0：正常；1：维保失败，需要更换
	private String equip_main_memo;// 维保备注

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "equip_main_id", unique = true, nullable = false, length = 11)
	public Integer getEquip_main_id() {
		return equip_main_id;
	}

	public void setEquip_main_id(Integer equip_main_id) {
		this.equip_main_id = equip_main_id;
	}

	@ManyToOne
	@JoinColumn(name = "equip_id")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@Column(name = "equip_main_info", length = 255)
	public String getEquip_main_info() {
		return equip_main_info;
	}

	public void setEquip_main_info(String equip_main_info) {
		this.equip_main_info = equip_main_info;
	}

	@Column(name = "equip_main_date")
	public Date getEquip_main_date() {
		return equip_main_date;
	}

	public void setEquip_main_date(Date equip_main_date) {
		this.equip_main_date = equip_main_date;
	}

	@Column(name = "equip_main_time", length = 8)
	public Integer getEquip_main_time() {
		return equip_main_time;
	}

	public void setEquip_main_time(Integer equip_main_time) {
		this.equip_main_time = equip_main_time;
	}

	@Column(name = "equip_main_worker", length = 16)
	public String getEquip_main_worker() {
		return equip_main_worker;
	}

	public void setEquip_main_worker(String equip_main_worker) {
		this.equip_main_worker = equip_main_worker;
	}

	@Column(name = "equip_main_result", length = 1)
	public Integer getEquip_main_result() {
		return equip_main_result;
	}

	public void setEquip_main_result(Integer equip_main_result) {
		this.equip_main_result = equip_main_result;
	}

	@Column(name = "equip_main_memo", length = 255)
	public String getEquip_main_memo() {
		return equip_main_memo;
	}

	public void setEquip_main_memo(String equip_main_memo) {
		this.equip_main_memo = equip_main_memo;
	}
}
