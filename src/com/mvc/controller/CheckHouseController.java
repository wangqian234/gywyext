package com.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.CheckHouse;
import com.mvc.service.CheckHouseService;
import com.utils.CookieUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 领班查房效率相关
 * 
 * @author zjn
 * @date 2017年1月17日
 */
@Controller
@RequestMapping("/checkHouse")
public class CheckHouseController {

	@Autowired
	CheckHouseService checkHouseService;

	/**
	 * 获取所有领班查房工作效率
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCheckHouseList.do")
	public @ResponseBody String getCheckHouseList(HttpServletRequest request) {
		String startTime = "";
		String endTime = "";
		JSONObject jsonObject = new JSONObject();
		List<CheckHouse> checkHouseList = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {
			startTime = request.getParameter("startTime");
			endTime = request.getParameter("endTime");
			checkHouseList = checkHouseService.getCheckHouseList(startTime, endTime);
		}

		String analyseResult = checkHouseService.getAnalyseResult(checkHouseList, "orderNum");
		jsonObject.put("checkHouseList", checkHouseList);
		jsonObject.put("analyseResult", analyseResult);
		return jsonObject.toString();
	}

	/**
	 * 导出领班查房工作效率汇总表，word格式
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportCheckHouseList.do")
	public ResponseEntity<byte[]> exportCheckHouseList(HttpServletRequest request, HttpServletResponse response) {
		String startTime = "";
		String endTime = "";
		ResponseEntity<byte[]> byteArr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {

			startTime = request.getParameter("startTime");
			endTime = request.getParameter("endTime");
			String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
			String modelPath = request.getSession().getServletContext()
					.getRealPath(ReportFormConstants.CHECKHOUSE_PATH);// 模板路径

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("path", path);
			map.put("modelPath", modelPath);
			byteArr = checkHouseService.exportCheckHouseList(map);
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}
	
	
	/**
	 * excel导出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportCheckHouseExcel.do")
	public ResponseEntity<byte[]> exportCheckHouseExcel(HttpServletRequest request, HttpServletResponse response) {
		String startTime = "";
		String endTime = "";
		ResponseEntity<byte[]> byteArr = null;

		if (StringUtil.strIsNotEmpty(request.getParameter("startTime"))
				&& StringUtil.strIsNotEmpty(request.getParameter("endTime"))) {

			startTime = request.getParameter("startTime");
			endTime = request.getParameter("endTime");
			String path = request.getSession().getServletContext().getRealPath(ReportFormConstants.SAVE_PATH);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("path", path);
			byteArr = checkHouseService.exportCheckHouseExcel(map);
		}
		response.addCookie(CookieUtil.exportFlag());// 返回导出成功的标记
		return byteArr;
	}

}
