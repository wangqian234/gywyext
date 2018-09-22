function try1(xdata,ydata,elsedata,divid){
			var x = [];
			var y = [];
			var l =0;
			var pp =0;
			divid.clear();
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
			for (var i = 0; i < 300; i++) {
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
				            /*dataZoom: {},*/
				            dataView: {readOnly: true},
				            magicType: {type: ['line','bar']},
				            /*restore: {},
				            saveAsImage: {}*/
				        }
				    },
				    /*dataZoom: [
				               {
				                   show: true,
				                   realtime: true,
				                   start: 65,
				                   end: 85
				               },
				               {
				                   type: 'inside',
				                   realtime: true,
				                   start: 65,
				                   end: 85
				               }
				           ],*/
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
				        		width:2,
				        	}
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
				        		color:'#00ffee',
				        		width:2,
				        	}
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
			var interval = setInterval(function () {
				if(l >= xdata.length)
					clearInterval(interval);
				console.log(l);
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
			}, 500);
			divid.setOption(option);
			divid.hideLoading();
			function divide(data){
				console.log(data);
				for(var i = 0;i<data.length;i++){
					var x = data[i].equip_oper_time;
					var y = data[i].equip_oper_info;
					xdata.push(x);
					ydata.push(y);
				};
				pp = 0;
			}
			
		} 

function try2(startDate,elsedata,divid){
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
		console.log(data);
		for(var i = 0;i<data.length;i++){
			var x = data[i].equip_oper_time;
			var y = data[i].equip_oper_info;
			xdata.push(x);
			ydata.push(y);
		};
		console.log(xdata);
		try1(xdata,ydata,elsedata,divid);
	}
	getdata(startDate,elsedata.equip_para_id,divide)
}

//以下为大屏专用echarts封装函数

