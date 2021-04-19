<template>
  <div style="padding:20px;height: 90%;">
<!--    顶部 饼图-->
    <div style="display: flex;flex-direction: row;justify-content: space-between;width: 100%;height: 30%;">
      <el-card shadow="always" style="width: 32%;height: 100%;" >
        <div style="height:165px;width: 165px;margin-top: -10px;margin-bottom: -10px">
          <svg id="svg1" style="height: 100%;width: 100%;background-color: #EFEFEF"></svg>
        </div>
      </el-card>
      <el-card shadow="always" style="width: 32%" >
        <div style="height:165px;width: 165px;margin-top: -10px;margin-bottom: -10px">
          <svg id="svg2" style="height: 100%;width: 100%;background-color: #EFEFEF"></svg>
        </div>
      </el-card>
      <el-card shadow="always" style="width: 32%" >
        <div style="height:165px;width: 165px;margin-top: -10px;margin-bottom: -10px">
          <svg id="svg1" style="height: 100%;width: 100%;background-color: #EFEFEF"></svg>
        </div>
      </el-card>
    </div>
<!--    底部 折线图-->
    <div style="width: 100%;height: 65%;margin-top: 20px;display: flex;flex-direction: row;justify-content: space-between">
      <el-card shadow="always" style="width: 49%;">

      </el-card>
      <el-card shadow="always" style="width: 49%" >

      </el-card>
    </div>
  </div>
</template>

<script>
import * as d3 from 'd3'

export default {
  name: "KbBasic",
  data(){
  return{
    data_circle_1:[
      {name:'农业', count:47055},
      {count:293, name:'刺绣织染'},
      {count:120, name:'包装'},
      {count:32052, name:'化学'},
      {name:'化工', count:7221},
      {count:4112, name:'医疗器械'},
      {count:1008, name:'印刷印染'},
      {name:'家用电器', count:598},
      {count:2956,name: '工业设计'},
      {name:'建筑', count:41540},
      {name:'机械工程', count:9988},
      {count:16339, name:'林业'},
      {name:'汽车', count:3860},
      {count:8842, name:'汽车工程'},
      {name:'渔业', count:11242},
      {name:'环境能源', count:6900},
      {count:77361, name:'电力电气'},
      {count:20809,name: '电子工程'},
      {name:'畜牧业', count:4013},
      {count:2692, name:'纺织服装'},
      {name:'计算机', count:222},
      {count:1225, name:'造纸'},
      {count:17,name: '金属工艺'},
      {name:'钢铁冶金', count:227219},

    ],
  }
  },
  methods:{
    drawSingle: function(svg, data, colorType) {

      let radius = 80

      let singleData = data

      let g = svg.append("g")
          .attr("transform", "translate(" + 82.5 + "," + 82.5 + ")");

      let pie = d3.pie()
          .padAngle(0.005)
          .sort(null)
          .value(d => d.count)

      let data_ready = pie(singleData)

      let color = d3.scaleOrdinal()
          .domain(singleData.map(d => d.name))

      if (colorType == 'Red2Blue') {
        color = color.range(d3.quantize(t => d3.interpolateSpectral(t * 0.8 + 0.1), singleData.length))
      } else if (colorType == 'Blue2Red') {
        color = color.range(d3.quantize(t => d3.interpolateSpectral(t * 0.8 + 0.1), singleData.length).reverse())
      }


      let arc = d3.arc()
          .innerRadius(radius * 0.5) // This is the size of the donut hole
          .outerRadius(radius * 0.8)

      // Another arc that won't be drawn. Just for labels positioning
      let outerArc = d3.arc()
          .innerRadius(radius * 0.9)
          .outerRadius(radius * 0.9)

      g
          .selectAll('allSlices')
          .data(data_ready)
          .enter()
          .append('path')
          .attr('d', arc)
          .attr('fill', function(d) {
            return (color(d.data.name))
          })
          .attr("stroke", "white")
          .style("stroke-width", "1px")
          .style("opacity", 0.7)
          .transition().duration(1000) // 环形图生成动画1000ms
          .attrTween('d', function(d) {
            let i = d3.interpolate({
              startAngle: 1.1 * Math.PI,
              endAngle: 1.1 * Math.PI
            }, d);
            return function(t) {
              return arc(i(t));
            };
          })

      // Add the polylines between chart and labels:
      // g
      //     .selectAll('allPolylines')
      //     //.data(data_ready.filter((d, i) => type == 4 ? i < 2 : i < 3)) // 攻击地点通常比例差距较大，取前两名提示即可
      //     //.data(data_ready)
      //     .enter()
      //     .append('polyline')
      //     .attr("stroke", "black")
      //     .style("fill", "none")
      //     .attr("stroke-width", 1)
      //     .attr('points', function(d) {
      //       let posA = arc.centroid(d) // line insertion in the slice
      //       let posB = outerArc.centroid(d) // line break: we use the other arc generator that has been built only for that
      //       let posC = outerArc.centroid(d); // Label position = almost the same as posB
      //       let midangle = d.startAngle + (d.endAngle - d.startAngle) / 2 // we need the angle to see if the X position will be at the extreme right or extreme left
      //       posC[0] = radius * 0.95 * (midangle < Math.PI ? 1 : -1); // multiply by 1 or -1 to put it on the right or on the left
      //       return [posA, posB, posC]
      //     })
      //     .style("opacity", 0)
      //     .transition().delay(1000).duration(800) // 等待环形图1000ms动画结束后播放提示信息的动画
      //     .style("opacity", 1)

      let text = g
          .selectAll('allLabels')
          // .data(data_ready.filter((d, i) => type == 4 ? i < 2 : i < 3))
          .data(data_ready)
          .enter()
          .append('text')
          .attr('transform', function(d) {
            let pos = outerArc.centroid(d);
            let midangle = d.startAngle + (d.endAngle - d.startAngle) / 2
            pos[0] = radius * 0.99 * (midangle < Math.PI ? 1 : -1);
            return 'translate(' + pos + ')';
          })
      text
          .append('tspan')
          .text(d => d.name)
          .style('text-anchor', function(d) {
            let midangle = d.startAngle + (d.endAngle - d.startAngle) / 2
            return (midangle < Math.PI ? 'start' : 'end')
          })
      text
          .append('tspan')
          // .text(d => '数量: ' + d.count + '     百分比: ' + (100 * d.data.attacks / attackCount.find(item => item.name == d.data.name).attacks).toFixed(2) +'%')
          .text(d => '数量: ' + d.count)
          .attr('x', 0)
          .attr('dy', 20)
          .style('text-anchor', function(d) {
            var midangle = d.startAngle + (d.endAngle - d.startAngle) / 2
            return (midangle < Math.PI ? 'start' : 'end')
          })
          .style('white-space', 'pre')
      text
          .style("opacity", 0)
          .transition().delay(1000).duration(800)
          .style("opacity", 1)
    },

  },
  mounted() {

  },
  created() {
    let svg = d3.select('#svg1')
    this.drawSingle(svg,this.data_circle_1,'Red2Blue')
  }
}
</script>

<style scoped>

</style>
