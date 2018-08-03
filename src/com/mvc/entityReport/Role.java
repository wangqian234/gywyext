package com.mvc.entityReport;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role  implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer role_id;//角色编号
	private String role_name;//角色名称
	private String role_permission;// 角色权限
	private Integer role_isdeleted;// 角色状态，0：存在，1：已删除
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	@Column(length = 32)
		public String getRole_name() {
			return role_name;
		}
	
		public void setRole_name(String role_name) {
			this.role_name = role_name;
		}
	
		@Column(length = 1280)
		public String getRole_permission() {
			return role_permission;
		}

		public void setRole_permission (String role_permission){
			this.role_permission =role_permission;
		}

		@Column(length = 1,columnDefinition = "INT not null default 0")
		public Integer getRole_isdeleted() {
			return  role_isdeleted;
		}

		public void setRole_isdeleted(Integer role_isdeleted){
			this. role_isdeleted = role_isdeleted;
		}

		
}

