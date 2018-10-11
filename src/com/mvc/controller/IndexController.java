package com.mvc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	@RequestMapping("/toIndexPage.do")
	public String toIndexPage() {
		System.out.println("我进来了");
		return "index/index";
	}

	/**
	 * 获取左侧菜单栏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInitLeft.do")
	public @ResponseBody String getInitLeft(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = new JSONObject();

		List<Map> leftResult = indexService.getInitLeft();
		jsonObject.put("leftResult", leftResult);
		return jsonObject.toString();
	}

	/**
	 * 获取首页数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectIndexData.do")
	public @ResponseBody String selectIndexData(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = new JSONObject();
		String proId = request.getParameter("proId");
		Integer alarmNum=indexService.getEquipAlarmNumByProId(Integer.valueOf(proId));//得到报警的设备，alarm——log表格缺字段
		System.out.println("3333"+alarmNum);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+5);
		Date updateDate=calendar.getTime();
		Integer mainNum=indexService.getEquipMainNumByProId(Integer.valueOf(proId),updateDate);//获取某个项目中将来五天内的待维保的设备
		Integer unhealthNum=indexService.getEquipUnhealthNumByProId(Integer.valueOf(proId));//获取不健康设备数目
		/*List<Map> leftResult = indexService.getInitLeft();
		jsonObject.put("leftResult", leftResult);*/
		jsonObject.put("alarmNum", alarmNum);
		jsonObject.put("mainNum", mainNum);
		jsonObject.put("unhealthNum", unhealthNum);
		return jsonObject.toString();
	}

	public void getScoket() {
		// 客户端
		// 1、创建客户端Socket，指定服务器地址和端口
		Socket socket;
		try {
			socket = new Socket("localhost", 8091);
			// 2、获取输出流，向服务器端发送信息
			OutputStream os = socket.getOutputStream();// 字节输出流
			PrintWriter pw = new PrintWriter(os);// 将输出流包装成打印流
			pw.write("用户名：admin；密码：123");
			pw.flush();
			socket.shutdownOutput();
			// 3、获取输入流，并读取服务器端的响应信息
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String info = null;
			while ((info = br.readLine()) != null) {
				System.out.println("我是客户端，服务器说：" + info);
			}
			// 4、关闭资源
			br.close();
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (Exception e) {

		}
	}

}
