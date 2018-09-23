package com.mvc.controller;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
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
	//gaogaogao
	//控制云台
	@RequestMapping(value = "/getTurn.do")
	public @ResponseBody String getTurn (HttpServletRequest request, HttpSession session){
		String turn_id = request.getParameter("turn_id");
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String url = null;			     
	    String param = null;
        if(turn_id.equals("0")){
        	 url = "https://open.ys7.com/api/lapp/device/ptz/start";			     
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=0&speed=1";
        }else if(turn_id.equals("1")){
        	 url = "https://open.ys7.com/api/lapp/device/ptz/start";			     
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=1&speed=1";
        }else if(turn_id.equals("2")){
        	 url = "https://open.ys7.com/api/lapp/device/ptz/start";			     
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=2&speed=1";
        }else if(turn_id.equals("3")){
        	 url = "https://open.ys7.com/api/lapp/device/ptz/start";			     
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=3&speed=1";
        }
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("[POST请求]向地址：" + url + " 发送数据：" + param + " 发生错误!");
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("关闭流异常");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);					
		return jsonObject.toString();
	}
	//停止云台控制
	
	@RequestMapping(value = "/getStop.do")
	public @ResponseBody String getStop (HttpServletRequest request, HttpSession session){					
		String turn_id = request.getParameter("turn_id");
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String url = "https://open.ys7.com/api/lapp/device/ptz/stop";			     
	    String param = null;			        
	    if(turn_id.equals("0")){
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=0";
        }else if(turn_id.equals("1")){			     
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=1";
        }else if(turn_id.equals("2")){			     
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=2";
        }else if(turn_id.equals("3")){			     
		     param = "accessToken=at.aedabs5ic4xy3ihwc949ethndttqp4au-1hprl09j6x-0tzlkhp-n0bw8skni&deviceSerial=C24186733&channelNo=1&direction=3";
        }
	    try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("[POST请求]向地址：" + url + " 发送数据：" + param + " 发生错误!");
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("关闭流异常");
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);					
		return jsonObject.toString();
	}
	
	//haile

	

}