function d444(){
	    $.get('../../../../mapjson/qingyuan.json', function(tjJson) {
	    	
	    d4.clear();
	 	   d4.showLoading({text:'皮一皮，很开心...'});
	    	
		    echarts.registerMap('qingyuan', tjJson);
		    d4.setOption({
		        series: [{
		            type: 'map',
		            map: 'qingyuan'
		        }]
		    });
		    
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

	option = {
	        title: {
	        text: '广东清远市',
	        subtext: '路线',
	        left: 'center',
	        textStyle: {
	            color: '#fff'
	            
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
	        //鼠标缩放和平移
	       /*  roam: true, */
	        itemStyle: {
	            normal: {
	                //         	color: '#ddd',
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
	                // shadowColor: 'rgba(255, 255, 255, 1)',
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
	d4.setOption(option);
	d4.hideLoading();
	d4.on('click',function(params){
		console.log(params);
		var id;
		if(params.data.name=="凤城郦都")
			id=1;
		else if(params.data.name=="凤城世家")
			id=2;
		else if(params.data.name=="东方巴黎")
			id=3;
		else if(params.data.name=="清远物业分公司")
			id=3;
		d666(id);
	});
		    
	   }); 
}

function d555(){
	d5.clear();
	d5.showLoading({text:'pppp'});
	
	var data1 = [{"name": "一月","value": 80}, {"name": "二月","value": 87.8},
	            {"name": "三月","value": 71}, {"name": "四月", "value": 80	},
	            {"name": "五月","value": 66}, {"name": "六月","value": 80	},
	            {"name": "七月","value": 80}
	            ];
	var data2 = [
	                {value:10, name:'rose1'},{value:5, name:'rose2'},
	                {value:15, name:'rose3'},{value:25, name:'rose4'},
	                {value:20, name:'rose5'},{value:35, name:'rose6'},
	                {value:30, name:'rose7'},{value:40, name:'rose8'}
	            ]
	var xData = [],
	    yData = [];
	var min = 50; 
	data1.map(function(a, b) {
	    xData.push(a.name);
	    if (a.value === 0) {
	        yData.push(a.value + min);
	    } else {
	        yData.push(a.value);
	    }
	});
	
	var option = {
			 title : [{
			        text: '故障报警次数统计',
			        /*subtext: '纯属虚构',*/
			        x:'left',
			        top: 0,
			        textStyle:{
			        	color:'white',
			        	fontSize:'16',
			        	fontFamily: 'lighter'    
			        }
			    },{
			        text: '故障报警准确率',
			        /*subtext: '纯属虚构',*/
			        x:'left',
			        top: '30%',
			        textStyle:{
			        	color:'white',
			        	fontSize:'16'
			        }
			    },{
			        text: '设备故障率',
			        x:'left',
			        top: '65%',
			        textStyle:{
			        	color:'white',
			        	fontSize:'16',
			        }
			    },],
			    grid: {
			        left: '5%',
			        right: '5%',
			        bottom: '25%',
			        top: '5%',
			        height: '23%',
			        /*width: '80%',*/
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
			        	rotate:+15,
			        },
			        axisLine: {//x轴颜色设置
			            lineStyle: {
			                color: '#0c3b71'
			            }
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
			            min: min,
			            max: 100,
			            axisLine: {
			                lineStyle: {
			                    color: '#0c3b71'
			                }
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
		                }
		            },
		            data: yData,
		            zlevel: 11

		        },
			        {
			            name:'半径模式',
			            type:'pie',
			            radius : [10, 90],
			            center : ['50%', '48%'],
			            roseType : 'radius',
			            data:data2
			        },
			        {
			            name:'面积模式',
			            type:'pie',
			            radius : [20, 90],
			            center : ['50%', '85%'],
			            roseType : 'area',
			            data:data2
			        }
			    ]
	};
;
	d5.setOption(option);
	d5.hideLoading();
}

function d666(id){
	d6.clear();
	d6.showLoading({text:'正在加载数据...'});
	
	var qy={
            name: '清远分公司',
            symbolSize: 30,
            itemStyle: {
                normal: {
                    borderColor: '#04f2a7',
                    borderWidth: 4,
                    shadowBlur: 10,
                    shadowColor: '#04f2a7',
                    color: '#001c43',
                }
            },
            category: 0,

        };
	var fcld={
            name: '凤城郦都',
            symbolSize: 15,
            itemStyle: {
                normal: {
                    borderColor: '#04f2a7',
                    borderWidth: 4,
                    shadowBlur: 10,
                    shadowColor: '#04f2a7',
                    color: '#001c43',
                }
            },
            category: 1,

        }
        var dfbl={
            name: '东方巴黎',
            symbolSize: 15,
            itemStyle: {
                normal: {
                    borderColor: '#04f2a7',
                    borderWidth: 4,
                    shadowBlur: 10,
                    shadowColor: '#04f2a7',
                    color: '#001c43',
                }
            },
            category: 1,

        }
        var fcsj={
            name: '凤城世家',
            symbolSize: 15,
            itemStyle: {
                normal: {
                    borderColor: '#04f2a7',
                    borderWidth: 4,
                    shadowBlur: 10,
                    shadowColor: '#04f2a7',
                    color: '#001c43',
                }
            },
            category: 1,
        }
	
	var option1 = {
	        tooltip: {},
	        animationDurationUpdate: 150,
	        animationEasingUpdate: 'quinticInOut',
	        color:['#83e0ff','#45f5ce','#b158ff'],
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

	                data:[
	                      {
	                        name: '凤城郦都',
	                        symbolSize: 30,
	                        category: 0,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#04f2a7',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#04f2a7',
	                                color: '#001c43',
	                            }
	                        },
	                    },
	                    {
	                        name: '水泵房',
	                        symbolSize: 15,
	                        category: 1,
	                        itemStyle: {
	                            normal: {
	                                borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },

	                    },
	                    {
	                        name: '水泵房供水管道',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'

	                            }
	                        },
	                        category: 1,

	                    },
	                    {
	                        name: '水泵房集水井水泵',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },
	                        category: 1,

	                    },
	                    {
	                        name: '水泵房消防水池',
	                        symbolSize: 15,
	                        category: 1,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },
	                    },
	                    {
	                        name: '水泵房生活水池',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },
	                        category: 1,

	                    },{
	                        name: '配电房变压器',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },
	                        category: 1,

	                    },{
	                        name: '楼顶消火栓',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },
	                        category: 1,

	                    }],
	                links: [
	                    {
	                        source: '凤城郦都',
	                        target: '水泵房',
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#e0f55a' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#639564' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        }
	                    },
	                    {
	                        source: '凤城郦都',
	                        target: '水泵房供水管道',
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#e0f55a' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#639564' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        }
	                    },
	                    {
	                        source: '凤城郦都',
	                        target: '水泵房集水井水泵', 
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#e0f55a' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#639564' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        }

	                    },
	                    {
	                        source: '凤城郦都',
	                        target: '水泵房消防水池',
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#e0f55a' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#639564' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        }
	                    },
	                    {
	                        source: '凤城郦都',
	                        target: '水泵房生活水池',
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#df6f30' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#915034' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        },
	                    },
	                    {
	                        source: '凤城郦都',
	                        target: '配电房变压器',
	                        lineStyle: {
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
	                        },
	                    },
	                    {
	                        source: '凤城郦都',
	                        target: '楼顶消火栓',
	                        lineStyle: {
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
	                        },
	                    }
	                ],
	                lineStyle: {//连线 设置
	                    normal: {
	                        opacity: 0.9,
	                        width: 3,
	                        curveness: 0
	                    }
	                },
	                categories:[
	                    {name: '下辖小区'},
	                    {name: '小区设备'}
	                ]
	            }
	        ]
	    }
	var option2 = {
	        tooltip: {},
	        animationDurationUpdate: 150,
	        animationEasingUpdate: 'quinticInOut',
	        color:['#83e0ff','#45f5ce','#b158ff'],
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

	                data: [
	                    {
	                        name: '凤城世家',
	                        symbolSize: 30,
	                        itemStyle: {
	                            normal: {
	                                borderColor: '#04f2a7',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#04f2a7',
	                                color: '#001c43',
	                            }
	                        },
	                        category: 0,

	                    },
	                    {
	                        name: '水泵房',
	                        symbolSize: 15,
	                        category: 1,
	                        itemStyle: {
	                            normal: {
	                                borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },

	                    },
	                    {
	                        name: '水泵房供水管道',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'

	                            }
	                        },
	                        category: 1,

	                    },
	                    {
	                        name: '水泵房集水井水泵',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },
	                        category: 1,

	                    },
	                    {
	                        name: '配电房变压器',
	                        symbolSize: 15,
	                        itemStyle: {
	                            normal: {
	                            	borderColor: '#b457ff',
	                                borderWidth: 4,
	                                shadowBlur: 10,
	                                shadowColor: '#b457ff',
	                                color: '#001c43'
	                            }
	                        },
	                        category: 1,

	                    }],
	                links: [
	                    {
	                        source: '凤城世家',
	                        target: '水泵房',
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#e0f55a' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#639564' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        }
	                    },
	                    {
	                        source: '凤城世家',
	                        target: '水泵房供水管道',
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#e0f55a' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#639564' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        }
	                    },
	                    {
	                        source: '凤城世家',
	                        target: '水泵房集水井水泵', 
	                        lineStyle: {
	                            normal: {
	                                color: {
	                                    type: 'linear',
	                                    x: 0,
	                                    y: 0,
	                                    x2: 0,
	                                    y2: 1,
	                                    colorStops: [{
	                                        offset: 0, color: '#e0f55a' // 0% 处的颜色
	                                    }, {
	                                        offset: 1, color: '#639564' // 100% 处的颜色
	                                    }],
	                                    globalCoord: false // 缺省为 false
	                                }
	                            }
	                        }

	                    },
	                    {
	                        source: '凤城世家',
	                        target: '配电房变压器',
	                        lineStyle: {
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
	                        },
	                    }
	                ],
	                lineStyle: {//连线 设置
	                    normal: {
	                        opacity: 0.9,
	                        width: 3,
	                        curveness: 0
	                    }
	                },
	                categories:[
	                    {name: '下辖小区'},
	                    {name: '小区设备'}
	                ]
	            }
	        ]
	    }
	var option3 = {
	        tooltip: {},
	        animationDurationUpdate: 150,
	        animationEasingUpdate: 'quinticInOut',
	        color:['#83e0ff','#45f5ce','#b158ff'],
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

	                data:[qy,fcld,dfbl,fcsj],
	                links:[{
                        source: '清远分公司',
                        target: '凤城世家',
                        lineStyle: {
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
                        },
                    },
                    {
                        source: '清远分公司',
                        target: '东方巴黎',
                        lineStyle: {
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
                        },
                    },
                    {
                        source: '清远分公司',
                        target: '凤城郦都',
                        lineStyle: {
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
                        },
                    },],
	                lineStyle: {//连线 设置
	                    normal: {
	                        opacity: 0.9,
	                        width: 3,
	                        curveness: 0
	                    }
	                }
	            }
	        ]
	    }
	if(id==1)
	    d6.setOption(option1);
	else if(id==2)
		d6.setOption(option2);
	else if(id==3)
		d6.setOption(option3);
	d6.on('click',function(params){
		console.log(params.data.name);
		if(params.data.name!=null){
			$.ajax({
		        url:"/gywyext/equipRealInfo/getEquipParaByName.do",
		        type:"post",
		        dataType: "json",
		        data: { searchKey: "水泵房湿度"},
		        success:function(data){
		        	var startDate ="2018-08-26 00:00:00"
		        	try2(startDate,data.result[0],d7)
					}
		    })
		}
	});
	d6.hideLoading();	
  }
