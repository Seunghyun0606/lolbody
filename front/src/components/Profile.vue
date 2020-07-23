<template>
<v-app id="sandbox">
    <v-main>
        <v-container fluid>
            <v-row align="center" justify="center">
                <table width="1000px">
                    <tr>
                        <td width="35%">
                            <v-card height="150px" algin="center">
                                <v-row>
                                    <v-col cols="3">
                                        <div class="avatar">
                                            <v-avatar class="ma-3" size="70">
                                                <v-img :src= "item.iconSrc"/>
                                            </v-avatar>
                                            <span class="level">105</span>
                                        </div>
                                    </v-col>
                                    <v-col cols="6">
                                        <v-card-title class="headline nickname" v-text="item.nickname"/>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12">
                                        <v-chip class="ma-2" color="green" text-color="white">
                                        배지1
                                        </v-chip>
                                        <v-chip class="ma-2" color="green" text-color="white">
                                        배지2
                                        </v-chip>
                                        <v-chip class="ma-2" color="green" text-color="white">
                                        배지3
                                        </v-chip>
                                    </v-col>
                                </v-row>
                            </v-card>
                        </td>
                        <td rowspan="2">
                            <v-card style="margin-top:10px;text-align:center;">
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
                            <v-card height="300px" algin="center">
                                <ul class="options">
                                    <li><a v-bind:class="{option_action: triger.rankGameActive}" @click="changeRankGame">랭크</a></li>
                                    <li><a v-bind:class="{option_action: triger.nomalGameActive}" @click="changeNomarlGame">일반</a></li>
                                </ul>
                                <div class="icons">
                                    <div class="icon">
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src="item.rank.src"/>
                                        </v-avatar>
                                        <v-card-text>
                                            <span>{{item.rank.winRate}}</span> (<span class="win">{{item.rank.win}}승</span> <span class="lose">{{item.rank.lose}}패</span>)
                                        </v-card-text>
                                    </div>
                                    <div class="icon">
                                        <v-avatar class="ma-3" size="50">
                                            <v-img :src="item.mostLine.src"/>
                                        </v-avatar>
                                        <v-card-text>
                                            {{item.mostLine.winRate}} (<span class="win">{{item.mostLine.win}}승</span> <span class="lose">{{item.mostLine.lose}}패</span>)
                                        </v-card-text>
                                    </div>
                                    <div class="icon">
                                        <v-avatar class="ma-3" size="50">
                                            <v-img :src="item.secondLine.src"/>
                                        </v-avatar>
                                        <v-card-text>
                                            {{item.secondLine.winRate}} (<span class="win">{{item.secondLine.win}}승</span> <span class="lose">{{item.secondLine.lose}}패</span>)
                                        </v-card-text>
                                    </div>
                                    <div class="icon">
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src="item.mostCham.src" />
                                        </v-avatar>
                                        <v-card-text>
                                            {{item.mostCham.winRate}} (<span class="win">{{item.mostCham.win}}승</span> <span class="lose">{{item.mostCham.lose}}패</span>)
                                        </v-card-text>
                                    </div>
                                </div>
                            </v-card>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <v-card height="150px" algin="center">
                                
                            </v-card>
                        </td>
                        <td rowspan="2">
                            <v-card style="margin-top:10px">
                                <RadarChart/>
                            </v-card>
                        </td>
                    </tr>
                </table>
            </v-row>
        </v-container>
    </v-main>
</v-app>
</template>

<script>
import RadarChart from '@/components/RadarChart';
import LineChart from '@/components/LineChart';

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
        item:{
            nickname : 'BBluee',
            iconSrc : require('@/assets/images/937.png'), 
            rank: {
                src : require('@/assets/images/Emblem_Challenger.png'),
                winRate: '55%',
                win : '11',
                lose: '9'
            },
            mostCham: {
                src : require('@/assets/images/Senna.png'),
                winRate: '55%',
                win : '11',
                lose: '9'
            },
            mostLine: {
                src : require('@/assets/images/SUP.png'),
                winRate: '55%',
                win : '11',
                lose: '9'
            },
            secondLine:{
                src : require('@/assets/images/JGL.png'),
                winRate: '55%',
                win : '11',
                lose: '9'
            },
        }
    }),
    methods:{
        getRankData(){
            //LineChart에 데이터  전달
        },
        getTotalPointData(){
            //LineChart에 데이터  전달
        },
        changeNomarlGame(){
            this.triger.nomalGameActive = true;
            this.triger.rankGameActive = false;
        },
        changeRankGame(){
            this.triger.nomalGameActive = false;
            this.triger.rankGameActive = true;
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
    border : #BDBDBD 1px solid;
}
.nickname {
    padding: 30px 0 0 5px !important;
}
.level{
    border : gold 0.5px solid;
    border-radius: 50px;
    padding: 2px 5px 2px 5px !important;
    font-size: 5px;
    color:gold;
    font-weight: bold;
    position: absolute;
    transform: translate(32px, -27px);
    background-color: rgb(26, 25, 25);
}
.v-btn {
    margin: 10px !important;
}
.v-chip{
    padding-left: 10px !important;
    padding-right: 10px !important;
}
.v-card {
    margin: 5px !important;
}
.apexcharts-canvas {
    text-align: center;
    margin-left : 0px !important;
}
.icons {
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
}
.options li {
    float: left;
    width: 50%;
}
.options li a {
    height: 30px;
    display: block;
    color: black;
    text-align: center;
    padding: 4px 0 0 0 !important;
    text-decoration: none;
    background-color: #8b8b8b;
}
.options li a:hover:not(.option_action) {
    background-color: rgb(160, 160, 160);
}
.option_action {
    background-color: white !important;
    border: white 0px solid !important;
}

.icon {
    display:inline-block;
    padding: 5px !important;
}
.win{
    color:rgb(2, 2, 214);
    font-size: 8px;
}
.lose{
    color:rgb(223, 3, 3);
    font-size: 8px;
}
</style> 
