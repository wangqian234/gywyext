var app = angular
		.module(
				'dataVApp',
				[ 'ngRoute', ],
				function($httpProvider) {// ngRoute引入路由依赖
					$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
					$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

					// Override $http service's default transformRequest
					$httpProvider.defaults.transformRequest = [ function(data) {
						/**
						 * The workhorse; converts an object to
						 * x-www-form-urlencoded serialization.
						 * 
						 * @param {Object}
						 *            obj
						 * @return {String}
						 */
						var param = function(obj) {
							var query = '';
							var name, value, fullSubName, subName, subValue, innerObj, i;

							for (name in obj) {
								value = obj[name];

								if (value instanceof Array) {
									for (i = 0; i < value.length; ++i) {
										subValue = value[i];
										fullSubName = name + '[' + i + ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value instanceof Object) {
									for (subName in value) {
										subValue = value[subName];
										fullSubName = name + '[' + subName
												+ ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value !== undefined
										&& value !== null) {
									query += encodeURIComponent(name) + '='
											+ encodeURIComponent(value) + '&';
								}
							}

							return query.length ? query.substr(0,
									query.length - 1) : query;
						};

						return angular.isObject(data)
								&& String(data) !== '[object File]' ? param(data)
								: data;
					} ];
				});

app.run([ '$rootScope', '$location', function($rootScope, $location) {
	$rootScope.$on('$routeChangeSuccess', function(evt, next, previous) {
		console.log('路由跳转成功');
		$rootScope.$broadcast('reGetData');
	});
} ]);

// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/dataV', {
		templateUrl : '/gywyext/jsp/equip/equipRealInfo/dataV/dataV.html',
		controller : 'equipRealInfoController'
	})
} ]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	
	var services = {};
	//控制云台
	services.getTurn = function (data){
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getTurn.do',
			data : data,
		})
	}
	//停止云台控制
	services.getStop = function (data){
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getStop.do',
			data : data,
		})
	}
	//获取告警信息
	services.getWaringNews = function (data){
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getWaringNews.do',
			data : data,
		})
	};
	// 根据项目获取所属设备信息
	services.getEquipmentListByProject = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getEquipmentListByProject.do',
			data : data
		});
	};
	//根据设备id查找设备特征参数
	services.getEquipPara = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getEquipPara.do',
			data : data
		});
	};
	//根据设备参数id获取实时数据
	services.getEquipRealData = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getEquipRealData.do',
			data : data
		});
	};
	//根据设备id获取设备预测维修结果
	services.getEquipPreById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'bigData/getEquipPreById.do',
			data : data
		});
	};
	//根据设备id获取健康状态分析数据
	services.getEquipRadarById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl +'bigData/getEquipRadarById.do',
			data : data
		});
	};
	//根据位置id获取设备信息
	services.getRoomEquipAnalysisByRoomId = function(data) {
		return $http({
			method : 'post',
			url : baseUrl +'bigData/getRoomEquipAnalysisByRoomId.do',
			data : data
		});
	};
	//获取公司信息
	services.getCompanyInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/getCompanyInfo.do',
			data : data,
		});
	};

	//获取项目及地址信息
	services.getProjectAndRoomInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getProjectAndRoomInfo.do',
			data : data,
		});
	};

	// 根据公司id获取项目信息
	services.selectProjectByCompId = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/selectProjectByCompId.do',
			data : data
		});
	};
	//送水水泵打开开启（OC开启）
	services.getScoket1 = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getScoket1.do',
			data : data
		});
	};
	//送水水泵打开关闭（OC关闭）
	services.getScoket2 = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getScoket2.do',
			data : data
		});
	};
	//送水水泵关闭开启（继电器开启）
	services.getScoket3 = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getScoket3.do',
			data : data
		});
	};
	//送水水泵关闭关闭（继电器开启）
	services.getScoket4 = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getScoket4.do',
			data : data
		});
	};

	//刷新
	services.refresh = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + '',
			data : data
		});
	};
	return services;
} ]);
app
		.controller(
				'equipRealInfoController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var equipment = $scope;
							var equip_room = $scope;
							var equip_type = $scope;
							var equip_para = $scope;
							
							equipment.equipara
							//显示实时状态(根据设备id获取设备参数信息)
							equipment.tankuang = function(equipmentId){
								$(".pop").fadeIn(200);
								$(".bgPop").fadeIn(200);
								services.getEquipPara({
									page : 1,
									searchKey : equipmentId
								}).success(function(data) {
									equipment.equipara = data.result;
									console.log(data.result);
									
								});
								
							}
							//获取参数实时状态
							equipment.getEquipRealData = function(equipParaId){								
								var startDate = null;
								var divid = echart;//传递显示图表的id
								var color = '#00ffee';
								if(equipment.startTime != null)
								startDate = equipment.startTime+" 00:00:00";//默认从起始日期凌晨开始显示数据
								//查询参数对应的设备信息
								for(var i=0;i<equipment.equipara.length;i++){
									if(equipParaId == equipment.equipara[i].equip_para_id){
										try2(startDate,equipment.equipara[i],divid);
										break;
									}
								}
								equipment.equipParaId = equipParaId;
								if(startDate != null)
								try2(startDate,equipment.equipara[equipment.Id],divid,color);
								else alert("请输入起始时间");
							}
							//显示模拟动画
							equipment.donghua = function(equipId){
								//console.log(equipId);
								if(equipId<8){
									//集水井
									if((equipId == 3)||(equipId == 6))
										document.getElementById("myframe").src = "http://localhost:8028/runtime.shtm?prjId=7&picId=25&rand=1539518544349";
									//显示消防系统
									else 
										document.getElementById("myframe").src = "http://localhost:8028/runtime.shtm?prjId=7&picId=24&rand=1539519268983";
								}
								else if(equipId>11){
									//展会模型配电房
									if((equipId == 12)||(equipId == 14)||(equipId == 18))
										document.getElementById("myframe").src = "http://localhost:8028/runtime.shtm?prjId=7&picId=28&rand=1539518696398";
									//展会模型水泵房
									else{
										document.getElementById("myframe").src = "http://localhost:8028/runtime.shtm?prjId=7&picId=27&rand=1539518656387";
										document.getElementById("b1").style.display = "";
										document.getElementById("b2").style.display = "";
									}
								}
								$(".pop2").fadeIn(200);
								$(".bgPop2").fadeIn(200);
							}
							//水泵开关标志位
							equipment.flag = 0;
							var time;
							//送水水泵的开启
							equipment.open = function(){
								if(equipment.flag == 0){
									color = document.getElementById("b1").style.backgroundColor;
									if(color == "rgb(255, 0, 0)"){
										equipment.flag = 1;
										services.getScoket1().success(function(data){
											flag = 0;
											time = setInterval(function(){
												if(flag == 1){
													services.getScoket2().success(function(data){
														});
													document.getElementById("b1").style.backgroundColor = "rgb(0, 255, 0)";
													document.getElementById("b2").style.backgroundColor = "rgb(255, 0, 0)";
													equipment.flag = 0;
													clearInterval(time);
												}
												flag++;
											},7000);
											});
									}
								}
								else alert("正在进行水泵操作，请稍候！！！");
							}
							equipment.close = function(){
								if(equipment.flag == 0){
									color = document.getElementById("b2").style.backgroundColor;
									if(color == "rgb(255, 0, 0)"){
										equipment.flag = 1;
										services.getScoket3().success(function(data){
											flag = 0;
											time = setInterval(function(){
												if(flag == 1){
													services.getScoket4().success(function(data){
													});
													document.getElementById("b1").style.backgroundColor = "rgb(255, 0, 0)";
													document.getElementById("b2").style.backgroundColor = "rgb(0, 255, 0)";
													equipment.flag = 0;
													clearInterval(time);
												}
												flag++;
											},7000);
											});
								}
								}
								else alert("正在进行水泵操作，请稍候！！！");
							}
							equipment.KeepViewTitle = null;
							// 根据项目获取所属设备列表
							function getEquipmentListByProject(searchKey) {
								services.getEquipmentListByProject({
									searchKey : searchKey
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							}
							equipment.preResult = '';
							equipment.equipName = '';
							//设备预测维修分析
							function getEquipPreById(equipmentId) {
								if(equipmentId ==null)
									equipment.preResult = "暂未选中具体设备，设备预测维修结果暂缺！！！";
								else {equipment.equipName = equipment.equipments[equipmentId-1].equip_name;
								services.getEquipPreById({
									equipmentId : equipmentId
								}).success(function(data) {
									equipment.preDate = data.result;
									if (data.result[0] == null
											|| data.result[0] == "") {
										equipment.preResult = "该设备从使用到现在还没有发生过故障,经分析预测设备下次维修时间为"
												+ equipment.formatDate(equipment.preDate[1].time)
												+ ",请在预测维修时间之前进行检修，以防发生突发故障！";
									} else {
										equipment.preResult = "  该设备上次维保时间为"
												+ equipment.formatDate(equipment.preDate[0].time)
												+ ",经分析预测设备下次维修时间为"
												+ equipment.formatDate(equipment.preDate[1].time)
												+ ",请在预测维修时间之前进行检修，以防发生突发故障！";
									}
							});}
							};
							equipment.formatDate = function(input) {
								var type = new Date(input).toLocaleDateString()
										.replace(/\//g, '-');
								return type;
							}
							//饼图
							function d444(data){
								d4.clear();
								d4.showLoading({text:'正在缓冲...'});
								var name=[];
								for(var i=0;i<data.length;i++){
									name.push(data[i].name);
								}
								option = {
									    tooltip : {
									        trigger: 'item',
									        formatter: "{a} <br/>{b} : {c} ({d}%)"
									    },
									    legend: {
									        /*x : 'center',
									        y : '280',*/
									    	bottom: '7%',
									        //left: '20%',
									        icon: 'circle',
									        data:name,
									        textStyle : {
									        	color: function(params) {
									        		//console.log(params);
								                    var num = data.length;
								                    return mycolor[params.dataIndex % num]
								                },
								                }
									    },
									    toolbox: {
									        show : true,
									        feature : {
									            mark : {show: true},
									            magicType : {
									                show: true,
									                type: ['pie', 'funnel']
									            }
									        }
									    },
									    calculable : true,
									    series : [
									        {
									            name:'故障统计',
									            type:'pie',
									            radius : [20, 90],
									            center : ['50%', '35%'],
									            labelLine: {
									            	normal: {
									            		length: 2,
									            	}
									            },
									            roseType : 'area',
									            data:data,
									        }
									    ]
									};
								
								d4.setOption(option);
								d4.hideLoading();
								
							}
							function exchange(name){
								var pp=equipment.equipments;
								var equipId=null;
								for(var i=0;i<pp.length;i++){
									if(pp[i].equip_name==name){
										equipId=pp[i].equip_id;
										break;
										}
								}
								if(equipId !=null){
								services.getWaringNews({
									searchKey : equipId,
									type : 'equip'
								}).success(function(data) {
									equipment.alarmData = data.data;
									if(equipment.alarmData[0]==null)
										alert("设备运行良好，暂无历史报警信息！！！");
									document.getElementById("scrolling1").style.display="none";
									equipment.warningTitle = name+"历史报警";
								});
								}
								else {
									pie(name);
									equipment.KeepViewTitle = name;
								}
							}
							//地图
							function d555(){
								var d5 = echarts.init(document.getElementById('d5'));
								
							    geoCoordMap1 = {
							    		'公元物业总公司':[113.1,23.745],
							    	    '展会演示项目':[113.3,24.3],
							    	    '清远凤城郦都':[113.056,23.5435],
							    	};
							    goData1 = [
							                 [{name: '公元物业总公司'}, {id: 2,name: '公元物业总公司',value: 120}],
									    	    [{name: '公元物业总公司'},{id: 1,name: '展会演示项目',value: 110}],
									    	    [{name: '公元物业总公司'},{id: 1,name: '清远凤城郦都',value:110}],
									    	];
								//console.log(goData1[1][1].value);
								    $.get('../../../../mapjson/qingyuan.json', function(tjJson) {
								    
								    d5.clear();
								 	   d5.showLoading({text:'正在缓冲...'});
									    echarts.registerMap('qingyuan', tjJson);
									    d5.setOption({
									        series: [{
									            type: 'map',
									            map: 'qingyuan'
									        }]
									    });

									    var geoCoordMap = geoCoordMap1;
									     //值控制圆点大小
									    	var goData = goData1;
									    	//值控制圆点大小
									    	/*var backData = [
									    	    [{name: '潍坊市'}, {id: 2,name: '济南市',value: 200}],
									    	    [{name: '青岛市'}, { id: 2,name: '济南市',value: 200}],
									    	    [{name: '清新县'},{id: 1,name: '清城区',value:100}]
									    	    //[{name: '烟台市'}, { id: 2,name: '济南市',value: 100}]
									    	];*/
									    	var planePath = 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z';
									    	var arcAngle = function(data) {
									    	    var j, k;
									    	    for (var i = 0; i < data.length; i++) {
									    	        var dataItem = data[i];
									    	        if (dataItem[1].id == 1) {
									    	            j = 0.2;
									    	            return j;
									    	        } else if (dataItem[1].id == 2) {
									    	            k = -0.2;
									    	            return k;
									    	        }
									    	    }
									    	}
									    	var convertData = function(data) {
									    	    var res = [];
									    	    for (var i = 0; i < data.length; i++) {
									    	        var dataItem = data[i];
									    	        var fromCoord = geoCoordMap[dataItem[0].name];
									    	        var toCoord = geoCoordMap[dataItem[1].name];
									    	        if (dataItem[1].id == 1) {
									    	            if (fromCoord && toCoord) {
									    	                res.push([{
									    	                    coord: fromCoord,
									    	                }, {
									    	                    coord: toCoord,
									    	                    value: dataItem[1].value //线条颜色

									    	                }]);
									    	            }
									    	        } else if (dataItem[1].id == 2) {
									    	            if (fromCoord && toCoord) {
									    	                res.push([{
									    	                    coord: fromCoord,
									    	                }, {
									    	                    coord: toCoord
									    	                }]);
									    	            }
									    	        }
									    	    }
									    	    return res;
									    	};
									    	var color = ['#fff', '#FF1493', '#0000FF'];
									    	var series = [];
									    	[
									    	    ['1', goData]/*,
									    	    ['2', backData]*/
									    	].forEach(function(item, i) {
									    	    series.push({
									    	        name: item[0],
									    	        type: 'lines',
									    	        zlevel: 2,
									    	        //线特效配置
									    	        effect: {
									    	        	show: false
									    	            //show: true,
									    	            //period: 6,
									    	            //trailLength: 0.1,
									    	            //symbol: planePath, //标记类型
									    	            //symbolSize: 10
									    	        },
									    	        lineStyle: {
									    	            normal: {
									    	                width: 0,
									    	                opacity: 0.4,
									    	                curveness: arcAngle(item[1]), //弧线角度
									    	                color: '#fff'
									    	            }
									    	        },
									    	        data: convertData(item[1])
									    	    }, {
									    	        type: 'effectScatter',
									    	        coordinateSystem: 'geo',
									    	        zlevel: 2,
									    	        //波纹效果
									    	        rippleEffect: {
									    	            period: 2,
									    	            brushType: 'stroke',
									    	            scale: 4
									    	        },
									    	        label: {
									    	            normal: {
									    	                show: true,
									    	                color: '#fff',
									    	                position: 'right',
									    	                formatter: '  {b}',
									    	                textStyle: {
								                                fontSize: 16,
								                                fontWeight: 'lighter',
								                            }

									    	            }
									    	        },
									    	        //终点形象
									    	        symbol: 'circle',
									    	        //圆点大小
									    	        symbolSize: function(val) {
									    	            return val[2] / 8;
									    	        },
									    	        itemStyle: {
									    	            normal: {
									    	                show: true
									    	            }
									    	        },
									    	        data: item[1].map(function(dataItem) {
									    	            return {
									    	                name: dataItem[1].name,
									    	                value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value])
									    	            };
									    	        })

								    });

								});
								var option = {
								        /*title: {
								        text: '公元物业总公司',
								        subtext: '业务布局',
								        left: 'center',
								        textStyle: {
								            color: '#fff',
								        	fontWeight:'lighter',
								        	fontSize:15,
								        },
								        x: 'center'
								    },*/
								    tooltip: {
								        trigger: 'item',
								        formatter: "{b}"
								    },
								    //线颜色及飞行轨道颜色
								    visualMap: {
								        show: false,
								        min: 0,
								        max:300,
								        color: ['#02e817', '#e80202', '#0233e8']
								    },
								    //地图相关设置
								    geo: {
								        map: 'qingyuan',
								        //视角缩放比例
								        zoom: 1,
								        //显示文本样式
								        label: {
								            normal: {
								                show: false,
								                textStyle: {
								                    color: '#fff'
								                }
								            },
								            emphasis: {
								                textStyle: {
								                    color: '#fff'
								                }
								            }
								        },
								        /*roam: true,*/
								        layoutCenter: ["50%", "50%"], //地图位置
								        layoutSize: "95%",//地图大小
								        itemStyle: {
								            normal: {
								            	color: "#04284e", //地图背景色
								                borderColor: 'rgba(147, 235, 248, 1)',
								                borderWidth: 1,
								                areaColor: {
								                    type: 'radial',
								                    x: 0.5,
								                    y: 0.5,
								                    r: 0.8,
								                    colorStops: [{
								                        offset: 0,
								                        color: 'rgba(175,238,238, 0)' // 0% 处的颜色
								                    }, {
								                        offset: 1,
								                        color: 'rgba(	47,79,79, .2)' // 100% 处的颜色
								                    }],
								                    globalCoord: false // 缺省为 false
								                },
								                shadowColor: 'rgba(128, 217, 248, 1)',
								                shadowOffsetX: -2,
								                shadowOffsetY: 2,
								                shadowBlur: 10
								            },
								            emphasis: {
								                areaColor: '#389BB7',
								                borderWidth: 0
								            }
								        }
								    },
								    series: series
								};
								d5.setOption(option);
								d5.hideLoading();
								window.onresize = d5.resize;//自适应窗口大小
								d5.on('click',function(params){

									if(params.data.name != "公元物业总公司"){
										getEquipmentListByProject(params.data.name);
										equipment.Project = params.data.name;
									}
									pie(params.data.name);
									equipment.KeepViewTitle = params.data.name;
									console.log(equipment.KeepViewTitle);
									document.getElementById("scrolling1").style.display="";
									equipment.warningTitle = "报警记录";
								});
								d4.on('click',function(params){
									exchange(params.data.name);

								});
								   }); 
							}
							//开始云台操作
							equipment.getTurn = function(id) {
								services.getTurn({
									turn_id : id
								}).success(function(data){
									equipment.yunTurn = data;
									if(equipment.yunTurn.result.code != "200"){
										alert(equipment.yunTurn.result.msg);
									}
								})
							}
							//停止云台操作
							equipment.getStop = function(id) {
								services.getStop({
									turn_id : id
								}).success(function(data){
									equipment.yunStop = data;
									if(equipment.yunTurn.result.code != "200"){
										alert(equipment.yunStop.result.msg);
									}
								})
							}
							//大数据分析
							equipment.dataAnalyse =function(equipmentId){
								getEquipPreById(equipmentId);
								services.getEquipRadarById({
									equipmentId : equipmentId
								}).success(function(data){
						        	radarResult = data.result;
						        	//console.log(radarResult);
						        	var ddd=[radarResult];
						        	d888(ddd,equipment.equipments[equipmentId-1].equip_name);
						        })
							}
							//设备故障统计
							function pie(name){

								services.refresh().success();
								var equipRoomId = null;
								for(var i=0;i<equipment.ProjRoom.length;i++){
									if(equipment.ProjRoom[i].proj_name==name){
										equipRoomId = equipment.ProjRoom[i].equip_room_id;
										services.getWaringNews({
											searchKey : equipRoomId,
											type : 'project',
										}).success(function(data) {
											equipment.projwarning = data.data;
											//console.log(equipment.projwarning);
											equipment.warningTitle = "报警记录";
											warning(equipment.projwarning);
										});
										services.getRoomEquipAnalysisByRoomId({
											page : 1,
											roomId: equipRoomId
										}).success(function(data) {
											pieResult = data.list;
											//console.log(pieResult);
											//console.log(data.analysis);
								        	d444(data.analysis);
											});
										break;
									}
								}
								if(equipRoomId ==null) {
									warning(equipment.allwarning);
									d444(equipment.ProjAlarm);
								}

							}
							// 初始化
							function initPage() {
								console.log("初始化成功equipmentController！");
								hugeData("公元物业总公司");
								getEquipPreById(null);
								var searchKey = "清远凤城郦都";
								equipment.Project = searchKey;
								//pie(searchKey);
								//获取公司信息
								services.getCompanyInfo().success(function(data){
									equipment.companys = data.result;
									/*// 根据公司id获取所属项目信息
									services.selectProjectByCompId({
										searchKey : equipment.companys[0].comp_id
									}).success(function(data) {
										equipment.projects = data.project;
									}) ;*/
								});
								//获取项目及位置信息
								services.getProjectAndRoomInfo().success(function(data){
									equipment.ProjAlarm = data.ProjAlarm;
									equipment.ProjRoom = data.ProjRoom;
									//console.log(equipment.ProjRoom);
									//console.log(equipment.ProjAlarm);
									d555();
									d444(equipment.ProjAlarm);
								});
								
								equipment.KeepViewTitle = "公元物业总公司";
								//根据项目获取所属设备信息列表
								services.getEquipmentListByProject({
									searchKey : searchKey
								}).success(function(data) {
									equipment.equipments = data.list;
									console.log("equipment.equipments");
									console.log(equipment.equipments);
									});	
								equipment.warningTitle=null;
								//获取告警信息
								services.getWaringNews({
									searchKey : null,
									type : 'all',
								}).success(function(data) {
									equipment.allwarning = data.data;
									equipment.warningTitle = "报警记录";
									warning(equipment.allwarning);
								});
							}
							initPage();
							
							//获取当前时间
							function CurentTime(){ 
						        var now = new Date();
						        var year = now.getFullYear();       //年
						        var month = now.getMonth() + 1;     //月
						        var day = now.getDate();            //日
						        var hh = now.getHours();            //时
						        var mm = now.getMinutes();          //分
						        var ss = now.getSeconds();           //秒
						        var clock = year + "-";
						        if(month < 10)
						            clock += "0";
						        clock += month + "-";
						        if(day < 10)
						            clock += "0";
						        clock += "0"+(day-25) + " ";
						        if(hh < 10)
						            clock += "0";
						        clock += hh + ":";
						        if (mm < 10) clock += '0'; 
						        clock += mm + ":"; 
						        if (ss < 10) clock += '0'; 
						        clock += ss; 
						        return(clock); 
						    }
							//报警信息滚动展示
							function warning(data){
							    //var data = '塞下秋来风景异，衡阳雁去无留意。四面边声连角起，千嶂里，长烟落日孤城闭。浊酒一杯家万里，燕然未勒归无计。羌管悠悠霜满地，人不寐，将军白发征夫泪。', //样例数据
							        //data_len = data.length,
							        //len = parseInt(Math.random()*6)+6, // 数据的长度
							        html = '<div class="ss">';
							    
							    for(var i=0; i<data.length; i++){
							        //var start = parseInt( Math.random()*(data_len-20) ),
							            //s = parseInt( Math.random()*data_len );
							        //html += '<div class="item"v>'+i+'- '+data.substr(start, s)+'</div>';
							        html += '<div class="item" style="color:#ff0000">'+timestampToTime(data[i].alarm_log_date.time)+' '+data[i].alarm_log_info+'</div>';
							    }
							    html += '</div>';
							    document.querySelector('.list .cc').innerHTML = html+html; // 复制一份数据
							    var height = document.querySelector('.list .ss').offsetHeight; // 一份数据的高度
							    addKeyFrames( '-'+(300)+'px' ); // 设置keyframes,可控制滚动速度
							    document.querySelector('.list .cc').className += ' rowup'; // 添加 rowup
							}
							
							//报警信息滚动展示css功能函数
							function addKeyFrames(y){
							    var style = document.createElement('style');
							    style.type = 'text/css';
							    var keyFrames = '\
							    @-webkit-keyframes rowup {\
							        0% {\
							            -webkit-transform: translate3d(0, 0, 0);\
							            transform: translate3d(0, 0, 0);\
							        }\
							        100% {\
							            -webkit-transform: translate3d(0, A_DYNAMIC_VALUE, 0);\
							            transform: translate3d(0, A_DYNAMIC_VALUE, 0);\
							        }\
							    }\
							    @keyframes rowup {\
							        0% {\
							            -webkit-transform: translate3d(0, 0, 0);\
							            transform: translate3d(0, 0, 0);\
							        }\
							        100% {\
							            -webkit-transform: translate3d(0, A_DYNAMIC_VALUE, 0);\
							            transform: translate3d(0, A_DYNAMIC_VALUE, 0);\
							        }\
							    }';
							    style.innerHTML = keyFrames.replace(/A_DYNAMIC_VALUE/g, y);
							    document.getElementsByTagName('head')[0].appendChild(style);
							}
							
							
							//时间戳转换为日期格式
							function timestampToTime(timestamp) {
						        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
						        var Y = date.getFullYear() + '-';
						        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
						        var D = (date.getDate() +1 <10 ? '0'+date.getDate() :date.getDate()) + ' ';
						        //var h = (date.getHours() +1 <10? '0'+date.getHours() :date.getHours()) + ':';
						        //var m = (date.getMinutes() +1 <10? '0'+date.getMinutes() :date.getMinutes()) + ':';
						        //var s = (date.getSeconds() +1 <10? '0'+date.getSeconds() :date.getSeconds()) + ':';
						        //return Y+M+D+h+m+s;
						        return Y+M+D;
						    }
						} ]);
//时间戳转换为日期格式
app.filter('dateType', function() {
	return function(input) {
		var date = "";
		if (input) {
			date = timestampToTime(input);
		}

		return date;
	}
});
//设备状态转换
app.filter('EquipState', function() {
	return function(input) {
		var type = "";
		if (input == '0') {
			type = "正常";
		} else if (input == '1') {
			type = "需要维修";
		} else if (input == '2') {
			type = "需要更换";
		} else {
			type = "正常";
		}

		return type;
	}
});