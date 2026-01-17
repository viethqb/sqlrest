<template>
  <div class="dashbord">
    <h1 class="page-title">DASHBOARD</h1>
    <el-row class="infoCrads">
      <el-col :span="6">
        <div class="cardItem">
          <div class="cardItem_txt">
            <CountTo class="cardItem_p0 color-green1"
                     :startVal="startVal"
                     :endVal="statistics.totalCount"
                     :duration="2000"></CountTo>
            <p class="cardItem_p1">Developed APIs</p>
          </div>
          <div class="cardItem_icon">
            <i class="el-icon-s-grid color-green1"></i>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="cardItem">
          <div class="cardItem_txt">
            <CountTo class="cardItem_p0 color-blue"
                     :startVal="startVal"
                     :endVal="statistics.openCount"
                     :duration="2000"></CountTo>
            <p class="cardItem_p1">Open APIs</p>
          </div>
          <div class="cardItem_icon">
            <i class="el-icon-s-data color-blue"></i>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="cardItem">
          <div class="cardItem_txt">
            <CountTo class="cardItem_p0 color-green2"
                     :startVal="startVal"
                     :endVal="statistics.publishCount"
                     :duration="2000"></CountTo>
            <p class="cardItem_p1">Online APIs</p>
          </div>
          <div class="cardItem_icon">
            <i class="el-icon-loading color-green2"></i>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="cardItem">
          <div class="cardItem_txt">
            <CountTo class="cardItem_p0 color-red"
                     :startVal="startVal"
                     :endVal="statistics.datasourceCount"
                     :duration="2000"></CountTo>
            <p class="cardItem_p1">Total Data Sources</p>
          </div>
          <div class="cardItem_icon">
            <i class="el-icon-office-building color-red"></i>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-card class="box-card" shadow="hover">
      <div slot="header"
           class="clearfix">
        <el-row>
          <el-col :span="8">
            <span>Time Range:</span>
            <el-select v-model="selectDays"
                       @change="selectChangedRangeTime"
                       placeholder="Please select time range">
              <el-option v-for="item in optionDays"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="8">
            <span>TOP N Count:</span>
            <el-select v-model="topNum"
                       @change="selectChangedTopNum"
                       placeholder="Please select topN">
              <el-option v-for="item in optionTopN"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="8">
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <div id="topPathChart"></div>
          </el-col>
          <el-col :span="8">
            <div id="topAppChart"></div>
          </el-col>
          <el-col :span="8">
            <div id="topAddrChart"></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <div id="barChart"></div>
          </el-col>
          <el-col :span="8">
            <div id="pieChart"></div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>
<script>
import CountTo from "vue-count-to";

