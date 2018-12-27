package com.mvc.entityReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "equip_state")
public class EquipState {

	
	private Integer equip_state_id;
	private Integer equip_state_flag; //标志位，0健康1故障
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "equip_state_id", unique = true, nullable = false, length = 11)
	public Integer getEquip_state_id() {
		return equip_state_id;
	}
	public void setEquip_state_id(Integer equip_state_id) {
		this.equip_state_id = equip_state_id;
	}
	public Integer getEquip_state_flag() {
		return equip_state_flag;
	}
	public void setEquip_state_flag(Integer equip_state_flag) {
		this.equip_state_flag = equip_state_flag;
	}
	
}
