function try1(xdata,ydata,elsedata){
							
	        console.log(xdata);
			echart.clear();
			var x = [];
			var y = [];
			var l =0;
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
			for (var i = 0; i < 100; i++) {
			    addData();
			    l++
			}
			
			var option = {
					title: {
				        text: elsedata.equip_para_name+'实时数据',
				    },
				    tooltip : {
				        trigger: 'axis',
				        axisPointer: {
				        	type: 'cross',
				            label: {
				                backgroundColor: '#6a7985'
				            }
				        }
				    },
				    legend: {
				          data: [elsedata.equip_para_name],
				          x: 'center'
				      },
					toolbox: {
				        show: true,
				        feature: {
				            dataZoom: {},
				            dataView: {readOnly: true},
				            magicType: {type: ['line','bar']},
				            restore: {},
				            saveAsImage: {}
				        }
				    },
				    dataZoom: [
				               {
				                   show: true,
				                   realtime: true,
				                   /*start: 65,
				                   end: 85*/
				               },
				               {
				                   type: 'inside',
				                   realtime: true,
				                  /* start: 65,
				                   end: 85*/
				               }
				           ],
				    xAxis: {
				        type: 'category',
				        data: x
				    },
				    yAxis: {
				        type: 'value',
				        name: '单位：'+elsedata.equip_para_unit,
				        boundaryGap: [0, '10%'],
				    },
				    series: [{
				    	name:elsedata.equip_para_name,
				        data: y,
				        type: 'line',
				        smooth: true,
				        itemStyle: {
				            // 点的颜色。
				            color: 'gray'
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
			setInterval(function () {
				console.log(l);
			    addData(true);
			    l++;
			    echart.setOption({
			        xAxis: {
			            data: x
			        },
			        series: [{
			            data: y
			        }]
			    });
			}, 1000);
			echart.setOption(option);
			echart.hideLoading();
		} 


/*selectRealTimeData = function(){
	echarts.clear();
	function randomData() {
	    now = new Date(+now + oneDay);
	    value = value + Math.random() * 21 - 10;
	    return {
	        name: now.toString(),
	        value: [
	            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
	            Math.round(value)
	        ]
	    }
	}

	var data = [];
	var now = +new Date(1997, 9, 3);
	var oneDay = 24 * 3600 * 1000;
	var value = Math.random() * 500;
	for (var i = 0; i < 1000; i++) {
	    data.push(randomData());
	}

	option = {
	    title: {
	        text: '某水箱实时压力曲线'
	    },
	    tooltip: {
	        trigger: 'axis',
	        formatter: function (params) {
	            params = params[0];
	            var date = new Date(params.name);
	            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
	        },
	        axisPointer: {
	            animation: false
	        }
	    },
	    legend: {
	          data: ['流量'],
	          x: 'center'
	      },
	       toolbox: {
	        show: true,
	        feature: {
	            dataZoom: {},
	            dataView: {readOnly: true},
	            magicType: {type: ['line','bar']},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'time',
	        splitLine: {
	            show: false
	        },
	        splitNumber:10
	    },
	    yAxis: {
	        type: 'value',
	        name: '单位:MPa',
	        boundaryGap: [0, '100%'],
	        splitLine: {
	            show: true
	        }
	    },
	    series: [{
	        name: '流量',
	        type: 'line',
	        showSymbol: false,
	        hoverAnimation: false,
	        data: data,
	        itemStyle: {
	            // 点的颜色。
	            color: 'gray'
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
	                     {name: '',yAxis: 250,label: {
	                            normal: {
	                                formatter: '最小值'
	                            }
	                        }},
	                        {name: '',yAxis: 450,label: {
	                            normal: {
	                                formatter: '额定值'
	                            }
	                        }},
	                        {name: '',yAxis: 700,label: {
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

	 setInterval(function () {

	    for (var i = 0; i < 10; i++) {
	        data.shift();
	        data.push(randomData());
	    }

	    echarts.setOption({
	        series: [{
	            data: data
	        }]
	    });
	}, 1000);
	echarts.setOption(option);
	echarts.hideLoading();
}
*/