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
	// 根据公司id获取项目信息
	services.selectProjectByCompId = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/selectProjectByCompId.do',
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
							
							//显示实时状态(根据设备id获取设备参数信息)
							equipment.tankuang = function(equipmentId){
								$(".pop").fadeIn(200);
								$(".bgPop").fadeIn(200);
								services.getEquipPara({
									page : 1,
									searchKey : equipmentId
								}).success(function(data) {
									equipment.equipara = data.result;
								});
								
							}
							//获取参数实时状态
							equipment.getEquipRealData = function(equipParaId){
								var divid = echart;//传递显示图表的id
								startDate = "2018-08-26"+" "+"00:00:00";//默认从起始日期凌晨开始显示数据
								if(startDate != null)
								try2(startDate,equipment.equipara[equipParaId-1],divid);
								else alert("请输入查询起始时间");
							}
							//显示模拟动画
							equipment.donghua = function(equipId){
								//console.log(equipId);
								if(equipId==3)
									document.getElementById("myframe").src = "http://localhost:8028/runtime.shtm?prjId=8&picId=35&rand=1539013922495";
								else if(equipId==2)
									document.getElementById("myframe").src = "http://localhost:8028/runtime.shtm?prjId=8&picId=37&rand=1539014082190";
								else
									document.getElementById("myframe").src = "http://localhost:8028/runtime.shtm?prjId=8&picId=34&rand=1539014132765";
								$(".pop2").fadeIn(200);
								$(".bgPop2").fadeIn(200);
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
									console.log(data.result);

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
							//获取bing饼图所需数据
							/*//切换map和模拟动画的可见性
							function exchange(){
								equipment.main = !equipment.main;
								equipment.main1 = !equipment.main1;
							}
							//选择对应的模拟动画页面链接地址
							function selectsrc(){
								// 消防供水系统http://localhost:8028/runtime.shtm?prjId=7&picId=24&rand=1538143247584
								// 水泵房http://localhost:8028/runtime.shtm?prjId=7&picId=32&rand=1538142836027
								// 地下车库集水井http://localhost:8028/runtime.shtm?prjId=7&picId=27&rand=1538141343458
								// 配电室http://localhost:8028/runtime.shtm?prjId=7&picId=34&rand=1538144714117
								document.getElementById("myframe").src="http://localhost:8028/runtime.shtm?prjId=7&picId=27&rand=1538141343458";
							}*/
							/*function d444(na){
								console.log("d444");
								d4.clear();
								d4.showLoading({text:'正在加载数据...'});
								var company=[{comp_id:'1',comp_name:'公元物业总公司'}];
								var project=[{comp_id:'1',proj_id:'8',proj_name:'凤城郦都'},
								             {comp_id:'1',proj_id:'9',proj_name:'东方巴黎'},
							                 {comp_id:'1',proj_id:'10',proj_name:'凤城世家'}];
								var position=[{proj_id:'8',equip_room_id:'1'},
											  {proj_id:'9',equip_room_id:'2'},
										      {proj_id:'10',equip_room_id:'3'}];
								var equip=[{equip_id:'1',equip_room_id:'1',equip_name:'水泵房'},
								           {equip_id:'2',equip_room_id:'1',equip_name:'水泵房供水管道'},
								           {equip_id:'3',equip_room_id:'1',equip_name:'水泵房集水井水泵'},
								           {equip_id:'4',equip_room_id:'1',equip_name:'水泵房消防水池'},
								           {equip_id:'5',equip_room_id:'1',equip_name:'水泵房生活水池'},
								           {equip_id:'6',equip_room_id:'1',equip_name:'配电房变压器'},
								           {equip_id:'7',equip_room_id:'1',equip_name:'楼顶消火栓'}
								           ];
								var equipara=[{equip_id:'1',equip_para_name:'水泵房温度'},
								              {equip_id:'1',equip_para_name:'水泵房湿度'},
								              {equip_id:'2',equip_para_name:'水泵房高区水压'},
								              {equip_id:'2',equip_para_name:'水泵房低区水压'},
								              {equip_id:'3',equip_para_name:'A相电压'},
								              {equip_id:'3',equip_para_name:'B相电压'},
								              {equip_id:'3',equip_para_name:'C相电压'},
								              {equip_id:'3',equip_para_name:'A相电流'},
								              {equip_id:'3',equip_para_name:'B相电流'},
								              {equip_id:'3',equip_para_name:'C相电流'},
								              {equip_id:'4',equip_para_name:'消防水池液位'},
								              {equip_id:'5',equip_para_name:'生活水池液位'},
								              {equip_id:'6',equip_para_name:'A相线圈温度'},
								              {equip_id:'6',equip_para_name:'B相线圈温度'},
								              {equip_id:'6',equip_para_name:'C相线圈温度'},
								              {equip_id:'6',equip_para_name:'铁芯温度'},
								              {equip_id:'7',equip_para_name:'消火栓末端压力'},];
								var dat1=[];
								var link1=[];
								var color1=['#001c43','#04f2a7','#b457ff','#25d053','#ffa800'];
								function pushlink(source,target){
									link1.push({
								        source: source,
								        target: target,
								        value: ' ',
								        lineStyle: linestyle1
								    })
								}
								function pushData(target,size,color){
									dat1.push({
							            name: target,
							            symbolSize: size,
							            draggable: true,
							            itemStyle: {
							                normal: {
							                    borderColor: color,
							                    borderWidth: 4,
							                    shadowBlur: 10,
							                    shadowColor: color,
							                    color: color1[0]
							                }
							            }
							        })
								}
								function push(name){
									for(var h=0;h<company.length;h++){
										if(na==company[h].comp_name){
											pushData(name,50,color1[1])
											for(var i=0;i<project.length;i++){
												if(project[i].comp_id==company[h].comp_id){
													pushData(project[i].proj_name,40,color1[2]);
													pushlink(name,project[i].proj_name)
												}
											}
											break;
										}
									}
									for(var j=0;j<project.length;j++){
										if(name==project[j].proj_name){
											pushData(name,40,color1[2])
											for(var k=0;k<position.length;k++){
											    if(project[j].proj_id==position[k].proj_id){
												    for(var m=0;m<equip.length;m++){
													    if(equip[m].equip_room_id==position[k].equip_room_id){
													    	pushData(equip[m].equip_name,25,color1[3]);
													    	pushlink(na,equip[m].equip_name)
													    }
												    }
												    break;
											    }
										    }
											break;
									    }
									}
									for(var m=0;m<equip.length;m++){
									    if(equip[m].equip_name==na){
									    	pushData(name,40,color1[3])
									    	for(var n=0;n<equipara.length;n++){
									    		if(equipara[n].equip_id==equip[m].equip_id){
									    			pushData(equipara[n].equip_para_name,20,color1[4]);
									    		    pushlink(name,equipara[n].equip_para_name)
									    		}
									    	}
									    	break;
									    }
								    }
								}
								push(na);	
								var linestyle1={
							            normal: {
							                color: {
							                    type: 'linear',
							                    x: 0,
							                    y: 0,
							                    x2: 0,
							                    y2: 1,
							                    colorStops: [{
							                        offset: 0, color: '#eda553' // 0% 处的颜色
							                    }, {
							                        offset: 1, color: '#7c785b' // 100% 处的颜色
							                    }],
							                    globalCoord: false // 缺省为 false
							                }
							            }
							        }
								var linestyle2={//连线 设置
							            normal: {
							                opacity: 0.9,
							                width: 3,
							                curveness: 0
							            }
							        }
								var option = {
								        tooltip: {},
								        animationDurationUpdate: 150,
								        animationEasingUpdate: 'quinticInOut',
								        textStyle: {
								        	fontWeight:'lighter',
								        	fontSize:10,
								        },
								        series: [
								            {
								                type: 'graph',
								                layout: 'force',
								                force:{
								                    repulsion:200,//控制各圆圈间连线长度
								                    edgeLength:50
								                },
								                symbolSize: 50,
								                roam: true,
								                label: {
								                    normal: {
								                        show: true
								                    }
								                },
								                edgeSymbolSize: [4, 10],
								                edgeLabel: {//连线注释
								                    normal: {
								                        show:true,
								                        textStyle: {
								                            fontSize: 13
								                        },
								                        formatter: "{c}"
								                    }
								                },
								                data:dat1,
								                links: link1,
								                lineStyle: linestyle2
								            }
								        ]
								    }
								d4.setOption(option);
								window.onresize = d4.resize;//自适应窗口大小
								d4.hideLoading();	
							  }*/
							//饼图
							function d444(data){
								var d4 = echarts.init(document.getElementById('d4')); 
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
									    	bottom: '8%',
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
									            		length: 1,
									            	}
									            },
									            roseType : 'area',
									            data:data,
									        }
									    ]
									};
								
								d4.setOption(option);
								d4.on('click',function(params){
									//console.log(params.data.name);
									exchange(params.data.name);
								});
								d4.hideLoading();
								
							}
							function exchange(name){
								document.getElementById("scrolling1").style.display="none";
								//document.getElementById("dd").style.display="";
								var pp=equipment.equipments;
								var equipId=null;
								for(var i=0;i<pp.length;i++){
									if(pp[i].equip_name==name){
										equipId=pp[i].equip_id;
										break;
										}
								}
								services.getWaringNews({
									searchKey : equipId
								}).success(function(data) {
									alarmData = data.data;
									//console.log(alarmData);
									if(alarmData[0]==null){
										alert("设备运行良好，暂无历史报警信息！！！")
									}
									else{
										equipment.warningTitle = name+"历史报警";
										equipment.warning = alarmData;
									}
								});
							}
							//地图
							function d555(na){
								var d5 = echarts.init(document.getElementById('d5'));
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
									    var geoCoordMap = {
									    		'公元物业总公司':[113.1,23.745],
									    		'清远东方巴黎':[113.7,23.9],
									    	    '清远凤城世家':[113.3,24.3],
									    	    '清远凤城郦都':[113.056,23.5435],

									    	};
									     //值控制圆点大小
									    	var goData = [
							                 [{name: '公元物业总公司'}, {id: 2,name: '公元物业总公司',value: 100}],
									    	    [{name: '公元物业总公司'}, {id: 1,name: '清远东方巴黎',value: 100}],
									    	    [{name: '公元物业总公司'},{id: 1,name: '清远凤城世家',value: 100}],
									    	    [{name: '公元物业总公司'},{id: 1,name: '清远凤城郦都',value:100}],
									    	    
									    	];
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
									    	            show: true,
									    	            period: 6,
									    	            trailLength: 0.1,
									    	            symbol: planePath, //标记类型
									    	            symbolSize: 10
									    	        },
									    	        lineStyle: {
									    	            normal: {
									    	                width: 1,
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
									    	            scale: 3
									    	        },
									    	        label: {
									    	            normal: {
									    	                show: true,
									    	                color: '#fff',
									    	                position: 'right',
									    	                formatter: '{b}'
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
								        title: {
								        text: '清远物业总公司',
								        subtext: '业务布局',
								        left: 'center',
								        textStyle: {
								            color: '#fff',
								        	fontWeight:'lighter',
								        	fontSize:14,
								        },
								        x: 'center'
								    },
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
								        itemStyle: {
								            normal: {
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
									//console.log(params.data.name);
									equipment.KeepViewTitle = params.data.name;
									getEquipmentListByProject(params.data.name);
									pie(params.data.name);
									equipment.warningTitle = "报警记录";
									document.getElementById("scrolling1").style.display="";
									//equipment.warning = equipment.projwarning;
									warning(equipment.projwarning);
									
								});
								/*var ssss;
								d4.on('click',function(params){
									if((params.data.itemStyle.borderColor=="#25d053")||
											(params.data.itemStyle.borderColor=="#b457ff")){
										d444(params.data.name);
										console.log(params);
										if(equipment.main){
											console.log("equipment.main");
											exchange();
											services.refresh({}).success(function(data){});//纯粹的刷新作用
										}
									}
									else if(params.data.itemStyle.borderColor=="#ffa800"){
										console.log(params);
										exchange();
										//selectsrc();
										services.refresh({}).success(function(data){});//纯粹的刷新作用
										$.ajax({
									        url:"/gywyext/equipRealInfo/getEquipParaByName.do",
									        type:"post",
									        dataType: "json",
									        data: { searchKey: params.data.name},
									        success:function(data){
									        	console.log(CurentTime());//获取起始时间
									        	try2(CurentTime(),data.result[0],d6)
												}
									    })
									}
								});*/
									    
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
								/*$.ajax({
							        url:"/gywyext/bigData/getEquipRadarById.do",
							        type:"post",
							        dataType: "json",
							        data: { equipmentId: equipmentId },
							        success:function(data){
							        	radarResult = data.result;
							        	//console.log(radarResult);
							        	var ddd=[radarResult];
							        	d888(ddd,equipment.equipments[equipmentId-1].equip_name);
							        }
							    })*/
							}
							//设备故障统计
							function pie(name){
								if(name=="清远凤城郦都")
									var equipRoomId=1;
								else if(name=="清远东方巴黎")
								    var equipRoomId=2;
								else if(name=="清远凤城世家")
									var equipRoomId=3;
								services.getRoomEquipAnalysisByRoomId({
									page : 1,
									roomId: equipRoomId
								}).success(function(data) {
									pieResult = data.list;
									//console.log(pieResult);
									//console.log(data.analysis);
						        	d444(data.analysis);
									});	
								/*$.ajax({
							        url:"/gywyext/bigData/getRoomEquipAnalysisByRoomId.do",
							        type:"post",
							        dataType: "json",
							        data: { page : 1,roomId: equipRoomId },
							        success:function(data){
							        	pieResult = data.list;
							        	console.log(pieResult);
							        	d444(data.analysis);
							        }
							    })*/
								
							}
							// 初始化
							function initPage() {
								console.log("初始化成功equipmentController！");
								//d444("公元物业总公司");
								//pie("清远凤城郦都",KeepViewTitle);
								d555("公元物业总公司");
								hugeData("公元物业总公司");
								getEquipPreById(null);
								var searchKey = "清远凤城郦都";
								pie(searchKey);
								//获取公司信息
								services.getCompanyInfo().success(function(data){
									equipment.companys = data.result;
									console.log(equipment.companys);
									// 根据公司id获取所属项目信息
									services.selectProjectByCompId({
										searchKey : equipment.companys[0].comp_id
									}).success(function(data) {
										equipment.projects = data.project;
									}) ;
								});
								/*// 根据公司id获取所属项目信息
								services.selectProjectByCompId({
									searchKey : comp_id
								}).success(function(data) {
									equipment.projects = data.project;
									console.log(equipment.projects);
								}) ;*/
								equipment.KeepViewTitle = searchKey;
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
									searchKey : null
								}).success(function(data) {
									equipment.projwarning = data.data;
									equipment.warningTitle = "报警记录";
									equipment.warning = equipment.projwarning
									warning(equipment.projwarning);
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
							        html += '<div class="item"v>'+timestampToTime(data[i].alarm_log_date.time)+' '+data[i].alarm_log_info+'</div>';
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