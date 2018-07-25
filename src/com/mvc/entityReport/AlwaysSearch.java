package com.mvc.entityReport;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "always_search")
public class AlwaysSearch {
	private Integer always_id;// 数据编号
	private Integer always_sender_Id;// 发送方代号
	private Integer always_receiver_Id;// 接收方代号
	private String always_info;// 传输内容
	private Date always_send_time;// 发送时间
	private Date always_handle_time;// 处理时间
	private Integer always_state;// 处理状态

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "always_id", unique = true, nullable = false, length = 11)
	public Integer getAlways_id() {
		return always_id;
	}

	public void setAlways_id(Integer always_id) {
		this.always_id = always_id;
	}

	@Column(name = "always_sender_Id", length = 11)
	public Integer getAlways_sender_Id() {
		return always_sender_Id;
	}

	public void setAlways_sender_Id(Integer always_sender_Id) {
		this.always_sender_Id = always_sender_Id;
	}

	@Column(name = "always_receiver_Id", length = 11)
	public Integer getAlways_receiver_Id() {
		return always_receiver_Id;
	}

	public void setAlways_receiver_Id(Integer always_receiver_Id) {
		this.always_receiver_Id = always_receiver_Id;
	}

	@Column(name = "always_info", length = 255)
	public String getAlways_info() {
		return always_info;
	}

	public void setAlways_info(String always_info) {
		this.always_info = always_info;
	}

	@Column(name = "always_send_time")
	public Date getAlways_send_time() {
		return always_send_time;
	}

	public void setAlways_send_time(Date always_send_time) {
		this.always_send_time = always_send_time;
	}

	@Column(name = "always_handle_time")
	public Date getAlways_handle_time() {
		return always_handle_time;
	}

	public void setAlways_handle_time(Date always_handle_time) {
		this.always_handle_time = always_handle_time;
	}

	@Column(name = "always_state", length = 1)
	public Integer getAlways_state() {
		return always_state;
	}

	public void setAlways_state(Integer always_state) {
		this.always_state = always_state;
	}

}
