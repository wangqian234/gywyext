package com.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.service.IndexService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
	IndexService indexService;
	
	/**
	 * 获取左侧菜单栏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInitLeft.do")
	public @ResponseBody String getInitLeft(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		List<Map> leftResult = indexService.getInitLeft();
		jsonObject.put("leftResult", leftResult);
		return jsonObject.toString();
	}

}
