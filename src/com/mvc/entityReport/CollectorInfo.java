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
@Table(name="collector_info")
public class CollectorInfo {

	private Integer collector_id;//采集器id，主键
	private String collector_ip;//采集器id设置
	private GatewayInfo gateway_info;//项目id
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "collector_id",unique=true,nullable=false, length = 11)
	public Integer getCollector_id() {
		return collector_id;
	}
	public void setCollector_id(Integer collector_id) {
		this.collector_id = collector_id;
	}
	
	public String getCollector_ip() {
		return collector_ip;
	}
	public void setCollector_ip(String collector_ip) {
		this.collector_ip = collector_ip;
	}
	
	@ManyToOne
	@JoinColumn(name="gateway_id")
	public GatewayInfo getGateway_info() {
		return gateway_info;
	}
	public void setGateway_info(GatewayInfo gateway_info) {
		this.gateway_info = gateway_info;
	}




}
