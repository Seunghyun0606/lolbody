<template>
    <div>
        <apexchart class='move-apexchart' type="radar" width="500px" :options="chartOptions" :series="series" v-if="check"></apexchart>
        <p class="text-center mt-5" v-else>전적이 없습니다</p>
    </div>
</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
    name: 'ProfileRadarChart',
    components: {
        apexchart
    },
    props: ['idx'],
    computed: {
        datas(){
            let tmp = this.$store.getters.getProfileRadarChart[this.idx];
            let result = [0, 0, 0];
            if(!isNaN(tmp[0]))
                result[0] = tmp[0];
            if(!isNaN(tmp[1]))
                result[1] = tmp[1];
            if(!isNaN(tmp[2]))
                result[2] = tmp[2];
            return result;
        },
        check(){
            if(this.datas.lenght == 0 || this.datas == null || isNaN(this.datas[0]) || isNaN(this.datas[1]) || isNaN(this.datas[2]))
                return false;
            return true;
        },
        chartOptions() {
            return {
                chart: {
                    toolbar: {
                        show: false,
                    },
                    legend:{
                        position: 'top'
                    },
                },
                xaxis: {
                    categories: ['공격력', '안정성', '영향력'],
                },
                yaxis: {
                    show: false,
                    tickAmount: 5,
                    min: 0,
                    max: 1,
                },
                markers: {
                    size: 3
                },
                tooltip: {
                    y: {
                        title: {
                            formatter: function() {
                                return ''
                            }
                        }
                    },
                }
            };
        },
        series(){
            return [{ data: this.datas}];
        }
    }
}
</script>

<style scoped>

.move-apexchart {
  position: relative;
  left: -90px;
}

</style>