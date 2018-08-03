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
@Table(name="equipment")
public class Equipment {
	
	
	private Integer equip_id;//设备编号，主键
	private String equip_no;//设备型号
	private String equip_name;//设备名称
	private String equip_pic;//设备图片
	private String equip_qrcode;//设备二维码
	private String equip_spec;//设备规格
	private EquipType equip_type;//设备分类编号，外键
	private EquipManu equip_manu;//设备制造商编号，外键
	private Date equip_pdate;//设备生产日期
	private Date equip_bdate;//设备购买日期
	private Date equip_idate;// 设备安装日期
	private Date equip_udate;//设备使用日期
	private Float equip_ifee;//设备安装费用
	private Float equip_bfee;//设备购买费用
	private Integer equip_state;//设备健康状态 0：正常 1：需要维修 2：需要更换
	private Integer equip_mdate;//设备维护周期
	private Date equip_ndate;//设备下次维保时间
	private Integer equip_atime;//设备寿命
	private User user;//设备负责人编号，外键
	private EquipRoom equip_room;//设备安装位置编号，外键
	private String  equip_memo;//设备备注
	private Integer equip_isdeleted;//设备是否删除 0：存在 1：已删除

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "equip_id",unique=true,nullable=false, length = 11)
	public Integer getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(Integer equip_id) {
		this.equip_id = equip_id;
	}
	
	@Column(name = "equip_no", length = 32)
	public String getEquip_no() {
		return equip_no;
	}
	public void setEquip_no(String equip_no) {
		this.equip_no = equip_no;
	}
	
	@Column(name = "equip_name", length = 64)
	public String getEquip_name() {
		return equip_name;
	}
	public void setEquip_name(String equip_name) {
		this.equip_name = equip_name;
	}
	
	@Column(name = "equip_pic", length = 255)
	public String getEquip_pic() {
		return equip_pic;
	}
	public void setEquip_pic(String equip_pic) {
		this.equip_pic = equip_pic;
	}
	
	@Column(name = "equip_qrcode", length = 255)
	public String getEquip_qrcode() {
		return equip_qrcode;
	}
	public void setEquip_qrcode(String equip_qrcode) {
		this.equip_qrcode = equip_qrcode;
	}
	
	@Column(name = "equip_spec", length = 64)
	public String getEquip_spec() {
		return equip_spec;
	}
	public void setEquip_spec(String equip_spec) {
		this.equip_spec = equip_spec;
	}
	
	@ManyToOne
	@JoinColumn(name="equip_type")
	public EquipType getEquip_type() {
		return equip_type;
	}
	public void setEquip_type(EquipType equip_type) {
		this.equip_type = equip_type;
	}
	
	@ManyToOne
	@JoinColumn(name="equip_manu")
	public EquipManu getEquip_manu() {
		return equip_manu;
	}
	public void setEquip_manu(EquipManu equip_manu) {
		this.equip_manu = equip_manu;
	}
	
	@Column(name = "equip_state", length = 1)
	public Integer getEquip_state() {
		return equip_state;
	}
	public void setEquip_state(Integer equip_state) {
		this.equip_state = equip_state;
	}
	
	@ManyToOne
	@JoinColumn(name="user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name="equip_room")
	public EquipRoom getEquip_room() {
		return equip_room;
	}
	public void setEquip_room(EquipRoom equip_room) {
		this.equip_room = equip_room;
	}
	
	@Column(name = "equip_memo", length = 255)
	public String getEquip_memo() {
		return equip_memo;
	}
	public void setEquip_memo(String equip_memo) {
		this.equip_memo = equip_memo;
	}
	
	@Column(name = "equip_isdeleted", length = 1)
	public Integer getEquip_isdeleted() {
		return equip_isdeleted;
	}
	public void setEquip_isdeleted(Integer equip_isdeleted) {
		this.equip_isdeleted = equip_isdeleted;
	}
	
	@Column(name = "equip_pdate")
	public Date getEquip_pdate() {
		return equip_pdate;
	}
	public void setEquip_pdate(Date equip_pdate) {
		this.equip_pdate = equip_pdate;
	}
	
	@Column(name = "equip_bdate")
	public Date getEquip_bdate() {
		return equip_bdate;
	}
	public void setEquip_bdate(Date equip_bdate) {
		this.equip_bdate = equip_bdate;
	}
	
	@Column(name = "equip_idate")
	public Date getEquip_idate() {
		return equip_idate;
	}
	public void setEquip_idate(Date equip_idate) {
		this.equip_idate = equip_idate;
	}
	
	@Column(name = "equip_udate")
	public Date getEquip_udate() {
		return equip_udate;
	}
	public void setEquip_udate(Date equip_udate) {
		this.equip_udate = equip_udate;
	}
	
	@Column(name = "equip_ifee")
	public Float getEquip_ifee() {
		return equip_ifee;
	}
	public void setEquip_ifee(Float equip_ifee) {
		this.equip_ifee = equip_ifee;
	}
	
	@Column(name = "equip_bfee")
	public Float getEquip_bfee() {
		return equip_bfee;
	}
	public void setEquip_bfee(Float equip_bfee) {
		this.equip_bfee = equip_bfee;
	}
	
	@Column(name = "equip_mdate")
	public Integer getEquip_mdate() {
		return equip_mdate;
	}
	public void setEquip_mdate(Integer equip_mdate) {
		this.equip_mdate = equip_mdate;
	}
	
	@Column(name = "equip_ndate")
	public Date getEquip_ndate() {
		return equip_ndate;
	}
	public void setEquip_ndate(Date equip_ndate) {
		this.equip_ndate = equip_ndate;
	}
	
	@Column(name = "equip_atime")
	public Integer getEquip_atime() {
		return equip_atime;
	}
	public void setEquip_atime(Integer equip_atime) {
		this.equip_atime = equip_atime;
	}


}
