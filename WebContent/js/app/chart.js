function Chart(data) {
	this.elementId = data.elementId;
	this.title = data.title;
	this.name = data.name;
	this.data = data.data;
	this.subtitle=data.subtitle;
}

Chart.prototype.init = function() {
	$(this.elementId)
			.highcharts(
					{
						credits:{
							text:'',
							href:''
						},
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : this.title
						},
						subtitle:{
							text : this.subtitle,
					        style:{
					        	color:"red"
					        }
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled : true,
									format : '<b>{point.name}</b>: {point.percentage:.1f} %',
									style : {
										color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
												|| 'black'
									}
								}
							}
						},
						series : [ {
							type : 'pie',
							name : this.name,
							data : this.data
						} ]
					});
}



//
var interval;
function try1(xdata,ydata,elsedata,divid){
			var x = [];
			var y = [];
			var l =0;
			var pp =0;
			divid.clear();
			clearInterval(interval);
			function addData(shift){
			    x.push(xdata[l]);
			    //水泵房湿度数值处理
			    if(elsedata.equip_para_id == 2 )
			    y.push((ydata[l]/10));
			    else if(elsedata.equip_para_id == 3 )
				    y.push((ydata[l]/10));
			    else  y.push(ydata[l]);
			    if (shift) {
			        x.shift();
			        y.shift();
			    }
			}
			function divide(data){
				for(var i = 0;i<data.length;i++){
					var x = data[i].equip_oper_time;
					var y = data[i].equip_oper_info;
					xdata.push(x);
					ydata.push(y);
				};
				pp = 0;
			}
			for (var i = 0; i < 50; i++) {
			    addData();
			    l++
			}
			
			var option = {
					title: {
				        text: elsedata.equip_para_name+'实时数据',
				        textStyle:{
				        	color:'white',
				        	fontSize:'12',
				        	fontFamily: 'lighter'    
				        }
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer: {
				        	type: 'cross',
				            /*label: {
				                backgroundColor: '#6a7985'
				            }*/
				        }
				    },
				    /*legend: {
				          data: [elsedata.equip_para_name],
				          x: 'center',
			        	  textStyle:{
					        	color:'white',
					        	fontFamily: 'lighter'    
					        } 
				      },*/
					toolbox: {
				        show: true,
				        feature: {
				            dataView: {readOnly: true},
				            magicType: {type: ['line','bar']},
				        }
				    },
				    grid: {
				        left: '10%',
				        right: '15%',
				        /*bottom: '25%',
				        top: '5%',
				        height: '23%',*/
				        /*containLabel: true,*/
				        /*z: 22*/
				    },
				    xAxis: {
				        type: 'category',
				        data: x,
				        //坐标轴样式设置
				        axisLabel:{
				        	interArrivar:0,
				        	rotate:-15,
				        },
				        axisLine:{
				        	lineStyle:{
				        		color:'#00ffee',
				        		width:1,
				        	},
				        	symbol:['none','arrow']
				        }
				    },
				    yAxis: {
				        type: 'value',
				        name: '单位：'+elsedata.equip_para_unit,
				      //y轴横线样式设置
				        splitLine:{
				        	show:false,
				        },
				        axisLabel:{
				        	interArrivar:0,
				        },
				        axisLine:{
				        	lineStyle:{
				        		/*color:'#00ffee',*/
				        		color:'#00ffee',
				        		width:1,
				        	},
				        	symbol:['none','arrow']
				        },            
				        boundaryGap: [0, '10%'],
				    },
				    series: [{
				    	name:elsedata.equip_para_name,
				        data: y,
				        type: 'line',
				        smooth: true,
				        itemStyle: {
				            // 点的颜色。
				            color: '#00feff'
				        },
				        markPoint: {
				                data: [
				                    {type: 'max', name: '最大值'},
				                    {type: 'min', name: '最小值'}
				                ],
				                itemStyle :{
				                	color:'red',
				                }
				            },
				            markLine: {
				                data: [
				                     {name: '',yAxis: elsedata.equip_para_min,label: {
				                            normal: {
				                                formatter: '最小值'
				                            }
				                        }},
				                        {name: '',yAxis: elsedata.equip_para_max,label: {
				                            normal: {
				                                formatter: '最大值'
				                            }
				                        }}
				                ],
				                itemStyle :{
				                	color:'blue',
				                }
				            }
				    }]
				};
			    interval = setInterval(function () {
				if(l >= xdata.length)
					clearInterval(interval);
			    addData(true);
			    l++;
			    if(pp == 0){
			    	if((l+50) >xdata.length){
			    		pp = 1;
			    		$.ajax({
			    	        url:"/gywyext/equipRealInfo/getEquipRealData.do",
			    	        type:"post",
			    	        dataType: "json",
			    	        data: { searchKey: elsedata.equip_para_id, startDate: xdata[xdata.length-1] },
			    	        success:function(data){
			    	        	divide(data.data);
			    	        }
			    	    })
			    	    }
			    }
			    divid.setOption({
			        xAxis: {
			            data: x
			        },
			        series: [{
			            data: y
			        }]
			    });
			}, 800);
		    var ppp=1;
		    divid.on('mouseover',function(param){
		    	console.log(param);
		        if (param!=null||ppp==1) {
		            ppp=2;
		            clearInterval(interval);
		        }
		    })
		    divid.on('click',function(param){
		    	if (param!=null||ppp==2) {
		            ppp=1;
		            clearInterval(interval);
		            interval = setInterval(function () {
						if(l >= xdata.length)
							clearInterval(interval);
					    addData(true);
					    l++;
					    if(pp == 0){
					    	if((l+50) >xdata.length){
					    		pp = 1;
					    		$.ajax({
					    	        url:"/gywyext/equipRealInfo/getEquipRealData.do",
					    	        type:"post",
					    	        dataType: "json",
					    	        data: { searchKey: elsedata.equip_para_id, startDate: xdata[xdata.length-1] },
					    	        success:function(data){
					    	        	divide(data.data);
					    	        }
					    	    })
					    	    }
					    }
					    divid.setOption({
					        xAxis: {
					            data: x
					        },
					        series: [{
					            data: y
					        }]
					    });
					}, 800);
		        }
		    })
			divid.setOption(option);
			divid.hideLoading();			
		} 

