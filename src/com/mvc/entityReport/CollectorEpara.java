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
@Table(name="collector_epara")
public class CollectorEpara {

	private Integer collepara_id;//采集器与设备参数表id，主键
	private String collector_ip;//采集器id（设置）
	private Integer collector_port;//0:485口1；1:485口2；2:I/O口1;3:I/O口2;4:I/O口3;5:I/O口4;6:I/O口5;
	private FunctionInfo function_info;//端口对应功能号
	private EquipPara equip_para;//设备特征参数id
	private Integer collepara_flag;//备用标识
	private GatewayInfo gateway_id;//网关信息
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "collepara_id",unique=true,nullable=false, length = 11)
	public Integer getCollepara_id() {
		return collepara_id;
	}
	public void setCollepara_id(Integer collepara_id) {
		this.collepara_id = collepara_id;
	}
	
	public String getCollector_ip() {
		return collector_ip;
	}
	public void setCollector_ip(String collector_ip) {
		this.collector_ip = collector_ip;
	}
	
	public Integer getCollector_port() {
		return collector_port;
	}
	public void setCollector_port(Integer collector_port) {
		this.collector_port = collector_port;
	}
	
	@ManyToOne
	@JoinColumn(name="function_id")
	public FunctionInfo getFunction_info() {
		return function_info;
	}
	public void setFunction_info(FunctionInfo function_info) {
		this.function_info = function_info;
	}
	
	@ManyToOne
	@JoinColumn(name="equip_para_id")
	public EquipPara getEquip_para() {
		return equip_para;
	}
	public void setEquip_para(EquipPara equip_para) {
		this.equip_para = equip_para;
	}
	
	public Integer getCollepara_flag() {
		return collepara_flag;
	}
	public void setCollepara_flag(Integer collepara_flag) {
		this.collepara_flag = collepara_flag;
	}
	
	@ManyToOne
	@JoinColumn(name="gateway_id")
	public GatewayInfo getGateway_id() {
		return gateway_id;
	}
	public void setGateway_id(GatewayInfo gateway_id) {
		this.gateway_id = gateway_id;
	}

}
