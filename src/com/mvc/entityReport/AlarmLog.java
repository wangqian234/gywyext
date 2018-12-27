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
@Table(name = "alarm_log")
public class AlarmLog {
	private Integer alarm_log_id;// 报警日志编号，主键
	private Equipment equipment;// 触发报警设备编号，外键
	private EquipPara equip_para;// 触发报警参数编号，外键
	private Date alarm_log_date;// 触发报警时间
	private String alarm_log_info;// 报警内容
	private User user;// 负责人，外键
	private String alarm_log_memo;// 备注
	private Integer alarm_log_ischecked;// 是否被轮询，0：未被轮询；1：已被轮询
	private String alarm_log_code;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "alarm_log_id", length = 11)
	public Integer getAlarm_log_id() {
		return alarm_log_id;
	}

	public void setAlarm_log_id(Integer alarm_log_id) {
		this.alarm_log_id = alarm_log_id;
	}

	@ManyToOne
	@JoinColumn(name = "equipment")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@ManyToOne
	@JoinColumn(name = "equip_para_id")
	public EquipPara getEquip_para() {
		return equip_para;
	}

	public void setEquip_para(EquipPara equip_para) {
		this.equip_para = equip_para;
	}

	@Column(name = "alarm_log_date")
	public Date getAlarm_log_date() {
		return alarm_log_date;
	}

	public void setAlarm_log_date(Date alarm_log_date) {
		this.alarm_log_date = alarm_log_date;
	}

	@Column(name = "alarm_log_info", length = 255)
	public String getAlarm_log_info() {
		return alarm_log_info;
	}

	public void setAlarm_log_info(String alarm_log_info) {
		this.alarm_log_info = alarm_log_info;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "alarm_log_memo", length = 255)
	public String getAlarm_log_memo() {
		return alarm_log_memo;
	}

	public void setAlarm_log_memo(String alarm_log_memo) {
		this.alarm_log_memo = alarm_log_memo;
	}

	@Column(name = "alarm_log_ischecked", length = 1)
	public Integer getAlarm_log_ischecked() {
		return alarm_log_ischecked;
	}

	public void setAlarm_log_ischecked(Integer alarm_log_ischecked) {
		this.alarm_log_ischecked = alarm_log_ischecked;
	}

	@Column(name = "alarm_log_code", length = 255)
	public String getAlarm_log_code() {
		return alarm_log_code;
	}

	public void setAlarm_log_code(String alarm_log_code) {
		this.alarm_log_code = alarm_log_code;
	}

}
