/**
 * 
 */
package com.base.constants;

/**
 * 权限初始化常量
 * 
 * @author zjn
 * @date 2016年10月28日
 */
public class PermissionConstants {

	public static final String index = "indexPer";
	public static final String system = "systemPer";
	public static final String systemleft = "systemleftPer";
	public static final String projleft = "projleftPer";
	public static final String project = "projPer";
	public static final String equipment = "equipmentPer";


	// 首页显示权限:{系统管理、项目管理、 设备管理、 大数据分析}
	public static final String[] indexPer = { "sys", "proj", "equip", "bigdata" };
	
	// 系统权限:{用户修改、删除,角色修改、删除}
	public static final String[] systemPer = {"uEdit", "uDel" ,"rEdit", "rDel" };
	
	// 系统权限:{用户添加、角色添加}
	public static final String[] systemleftPer = {"uAdd", "rAdd" };

	// 项目权限:{公司添加、项目添加}
	public static final String[] projleftPer = { "cAdd", "pAdd" };
	
	// 项目权限:{公司修改、删除，项目修改、删除}
	public static final String[] projPer = { "cEdit", "cDel" ,"pEdit", "pDel","pRAdd","pRDel" };
		
	// 设备权限:{设备添加、修改、删除}
	public static final String[] equipmentPer = { "eAdd", "eEdit", "eDel" };
	
}