function try2(startDate,elsedata,divid){
	console.log("try2");
	var xdata=[];
	var ydata=[];
	function getdata(startDate,equiparaId,divide){//获取特征参数实时数据
		$.ajax({
	        url:"/gywyext/equipRealInfo/getEquipRealData.do",
	        type:"post",
	        dataType: "json",
	        data: { searchKey: equiparaId, startDate: startDate },
	        success:function(data){
	        	if(typeof divide == 'function'){
	        		divide(data.data);
				}
	        }
	    })
	}
	function divide(data){//数据处理
		for(var i = 0;i<data.length;i++){
			var x = data[i].equip_oper_time;
			var y = data[i].equip_oper_info;
			xdata.push(x);
			ydata.push(y);
		};
		try1(xdata,ydata,elsedata,divid);
	}
	getdata(startDate,elsedata.equip_para_id,divide)
}

//以下为大屏专用echarts封装函数
function d777(data){
	console.log("d777");
	d7.clear();
	d7.showLoading({text:'正在缓冲...'});
	var xData = [],
	    yData = [];
	for(var i=0;i<data.length;i++){
		xData.push(data[i].name);
		yData.push(data[i].value);
	}
	
	var option = {  
			title: {
		        text: '故障种类分析',
		        left: 'left',
		        textStyle:{
		        	color:"#00ffee",
		        	fontWeight:'lighter',
    	        	  fontSize:12,
		        }
		    },
			grid: {
			        //left: '5%',
			        //right: '5%',
			        bottom: '5%',
			        top: '15%',
			        containLabel: true,
			        z: 22
			    },
			    xAxis: [{
			        type: 'category',
			        gridIndex: 0,
			        data: xData,
			        axisTick: {
			            alignWithLabel: true
			        },
			        axisLabel:{
			        	interArrivar:0,
			        	rotate:+30,
			        },
			        axisLine: {//x轴颜色设置
			            lineStyle: {
			                color: '#00ffee'
			            },
			            symbol:['none','arrow']
			        }
			    }],
			    yAxis: [{
			            type: 'value',
			            gridIndex: 0,
			            splitLine: {
			                show: false
			            },
			            axisTick: {
			                show: false
			            },
			            min: 0,
			            max: 50,
			            axisLine: {
			                lineStyle: {
			                    color: '#00ffee'
			                },
			                symbol:['none','arrow']
			            }
			        }
			    ],
			    calculable : true,
			    series : [{
		            name: '合格率',
		            type: 'bar',
		            barWidth: '30%',
		            xAxisIndex: 0,
		            yAxisIndex: 0,
		            itemStyle: {
		                normal: {
		                    barBorderRadius: 30,
		                    color: new echarts.graphic.LinearGradient(
		                        0, 0, 0, 1, [{
		                                offset: 0,
		                                color: '#00feff'
		                            },
		                            {
		                                offset: 0.5,
		                                color: '#027eff'
		                            },
		                            {
		                                offset: 1,
		                                color: '#0286ff'
		                            }
		                        ]
		                    )
		                },
			            label:{}
		            },
		            data: yData,
		            zlevel: 11

		        }]
	};
	d7.setOption(option);
	window.onresize = d7.resize;//自适应窗口大小
	d7.hideLoading();
}
function d888(data){
	console.log("d888");
	d8.clear();
	d8.showLoading({text:'正在缓冲...'});
	          var lineStyle = {
	              normal: {
	                  width: 1,
	                  opacity: 0.8
	              }
	          };

	          option = {
	              title: {
	                  text: '设备健康状态分析',
	                  left: 'left',
	                  textStyle: {
	                      color: '#00ffee',
	                      fontWeight:'lighter',
	      	        	  fontSize:12,
	                  }
	              },
	              radar: {
	                  indicator: [
	                      {name: '可靠度', max: 100,color:'#00ffee'},
	                      {name: '健康指数', max: 100,color:'#00ffee'},
	                      {name: '剩余寿命', max: 100,color:'#00ffee'},
	                  
	                  ],
	                  shape: 'polygon',
	                  splitNumber: 5,
	                  name: {
	                      textStyle: {
	                          color: '#fff'
	                      }
	                  },
	                  splitLine: {
	                      lineStyle: {
	                          color: [
	                              '#449cff',
	                          ]
	                      }
	                  },
	                  splitArea: {
	                      show: false
	                  },
	                  axisLine: {
	                      lineStyle: {
	                          color: '#449cff'
	                      }
	                  }
	              },
	              series: [
	                  {
	                      name: ' ',
	                      type: 'radar',
	                      lineStyle: lineStyle,
	                      data: data,
	                      symbol: 'none',
	                      itemStyle: {
	                          normal: {
	                              color: 'red'
	                          }
	                      },
	                      areaStyle: {
	                      normal: {
	                          color: {
	                              type: 'linear',
	                              x: 0,
	                              y: 0,
	                              x2: 0,
	                              y2: 1,
	                              colorStops: [{
	                                  offset: 0,
	                                  color: '#44ff86'
	                              }, {
	                                  offset: 1,
	                                  color: '#0060ff'
	                              }],
	                              globalCoord: false
	                          }
	                      }
	                  },
	                  }
	              ]
	          };
	d8.setOption(option);
	window.onresize = d8.resize;//自适应窗口大小
	d8.hideLoading();
}
function d999(data){
	console.log("d999");
	d9.clear();
	d9.showLoading({text:'正在缓冲...'});
	var value = [100,56];
	var title = ['上次维护时间','下次维护时间'];
	var Color1 = ['#F57474','#1089E7'];
	option = {
	    xAxis: {
	        show: false
	    },
	    //color: '#00ffee',
	    yAxis: [{
	        show: true,
	        data: title,
	        inverse: true,
	        axisLine: {
	            show: false
	        },
	        splitLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {color: '#00ffee',}
	    }],
	    grid: {
	        left: '5%',
	        right: '10%',
	        bottom: '25%',
	        top: '5%',
	        height: '50%',
	        width: '80%',
	        containLabel: true,
	        z: 22
			},
	    series: [{
	        name: '条',
	        type: 'bar',
	        yAxisIndex: 0,
	        data: value,
	        barWidth: 15,
	        itemStyle: {
	            normal: {
	                barBorderRadius: 30,
	                color: function(params) {
	                    var num = Color1.length;
	                    return Color1[params.dataIndex % num]
	                },
	            }
	        },
	        label: {
	            normal: {
	                show: true,
	                position: 'inside',
	                formatter: function(params) {
	                    var num = value.length;
	                    return data[params.dataIndex % num].time
	                },
	            }
	        },
	    }]
	};
	d9.setOption(option);
	window.onresize = d9.resize;//自适应窗口大小
	d9.hideLoading();
}

//大数据分析
function hugeData(equip){
	//equip内容应当包括equip_id,equip_room_id,equip_name
	//数据处理完成之后调用 d777()，d888(),d999(),
	
	console.log("hugeData");
	var data7 = [
                {value:10, name:'rose1'},{value:5, name:'rose2'},
                {value:15, name:'rose3'},{value:25, name:'rose4'},
                {value:20, name:'rose5'},{value:35, name:'rose6'},
                {value:30, name:'rose7'},{value:40, name:'rose8'}
	            ];
	var data8 =[[55,100,56]];
	var data9 =[{time:'2018-09-25'},
	            {time:'2018-10-15'}];
	d777(data7);
	d888(data8);
	d999(data9);
}

