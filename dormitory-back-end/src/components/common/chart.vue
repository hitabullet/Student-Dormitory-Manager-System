<template>
    <div class="chart_box">
        <div id="bar_echart"></div>

        <div ref='pie_echart' id="pie_echart"> </div>
    </div>
</template>

<script>
    import echarts from 'echarts'

    export default {
        name: 'chart',
        data() {
            return {
                pieDataList: [],
                barXAxis: [],
                barData: []
            }
        },
        methods: {
            // 饼图图表初始化数据
            initPieChart() {
                let myChart = echarts.init(this.$refs.pie_echart);
                myChart.setOption({
                    legend: {
                      orient: 'vertical',
                      show: true,
                      left: 20,
                      top: 20,
                    },
                    title: {
                        text: "学生居住状态",
                        bottom: "5px",
                        left: "center"
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} <br/> ({d}%)"
                    },
                    color: ['#409EFF', '#F56C6C', '#67C23A'],
                    series: [
                        {
                            name: '学生住宿',
                            type: 'pie',
                            radius: '70%',
                            center: ['50%', '50%'],
                            avoidLabelOverlap: false,
                            data: this.pieDataList,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 5,
                                    shadowOffsetX: 1,
                                    shadowColor: '#409EFF'
                                }
                            }
                        }
                    ]
                })
            },

            // 柱状图图表初始化数据
            initBarChart() {
                let this_ = this;
                let myChart = echarts.init(document.getElementById('bar_echart'));
                let option = {
                    title: {
                        text: "近7天设备报修量",
                        bottom: "5px",
                        left: "center"
                    },
                    color: ['#409EFF'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: this.barXAxis,
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '设备数量',
                            type: 'bar',
                            barWidth: '50%',
                            data: this.barData
                        }
                    ]
                };
                myChart.setOption(option)
            },

            getEchartsData() {
                // 请求饼图数据
                this.$axios.get("/api/record/leave", {
                    params: {},
                }).then(response => {
                    // 处理后台返回数据
                    this.pieDataList = [];
                    this.pieDataList.push(
                        {value: response.data.checkSum, name: '在住'}
                    );
                    this.pieDataList.push(
                        {value: response.data.exitCheckSum, name: '已退寝'}
                    );
                    this.pieDataList.push(
                        {value: response.data.uncheckSum, name: '未入住'}
                    );
                    this.initPieChart();
                }).catch(error => {
                });

                // 请求柱状图数据
                this.$axios.get("/api/record/repair", {
                    params: {},
                }).then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        this.barXAxis.push(response.data[i].repairtime);
                        this.barData.push(response.data[i].num);
                    }
                    this.initBarChart();
                }).catch(error => {
                });
            }
        },
        watch: {},
        created() {

        },
        mounted() {
            this.getEchartsData();
        }
    }

</script>

<style lang="scss" scoped>
    .chart_box {

    }

    #bar_echart {
        width: 35%;
        height: 280px;
        float: left;
        position: absolute;
        display: inline-block;
        left: 30px;
        background-color: #f2f2f2;
        top: 210px;
    }

    #pie_echart {
        width: 46%;
        height: 280px;
        float: right;
        position: absolute;
        display: inline-block;
        left: 45%;
        top: 210px;
        background-color: #f2f2f2;
    }

</style>
