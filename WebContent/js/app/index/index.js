var app = angular
		.module(
				'index',
				[ 'ngRoute' ],
				function($httpProvider) {// ngRoute引入路由依赖
					$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
					$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

					// Override $http service's default transformRequest
					$httpProvider.defaults.transformRequest = [ function(data) {
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

// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : '/gywyext/jsp/index/init.html',
		controller : 'indexController'
	})
} ]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};

	// 获取左侧菜单栏
	services.getInitLeft = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getInitLeft.do',
			data : data
		});
	};
	services.selectIndexData = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/selectIndexData.do',
			data : data
		});
	};
	services.selectIndexAlramLog = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/selectIndexAlramLog.do',
			data : data
		});
	}
	services.selectIndexMainEquip = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/selectIndexMainEquip.do',
			data : data
		});
	}
	services.selectIndexUnhealthEquip = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/selectIndexUnhealthEquip.do',
			data : data
		});
	}
	//根据项目id获得设备报警信息
	services.getProEquipAnalysis = function(data) {
		return $http({
			method : 'post',
			url : baseUrl +'alarmLog/getProEquipAnalysis.do',
			data : data
		});
	};
	return services;
} ]);

app.controller('indexController', [ '$scope', 'services', '$location',
		"$timeout", '$interval',
		function($scope, services, $location, $timeout, $interval) {
			var index = $scope;
			index.proId;
			index.alarmNum = 0;
			index.mainNum = 0;
			index.unhealthNum = 0;
			// 换页函数
			function pageTurn(totalPage, page, Func) {
				$(".tcdPageCode").empty();
				var $pages = $(".tcdPageCode");
				if ($pages.length != 0) {
					$(".tcdPageCode").createPage({
						pageCount : totalPage,
						current : page,
						backFn : function(p) {
							Func(p);
						}
					});
				}
			}
			// 获取左侧蓝菜单
			index.getLeftData = function() {
				services.getInitLeft({}).success(function(data) {
					console.log("zzzz" + JSON.stringify(data));
					var arr = data.leftResult;
					var map = {}, dest = [];
					for (var i = 0; i < arr.length; i++) {
						var ai = arr[i];
						if (!map[ai.comp_id]) {
							dest.push({
								comp_id : ai.comp_id,
								comp_name : ai.comp_name,
								data : [ ai ]
							});
							map[ai.comp_id] = ai;
						} else {
							for (var j = 0; j < dest.length; j++) {
								var dj = dest[j];
								if (dj.comp_id == ai.comp_id) {
									dj.data.push(ai);
									break;
								}
							}
						}
					}
					index.leftData = dest;
					var leftData = JSON.stringify(dest)
					sessionStorage.setItem('leftData', leftData);

					setTimeout(function() {
						var tt = $(".dd");
						console.log(tt[0]);
						tt[0].click();
					}, 10);

				});
			}
			// 点击项目触发事件
			index.selectBaseInfoByProj = function(str, name, $event) {
				index.projName = name;
				index.proId = str;
				services.selectIndexData({
					"proId" : str
				}).success(function(data) {
					index.alarmNum = data.alarmNum;
					index.mainNum = data.mainNum;
					index.unhealthNum = data.unhealthNum;
				});
				
				services.getProEquipAnalysis({
					proj_id : str
				}).success(function(data) {
					console.log("wangq"+data.analysis);
		        	d444(data.analysis);
				});
				
				$(".nav-second-level li").removeClass("liActive");
				var oObj = window.event.srcElement;
				var oTr = oObj.parentNode;
				oTr.className = "liActive";
			}

			index.selectIndexAlramLog = function() {

				$("#tipAdd").fadeIn(200);
				$(".overlayer").fadeIn(200);
				services.selectIndexAlramLog({
					"proId" : index.proId,
					"page" : 1,
				}).success(function(data) {

					index.alramLogList = data.list;
					pageTurn(data.totalPage, 1, selectIndexAlramLogList);
				});
			}

			function selectIndexAlramLogList(page) {
				services.selectIndexAlramLog({
					"proId" : index.proId,
					"page" : page,
				}).success(function(data) {

					index.alramLogList = data.list;

				});
			}

			index.selectIndexMainEquip = function() {

				$("#tipMainEquip").fadeIn(200);
				$(".overlayer").fadeIn(200);
				services.selectIndexMainEquip({
					"proId" : index.proId,
					"page" : 1,
				}).success(function(data) {

					index.mainEquipList = data.list;
					pageTurn(data.totalPage, 1, selectIndexMainEquipList);
				});
			}

			function selectIndexMainEquipList(page) {
				$("#tipMainEquip").fadeIn(200);
				$(".overlayer").fadeIn(200);
				services.selectIndexMainEquip({
					"proId" : index.proId,
					"page" : page,
				}).success(function(data) {

					index.mainEquipList = data.list;
				});
			}

			index.selectIndexUnhealthEquip = function() {

				$("#tipUnhealthEquip").fadeIn(200);
				$(".overlayer").fadeIn(200);
				services.selectIndexUnhealthEquip({
					"proId" : index.proId,
					"page" : 1,
				}).success(function(data) {

					index.unhealthEquipList = data.list;
					pageTurn(data.totalPage, 1, selectIndexUnhealthEquipList);
				});
			}

			function selectIndexUnhealthEquipList(page) {
				$("#tipUnhealthEquip").fadeIn(200);
				$(".overlayer").fadeIn(200);
				services.selectIndexUnhealthEquip({
					"proId" : index.proId,
					"page" : page,
				}).success(function(data) {

					index.unhealthEquipList = data.list;
				});
			}

			$("#cancelAdd").click(function() {
				$(".tip").fadeOut(200);
				$(".overlayer").fadeOut(200);
				taskHtml.task = ""

			});
			// 这里添加一个方法修改标志位，将该合同的任务由新接收任务改为未完成任务

			$(".tiptop a").click(function() {
				$(".overlayer").fadeOut(200);
				$(".tip").fadeOut(200);
			});

			$(".sure").click(function() {

				$(".overlayer").fadeOut(100);
				$(".tip").fadeOut(100);
			});

			$(".cancel").click(function() {
				$(".overlayer").fadeOut(100);
				$(".tip").fadeOut(100);
			});
			
			//饼图初始化
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

			// 初始化页面信息
			function initData() {
				console.log("初始化页面index！");
				if ($location.path().indexOf('/init') == 0) {

					index.type = "init";

					index.getLeftData();

				} else if ($location.path().indexOf('/equipPre') == 0) {

					index.type = "equipPre";
					index.getLeftData();
				} else {
					index.getLeftData();
				}
			}
			initData();
		} ]);

// 时间的格式化的判断
app.filter('dateType', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}

		return type;
	}
});
// 时间的格式化的判断
app.filter('DateTimeFormat', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = input.substr(0, 10);
		} else {
			type = "无";
		}

		return type;
	}
});

// 时间的格式化的判断
app.filter('EquipState', function() {
	return function(input) {
		var type = "";
		if (input == '状况非常差') {
			type = "正常";
		} else if (input == '1') {
			type = "状况差";
		} else if (input == '2') {
			type = "状态较差";
		} else {
			type = "状况差";
		}

		return type;
	}
});