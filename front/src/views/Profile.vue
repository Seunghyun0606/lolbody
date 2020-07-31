<template>
<v-app id="sandbox">
    <v-main>
        <table width="1000px">
            <tr>
                <td width="35%">
                    <v-card elevation="0" outlined height="150px" algin="center">
                        <v-row>
                            <v-col cols="3">
                                <div class="avatar">
                                    <v-avatar size="70">
                                        <v-img :src="require('@/assets/images/profileicon/'+item.profileIconId+'.png')"/>
                                    </v-avatar>
                                    <span class="level">{{item.summonerLevel}}</span>
                                </div>
                            </v-col>
                            <v-col cols="8">
                                <div class="nicknameBox">
                                    <v-card-title class="headline nickname" v-text="item.summonerName"/>
                                    <v-btn elevation="0" color="info">전적 갱신</v-btn>
                                    <span class="leastUpdate">최근 업데이트: 3시간 전</span>
                                </div>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12">
                                <div class="badge">
                                    <v-chip class="ma-2" color="green" text-color="white">
                                    배지1
                                    </v-chip>
                                    <v-chip class="ma-2" color="green" text-color="white">
                                    배지2
                                    </v-chip>
                                    <v-chip class="ma-2" color="green" text-color="white">
                                    배지3
                                    </v-chip>
                                </div>
                            </v-col>
                        </v-row>
                    </v-card>
                </td>
                <td rowspan="2">
                    <v-card elevation="0" outlined style="margin-top:10px;text-align:center;">
                        <ul class="options">
                            <li><a v-bind:class="{option_action: triger.LPActive}" @click="changeLP">LP</a></li>
                            <li><a v-bind:class="{option_action: triger.totalPointActive}" @click="changeTotalPointDate">총점</a></li>
                        </ul>
                        <div class="LineChart">
                            <LineChart/>
                        </div>
                    </v-card>
                </td>
            </tr>
            <tr>
                <td>
                    <v-card elevation="0" outlined height="300px" algin="center">
                        <ul class="options">
                            <li><a v-bind:class="{option_action: triger.rankGameActive}" @click="changeRankGame">랭크</a></li>
                            <li><a v-bind:class="{option_action: triger.nomalGameActive}" @click="changeNomarlGame">일반</a></li>
                        </ul>
                        <div class="icons">
                            <div class="icon">
                                <div class="avatar">
                                    <img :src="require('@/assets/images/tier/'+now.src+'.png')" height= "75px"/>
                                    <span class="rank">{{now.rank}}</span>
                                </div>
                                <v-card-text>
                                    <span class="winRate">{{Math.round(now.totalRecord.winRate*100)/100}}% (<span class="win">{{now.totalRecord.wins}}승</span> <span class="lose">{{now.totalRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                            <div class="icon">
                                <v-avatar class="ma-3" size="50">
                                    <v-img :src="require('@/assets/images/position/'+now.mostLine+'.png')"/>
                                </v-avatar>
                                <v-card-text>
                                    <span class="winRate">{{Math.round(now.mostLineRecord.winRate*100)/100}}% (<span class="win">{{now.mostLineRecord.wins}}승</span> <span class="lose">{{now.mostLineRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                            <div class="icon">
                                <v-avatar class="ma-3" size="50">
                                    <v-img :src="require('@/assets/images/position/'+now.secondLine+'.png')"/>
                                </v-avatar>
                                <v-card-text>
                                    <span class="winRate">{{Math.round(now.secondLineRecord.winRate*100)/100}}% (<span class="win">{{now.secondLineRecord.wins}}승</span> <span class="lose">{{now.secondLineRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                            <div class="icon">
                                <v-avatar class="ma-3" size="70">
                                    <v-img :src="require('@/assets/images/champion/'+now.mostCham+'.png')" />
                                </v-avatar>
                                <v-card-text>
                                    <span class="winRate">{{Math.round(now.mostChamRecord.winRate*100)/100}}% (<span class="win">{{now.mostChamRecord.wins}}승</span> <span class="lose">{{now.mostChamRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                        </div>
                    </v-card>
                </td>
            </tr>
            <tr>
                <td>
                    <v-card elevation="0" outlined height="150px" algin="center">
                        
                    </v-card>
                </td>
                <td rowspan="2">
                    <v-card elevation="0" outlined style="margin-top:10px">
                        <RadarChart/>
                    </v-card>
                </td>
            </tr>
        </table>
    </v-main>
</v-app>
</template>

<script>
//import axios from 'axios';
import RadarChart from '@/components/profile/RadarChart';
import LineChart from '@/components/profile/LineChart';
import { mapActions } from "vuex"
import { mapState } from "vuex"
import { mapGetters } from  "vuex"

export default {
    name:'Profile',
    components:{
        RadarChart,
        LineChart
    },
    data: ()=>({
        triger:{
            totalPointActive: false,
            LPActive: true,
            nomalGameActive : false,
            rankGameActive : true
        },
        now:{},
        item:{}
    }),
    created(){
        // axios.get('http://13.125.220.135/profile/parkjamal')
        // .then(({data}) => {
        //     this.item = data;
        //     console.log(data);
        //     this.now = this.item.rankedRecord;
        //     this.now.src = this.item.tier;
        // }).catch(function (error) {
        //     if (error.response) {
        //         console.log(error.response.data);
        //         console.log(error.response.status);
        //         console.log(error.response.headers);
        //     }
        // });
        this.getProfileDatas('parkjamal');
        this.item = this.profileDatas;
        console.log(this.item);
        this.now = this.item.rankedRecord;
        this.now.src = this.item.tier;
        this.now.rank = this.item.rank;
    },
    computed: {
      ...mapState([
        'profileDatas',
      ]),
      ...mapGetters([
      ]),
    },
    methods:{
        ...mapActions([
            'getProfileDatas',
        ]),
        getRankData(){
            //LineChart에 데이터  전달
        },
        getTotalPointData(){
            //LineChart에 데이터  전달
        },
        changeNomarlGame(){
            this.triger.nomalGameActive = true;
            this.triger.rankGameActive = false;
            this.now = this.item.blindRecord;
            this.now.src = 'unranked';
            this.now.rank = 'unranked';
        },
        changeRankGame(){
            this.triger.nomalGameActive = false;
            this.triger.rankGameActive = true;
            this.now = this.item.rankedRecord;
            this.now.src = this.item.tier;
            this.now.rank = this.item.rank;
        },
        changeLP(){
            this.triger.LPActive = true;
            this.triger.totalPointActive = false;
        },
        changeTotalPointDate(){
            this.triger.LPActive = false;
            this.triger.totalPointActive = true;
        }
    }
}
</script>

<style>
* {
    padding : 0px !important;
    margin : 0px !important;
}
.graph_btn {
    margin-left: 10px !important;
}
td{
    vertical-align: top;
}
.v-avatar {
    border : rgb(200, 170, 110) 1px solid;
}
.avatar{
    text-align: center;
    height: 84px;
    margin: 10px 0 0 10px !important;
}
.level{
    border : gold 1px solid;
    border-radius: 50px;
    padding: 2px 5px 2px 5px !important;
    font-size: 0.625em;
    color:gold;
    font-weight: bold;
    position: relative;
    top: -15px;
    background-color: rgb(26, 25, 25);
}
.nicknameBox{
    padding: 15px 0 0 10px !important;
}
.v-btn {
    margin: 4px 4px 0 0 !important;
    padding: 3px 6px 3px 6px !important;
    height: 30px !important;
}
.v-btn__content{
    font-size: 0.875em;
}
.leastUpdate{
    position: absolute;
    transform:translate(0, 13px);
    font-size: 0.625em;
}
.badge{
    margin-left: 5px !important;
}
.v-chip{
    padding: 2px 10px 0 10px !important;
}
.v-card {
    margin: 5px !important;
    background-color: #fafafa !important;
}
.apexcharts-canvas {
    text-align: center;
    margin-left : 0px !important;
}
.icons {
    margin-top: 10px !important;
    text-align: center;
}
.LineChart{
    padding: 0 30px 0 30px !important;
}

.options {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #c4c4c4;
    box-shadow: 2px 2px 2px rgb(161, 161, 161) inset;
    position: relative;
    top : -0.3px;
}
.options li {
    float: left;
    width: 100px;
}
.options li a {
    height: 40px;
    display: block;
    color: black;
    text-align: center;
    padding: 9px 0 0 0 !important;
    text-decoration: none;
    background-color: #c4c4c4;
    box-shadow: 2px 2px 2px rgb(161, 161, 161) inset;
}
.options li a:hover:not(.option_action) {
    background-color: rgb(160, 160, 161);
}
.option_action {
    background-color: #fafafa !important;
    box-shadow: 2px 2px 2px rgb(161, 161, 161) !important;
}

.icon {
    display:inline-block;
    padding: 5px !important;
    width: 112px;
}
.winRate{
    font-size: 0.875em;
}
.win{
    color:rgb(1, 1, 211);
    font-size: 0.8125em;
}
.lose{
    color:rgb(214, 0, 0);
    font-size: 0.8125em;
}
</style> 
