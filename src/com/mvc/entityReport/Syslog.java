package com.mvc.entityReport;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "syslog")

public class Syslog implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer  syslog_id;//系统日志编号，主键
	private Date syslog_time;//系统日志记录时间
	private User user;//用户编号，外键
	private String syslog_event;//用户操作内容
	private String syslog_memo;//系统日志备注
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "syslog_id",length = 11)
	public Integer getSyslog_id() {
		return syslog_id;
	}
	public void setSyslog_id(Integer syslog_id) {
		this.syslog_id = syslog_id;
	}
	

	public Date getSyslog_time() {
		return syslog_time;
	}
	public void setSyslog_time(Date syslog_time) {
		this.syslog_time = syslog_time;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = " syslog_event",length = 255)
	public String getSyslog_event() {
		return syslog_event;
	}
	public void setSyslog_event(String syslog_event) {
		this.syslog_event = syslog_event;
	}
	@Column(name = " syslog_memo",length = 255)
	public String getSyslog_memo() {
		return syslog_memo;
	}
	public void setSyslog_memo(String syslog_memo) {
		this.syslog_memo = syslog_memo;
	}
	
	
	
}
