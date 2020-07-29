<template>
<v-app id="sandbox">
    <v-main>
        <table width="1000px">
            <tr>
                <td style="vertical-align: top" width="34%">
                    <v-card class="ma-1 mb-2" elevation="0" outlined height="150px">
                        <v-row>
                            <v-col cols="3">
                                <div class="avatar mt-3 ml-3 text-center">
                                    <v-avatar size="70">
                                        <v-img :src="require('@/assets/images/profileicon/'+item.profileIconId+'.png')"/>
                                    </v-avatar>
                                    <span class="level fs-10">{{item.summonerLevel}}</span>
                                </div>
                            </v-col>
                            <v-col cols="8">
                                <div class="pt-4 pl-4">
                                    <v-card-title class="headline nickname" v-text="item.summonerName"/>
                                    <v-btn class="mt-2 mr-1 py-3 px-2 fs-14" elevation="0" color="info">전적 갱신</v-btn>
                                    <span class="leastUpdate fs-10">최근 업데이트: 3시간 전</span>
                                </div>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12">
                                <div class="ml-2">
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
                    <v-card class="ma-1 mb-2" elevation="0" outlined height="300px" algin="center">
                        <ul class="options">
                            <li><a v-bind:class="{option_action: triger.rankGameActive}" @click="changeRankGame">랭크</a></li>
                            <li><a v-bind:class="{option_action: triger.nomalGameActive}" @click="changeNomarlGame">일반</a></li>
                        </ul>
                        <div class="mt-2 text-center">
                            <div class="icon pa-1 d-inline-block">
                                <span class="rank fs-14">{{now.rank}}</span>
                                <img :src="require('@/assets/images/tier/'+now.src+'.png')" class="d-block mx-auto" height= "75px"/>
                                <v-card-text>
                                    <span class="fs-14">{{Math.round(now.totalRecord.winRate*100)/100}}% (<span class="win fs-13">{{now.totalRecord.wins}}승</span> <span class="lose fs-13">{{now.totalRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                            <div class="icon pa-1 d-inline-block">
                                <v-avatar class="ma-3" size="70">
                                    <v-img :src="require('@/assets/images/champion/'+now.mostCham+'.png')" alt="모스트 픽"/>
                                </v-avatar>
                                <v-card-text>
                                    <span class="fs-14">{{Math.round(now.mostChamRecord.winRate*100)/100}}% (<span class="win fs-13">{{now.mostChamRecord.wins}}승</span> <span class="lose fs-13">{{now.mostChamRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                            <div class="icon pa-1 d-inline-block">
                                <v-avatar class="ma-3" size="50">
                                    <v-img :src="require('@/assets/images/position/'+now.mostLine+'.png')"/>
                                </v-avatar>
                                <v-card-text>
                                    <span class="fs-14">{{Math.round(now.mostLineRecord.winRate*100)/100}}% (<span class="win fs-13">{{now.mostLineRecord.wins}}승</span> <span class="lose fs-13">{{now.mostLineRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                            <div class="icon pa-1 d-inline-block">
                                <v-avatar class="ma-3" size="50">
                                    <v-img :src="require('@/assets/images/position/'+now.secondLine+'.png')"/>
                                </v-avatar>
                                <v-card-text>
                                    <span class="fs-14">{{Math.round(now.secondLineRecord.winRate*100)/100}}% (<span class="win fs-13">{{now.secondLineRecord.wins}}승</span> <span class="lose fs-13">{{now.secondLineRecord.losses}}패</span>)</span>
                                </v-card-text>
                            </div>
                        </div>
                    </v-card>
                </td>
                <td style="vertical-align: top">
                    <v-card class="text-center ma-1 mb-2" elevation="0" outlined>
                        <ul class="options">
                            <li><a v-bind:class="{option_action: triger.LPActive}" @click="changeLP">LP</a></li>
                            <li><a v-bind:class="{option_action: triger.totalPointActive}" @click="changeTotalPointDate">총점</a></li>
                        </ul>
                        <div class="px-5">
                            <LineChart/>
                        </div>
                    </v-card>

                    <v-card class="ma-1 mb-2" elevation="0" outlined>
                        <RadarChart/>
                    </v-card>
                    <ProfileGameHistory/>
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
import ProfileGameHistory from '@/components/profile/ProfileGameHistory';
import { mapActions } from "vuex"
import { mapState } from "vuex"
//import { mapGetters } from  "vuex"

export default {
    name:'Profile',
    components:{
        RadarChart,
        LineChart,
        ProfileGameHistory
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
        this.now = this.item.rankedRecord;
        this.now.src = this.item.tier;
        this.now.rank = this.item.rank;
    },
    computed: {
      ...mapState([
        'profileDatas',
      ]),
    //   ...mapGetters([
    //       'profileDatas'
    //   ]),
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

<style scoped>
* {
    padding : 0px !important;
    margin : 0px !important;
}
.v-avatar {
    border : rgb(200, 170, 110) 1px solid;
}
.avatar{
    height: 84px;
}
.level {
    border : gold 1px solid;
    border-radius: 50px;
    padding: 2px 5px 2px 5px !important;
    color:gold;
    font-weight: bold;
    position: relative;
    top: -15px;
    background-color: rgb(26, 25, 25);
}
.v-btn {
    height: 30px !important;
}
.leastUpdate{
    position: absolute;
    transform: translate(0, 16px);
}
.v-chip{
    padding: 2px 10px 0 10px !important;
}
.rank{
    font-family: cursive;
    color: rgb(231, 197, 0);
    font-weight: 500;
    text-shadow: -1px 0 #000000, 0 1px #5e5e5e, 1px 0 #5e5e5e, 0 -1px #5e5e5e;
}
.v-card {
    background-color: #fafafa !important;
}
.apexcharts-canvas {
    text-align: center;
    margin-left : 0px !important;
}
.options {
    list-style-type: none;
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
    width: 130px;
}
.fs-15{
    font-size: 0.938em;
}
.fs-14{
    font-size: 0.875em;
}
.fs-13{
    font-size: 0.813em;
}
.fs-10{
    font-size: 0.625em;
}
.win{
    color:rgb(1, 1, 211);
}
.lose{
    color:rgb(211, 1, 1);
}
</style> 
