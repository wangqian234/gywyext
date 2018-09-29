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
	}
	//可能是刷新作用
	services.refresh = function (data){
		return $http({
			method : 'post',
			url : '',
			data : data,
		})
	}
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
							equipment.main = false;
							equipment.main1 = true;
							//切换map和模拟动画的可见性
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
							}
							function d444(na){
								console.log("d444");
								d4.clear();
								d4.showLoading({text:'正在加载数据...'});
								var company=[{comp_id:'1',comp_name:'清远物业分公司'}];
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
							  }
							
							function d555(na){
								console.log("d555");
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
									    var geoCoordMap = {
									    		'清远物业分公司':[113.1,23.745],
									    		'东方巴黎':[113.7,23.9],
									    	    '凤城世家':[113.3,24.3],
									    	    '凤城郦都':[113.056,23.5435],

									    	};
									     //值控制圆点大小
									    	var goData = [
							                 [{name: '清远物业分公司'}, {id: 2,name: '清远物业分公司',value: 250}],
									    	    [{name: '清远物业分公司'}, {id: 1,name: '东方巴黎',value: 80}],
									    	    [{name: '清远物业分公司'},{id: 1,name: '凤城世家',value: 120}],
									    	    [{name: '清远物业分公司'},{id: 1,name: '凤城郦都',value:180}],
									    	    
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
									d444(params.data.name);
								});
								var ssss;
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
										selectsrc();
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
								});
									    
								   }); 
							}
							
							
							//报警信息滚动展示
					        function warningsNews(){
					        	var area = document.getElementById('d3');//获取div的id
//					        	var cont1 = document.getElementsByClassName('l1div')[0];//获取列表1的id
					        	var cont1 = $('.lidiv')[0];//获取列表1的id
					        	var cont2 = document.getElementById('l2');//获取列表2的id
					        	
					        	console.log(area);
								console.log(cont1);
								console.log(cont2);

					        	area.scrollTop = 0;
					        	// 克隆cont1给cont2
					        	cont2.innerHTML = cont1.innerHTML;
					        	var time = 50;
					        	var interval = setInterval(function(){
					        		if(area.scrollTop >= cont1.scrollHeight) {
					        	        area.scrollTop = 0;
					        	    }else {
					        	        area.scrollTop++;
					        	    }
					        	}, time);
					        	area.onmouseover = function () {
					        	    clearInterval(interval);
					        	};
					        	area.onmouseout = function () {
					        	    // 继续执行之前的定时器
					        	    interval = setInterval(function(){
						        		if(area.scrollTop >= cont1.scrollHeight) {
						        	        area.scrollTop = 0;
						        	    }else {
						        	        area.scrollTop++;
						        	    }
						        	}, time);
					        	};
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
							// 初始化
							function initPage() {
								console.log("初始化成功equipmentController！");
								d444("清远物业分公司");
								d555("清远物业分公司");
								hugeData("清远物业分公司");
								$.ajax({
							        url:"/gywyext/equipRealInfo/getEquipParaByName.do",
							        type:"post",
							        dataType: "json",
							        data: { searchKey: "A相线圈温度"},
							        success:function(data){
						        	try2("2018-09-00 20:52:00",data.result[0],d6)
										}
							    });
								services.getWaringNews({
									searchKey : null
								}).success(function(data) {
									equipment.warning = data.data;
									warningsNews();
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
						} ]);