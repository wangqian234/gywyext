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
function try1(xdata,ydata,elsedata,divid,color){
			var x = [];
			var y = [];
			var l =0;
			var pp =0;
			divid.clear();
			clearInterval(interval);
			function addData(shift){
			    x.push(xdata[l]);
			    if(ydata[l]==null)
					y.push(0);
				else{//水泵房湿度数值处理
				    if(elsedata.equip_para_id == 2 )
					    y.push((ydata[l]/10));
					    else if(elsedata.equip_para_id == 3 )
						    y.push((ydata[l]/10));
					    else  y.push(ydata[l]);
				    }
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
				        	color:color,
				        	fontSize:'12',
				        	fontFamily: 'lighter'    
				        },
			            left:'4%',
			            zlevel:13,
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
				        	 saveAsImage: {},
				            dataView: {readOnly: true},
				            magicType: {type: ['line','bar']},
				        },
				        right:'4%',
				    },
				    grid: {
				        left: '10%',
				        right: '15%',
				        zlevel: 13,
				        /*bottom: '25%',
				        top: '5%',
				        height: '23%',*/
				        /*containLabel: true,*/
				        /*z: 22*/
				    },
				    xAxis: {
				        type: 'category',
				        data: x,
				        zlevel: 13,
				        //坐标轴样式设置
				        axisLabel:{
				        	interArrivar:0,
				        	rotate:-20,
				        },
				        axisLine:{
				        	lineStyle:{
				        		color:color,
				        		width:1,
				        	},
				        	symbol:['none','arrow']
				        }
				    },
				    yAxis: {
				        type: 'value',
				        zlevel: 13,
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
				        		color:color,
				        		width:1,
				        	},
				        	symbol:['none','arrow']
				        },            
				        boundaryGap: [0, '10%'],
				    },
				    series: [{
				    	name:elsedata.equip_para_name,
				        data: y,
				        zlevel: 13,
				        type: 'line',
				        smooth: true,
				        itemStyle: {
				            // 点的颜色。
				            color: color
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
			    	        url:"/gywyext/equipEquipment/getEquipRealData.do",
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
					    	        url:"/gywyext/equipEquipment/getEquipRealData.do",
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

function try2(startDate,elsedata,divid,color){
	var xdata=[];
	var ydata=[];
	function getdata(startDate,equiparaId,divide){//获取特征参数实时数据
		$.ajax({
	        url:"/gywyext/equipEquipment/getEquipRealData.do",
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
		if(xdata.length>10)
			try1(xdata,ydata,elsedata,divid,color);
		else alert("该时间段的数据暂缺，请更换起始时间");
	}
	getdata(startDate,elsedata.equip_para_id,divide)
}

//以下为大屏专用echarts封装函数
/*function d444(data,KeepViewTitle){
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
		        x : 'center',
		        y : '280',
		        data:name,
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
		            name:' ',
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
		console.log(params.data.name);
		console.log(KeepViewTitle);
	})
	d4.hideLoading();
	
}*/
function d777(data){
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
function d888(radarResult,titlename){
	d8.clear();
	d8.showLoading({text:'正在缓冲...'});
	var healthstate = '';
	var reliability = '';
	var leftlife = '';
	var analysisResult = '';
    var result = [];
    for(var i=0;i<radarResult.length;i++){
    	result.push(radarResult[i]);
    }
    var dangerData={
        value : [0.5,0.5,0.3],
        name : '危险指标'
    };
    var mycolor = ['#ef4b4c', '#b1eadb'];
	option = {
	    title: {
	        text: titlename+'健康状态分析',
	        left: 'center',
	        textStyle: {
	            color: '#00ffee',
	            fontWeight:'lighter',
	        	fontSize:14,
	        }
	    },
	    tooltip:{
	    	formatter:function(params){
	    		if(params.value[0]<0.5)
	    			 healthstate= "设备健康指数低于警戒值，建议进行检修 ";
	    		if(params.value[1]<0.5)
    			     reliability= "设备可靠度低于警戒值，建议进行检修 ";
	    		if(params.value[2]<0.3)
	    			 leftlife= "设备剩余寿命低于警戒值，建议进行检修，考虑更换设备 ";
	    		if(healthstate !=null)
	    			analysisResult = healthstate;
	    		if(reliability !=null)
	    			analysisResult += reliability;
	    		if(leftlife !=null)
	    			analysisResult += leftlife;
	    		if ('' != analysisResult)
	    			return analysisResult;
	    		if(params.name == "分析结果") return "设备各项指数正常，运行良好";
	    	}
	    },
	    legend: {
	    	bottom: '3%',
	        left: '20%',
	        icon: 'circle',
	        data: ['危险指标','分析结果'],
	        textStyle : {
	        	color: function(params) {
	        		console.log(params);
                    var num = mycolor.length;
                    return mycolor[params.dataIndex % num]
                },
	        	},
	        },
	    radar: {
	    	radius:"60%",
	    	center: ['50%','62%'],
	        indicator: [
	            {name: '可靠度', max: 1,color:'#00ffee'},
	            {name: '健康指数', max: 1,color:'#00ffee'},
	            {name: '剩余寿命', max: 1,color:'#00ffee'},
	          
	        ],
	        splitNumber: 4,
	        axisLine: {
	            lineStyle: {
	                color: '#fff',
	                opacity: .2
	            }
	        },
	        splitLine: {
	            lineStyle: {
	                color: '#fff',
	                opacity: .2
	            }
	        },
	        splitArea: {
	            areaStyle: {
	                color: 'rgba(127,95,132,.3)',
	                opacity: 1,
	                shadowBlur: 45,
	                shadowColor: 'rgba(0,0,0,.5)',
	                shadowOffsetX: 0,
	                shadowOffsetY: 15,
	            }
	        }
	    },
	    series: [
	        {
	        	name:'',
	        	type: 'radar',
	            symbolSize: 0,
	            areaStyle: {
	                normal: {
	                    shadowBlur: 13,
	                    shadowColor: 'rgba(0,0,0,.2)',
	                    shadowOffsetX: 0,
	                    shadowOffsetY: 10,
	                    opacity: 1
	                }
	            },
	            data: [dangerData,
	            	{name : '分析结果',
	            	value : result[0],}],
	            color: mycolor
	        }
	    ]
	};
	d8.setOption(option);
	window.onresize = d8.resize;//自适应窗口大小
	d8.hideLoading();
}
function d999(alarmData){
	console.log(alarmData);
	d9.clear();
	d9.showLoading({text:'正在缓冲...'});
	var data = [];//柱状条长度
	var titlename = [];//柱状条左侧标题
	var content = [];//柱状条内容
	var myColor = ['#1089E7', '#F57474', '#56D0E3', '#F8B448', '#8B78F6'];//柱状条颜色
	for(var i=0;i<alarmData.length;i++){
		data.push(150);
		titlename.push(timestampToTime(alarmData[i].alarm_log_date.time));
		content.push(alarmData[i].alarm_log_info);
	}
	option = {
	    //backgroundColor: '#0e2147',
	    grid: {
	           left: '5%',
	           right: '4%',
	           bottom: '3%',
	           containLabel: true
	       },
	    xAxis: {
	        show: false
	    },
	    yAxis: [{
	        show: true,
	        data: titlename,
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
	        axisLabel: {
	            color: '#fff',
	            formatter: function(value, index) {
	                return [
	                    '{lg|' + (index + 1) + '}' + '{title|' + value + '} '
	                ].join('\n')
	            },
	            rich: {
	                lg: {
	                    backgroundColor: '#339911',
	                    color: '#fff',
	                    borderRadius: 3,
	                    // padding: 5,
	                    align: 'center',
	                    width: 15,
	                    height: 15
	                },
	            }
	        },


	    }],
	    series: [{
	        name: '条',
	        type: 'bar',
	        yAxisIndex: 0,
	        data: data,
	        barWidth: 15,
	        itemStyle: {
	            normal: {
	                barBorderRadius: 15,
	                color: function(params) {
	                    var num = myColor.length;
	                    return myColor[params.dataIndex % num]
	                },
	            }
	        },
	        label: {
	            normal: {
	                show: true,
	                position: 'inside',
	                formatter: function(params) {
	                    var num = alarmData.length;
	                    return content[params.dataIndex % num]
	                },
	            }
	        },
	    }, ]
	};
	d9.setOption(option);
	window.onresize = d9.resize;//自适应窗口大小
	d9.hideLoading();
}

//大数据分析
function hugeData(equip){
	//equip内容应当包括equip_id,equip_room_id,equip_name
	//数据处理完成之后调用 d777()，d888(),d999(),
	var data7 = [
                {value:10, name:'rose1'},{value:5, name:'rose2'},
                {value:15, name:'rose3'},{value:25, name:'rose4'},
                {value:20, name:'rose5'},{value:35, name:'rose6'},
                {value:30, name:'rose7'},{value:40, name:'rose8'}
	            ];
	var data8 =[[0.6,0.6,0.6]];
	var data9 =[{time:'2018-09-25'},
	            {time:'2018-10-15'}];
	//d777(data7);
	d888(data8,'');
	//d999(data9);
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

