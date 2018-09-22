package com.mvc.controller;

import java.util.List;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.Equipment;
import com.mvc.service.EquipRealInfoService;
import com.utils.Pager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/equipRealInfo")
public class EquipRealInfoController {
	
	@Autowired
	EquipRealInfoService equipRealInfoService;
	
	//根据页数显示设备信息列表
			@RequestMapping(value = "/getEquipmentListByPage.do")
			public @ResponseBody String getEquipmentListByPage(HttpServletRequest request, HttpSession session) {
				System.out.println("Controller");
				JSONObject jsonObject = new JSONObject();
				String searchKey = request.getParameter("searchKey");
				Integer totalRow = equipRealInfoService.countEqTotal(searchKey);
				Pager pager = new Pager();
				pager.setPage(Integer.valueOf(request.getParameter("page")));
				pager.setTotalRow(Integer.parseInt(totalRow.toString()));
				List<Equipment> list = equipRealInfoService.selectEquipmentByPage(searchKey, pager.getOffset(), pager.getLimit());
				jsonObject.put("list", list);
				jsonObject.put("totalPage", pager.getTotalPage());
				/*System.out.println(jsonObject.toString());*/
				
				return jsonObject.toString();
			}
			
	//根据设备id查找设备特征参数
			@RequestMapping(value = "/getEquipPara.do")
			public @ResponseBody String getEquipPara(HttpServletRequest request, HttpSession session){
				JSONObject jsonObject = new JSONObject();
				try{
					String searchKey = request.getParameter("searchKey");
					List<EquipPara> result = equipRealInfoService.getEquipPara(searchKey);
					jsonObject.put("result", result);
				} catch (Exception e){
					jsonObject.put("error", "暂未找到相关数据");
				}
				return jsonObject.toString();
			}
	//根据设备参数名字查找设备特征参数信息
			@RequestMapping(value = "/getEquipParaByName.do")
			public @ResponseBody String getEquipParaByName(HttpServletRequest request, HttpSession session){
				JSONObject jsonObject = new JSONObject();
				try{
					String searchKey = request.getParameter("searchKey");
					List<EquipPara> result = equipRealInfoService.getEquipParaByName(searchKey);
					jsonObject.put("result", result);
				} catch (Exception e){
					jsonObject.put("error", "暂未找到相关数据");
				}
				System.out.println(jsonObject.toString());
				return jsonObject.toString();
			}
			
			//根据设备参数id查找设备特征参数实时数据
	@RequestMapping(value = "/getEquipRealData.do")
			public @ResponseBody String getEquipRealData(HttpServletRequest request, HttpSession session){
				JSONObject jsonObject = new JSONObject();
				System.out.println("123");
				try{
					String searchKey = request.getParameter("searchKey");
					String startDate = request.getParameter("startDate");
					System.out.println(searchKey);
					System.out.println(startDate);
					List<EquipOper> data = equipRealInfoService.getEquipRealData(searchKey,startDate);
					jsonObject.put("data", data);
				} catch (Exception e){
					jsonObject.put("error", "暂未找到相关数据");
				}
				return jsonObject.toString();
			}
	

}