export default {
  name: "Dashboard",
  components: {
    CountTo
  },
  data () {
    return {
      startVal: 0,
      statistics: {},
      optionDays: [
        { label: 'Within 1 day', value: 1 },
        { label: 'Within 3 days', value: 3 },
        { label: 'Within 7 days', value: 7 },
        { label: 'Within 30 days', value: 30 },
      ],
      optionTopN: [
        { label: 'Top 3', value: 3 },
        { label: 'Top 5', value: 5 },
        { label: 'Top 6', value: 6 },
        { label: 'Top 8', value: 8 },
        { label: 'Top 10', value: 10 },
      ],
      selectDays: 7,
      topNum: 6,
      barChart: null,
      pieChart: null,
      topPathChart: null,
      topAppChart: null,
      topAddrChart: null,
      barChartData: {
        title: {
          text: 'Trend Statistics'
        },
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: [
            {
              name: 'Total',
              textStyle: {
                color: '#000'
              }
            },
            {
              name: 'Success',
              textStyle: {
                color: '#000'
              }
            }
          ]
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: {
          type: "category",
          boundaryGap: true,
          data: [],
          axisLabel: {
            interval: 0,
            textStyle: {
              color: '#000',
              fontSize: 10
            },
            margin: 8
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: 'rgb(2,121,253)'
            }
          },
          axisTick: {
            show: false,
          }
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            name: "Total",
            type: "bar",
            barWidth: '8%',
            data: []
          },
          {
            name: "Success",
            type: "bar",
            barWidth: '8%',
            data: []
          }
        ]
      },
      pieChartData: {
        title: {
          text: 'Failure Rate'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'right',
        },
        series: [
          {
            name: 'Operation Status',
            type: 'pie',
            radius: '55%',
            data: [
              { value: 0, name: 'Success' },
              { value: 0, name: 'Failure' },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      topPathData: {
        title: {
          text: 'TOP APIs'
        },
        color: ['#40c9c6'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: []
        },
        series: [
          {
            type: 'bar',
            data: [12, 44, 55, 67, 89, 112]
          }
        ]
      },
      topAppData: {
        title: {
          text: 'TOP Applications'
        },
        color: ['#36a3f7'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: []
        },
        series: [
          {
            type: 'bar',
            data: [12, 44, 55, 67, 89, 112]
          }
        ]
      },
      topAddrData: {
        title: {
          text: 'TOP Addresses'
        },
        color: ['#34bfa3'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: []
        },
        series: [
          {
            type: 'bar',
            data: [12, 44, 55, 67, 89, 112]
          }
        ]
      }
    };
  },
  methods: {
    loadTotal: function () {
      this.$http.get("/sqlrest/manager/api/v1/overview/counter")
        .then(
          res => {
            if (0 === res.data.code) {
              this.statistics = res.data.data;
            }
          }
        );
    },
    loadData: function () {
      this.$http.get("/sqlrest/manager/api/v1/overview/trend/" + this.selectDays)
        .then(
          res => {
            if (0 === res.data.code) {
              var lists = res.data.data;
              var xAxisData = [];
              var y1AxisData = [];
              var y2AxisData = [];
              for (var i = 0; i < lists.length; i++) {
                xAxisData.push(lists[i].ofDate);
                y1AxisData.push(lists[i].total);
                y2AxisData.push(lists[i].success);
              }
              this.barChartData.xAxis.data = xAxisData;
              this.barChartData.series[0].data = y1AxisData;
              this.barChartData.series[1].data = y2AxisData;

              this.barChart.setOption(this.barChartData, true);
            }
          }
        );

      this.$http.get("/sqlrest/manager/api/v1/overview/ratio/" + this.selectDays)
        .then(
          res => {
            if (0 === res.data.code) {
              var result = res.data.data;
              var list = []
              result.forEach(item => list.push({ name: item.name, value: item.count }))
              this.pieChartData.series[0].data = list
              this.pieChart.setOption(this.pieChartData, true);
            }
          }
        );

      this.$http.get("/sqlrest/manager/api/v1/overview/top/path/" + this.selectDays + "?n=" + this.topNum)
        .then(
          res => {
            if (0 === res.data.code) {
              var result = res.data.data;
              this.topPathData.yAxis.data = result.map(t => t.name).reverse()
              this.topPathData.series[0].data = result.map(t => t.count).reverse()
              this.topPathData.title.text = 'TOP ' + this.topNum + ' APIs'
              this.topPathChart.setOption(this.topPathData, true);
            }
          }
        );

      this.$http.get("/sqlrest/manager/api/v1/overview/top/client/" + this.selectDays + "?n=" + this.topNum)
        .then(
          res => {
            if (0 === res.data.code) {
              var result = res.data.data;
              this.topAppData.yAxis.data = result.map(t => t.name).reverse()
              this.topAppData.series[0].data = result.map(t => t.count).reverse()
              this.topAppData.title.text = 'TOP ' + this.topNum + ' Applications'
              this.topAppChart.setOption(this.topAppData, true);
            }
          }
        );

      this.$http.get("/sqlrest/manager/api/v1/overview/top/addr/" + this.selectDays + "?n=" + this.topNum)
        .then(
          res => {
            if (0 === res.data.code) {
              var result = res.data.data;
              this.topAddrData.yAxis.data = result.map(t => t.name).reverse()
              this.topAddrData.series[0].data = result.map(t => t.count).reverse()
              this.topAddrData.title.text = 'TOP ' + this.topNum + ' Addresses'
              this.topAddrChart.setOption(this.topAddrData, true);
            }
          }
        );

    },
    selectChangedRangeTime: function () {
      this.loadData();
    },
    selectChangedTopNum: function () {
      this.loadData();
    }
  },
  created () {
    this.loadTotal();
  },
  mounted () {
    this.barChart = this.$echarts.init(document.getElementById("barChart"));
    this.pieChart = this.$echarts.init(document.getElementById("pieChart"));
    this.topPathChart = this.$echarts.init(document.getElementById("topPathChart"));
    this.topAppChart = this.$echarts.init(document.getElementById("topAppChart"));
    this.topAddrChart = this.$echarts.init(document.getElementById("topAddrChart"));
    this.loadData();
  }
};
</script>

<style scoped>
.dashbord {
  background-color: transparent;
  padding: 20px;
}

.color-green1 {
  color: #40c9c6 !important;
}
.color-blue {
  color: #36a3f7 !important;
}
.color-red {
  color: #f4516c !important;
}
.color-green2 {
  color: #34bfa3 !important;
}

.infoCrads {
  margin: 0 0 20px 0;
}

.infoCrads .el-col {
  padding: 10px;
}

.infoCrads .el-col .cardItem {
  height: 128px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
}

.infoCrads .el-col .cardItem:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
  transform: translateY(-2px);
}

.cardItem {
  color: #666;
}

.cardItem .cardItem_txt {
  flex: 1;
}

.cardItem .cardItem_txt .cardItem_p0 {
  font-size: 32px;
  font-weight: 700;
  margin: 0 0 8px 0;
  line-height: 1.2;
}

.cardItem .cardItem_txt .cardItem_p1 {
  font-size: 14px;
  margin: 0;
  color: #999;
  font-weight: 500;
}

.cardItem .cardItem_icon {
  font-size: 64px;
  font-weight: bold;
  opacity: 0.8;
}

#barChart {
  width: 100%;
  height: 300px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 20px;
}
#pieChart {
  width: 100%;
  height: 300px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 20px;
}
#topPathChart {
  width: 100%;
  height: 300px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 20px;
}
#topAppChart {
  width: 100%;
  height: 300px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 20px;
}
#topAddrChart {
  width: 100%;
  height: 300px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 20px;
}

.box-card {
  margin-top: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.box-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

.box-card .el-card__header {
  padding: 18px 20px;
  background-color: #fafafa;
  border-bottom: 1px solid #ebeef5;
}

.box-card .el-card__body {
  padding: 20px;
}

.page-title {
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  color: #606266;
  padding: 15px 0;
  margin-bottom: 10px;
  text-transform: uppercase;
}
</style>