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
@Table(name="equip_room")
public class EquipRoom {
	private Integer equip_room_id;//设备位置编号，主键
	private String equip_room_name;//设备位置名称
	private Project project;//设备所属项目编号,外键
	private String equip_room_memo;//设备位置备注
	private Integer equip_room_isdeleted;//设备位置是否删除 0：存在 1：已删除
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "equip_room_id",unique=true,nullable=false, length = 11)
	public Integer getEquip_room_id() {
		return equip_room_id;
	}
	public void setEquip_room_id(Integer equip_room_id) {
		this.equip_room_id = equip_room_id;
	}
	
	@Column(name = "equip_room_name", length = 64)
	public String getEquip_room_name() {
		return equip_room_name;
	}
	public void setEquip_room_name(String equip_room_name) {
		this.equip_room_name = equip_room_name;
	}
	
	@ManyToOne
	@JoinColumn(name="proj_id")
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@Column(name = "equip_room_memo", length = 255)
	public String getEquip_room_memo() {
		return equip_room_memo;
	}
	public void setEquip_room_memo(String equip_room_memo) {
		this.equip_room_memo = equip_room_memo;
	}
	
	@Column(name = "equip_room_isdeleted", length = 1,columnDefinition = "INT not null default 0")
	public Integer getEquip_room_isdeleted() {
		return equip_room_isdeleted;
	}
	public void setEquip_room_isdeleted(Integer equip_room_isdeleted) {
		this.equip_room_isdeleted = equip_room_isdeleted;
	}
	
}