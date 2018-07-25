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
@Table(name = "equip_slog")
public class EquipSlog {
	private Integer equip_slog_id;// 设备报废编号，主键
	private Equipment equipment;// 设备编号，外键
	private String equip_slog_info;// 报废原因
	private Date equip_slog_date;// 报废时间
	private String equip_slog_worker;// 报废申请人
	private Integer equip_slog_state;// 报废申请状态，0：未通过；1：通过
	private User user;// 负责人，外键
	private String equip_slog_memo;// 备注

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "equip_slog_id", unique = true, nullable = false, length = 11)
	public Integer getEquip_slog_id() {
		return equip_slog_id;
	}

	public void setEquip_slog_id(Integer equip_slog_id) {
		this.equip_slog_id = equip_slog_id;
	}

	@ManyToOne
	@JoinColumn(name = "equip_id")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@Column(name = "equip_slog_info", length = 255)
	public String getEquip_slog_info() {
		return equip_slog_info;
	}

	public void setEquip_slog_info(String equip_slog_info) {
		this.equip_slog_info = equip_slog_info;
	}

	@Column(name = "equip_slog_date")
	public Date getEquip_slog_date() {
		return equip_slog_date;
	}

	public void setEquip_slog_date(Date equip_slog_date) {
		this.equip_slog_date = equip_slog_date;
	}

	@Column(name = "equip_slog_worker", length = 16)
	public String getEquip_slog_worker() {
		return equip_slog_worker;
	}

	public void setEquip_slog_worker(String equip_slog_worker) {
		this.equip_slog_worker = equip_slog_worker;
	}

	@Column(name = "equip_slog_state", length = 1)
	public Integer getEquip_slog_state() {
		return equip_slog_state;
	}

	public void setEquip_slog_state(Integer equip_slog_state) {
		this.equip_slog_state = equip_slog_state;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "equip_slog_memo", length = 255)
	public String getEquip_slog_memo() {
		return equip_slog_memo;
	}

	public void setEquip_slog_memo(String equip_slog_memo) {
		this.equip_slog_memo = equip_slog_memo;
	}

}
