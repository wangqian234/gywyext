var app = angular
		.module(
				'equipmentApp',
				[ 'ngRoute','angularFileUpload' ],
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
	
	//获取左侧菜单栏
	services.getInitLeft = function() {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getInitLeft.do',
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
	//根据proj_id查找设备
	services.selectBaseInfoByProj = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/selectBaseInfoByProj.do',
			data : data
		});
	};
	//添加设备安装位置信息
	services.addEquipRoom = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/addEquipRoom.do',
			data : data
		});
	};

	//获取设备分类信息
	services.getEquipTypeInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipTypeInfo.do',
			data : data
		});
	};	
	
/*	//获取设备制造商信息
	services.getEquipManuInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipManuInfo.do',
			data : data
		});
	};*/
	
	//添加设备特征参数信息
	services.addEquipPara = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/addEquipPara.do',
			data : data
		});
	};
	
	//根据设备id查找设备特征参数
	services.getEquipPara = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipPara.do',
			data : data
		});
	};

	//添加设备特征参数信息
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
							var equip_room = $scope;
							var equip_type = $scope;
							var equip_para = $scope;
							// 根据页数获取设备列表
							function getEquipmentListByPage(page) {
								services.getEquipmentListByPage({
									page : page,
									searchKey : searchKey
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							}
							equipment.count = [1];
//							equipment.test = function(){
//								equipment.count.push("1");
//							}
							// 换页
							function pageTurn(totalPage, page, Func) {
								$(".tcdPageCode").empty();
								var pa = 1
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
							equipment.equipmentInfo;
							// 添加设备信息
							equipment.addEquipment = function() {
//								if(!compareDateTime(equipment.equipmentInfo.equip_pdate,equipment.equipmentInfo.equip_udate,equipment.equipmentInfo.equip_ndate)){
//									alert("请输入正确的时间")
//									return;
//								};
								equipment.para = {
										paraname:[],
										paravalue:[],
										parare:[]
									}
								$("input[name='paraname']").each(function(){
									equipment.para.paraname.push($(this).val());
								})
								$("input[name='paravalue']").each(function(){
									equipment.para.paravalue.push($(this).val());
								})
								$("input[name='parare']").each(function(){
									equipment.para.parare.push($(this).val());
								})
								if(sessionStorage.getItem("PicFile")){
									console.log(sessionStorage.getItem("PicFile"));
									equipment.equipmentInfo.file_id = JSON.stringify(sessionStorage.getItem("PicFile")).file_id;
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

							// 添加设备位置信息
							equip_room.addEquipRoom = function() {
								var equiproomFormData = JSON.stringify(equip_room.equiproomInfo);
								if (confirm("是否添加该安装位置信息？") == true) {
								services.addEquipRoom({
									equip_room : equiproomFormData
								}).success(function(equip_room) {
									alert("添加成功！")
										$location.path('equipAdd/');
         						});
							}
							}	
							
							// 添加设备特征参数信息
							equip_para.addEquipPara = function() {
								var equipparaFormData = JSON.stringify(equip_para.equipparaInfo);			
								if (confirm("是否添加该属性？") == true) {
								services.addEquipPara({
									equip_para : equipparaFormData
								}).success(function(equip_para) {
										$location.path('equipAdd/');
         						});
							}
							}	
							// 点击新建按钮事件
							equip_para.addNewEquipPara = function(e) {
								preventDefault(e);
								equip_para.equipparaInfo = "";
								$(".overlayer").fadeIn(200);
								$(".tip").fadeIn(200);
								$("#addEquipPara-form").slideDown(200);
								};
								function preventDefault(e) {
									if (e && e.preventDefault) {
										// 阻止默认浏览器动作(W3C)
										e.preventDefault();
									} else {
										// IE中阻止函数器默认动作的方式
										window.event.returnValue = false;
										return false;
									}
								}
							
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
								if(!compareDateTime(equipment.equipmentInfo.equip_pdate,equipment.equipmentInfo.equip_udate,equipment.equipmentInfo.equip_ndate)){
									alert("请检查输入时间")
									return;
								};
								equipment.equipmentInfo.equip_type = equipment.equipmentInfo.equip_type.equip_type_id;
								equipment.equipmentInfo.equip_room = equipment.equipmentInfo.equip_room.equip_room_id;
								var EqFormData = JSON.stringify(equipment.equipmentInfo);
								if (confirm("是否修改该设备信息？") == true) {
								services.updateEquipmentById({
									equipment : EqFormData
								}).success(function(data) {
									    alert("修改成功！")
										$location.path('equipBaseInfo/');
									})
								}
							}
						
							//查看设备详细信息
							equipment.getEquipmentDetail = function(e){
								var equipmentDetail = JSON.stringify(e);
								sessionStorage.setItem('equipmentDetail', equipmentDetail);
								$location.path("/equipDetail");
							}

							//根据proj_id查找设备信息
							equipment.selectBaseInfoProj_id;							
							equipment.selectBaseInfoByProj = function(proj_id){
								sessionStorage.setItem("proj_id",proj_id);       //新建中的设备获取用到
								equipment.selectBaseInfoProj_id = proj_id;
								services.selectBaseInfoByProj({
									page : 1,
									proj_id : proj_id
								}).success(function(data) {
									equipment.equipments = data.equipment;
									equipment.equiproom_p = data.room;									
								});
							}
							equipment.test = function(){
								console.log($scope.fileBean);
							}
							function selectBaseInfoByProjPag(page){
								services.selectBaseInfoByProj({
									page : page,
									proj_id : equipment.selectBaseInfoProj_id
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							}
								
							// 读取设备安装位置信息
							equip_room.selectEquipRoomById = function(proj_id) {
								 proj_id = sessionStorage.getItem('proj_id');
								services.selectEquipmentById(
									proj_id
								).success(function(data) {
									equip_room.equip_room = data.equip_room;
								});
							};
						
                            //时间样式转化
                            function changeDateType(date) {
                            if (date != "") {
                            var DateTime = new Date(date.time).toLocaleDateString().replace(/\//g, '-');
                             } else {
	                           var DateTime = "";
                             }
	                           return DateTime;
                             }

							equipment.equip_state = 0;
							equipment.equip_room = 0;
													
							// 初始化
							function initPage() {
								console.log("初始化成功equipmentController！")

								if ($location.path().indexOf('/equipBaseInfo') == 0) {
									if(sessionStorage.getItem('leftData')){
										equipment.leftData = JSON.parse(sessionStorage.getItem('leftData'));
									}
									searchKey = null;
									services.getEquipmentListByPage({
										page : 1,
										searchKey : searchKey
									}).success(function(data) {
										equipment.equipments = data.list;
										pageTurn(
												data.totalPage,
												1,
												getEquipmentListByPage);

									});
								} else if ($location.path().indexOf('/equipUpdate') == 0) {
									var equip_id = sessionStorage.getItem("equipmentId");
									services.selectEquipmentById({
												equip_id : equip_id
									}).success(function(data) {
										equipment.equipmentInfo = data.equipment;
									if (data.equipment.equip_pdate) {
										equipment.equipmentInfo.equip_pdate = changeDateType(data.equipment.equip_pdate);
										}		
									if (data.equipment.equip_udate) {
										equipment.equipmentInfo.equip_udate = changeDateType(data.equipment.equip_udate);
										}
									if (data.equipment.equip_ndate) {
										equipment.equipmentInfo.equip_ndate = changeDateType(data.equipment.equip_ndate);
										}
									});
									var proj_id=sessionStorage.getItem('proj_id');
									services.selectEquipRoomByProj({
										proj_id : proj_id
									}).success(function(data){
										equip_room.equip_room = data.equip_room;
									})
									services.getEquipTypeInfo().success(function(data1){
										equip_type.equip_type = data1.result;
									})
								}else if ($location.path().indexOf('/equipAdd') == 0){
									var proj_id=sessionStorage.getItem('proj_id');
									services.selectEquipRoomByProj({
										proj_id : proj_id
									}).success(function(data){
										equip_room.equip_room = data.equip_room;
									})
									services.getEquipTypeInfo().success(function(data1){
										equip_type.equip_type = data1.result;
									})
								}else if ($location.path().indexOf('/leftInit') == 0) {
									services.getInitLeft().success(function(data) {
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
										sessionStorage.setItem('leftData', leftData);

									});
									searchKey = null;
									services.getEquipmentListByPage({
										page : 1,
										searchKey : ""
									}).success(function(data) {
										equipment.equipments = data.list;
										pageTurn(
												data.totalPage,
												1,
												getEquipmentListByPage);

									});
								} else if($location.path().indexOf('/equipDetail') == 0){
									equipment.equipmentDetail = JSON.parse(sessionStorage.getItem('equipmentDetail'));
									equipment.leftData = JSON.parse(sessionStorage.getItem('leftData'));
									services.getEquipPara({
										equip_id : equipment.equipmentDetail.equip_id
									}).success(function(data){
										if(data.error){
											alert(data.error);
											history.go(-1);
											return;
										}
										equipment.equipmentPara = data.result;
										console.log(equipment.equipmentPara);
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
					var fileSizeNum=0;
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

					$scope.uploadAll=function(){
							if(fileSizeNum>=31457280){
								alert("一次性文件上传量不能超过30M");
								return false;
							}else{
								uploader.uploadAll();
							}
					}
					// CALLBACKS

					uploader.onWhenAddingFileFailed = function(
							item /* {File|FileLikeObject} */, filter,
							options) {
						console.info('onWhenAddingFileFailed', item,
								filter, options);
					};
					uploader.onAfterAddingFile = function(fileItem) {
						fileSizeNum+=fileItem.file.size;
						console.info('onAfterAddingFile', fileItem);
					};
					uploader.onAfterAddingAll = function(addedFileItems) {
					
						console
								.info('onAfterAddingAll',
										addedFileItems);
					};
					uploader.onBeforeUploadItem = function(item) {
						var a=1;
						
						if(a==0){
							return true;
						}else{
							return false;
						}
						console.info('onBeforeUploadItem', item);
					};
					uploader.onProgressItem = function(fileItem,
							progress) {
						console.info('onProgressItem', fileItem,
								progress);
					};
					uploader.onProgressAll = function(progress) {
						console.info('onProgressAll', progress);
					};
					uploader.onSuccessItem = function(fileItem,
							response, status, headers) {
						console.info('onSuccessItem', fileItem,
								response, status, headers);
					};
					uploader.onErrorItem = function(fileItem, response,
							status, headers) {
						console.info('onErrorItem', fileItem, response,
								status, headers);
					};
					uploader.onCancelItem = function(fileItem,
							response, status, headers) {
						console.info('onCancelItem', fileItem,
								response, status, headers);
					};
					uploader.onCompleteItem = function(fileItem,
							response, status, headers) {
						console.info('onCompleteItem', fileItem,
								response, status, headers);
						$scope.fileBean = response
						sessionStorage.setItem("picFile",response);
					};
					uploader.onCompleteAll = function() {
						alert("文件上传成功！");
					};
					console.info('uploader', uploader);
					/* ！！！上传文件完 */
				} ]);

//时间戳转换
app.filter('timer', function() {
	return function(input) {
		var type = "";
		if (input != null) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}

		return type;
	}
});

//电话格式判断
function checkTel(obj){
	 var tel=document.getElementById("equipmentInfo.equip_tel").value;
	 if(!((/^0\d{2,3}-?\d{7,8}$/.test(tel))||(/^1(3|4|5|7|8)\d{9}$/.test(tel)))){
		 alert("电话格式有误，请重填!");
		 obj.value='';
	}
	}
//只允许输入两位小数的正则判断
function changeTwoNum(obj){
	//清除"数字"和"."以外的字符
	  obj.value = obj.value.replace(/[^\d.]/g,"");
	  
    //验证第一个字符是数字而不是.
	  obj.value = obj.value.replace(/^\./g,"");
	 
	//只保留第一个. 清除多余的
	  obj.value = obj.value.replace(/\.{2,}/g,".");
	  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	 
	//只能输入两个小数 
	  obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
}

//没有输入详情显示为空 
app.filter('sgFilter',function() { 
	return function(input){ 
		if(input == "" || input == null){
			var input = "空";
			return input; 		
		}
		else{
			return input;
		}
	}
});

	//判断输入时间是否正确
function compareDateTime(equip_pdate,equip_udate,equip_ndate) {
	var date1 = new Date(equip_pdate);
	var date2 = new Date(equip_udate);
	var date3 = new Date(equip_ndate);
	if (date1.getTime() <= date2.getTime() && date2.getTime()<= date3.getTime()) {
		return true;
	} else {
		return false;
	}
}


