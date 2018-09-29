var app = angular
		.module(
				'equipmentApp',
				[ 'ngRoute', 'angularFileUpload' ],
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
	$routeProvider.when('/equipBaseInfo', {
		templateUrl : '/gywyext/jsp/equip/equipBaseInfo.html',
		controller : 'equipmentController'
	}).when('/equipAdd', {
		templateUrl : '/gywyext/jsp/equip/equipAdd.html',
		controller : 'equipmentController'
	}).when('/equipRoomAdd', {
		templateUrl : '/gywyext/jsp/equip/equipRoomAdd.html',
		controller : 'equipmentController'
	}).when('/equipDetail', {
		templateUrl : '/gywyext/jsp/equip/equipDetail.html',
		controller : 'equipmentController'
	}).when('/equipUpdate', {
		templateUrl : '/gywyext/jsp/equip/equipUpdate.html',
		controller : 'equipmentController'
	}).when('/leftInit', {
		templateUrl : '/gywyext/jsp/equip/equipBaseInfo.html',
		controller : 'equipmentController'
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

	// 删除
	services.deleteEquipmentInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/deleteEquipmentInfo.do',
			data : data
		});
	};

	// 根据页数获取设备信息
	services.getEquipmentListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipmentListByPage.do',
			data : data
		});
	};
	// ceshi
	services.getEquipmentListByRS = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipmentListByRS.do',
			data : data
		});
	};
	// 添加设备信息
	services.addEquipment = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/addEquipment.do',
			data : data
		});
	};
	// 修改设备信息
	services.updateEquipmentById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/updateEquipmentById.do',
			data : data
		});
	};
	// 根据id获取设备信息
	services.selectEquipmentById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/selectEquipmentById.do',
			data : data
		});
	};
	// 根据proj_id查找设备
	services.selectBaseInfoByProj = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/selectBaseInfoByProj.do',
			data : data
		});
	};
	// 添加设备安装位置信息
	services.addEquipRoom = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/addEquipRoom.do',
			data : data
		});
	};

	// 获取设备分类信息
	services.getEquipTypeInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipTypeInfo.do',
			data : data
		});
	};

	// 获取用户信息
	services.getUserInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getUserInfo.do',
			data : data
		});
	};

	/*
	 * //获取设备制造商信息 services.getEquipManuInfo = function(data) { return $http({
	 * method : 'post', url : baseUrl + 'equipEquipment/getEquipManuInfo.do',
	 * data : data }); };
	 */

	// 根据设备id查找设备特征参数
	services.getEquipPara = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipPara.do',
			data : data
		});
	};

	// 根据项目查找安装位置信息
	services.selectEquipRoomByProj = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/selectEquipRoomByProj.do',
			data : data
		});
	};

	return services;
} ]);
app
		.controller(
				'equipmentController',
				[
						'$scope',
						'services',
						'$location',
						'FileUploader',
						function($scope, services, $location, FileUploader) {
							var equipment = $scope;
							var equip_rooms;
							var equip_types;
							var equip_para;
							var users;
							var pa = 1

							// 换页
							function pageTurn(totalPage, page, Func) {
								$(".tcdPageCode").empty();

								var $pages = $(".tcdPageCode");
								if ($pages.length != 0) {
									$(".tcdPageCode").createPage({
										pageCount : totalPage,
										current : page,
										backFn : function(p) {
											pa = p;
											Func(p);
										}
									});
								}
							}

							// 根据页数获取设备列表
							function getEquipmentListByPage(page) {
								services.getEquipmentListByPage({
									page : page,
									searchKey : searchKey
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							}

							// 根据限制条件获取设备信息
							function getEquipmentListByRS(page) {
								services.getEquipmentListByRS({
									page : page,
									eqRoom : eqRoom,
									eqState : eqState,
									proj_id : proj_id
								}).success(function(data) {
									equipment.equipments = data.list;
									pageTurn(data.totalPage,page,getEquipmentListByRS);
								});
							}
							// 根据设备名称筛选设备信息
							equipment.selectEquipmentByName = function() {
								searchKey = equipment.equipName;
								services.getEquipmentListByPage({
									page : 1,
									searchKey : searchKey
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							};

							// 根据room，state筛选设备信息
							equipment.selectEquipmentByRS = function() {
								eqRoom = equipment.equipRoomm;
								eqState = equipment.equipStatee;
								services.getEquipmentListByRS({
									page : 1,
									eqRoom : eqRoom,
									eqState : eqState,
									proj_id : equipment.selectBaseInfoProj_id									
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							};


							// 删除设备信息
							equipment.deleteEquipmentInfo = function(equip_id) {
								if (confirm("是否删除该设备信息？") == true) {
									services.deleteEquipmentInfo({
										equipmentId : equip_id
									}).success(function(data) {
										equipment.result = data;
										alert("删除设备信息成功！");
										$location.path('equipBaseInfo/');
									});
								}
							}

							// 添加设备信息
							equipment.addEquipment = function() {
								if (!compareDateTime(
										equipment.equipmentInfo.equip_pdate,
										equipment.equipmentInfo.equip_udate,
										equipment.equipmentInfo.equip_ndate)) {
									alert("请输入正确的时间")
									return;
								}
								;
								equipment.para = {
									paraname : [],
									paravalue : [],
									parare : [],
									paraunit : []
								}
								$("input[name='paraname']").each(function() {
									equipment.para.paraname.push($(this).val());
								})
								$("input[name='paravalue']").each(function() {
									equipment.para.paravalue.push($(this).val());
								})
								$("input[name='parare']").each(function() {
									equipment.para.parare.push($(this).val());
								})
								$("input[name='paraunit']").each(function() {
									equipment.para.paraunit.push($(this).val());
								})
								alert(sessionStorage.getItem("picFile"))
								if (sessionStorage.getItem("picFile") != null) {
									console.log(sessionStorage.getItem("picFile"));
									equipment.equipmentInfo.file_id = JSON.parse(sessionStorage.getItem("picFile")).fileBean.file_id;
								}

								var equipmentpara = JSON.stringify(equipment.para);
								var equipmentFormData = JSON.stringify(equipment.equipmentInfo);
								if (confirm("是否添加该设备信息？") == true) {
									services.addEquipment({
										equipment : equipmentFormData,
										equipmentpara : equipmentpara
									}).success(function(equipment) {
										alert("添加成功！")
										$location.path('equipBaseInfo/');
									});
								}
							}
							//
							// // 添加设备位置信息
							// equip_room.addEquipRoom = function() {
							// var equiproomFormData =
							// JSON.stringify(equip_room.equiproomInfo);
							// if (confirm("是否添加该安装位置信息？") == true) {
							// services.addEquipRoom({
							// equip_room : equiproomFormData
							// }).success(function(equip_room) {
							// alert("添加成功！")
							// $location.path('equipAdd/');
							// });
							// }
							// }

							// 查看ID，并记入sessionStorage
							equipment.getEquipmentId = function(equipmentId) {
								sessionStorage.setItem('equipmentId',equipmentId);
							};
							// 读取设备信息
							equipment.selectEquipmentById = function(equipmentId) {
								var equip_id = sessionStorage.getItem('equipmentId');
								services.selectEquipmentById({
									equip_id : equipmentId
								}).success(function(data) {
									equipment.equipment = data.equipment;
								});
							};

							// 修改设备信息
							equipment.updateEquipment = function() {
								if (!compareDateTime(
										equipment.equipmentInfo.equip_pdate,
										equipment.equipmentInfo.equip_udate,
										equipment.equipmentInfo.equip_ndate)) {
									alert("请检查输入时间")
									return;
								}
								;
								equipment.para = {
										paraname : [],
										paravalue : [],
										parare : [],
										paraunit : []
									}
									$("input[name='paraname']").each(
											function() {
												equipment.para.paraname.push($(this).val());
											})
									$("input[name='paravalue']").each(
											function() {
												equipment.para.paravalue.push($(this).val());
											})
									$("input[name='parare']").each(function() {
										equipment.para.parare.push($(this).val());
									})
									$("input[name='paraunit']").each(
											function() {
										equipment.para.paraunit.push($(this).val());
											})
								 var equipmentpara =JSON.stringify(equipment.para);
								alert(equipmentpara)
								 var EqFormData = JSON.stringify(equipment.equipmentInfo);
								if (confirm("是否修改该设备信息？") == true) {
									services.updateEquipmentById({
										equipment : EqFormData,							
									   equipmentpara : equipmentpara									
									}).success(function(data) {
										alert("修改成功！")
										$location.path('equipBaseInfo/');
									})
								}
							}

							// 查看设备详细信息
							equipment.getEquipmentDetail = function(e) {
								var equipmentDetail = JSON.stringify(e);
								sessionStorage.setItem('equipmentDetail',equipmentDetail);
								$location.path("/equipDetail");
							}

							// 根据proj_id查找设备基本信息			
							equipment.selectBaseInfoByProj = function(proj_id) {
								sessionStorage.setItem("proj_id", proj_id); // 新建中的设备获取用到
								equipment.selectBaseInfoProj_id = proj_id;
								services.selectBaseInfoByProj({
									page : 1,
									proj_id : proj_id
								}).success(function(data) {
									console.log('打印项目包含的设备', data);
									equipment.equipments = data.equipment;
									equipment.equiproom_p = data.room;
								});
							}
						
							function selectBaseInfoByProjPag(page) {
								services.selectBaseInfoByProj({
									page : page,
									proj_id : equipment.selectBaseInfoProj_id
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							}

							
						/*	 //读取设备安装位置信息 
							 equip_room.selectEquipRoomById =
							  function(proj_id) { proj_id =
							  sessionStorage.getItem('proj_id');
							  services.selectEquipmentById( proj_id
							  ).success(function(data) { equip_rooms.equip_room =
							  data.equip_room; 
							  }); 
							  };*/
							 

							// 时间样式转化
							function changeDateType(date) {
								if (date != "") {
									var DateTime = new Date(date.time).toLocaleDateString().replace(/\//g, '-');
								} else {
									var DateTime = "";
								}
								return DateTime;
							}
							// 小数位数修正
							function changeFloat(f) {
								if (!f) {
									var num = parseFloat('0').toFixed(2);
								} else {
									var num = parseFloat(f).toFixed(2);
								}
								return num;
							}
							// 初始化
							function initPage() {
								console.log("初始化成功equipmentController！")
								if ($location.path().indexOf('/equipBaseInfo') == 0) {
									if (sessionStorage.getItem('leftData')) {
										equipment.leftData = JSON.parse(sessionStorage.getItem('leftData'));
									}
									searchKey = null;
									services.getEquipmentListByPage({
												page : 1,
												searchKey : searchKey
											}).success(
													function(data) {
														equipment.equipments = data.list;
														pageTurn(
																data.totalPage,
																1,
																getEquipmentListByPage);
													});
									
									eqRoom = null;
									eqState = null;
									services.getEquipmentListByRS({
												page : 1,
												eqRoom : eqRoom,
												eqState : eqState
											})
											.success(
													function(data) {
														equipment.equipments = data.list;
														pageTurn(
																data.totalPage,
																1,
																getEquipmentListByRS);
													});									
								} else if ($location.path().indexOf('/equipUpdate') == 0) {
									var equip_id = sessionStorage.getItem("equipmentId");
									services.selectEquipmentById({
												equip_id : equip_id
											}).success(
													function(data) {
														equipment.equipmentInfo = data.equipment;
														equipment.equipmentInfo.equip_type = data.equipment.equip_type.equip_type_id;
														equipment.equipmentInfo.equip_room = data.equipment.equip_room.equip_room_id;
														equipment.equipmentInfo.user = data.equipment.user.user_id;
														if (data.equipment.equip_pdate) {
															equipment.equipmentInfo.equip_pdate = changeDateType(data.equipment.equip_pdate);
														}
														if (data.equipment.equip_udate) {
															equipment.equipmentInfo.equip_udate = changeDateType(data.equipment.equip_udate);
														}
														if (data.equipment.equip_ndate) {
															equipment.equipmentInfo.equip_ndate = changeDateType(data.equipment.equip_ndate);
														}
														if (data.equipment.equip_bfee) {
															equipment.equipmentInfo.equip_bfee = changeFloat(data.equipment.equip_bfee);
														}
													});
									var proj_id = sessionStorage.getItem('proj_id');
									services.selectEquipRoomByProj({
												proj_id : proj_id
											}).success(
													function(data) {
														equipment.equip_rooms = data.equip_room;
													})
									services.getEquipTypeInfo().success(
											function(data1) {
												equipment.equip_types = data1.result;
											})
									services.getUserInfo().success(
											function(data2) {
												equipment.users = data2.result;
											})
									services.getEquipPara({
										equip_id : equip_id
									}).success(function(data) {
										if (data.error) {
											alert(data.error);
											history.go(-1);
											return;
										}
										equipment.equipmentPara = data.result;
										console.log(equipment.equipmentPara);
									})
								} else if ($location.path().indexOf('/equipAdd') == 0) {
									var proj_id = sessionStorage.getItem('proj_id');
									services.selectEquipRoomByProj({
										proj_id : proj_id
									}).success(function(data) {							
										 equipment.equip_rooms = data.equip_room;
										 })
									services.getEquipTypeInfo().success(
											function(data1) {
										equipment.equip_types = data1.result;
										})
									services.getUserInfo().success(
											function(data2) {
										equipment.users = data2.result;
										})
								} else if ($location.path().indexOf('/leftInit') == 0) {
									services.getInitLeft({}).success(
													function(data) {
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

														equipment.leftData = dest;
														var leftData = JSON.stringify(dest)
														sessionStorage.setItem('leftData',leftData);

														searchKey = null;
														services.getEquipmentListByPage(
																		{
																			page : 1,
																			searchKey : ""
																		})
																.success(
																		function(data) {
																			equipment.equipments = data.list;
																			pageTurn(
																					data.totalPage,
																					1,
																					getEquipmentListByPage);

																		});

													});
									
									equipment.equipRoomm = "0";
									equipment.equipStatee = "0";
									var eqRoom = JSON.stringify(equipment.equipRoomm) ;
									eqState = JSON.stringify(equipment.equipStatee)
									services.getEquipmentListByRS({
										page : 1,
										eqRoom : eqRoom,
										eqState : eqState
									})
									.success(
											function(data) {
												equipment.equipments = data.list;
												pageTurn(
														data.totalPage,
														1,
														getEquipmentListByRS);
											});
								} else if ($location.path().indexOf('/equipDetail') == 0) {
									equipment.equipmentDetail = JSON.parse(sessionStorage.getItem('equipmentDetail'));
									equipment.leftData = JSON.parse(sessionStorage.getItem('leftData'));
									services.getEquipPara(
													{
														equip_id : equipment.equipmentDetail.equip_id
													}).success(
													function(data) {
														if (data.error) {
															alert(data.error);
															history.go(-1);
															return;
														}
														equipment.equipmentPara = data.result;
													})
								}
							}

							initPage();
						} ]);

app
		.controller(
				"UploadController",
				[
						'$scope',
						'FileUploader',
						function($scope, FileUploader) {
							/* ！！！上传文件 */
							var uploader = $scope.uploader = new FileUploader({
								url : '/gywyext/file/upload.do',
							});
							var fileSizeNum = 0;
							$scope.fileBean;

							// FILTERS

							uploader.filters.push({
								name : 'customFilter',
								fn : function(
										item /* {File|FileLikeObject} */,
										options) {
									return this.queue.length < 10;
								}
							});

							$scope.picture;

							$scope.uploadAll = function() {
								if (fileSizeNum >= 31457280) {
									alert("一次性文件上传量不能超过30M");
									return false;
								} else {
									uploader.uploadAll();
								}
							}
							// CALLBACKS

							uploader.onWhenAddingFileFailed = function(
									item /* {File|FileLikeObject} */, filter,options) {
								console.info('onWhenAddingFileFailed', item,filter, options);
							};
							uploader.onAfterAddingFile = function(fileItem) {
								fileSizeNum += fileItem.file.size;
								console.info('onAfterAddingFile', fileItem);
							};
							uploader.onAfterAddingAll = function(addedFileItems) {
								console.info('onAfterAddingAll',addedFileItems);
							};
							uploader.onBeforeUploadItem = function(item) {
								var a = 1;
								if (a == 0) {
									return true;
								} else {
									return false;
								}
								console.info('onBeforeUploadItem', item);
							};
							uploader.onProgressItem = function(fileItem,progress) {
								console.info('onProgressItem', fileItem,progress);
							};
							uploader.onProgressAll = function(progress) {
								console.info('onProgressAll', progress);
							};
							uploader.onSuccessItem = function(fileItem,response, status, headers) {
								console.info('onSuccessItem', fileItem,response, status, headers);
							};
							uploader.onErrorItem = function(fileItem, response,status, headers) {
								console.info('onErrorItem', fileItem, response,status, headers);
							};
							uploader.onCancelItem = function(fileItem,response, status, headers) {
								console.info('onCancelItem', fileItem,response, status, headers);
							};
							uploader.onCompleteItem = function(fileItem,response, status, headers) {
								console.info('onCompleteItem', fileItem,response, status, headers);
								$scope.fileBean = response
								console.log("picFile"+ JSON.stringify(response))
								sessionStorage.setItem("picFile", JSON.stringify(response));
							};
							uploader.onCompleteAll = function() {
								alert("文件上传成功！");
							};
							console.info('uploader', uploader);
							/* ！！！上传文件完 */
						} ]);

// 时间戳转换
app.filter('timer', function() {
	return function(input) {
		var type = "";
		if (input != null) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}

		return type;
	}
});

// 电话格式判断
function checkTel(obj) {
	var tel = document.getElementById("equipmentInfo.equip_tel").value;
	if (!((/^0\d{2,3}-?\d{7,8}$/.test(tel)) || (/^1(3|4|5|7|8)\d{9}$/.test(tel)))) {
		alert("电话格式有误，请重填!");
		obj.value = '';
	}
}
// 只允许输入两位小数的正则判断
function changeTwoNum(obj) {
	// 清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/[^\d.]/g, "");

	// 验证第一个字符是数字而不是.
	obj.value = obj.value.replace(/^\./g, "");

	// 只保留第一个. 清除多余的
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");

	// 只能输入两个小数
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
}

// 没有输入详情显示为空
app.filter('sgFilter', function() {
	return function(input) {
		if (input == "" || input == null) {
			var input = "";
			return input;
		} else {
			return input;
		}
	}
});

// 判断输入时间逻辑是否正确
function compareDateTime(equip_pdate, equip_udate, equip_ndate) {
	var date1 = new Date(equip_pdate);
	var date2 = new Date(equip_udate);
	var date3 = new Date(equip_ndate);
	if (date1.getTime() <= date2.getTime()
			&& date2.getTime() <= date3.getTime()) {
		return true;
	} else {
		return false;
	}
}