function d777(){
	   d7.clear();
	   d7.showLoading({text: '正在努力的读取数据中...'});
	   var pathSymbols = {
			    reindeer: 'path://M-22.788,24.521c2.08-0.986,3.611-3.905,4.984-5.892 c-2.686,2.782-5.047,5.884-9.102,7.312c-0.992,0.005-0.25-2.016,0.34-2.362l1.852-0.41c0.564-0.218,0.785-0.842,0.902-1.347 c2.133-0.727,4.91-4.129,6.031-6.194c1.748-0.7,4.443-0.679,5.734-2.293c1.176-1.468,0.393-3.992,1.215-6.557 c0.24-0.754,0.574-1.581,1.008-2.293c-0.611,0.011-1.348-0.061-1.959-0.608c-1.391-1.245-0.785-2.086-1.297-3.313 c1.684,0.744,2.5,2.584,4.426,2.586C-8.46,3.012-8.255,2.901-8.04,2.824c6.031-1.952,15.182-0.165,19.498-3.937 c1.15-3.933-1.24-9.846-1.229-9.938c0.008-0.062-1.314-0.004-1.803-0.258c-1.119-0.771-6.531-3.75-0.17-3.33 c0.314-0.045,0.943,0.259,1.439,0.435c-0.289-1.694-0.92-0.144-3.311-1.946c0,0-1.1-0.855-1.764-1.98 c-0.836-1.09-2.01-2.825-2.992-4.031c-1.523-2.476,1.367,0.709,1.816,1.108c1.768,1.704,1.844,3.281,3.232,3.983 c0.195,0.203,1.453,0.164,0.926-0.468c-0.525-0.632-1.367-1.278-1.775-2.341c-0.293-0.703-1.311-2.326-1.566-2.711 c-0.256-0.384-0.959-1.718-1.67-2.351c-1.047-1.187-0.268-0.902,0.521-0.07c0.789,0.834,1.537,1.821,1.672,2.023 c0.135,0.203,1.584,2.521,1.725,2.387c0.102-0.259-0.035-0.428-0.158-0.852c-0.125-0.423-0.912-2.032-0.961-2.083 c-0.357-0.852-0.566-1.908-0.598-3.333c0.4-2.375,0.648-2.486,0.549-0.705c0.014,1.143,0.031,2.215,0.602,3.247 c0.807,1.496,1.764,4.064,1.836,4.474c0.561,3.176,2.904,1.749,2.281-0.126c-0.068-0.446-0.109-2.014-0.287-2.862 c-0.18-0.849-0.219-1.688-0.113-3.056c0.066-1.389,0.232-2.055,0.277-2.299c0.285-1.023,0.4-1.088,0.408,0.135 c-0.059,0.399-0.131,1.687-0.125,2.655c0.064,0.642-0.043,1.768,0.172,2.486c0.654,1.928-0.027,3.496,1,3.514 c1.805-0.424,2.428-1.218,2.428-2.346c-0.086-0.704-0.121-0.843-0.031-1.193c0.221-0.568,0.359-0.67,0.312-0.076 c-0.055,0.287,0.031,0.533,0.082,0.794c0.264,1.197,0.912,0.114,1.283-0.782c0.15-0.238,0.539-2.154,0.545-2.522 c-0.023-0.617,0.285-0.645,0.309,0.01c0.064,0.422-0.248,2.646-0.205,2.334c-0.338,1.24-1.105,3.402-3.379,4.712 c-0.389,0.12-1.186,1.286-3.328,2.178c0,0,1.729,0.321,3.156,0.246c1.102-0.19,3.707-0.027,4.654,0.269 c1.752,0.494,1.531-0.053,4.084,0.164c2.26-0.4,2.154,2.391-1.496,3.68c-2.549,1.405-3.107,1.475-2.293,2.984 c3.484,7.906,2.865,13.183,2.193,16.466c2.41,0.271,5.732-0.62,7.301,0.725c0.506,0.333,0.648,1.866-0.457,2.86 c-4.105,2.745-9.283,7.022-13.904,7.662c-0.977-0.194,0.156-2.025,0.803-2.247l1.898-0.03c0.596-0.101,0.936-0.669,1.152-1.139 c3.16-0.404,5.045-3.775,8.246-4.818c-4.035-0.718-9.588,3.981-12.162,1.051c-5.043,1.423-11.449,1.84-15.895,1.111 c-3.105,2.687-7.934,4.021-12.115,5.866c-3.271,3.511-5.188,8.086-9.967,10.414c-0.986,0.119-0.48-1.974,0.066-2.385l1.795-0.618 C-22.995,25.682-22.849,25.035-22.788,24.521z',
			    plane: 'path://M1.112,32.559l2.998,1.205l-2.882,2.268l-2.215-0.012L1.112,32.559z M37.803,23.96 c0.158-0.838,0.5-1.509,0.961-1.904c-0.096-0.037-0.205-0.071-0.344-0.071c-0.777-0.005-2.068-0.009-3.047-0.009 c-0.633,0-1.217,0.066-1.754,0.18l2.199,1.804H37.803z M39.738,23.036c-0.111,0-0.377,0.325-0.537,0.924h1.076 C40.115,23.361,39.854,23.036,39.738,23.036z M39.934,39.867c-0.166,0-0.674,0.705-0.674,1.986s0.506,1.986,0.674,1.986 s0.672-0.705,0.672-1.986S40.102,39.867,39.934,39.867z M38.963,38.889c-0.098-0.038-0.209-0.07-0.348-0.073 c-0.082,0-0.174,0-0.268-0.001l-7.127,4.671c0.879,0.821,2.42,1.417,4.348,1.417c0.979,0,2.27-0.006,3.047-0.01 c0.139,0,0.25-0.034,0.348-0.072c-0.646-0.555-1.07-1.643-1.07-2.967C37.891,40.529,38.316,39.441,38.963,38.889z M32.713,23.96 l-12.37-10.116l-4.693-0.004c0,0,4,8.222,4.827,10.121H32.713z M59.311,32.374c-0.248,2.104-5.305,3.172-8.018,3.172H39.629 l-25.325,16.61L9.607,52.16c0,0,6.687-8.479,7.95-10.207c1.17-1.6,3.019-3.699,3.027-6.407h-2.138 c-5.839,0-13.816-3.789-18.472-5.583c-2.818-1.085-2.396-4.04-0.031-4.04h0.039l-3.299-11.371h3.617c0,0,4.352,5.696,5.846,7.5 c2,2.416,4.503,3.678,8.228,3.87h30.727c2.17,0,4.311,0.417,6.252,1.046c3.49,1.175,5.863,2.7,7.199,4.027 C59.145,31.584,59.352,32.025,59.311,32.374z M22.069,30.408c0-0.815-0.661-1.475-1.469-1.475c-0.812,0-1.471,0.66-1.471,1.475 s0.658,1.475,1.471,1.475C21.408,31.883,22.069,31.224,22.069,30.408z M27.06,30.408c0-0.815-0.656-1.478-1.466-1.478 c-0.812,0-1.471,0.662-1.471,1.478s0.658,1.477,1.471,1.477C26.404,31.885,27.06,31.224,27.06,30.408z M32.055,30.408 c0-0.815-0.66-1.475-1.469-1.475c-0.808,0-1.466,0.66-1.466,1.475s0.658,1.475,1.466,1.475 C31.398,31.883,32.055,31.224,32.055,30.408z M37.049,30.408c0-0.815-0.658-1.478-1.467-1.478c-0.812,0-1.469,0.662-1.469,1.478 s0.656,1.477,1.469,1.477C36.389,31.885,37.049,31.224,37.049,30.408z M42.039,30.408c0-0.815-0.656-1.478-1.465-1.478 c-0.811,0-1.469,0.662-1.469,1.478s0.658,1.477,1.469,1.477C41.383,31.885,42.039,31.224,42.039,30.408z M55.479,30.565 c-0.701-0.436-1.568-0.896-2.627-1.347c-0.613,0.289-1.551,0.476-2.73,0.476c-1.527,0-1.639,2.263,0.164,2.316 C52.389,32.074,54.627,31.373,55.479,30.565z',
			    rocket: 'path://M-244.396,44.399c0,0,0.47-2.931-2.427-6.512c2.819-8.221,3.21-15.709,3.21-15.709s5.795,1.383,5.795,7.325C-237.818,39.679-244.396,44.399-244.396,44.399z M-260.371,40.827c0,0-3.881-12.946-3.881-18.319c0-2.416,0.262-4.566,0.669-6.517h17.684c0.411,1.952,0.675,4.104,0.675,6.519c0,5.291-3.87,18.317-3.87,18.317H-260.371z M-254.745,18.951c-1.99,0-3.603,1.676-3.603,3.744c0,2.068,1.612,3.744,3.603,3.744c1.988,0,3.602-1.676,3.602-3.744S-252.757,18.951-254.745,18.951z M-255.521,2.228v-5.098h1.402v4.969c1.603,1.213,5.941,5.069,7.901,12.5h-17.05C-261.373,7.373-257.245,3.558-255.521,2.228zM-265.07,44.399c0,0-6.577-4.721-6.577-14.896c0-5.942,5.794-7.325,5.794-7.325s0.393,7.488,3.211,15.708C-265.539,41.469-265.07,44.399-265.07,44.399z M-252.36,45.15l-1.176-1.22L-254.789,48l-1.487-4.069l-1.019,2.116l-1.488-3.826h8.067L-252.36,45.15z',
			    train: 'path://M67.335,33.596L67.335,33.596c-0.002-1.39-1.153-3.183-3.328-4.218h-9.096v-2.07h5.371 c-4.939-2.07-11.199-4.141-14.89-4.141H19.72v12.421v5.176h38.373c4.033,0,8.457-1.035,9.142-5.176h-0.027 c0.076-0.367,0.129-0.751,0.129-1.165L67.335,33.596L67.335,33.596z M27.999,30.413h-3.105v-4.141h3.105V30.413z M35.245,30.413 h-3.104v-4.141h3.104V30.413z M42.491,30.413h-3.104v-4.141h3.104V30.413z M49.736,30.413h-3.104v-4.141h3.104V30.413z  M14.544,40.764c1.143,0,2.07-0.927,2.07-2.07V35.59V25.237c0-1.145-0.928-2.07-2.07-2.07H-9.265c-1.143,0-2.068,0.926-2.068,2.07 v10.351v3.105c0,1.144,0.926,2.07,2.068,2.07H14.544L14.544,40.764z M8.333,26.272h3.105v4.141H8.333V26.272z M1.087,26.272h3.105 v4.141H1.087V26.272z M-6.159,26.272h3.105v4.141h-3.105V26.272z M-9.265,41.798h69.352v1.035H-9.265V41.798z',
			    ship: 'path://M16.678,17.086h9.854l-2.703,5.912c5.596,2.428,11.155,5.575,16.711,8.607c3.387,1.847,6.967,3.75,10.541,5.375 v-6.16l-4.197-2.763v-5.318L33.064,12.197h-11.48L20.43,15.24h-4.533l-1.266,3.286l0.781,0.345L16.678,17.086z M49.6,31.84 l0.047,1.273L27.438,20.998l0.799-1.734L49.6,31.84z M33.031,15.1l12.889,8.82l0.027,0.769L32.551,16.1L33.031,15.1z M22.377,14.045 h9.846l-1.539,3.365l-2.287-1.498h1.371l0.721-1.352h-2.023l-0.553,1.037l-0.541-0.357h-0.34l0.359-0.684h-2.025l-0.361,0.684 h-3.473L22.377,14.045z M23.695,20.678l-0.004,0.004h0.004V20.678z M24.828,18.199h-2.031l-0.719,1.358h2.029L24.828,18.199z  M40.385,34.227c-12.85-7.009-25.729-14.667-38.971-12.527c1.26,8.809,9.08,16.201,8.213,24.328 c-0.553,4.062-3.111,0.828-3.303,7.137c15.799,0,32.379,0,48.166,0l0.066-4.195l1.477-7.23 C50.842,39.812,45.393,36.961,40.385,34.227z M13.99,35.954c-1.213,0-2.195-1.353-2.195-3.035c0-1.665,0.98-3.017,2.195-3.017 c1.219,0,2.195,1.352,2.195,3.017C16.186,34.604,15.213,35.954,13.99,35.954z M23.691,20.682h-2.02l-0.588,1.351h2.023 L23.691,20.682z M19.697,18.199l-0.721,1.358h2.025l0.727-1.358H19.697z',
			    car: 'path://M49.592,40.883c-0.053,0.354-0.139,0.697-0.268,0.963c-0.232,0.475-0.455,0.519-1.334,0.475 c-1.135-0.053-2.764,0-4.484,0.068c0,0.476,0.018,0.697,0.018,0.697c0.111,1.299,0.697,1.342,0.931,1.342h3.7 c0.326,0,0.628,0,0.861-0.154c0.301-0.196,0.43-0.772,0.543-1.78c0.017-0.146,0.025-0.336,0.033-0.56v-0.01 c0-0.068,0.008-0.154,0.008-0.25V41.58l0,0C49.6,41.348,49.6,41.09,49.592,40.883L49.592,40.883z M6.057,40.883 c0.053,0.354,0.137,0.697,0.268,0.963c0.23,0.475,0.455,0.519,1.334,0.475c1.137-0.053,2.762,0,4.484,0.068 c0,0.476-0.018,0.697-0.018,0.697c-0.111,1.299-0.697,1.342-0.93,1.342h-3.7c-0.328,0-0.602,0-0.861-0.154 c-0.309-0.18-0.43-0.772-0.541-1.78c-0.018-0.146-0.027-0.336-0.035-0.56v-0.01c0-0.068-0.008-0.154-0.008-0.25V41.58l0,0 C6.057,41.348,6.057,41.09,6.057,40.883L6.057,40.883z M49.867,32.766c0-2.642-0.344-5.224-0.482-5.507 c-0.104-0.207-0.766-0.749-2.271-1.773c-1.522-1.042-1.487-0.887-1.766-1.566c0.25-0.078,0.492-0.224,0.639-0.241 c0.326-0.034,0.345,0.274,1.023,0.274c0.68,0,2.152-0.18,2.453-0.48c0.301-0.303,0.396-0.405,0.396-0.672 c0-0.268-0.156-0.818-0.447-1.146c-0.293-0.327-1.541-0.49-2.273-0.585c-0.729-0.095-0.834,0-1.022,0.121 c-0.304,0.189-0.32,1.919-0.32,1.919l-0.713,0.018c-0.465-1.146-1.11-3.452-2.117-5.269c-1.103-1.979-2.256-2.599-2.737-2.754 c-0.474-0.146-0.904-0.249-4.131-0.576c-3.298-0.344-5.922-0.388-8.262-0.388c-2.342,0-4.967,0.052-8.264,0.388 c-3.229,0.336-3.66,0.43-4.133,0.576s-1.633,0.775-2.736,2.754c-1.006,1.816-1.652,4.123-2.117,5.269L9.87,23.109 c0,0-0.008-1.729-0.318-1.919c-0.189-0.121-0.293-0.225-1.023-0.121c-0.732,0.104-1.98,0.258-2.273,0.585 c-0.293,0.327-0.447,0.878-0.447,1.146c0,0.267,0.094,0.379,0.396,0.672c0.301,0.301,1.773,0.48,2.453,0.48 c0.68,0,0.697-0.309,1.023-0.274c0.146,0.018,0.396,0.163,0.637,0.241c-0.283,0.68-0.24,0.524-1.764,1.566 c-1.506,1.033-2.178,1.566-2.271,1.773c-0.139,0.283-0.482,2.865-0.482,5.508s0.189,5.02,0.189,5.86c0,0.354,0,0.976,0.076,1.565 c0.053,0.354,0.129,0.697,0.268,0.966c0.232,0.473,0.447,0.516,1.334,0.473c1.137-0.051,2.779,0,4.477,0.07 c1.135,0.043,2.297,0.086,3.33,0.11c2.582,0.051,1.826-0.379,2.928-0.36c1.102,0.016,5.447,0.196,9.424,0.196 c3.976,0,8.332-0.182,9.423-0.196c1.102-0.019,0.346,0.411,2.926,0.36c1.033-0.018,2.195-0.067,3.332-0.11 c1.695-0.062,3.348-0.121,4.477-0.07c0.886,0.043,1.103,0,1.332-0.473c0.132-0.269,0.218-0.611,0.269-0.966 c0.086-0.592,0.078-1.213,0.078-1.565C49.678,37.793,49.867,35.408,49.867,32.766L49.867,32.766z M13.219,19.735 c0.412-0.964,1.652-2.9,2.256-3.244c0.145-0.087,1.426-0.491,4.637-0.706c2.953-0.198,6.217-0.276,7.73-0.276 c1.513,0,4.777,0.078,7.729,0.276c3.201,0.215,4.502,0.611,4.639,0.706c0.775,0.533,1.842,2.28,2.256,3.244 c0.412,0.965,0.965,2.858,0.861,3.116c-0.104,0.258,0.104,0.388-1.291,0.275c-1.387-0.103-10.088-0.216-14.185-0.216 c-4.088,0-12.789,0.113-14.184,0.216c-1.395,0.104-1.188-0.018-1.291-0.275C12.254,22.593,12.805,20.708,13.219,19.735 L13.219,19.735z M16.385,30.511c-0.619,0.155-0.988,0.491-1.764,0.482c-0.775,0-2.867-0.353-3.314-0.371 c-0.447-0.017-0.842,0.302-1.076,0.362c-0.23,0.06-0.688-0.104-1.377-0.318c-0.688-0.216-1.092-0.155-1.316-1.094 c-0.232-0.93,0-2.264,0-2.264c1.488-0.068,2.928,0.069,5.621,0.826c2.693,0.758,4.191,2.213,4.191,2.213 S17.004,30.357,16.385,30.511L16.385,30.511z M36.629,37.293c-1.23,0.164-6.386,0.207-8.794,0.207c-2.412,0-7.566-0.051-8.799-0.207 c-1.256-0.164-2.891-1.67-1.764-2.865c1.523-1.627,1.24-1.576,4.701-2.023C24.967,32.018,27.239,32,27.834,32 c0.584,0,2.865,0.025,5.859,0.404c3.461,0.447,3.178,0.396,4.699,2.022C39.521,35.623,37.887,37.129,36.629,37.293L36.629,37.293z  M48.129,29.582c-0.232,0.93-0.629,0.878-1.318,1.093c-0.688,0.216-1.145,0.371-1.377,0.319c-0.231-0.053-0.627-0.371-1.074-0.361 c-0.448,0.018-2.539,0.37-3.313,0.37c-0.772,0-1.146-0.328-1.764-0.481c-0.621-0.154-0.966-0.154-0.966-0.154 s1.49-1.464,4.191-2.213c2.693-0.758,4.131-0.895,5.621-0.826C48.129,27.309,48.361,28.643,48.129,29.582L48.129,29.582z',
			    run: 'path://M13.676,32.955c0.919-0.031,1.843-0.008,2.767-0.008v0.007c0.827,0,1.659,0.041,2.486-0.019 c0.417-0.028,1.118,0.325,1.14-0.545c0.014-0.637-0.156-1.279-0.873-1.367c-1.919-0.241-3.858-0.233-5.774,0.019 c-0.465,0.062-0.998,0.442-0.832,1.069C12.715,32.602,13.045,32.977,13.676,32.955z M14.108,29.013 c1.47-0.007,2.96-0.122,4.414,0.035c1.792,0.192,3.1-0.412,4.273-2.105c-3.044,0-5.882,0.014-8.719-0.01 c-0.768-0.005-1.495,0.118-1.461,1C12.642,28.731,13.329,29.014,14.108,29.013z M23.678,36.593c-0.666-0.69-1.258-1.497-2.483-1.448 c-2.341,0.095-4.689,0.051-7.035,0.012c-0.834-0.014-1.599,0.177-1.569,1.066c0.031,0.854,0.812,1.062,1.636,1.043 c1.425-0.033,2.852-0.01,4.278-0.01v-0.01c1.562,0,3.126,0.008,4.691-0.005C23.614,37.239,24.233,37.174,23.678,36.593z  M32.234,42.292h-0.002c-1.075,0.793-2.589,0.345-3.821,1.048c-0.359,0.193-0.663,0.465-0.899,0.799 c-1.068,1.623-2.052,3.301-3.117,4.928c-0.625,0.961-0.386,1.805,0.409,2.395c0.844,0.628,1.874,0.617,2.548-0.299 c1.912-2.573,3.761-5.197,5.621-7.814C33.484,42.619,33.032,42.387,32.234,42.292z M43.527,28.401 c-0.688-1.575-2.012-0.831-3.121-0.895c-1.047-0.058-2.119,1.128-3.002,0.345c-0.768-0.677-1.213-1.804-1.562-2.813 c-0.45-1.305-1.495-2.225-2.329-3.583c2.953,1.139,4.729,0.077,5.592-1.322c0.99-1.61,0.718-3.725-0.627-4.967 c-1.362-1.255-3.414-1.445-4.927-0.452c-1.933,1.268-2.206,2.893-0.899,6.11c-2.098-0.659-3.835-1.654-5.682-2.383 c-0.735-0.291-1.437-1.017-2.293-0.666c-2.263,0.927-4.522,1.885-6.723,2.95c-1.357,0.658-1.649,1.593-1.076,2.638 c0.462,0.851,1.643,1.126,2.806,0.617c0.993-0.433,1.994-0.857,2.951-1.374c1.599-0.86,3.044-0.873,4.604,0.214 c1.017,0.707,0.873,1.137,0.123,1.849c-1.701,1.615-3.516,3.12-4.933,5.006c-1.042,1.388-0.993,2.817,0.255,4.011 c1.538,1.471,3.148,2.869,4.708,4.315c0.485,0.444,0.907,0.896-0.227,1.104c-1.523,0.285-3.021,0.694-4.538,1.006 c-1.109,0.225-2.02,1.259-1.83,2.16c0.223,1.07,1.548,1.756,2.687,1.487c3.003-0.712,6.008-1.413,9.032-2.044 c1.549-0.324,2.273-1.869,1.344-3.115c-0.868-1.156-1.801-2.267-2.639-3.445c-1.964-2.762-1.95-2.771,0.528-5.189 c1.394-1.357,1.379-1.351,2.437,0.417c0.461,0.769,0.854,1.703,1.99,1.613c2.238-0.181,4.407-0.755,6.564-1.331 C43.557,30.447,43.88,29.206,43.527,28.401z',
			    walk: 'path://M29.902,23.275c1.86,0,3.368-1.506,3.368-3.365c0-1.859-1.508-3.365-3.368-3.365 c-1.857,0-3.365,1.506-3.365,3.365C26.537,21.769,28.045,23.275,29.902,23.275z M36.867,30.74c-1.666-0.467-3.799-1.6-4.732-4.199 c-0.932-2.6-3.131-2.998-4.797-2.998s-7.098,3.894-7.098,3.894c-1.133,1.001-2.1,6.502-0.967,6.769 c1.133,0.269,1.266-1.533,1.934-3.599c0.666-2.065,3.797-3.466,3.797-3.466s0.201,2.467-0.398,3.866 c-0.599,1.399-1.133,2.866-1.467,6.198s-1.6,3.665-3.799,6.266c-2.199,2.598-0.6,3.797,0.398,3.664 c1.002-0.133,5.865-5.598,6.398-6.998c0.533-1.397,0.668-3.732,0.668-3.732s0,0,2.199,1.867c2.199,1.865,2.332,4.6,2.998,7.73 s2.332,0.934,2.332-0.467c0-1.401,0.269-5.465-1-7.064c-1.265-1.6-3.73-3.465-3.73-5.265s1.199-3.732,1.199-3.732 c0.332,1.667,3.335,3.065,5.599,3.399C38.668,33.206,38.533,31.207,36.867,30.74z'
			};

		var option = {
				title: {
			           text: '各项设备故障统计',
			           subtext: '****小区',
			           x: 'center'
			       },
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'none'
			        },
			        formatter: function (params) {
			            return params[0].name + ': ' + params[0].value;
			        }
			    },
			    xAxis: {
			        data: ['设备1', '设备2', '设备3', '设备4', '设备5', '设备5', '设备6', '设备7', ],
			        axisTick: {show: false},
			        axisLine: {show: false},
			        axisLabel: {
			            textStyle: {
			                color: '#e54035'
			            }
			        }
			    },
			    yAxis: {
			        splitLine: {show: false},
			        axisTick: {show: false},
			        axisLine: {show: false},
			        axisLabel: {show: false}
			    },
			    color: ['#e54035'],
			    series: [{
			        name: 'hill',
			        type: 'pictorialBar',
			        barCategoryGap: '-130%',
			        // symbol: 'path://M0,10 L10,10 L5,0 L0,10 z',
			        symbol: 'path://M0,10 L10,10 C5.5,10 5.5,5 5,0 C4.5,5 4.5,10 0,10 z',
			        itemStyle: {
			            normal: {
			                opacity: 0.5
			            },
			            emphasis: {
			                opacity: 1
			            }
			        },
			        data: [123, 60, 25, 18, 12, 9, 2, 1],
			        z: 10
			    }, {
			        name: 'glyph',
			        type: 'pictorialBar',
			        barGap: '-100%',
			        symbolPosition: 'end',
			        symbolSize: 50,
			        symbolOffset: [0, '-120%'],
			        data: [{
			            value: 123,
			            symbol: pathSymbols.reindeer,
		            symbolSize: [60, 60]
				        }, {
				            value: 60,
				            symbol: pathSymbols.rocket,
				            symbolSize: [50, 60]
				        }, {
				            value: 25,
				            symbol: pathSymbols.plane,
				            symbolSize: [65, 35]
				        }, {
				            value: 18,
				            symbol: pathSymbols.train,
				            symbolSize: [50, 30]
				        }, {
				            value: 12,
				            symbol: pathSymbols.ship,
				            symbolSize: [50, 35]
				        }, {
				            value: 9,
				            symbol: pathSymbols.car,
				            symbolSize: [40, 30]
				        }, {
				            value: 2,
				            symbol: pathSymbols.run,
				            symbolSize: [40, 50]
				        }, {
				            value: 1,
				            symbol: pathSymbols.walk,
				            symbolSize: [40, 50]
				        }]
				    }]
				};
				d7.setOption(option);
			    d7.hideLoading();
	   }