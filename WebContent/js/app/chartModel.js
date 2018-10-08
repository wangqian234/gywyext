//饼图
function drawPieChart(jsonData) {
	var chart = new PieChart({
		title : jsonData.title,
		dataContent : jsonData.dataContent
	});
	var mychart = chart.init(jsonData.domElement);

	return mychart;
}

// 饼图
function PieChart(data) {
	this.title = data.title;
	this.dataContent = data.dataContent;
}

// 饼图
PieChart.prototype.init = function(domElement) {
	var pieChart = echarts.init(domElement);
	// 指定图表的配置项和数据
	var pieOption = {
		title : {
			x : 'center',
			y : 'top',
			text : this.title,
			textStyle : {// 标题内容的样式
				fontSize : 14
			// 主题文字字体大小，默认为18px
			},
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		// legend: {
		// orient : 'vertical',
		// x : 'left',
		// data:['1~3月份','4~5月份','6~8月份','9~10月份','11~12月份']
		// },
		toolbox : {
			show : false,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'pie', 'funnel' ],
					option : {
						funnel : {
							x : '25%',
							width : '50%',
							funnelAlign : 'left',
							max : 1548
						}
					}
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		series : this.dataContent
	};
	pieChart.setOption(pieOption);
	return pieChart;
}
function drawRadarChart(jsonData) {
var chart=new RadarChart({
	"title":jsonData.title,
	"model":jsonData.model,
	"dataContent":jsonData.dataContent
});
var mychart = chart.init(jsonData.domElement);

return mychart;
}
//柱状图
function RadarChart(data) {
	this.title = data.title;
	this.dataContent = data.dataContent;
	this.model = data.model;
	
}

// 柱状图类型
RadarChart.prototype.init = function(domElement) {
	var radarChart = echarts.init(domElement);
	var radarOption  = {
		    title: {
		        text: this.title
		    },
		    tooltip: {},
		    legend: {
		        data: []
		    },
		    radar: {
		        // shape: 'circle',
		        name: {
		            textStyle: {
		                color: '#fff',
		                backgroundColor: '#999',
		                borderRadius: 3,
		                padding: [3, 5]
		           }
		        },
		        indicator: this.model
		    },
		    series: this.dataContent
		};

	radarChart.setOption(radarOption);
	return radarChart;
}





// 柱状图
function drawBarChart(jsonData) {
	var chart = new BarChart({
		title : jsonData.title,
		dataContent : jsonData.dataContent,
		x_Axis : jsonData.x_Axis,
		y_Axis : jsonData.y_Axis

	});
	var mychart = chart.init(jsonData.domElement);
	return mychart;
}

// 柱状图
function BarChart(data) {
	this.title = data.title;
	this.subTitle = "";
	this.dataContent = data.dataContent;
	this.x_Axis = data.x_Axis;
	this.y_Axis = data.y_Axis;
}

// 柱状图类型
BarChart.prototype.init = function(domElement) {
	var barChart = echarts.init(domElement);
	var barOption = {
		title : {
			x : 'center',
			y : 'top',
			text : this.title,
			textStyle : {// 标题内容的样式
				fontSize : 14
			// 主题文字字体大小，默认为18px
			},
		},
		toolbox : {
			show : false,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			data : this.x_Axis
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : this.dataContent
	};

	barChart.setOption(barOption);
	return barChart;
}

// 绘制折线图
function drawLineChart(jsonData) {
	var chart = new LineChart({
		title : jsonData.title,
		dataContent : jsonData.dataContent,
		x_Axis : jsonData.x_Axis,
		y_Axis : jsonData.y_Axis

	});
	var mychart = chart.init(jsonData.domElement);
	return mychart;
}

// 折线图类
function LineChart(data) {
	this.title = data.title;
	this.subTitle = "";
	this.dataContent = data.dataContent;
	this.x_Axis = data.x_Axis;
	this.y_Axis = data.y_Axis;
}
// 折线图类型
LineChart.prototype.init = function(domElement) {
	var myChart = echarts.init(domElement);
	var option = {
		title : {
			show : true,
			text : this.title,
			x : 'center',
			y : 'top',
			textStyle : {// 标题内容的样式
				fontSize : 14
			// 主题文字字体大小，默认为18px
			}
		},
		tooltip : {
			trigger : 'axis'
		},
		toolbox : {
			show : false,
			feature : {
				dataView : {
					show : true
				},
				restore : {
					show : true
				},
				dataZoom : {
					show : true
				},
				saveAsImage : {
					show : true
				},
				magicType : {
					type : [ 'line', 'bar' ]
				}
			}
		},
		legend : {
			data : [ this.dataType ],
			x : 'right',
			y : 'bottom'
		},
		xAxis : {
			data : this.x_Axis,
			type : 'category',
			boundaryGap : false

		},
		yAxis : [ {
			type : 'value'
		} ],
		grid : { // 控制图的大小，调整下面这些值就可以，
		// x: 55,
		// y2: 125// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
		},
		series : this.dataContent
	};

	// 使用刚指定的配置项和数据显示图表。
	// myChart.hideLoading();
	myChart.setOption(option);
	return myChart;

}

// 将时间戳转化成正常日期
function exchangeDate(time) {
	if (time != null && time != "") {
		var date = new Date(time);
		Y = date.getFullYear() + '-';
		M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
				.getMonth() + 1)
				+ '-';
		D = date.getDate() + ' ';
		h = date.getHours() + ':';
		m = date.getMinutes() + ':';
		s = date.getSeconds();
		return Y + M + D + h + m + s; //
	} else {
		return "";
	}

}