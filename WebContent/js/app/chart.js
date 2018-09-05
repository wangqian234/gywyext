//
function try2(xdata,ydata){    
    //初始化echarts实例
	echart.clear();
    /*var base = + new Date(2017,3,8);*/
   /* var oneDay = 24*3600*1000;*/
    var date = [];
    /*var data = [Math.random()*150];*/
    var data = [];
    /*var now = new Date(base);*/
    var day = 30;
    /*function addData(shift){*/
    function addData(x,y,shift){
        /*now = [now.getFullYear(),now.getMonth()+1,now.getDate()].join('/');   */  
        date.push(x);      
        data.push(y);
        if (shift) {
            console.log(data);
            date.shift();
            data.shift();
        }
        /*now = new Date(+new Date(now)+oneDay);   */     
    }

    for (var i = 0; i < day; i++) {
    	var x = xdata[i];
    	var y = ydata[i];
        addData(x,y);
    }
    //设置图标配置项
    var option = {
        title:{
            text:'ECharts 30天内数据实时更新'
        },
        xAxis:{
            type:"category",
            boundaryGap:false,
            data:date
        },
        yAxis:{
            boundaryGap:[0,'100%'],
            type:'value'
        },
        series:[{
            name:'成交',
            type:'line',
            smooth:true, //数据光滑过度
            symbol:'none', //下一个数据点
            stack:'a',
            /*areaStyle:{
                normal:{  
                    color:'red'
                }
            },*/
            data:data
        }]
    }
    var j = 0;
    setInterval(function(){
    	for(var i=0;i<10;i++)
    	{
    		var x = xdata[j+i];
    		var y = ydata[j+i];
    		addData(x,y);
    		j = j+1;
    	}
        /*addData(true);*/
        echart.setOption({
            xAxis:{
                data:date
            },
            series:[{
                name:'成交',
                data:data
            }]
        });
    },1000)
    echart.setOption(option);
}


function try1(xdata,ydata){
								
			echart.clear();
			
			var option = {
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
				        type: 'category',
				        data: xdata
				    },
				    yAxis: {
				        type: 'value',
				    },
				    series: [{
				        data: ydata,
				        type: 'line',
				        smooth: true
				    }]
				};
			console.log(xdata[0]);
			echart.setOption(option);
		} 
//
//DynamicData+TimeAxis
function DDTA(data) {
}
function selectRealTimeData(xdata,ydata){
	echart.clear();
	/*function randomData(i) {
	    value = ydata[i];
	    return {
	        value: [
	           xdata[i].join('/'),
	            Math.round(value)
	        ]
	    }
	}*/
	function addData(xdata,ydata,i,shift){
        /*now = [now.getFullYear(),now.getMonth()+1,now.getDate()].join('/');*/        
        date.push(xdata[i]);        
        data.push(ydata[i]);
        if (shift) {
            console.log(data);
            date.shift();
            data.shift();
        }
    /*    now = new Date(+new Date(now)+oneDay);*/        
    }
	var data = [];
	var date = [];
	/*console.log(xdata);*/
	/*for (var i = 0; i < 63; i++) {
		data.push(randomData());
	}*/
	/*var value = null;*/
	for (var i = 0; i < 100; i++) {
	   /* data.push(randomData());*/
	    addData(xdata,ydata,i);
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
	            /*return date.getSeconds()+'/'+date.getMinutes()+'/'+date.getHours()+'/'+
	            date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];*/
	            return date.getFullYear()+'/'+(date.getMonth() + 1)+'/'+date.getDate()+'  '+
	            date.getHours()+':'+date.getMinutes()+':'+date.getSeconds()+' : '+params.value[1];
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
	        type: 'category',
	        data: date,
	        splitLine: {
	            show: false
	        },
	        /*splitNumber:10*/
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
	                     {name: '',yAxis: '',label: {
	                            normal: {
	                                formatter: '最小值'
	                            }
	                        }},
	                        {name: '',yAxis: '',label: {
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
	var j = 0;

	setInterval(function () {

	    /*for (var i = 0; i < 10; i++) {
	    	j = j+i;
	        data.shift();
	        data.push(randomData(j));
	        
	    }

	    echart.setOption({
	        series: [{
	            data: data
	        }]*/
	    
	    addData(true);
	    echart.setOption({
            xAxis:{
                data:date
            },
            series:[{
               /* name:'成交',*/
                data:data
            }]
        });
	    
	    /*});*/
	}, 2000);
	echart.setOption(option);
	echart.hideLoading();
	/*echarts1.setInterval();*/
}

//md
function try3(x_data,y_data){
	console.log("12");
	echart.clear();
	function Data(x,y) {
	    date.push(x);
	    data.push(y);
	}
	var date = [];
	var data = [];
	for (var i = 0; i < 10; i++) {
		if((i+1)!=10){
			if(x_data[i] != x_data[i+1])
			Data(x_data[i],y_data[i]);
		}
		else Data(x_data[i],y_data[i]);
			
	};
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
	        type: 'category',
	        data: date,
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
	            /*markLine: {
	                data: [
	                     {name: '',yAxis: '',label: {
	                            normal: {
	                                formatter: '最小值'
	                            }
	                        }},
	                        {name: '',yAxis: 450,label: {
	                            normal: {
	                                formatter: '额定值'
	                            }
	                        }},
	                        {name: '',yAxis: '',label: {
	                            normal: {
	                                formatter: '最大值'
	                            }
	                        }}
	                ],
	                itemStyle :{
	                	color:'blue',
	                }
	            }*/
	            
	    }]
	};
	echart.setOption(option);
	echart.setOption({
	    	xAxis:[{
	    		data:date
	    	}],
	        series: [{
	            data: data
	        }]
	    });
	/*echart.setOption(option);*/
	echart.hideLoading();
}
//md

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