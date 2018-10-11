package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.Equipment;
import com.utils.Pager;
import com.mvc.service.AlarmLogService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/alarmLog")
public class AlarmLogController {

	@Autowired
	AlarmLogService alarmLogService;

	 @RequestMapping(value = "/getAlarmListByPage.do")
		public @ResponseBody String getAlarmsByPrarm(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String searchKey = request.getParameter("searchKey");
			Integer totalRow = alarmLogService.countAlarmTotal(searchKey);
			Pager pager = new Pager();
			pager.setPage(Integer.valueOf(request.getParameter("page")));
			pager.setTotalRow(Integer.parseInt(totalRow.toString()));
			List<AlarmLog> list = alarmLogService.getAlarmListByPage(searchKey, pager.getOffset(), pager.getLimit());
			jsonObject.put("list", list);
			jsonObject.put("totalPage", pager.getTotalPage());
			return jsonObject.toString();
		}
	 
	 
	 
}
