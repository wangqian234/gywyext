package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equip")
public class equipController {

	/**
	 * 
	 * 
	 *@Title: equipPage 
	 *@Description: equip首页
	 *@param @return
	 *@return String
	 *@throws
	 */
	@RequestMapping("/toequipInfo.do")
	public String adPage() {
		System.out.println("我进来了");
		return "equip/equipInfo/index";
	}
	
}
