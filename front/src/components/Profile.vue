<template>
<v-app id="sandbox">
    <v-main>
        <v-container fluid>
            <v-row algin="center" justify="center">
                <v-col cols="10">
                    <table width="100%">
                        <tr>
                            <td width="30%">
                                <v-card height="150px" algin="center">
                                    <v-row>
                                        <v-col cols="3">
                                            <div class="avatar">
                                                <v-avatar class="ma-3" size="70">
                                                    <v-img :src= "item.iconSrc"/>
                                                </v-avatar>
                                            </div>
                                        </v-col>
                                        <v-col cols="6">
                                            <v-card-title class="headline" v-text="item.nickname"/>
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
                                <v-card>
                                    <v-row>
                                        <v-col cols="1">
                                            <v-btn class="graph_btn">
                                                LP
                                            </v-btn>
                                        </v-col>
                                        <v-col cols="1">
                                            <v-btn class="graph_btn"> 
                                                총점
                                            </v-btn>
                                        </v-col>
                                    </v-row>
                                </v-card>
                                
                                <v-card style="margin-top:10px">
                                    <div>
                                    <LineChart/>
                                    </div>
                                </v-card>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <v-card height="150px" algin="center">
                                    <div>
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src="item.lank.src" />
                                        </v-avatar>
                                        <v-card-text>
                                            {{item.lank.winRate}} ({{item.lank.win}}승 {{item.lank.lose}}패)
                                        </v-card-text>
                                    </div>
                                </v-card>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <v-card height="150px" algin="center">
                                    <div>
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src="item.mostCham.src" />
                                        </v-avatar>
                                        <v-card-text>
                                            {{item.mostCham.winRate}} ({{item.mostCham.win}}승 {{item.mostCham.lose}}패)
                                        </v-card-text>
                                    </div>
                                </v-card>
                            </td>
                            <td rowspan="2">
                                <v-card style="margin-top:10px">
                                    <RadarChart/>
                                </v-card>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <v-card height="150px" algin="center">
                                    <div style="display:inline-block;">
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src="item.mostLine.src"/>
                                        </v-avatar>
                                        <v-card-text>
                                            {{item.mostLine.winRate}} ({{item.mostLine.win}}승 {{item.mostLine.lose}}패)
                                        </v-card-text>
                                    </div>
                                    <div style="display:inline-block;">
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src="item.secondLine.src"/>
                                        </v-avatar>
                                        <v-card-text>
                                            {{item.secondLine.winRate}} ({{item.secondLine.win}}승 {{item.secondLine.lose}}패)
                                        </v-card-text>
                                    </div>
                                </v-card>
                            </td>
                        </tr>
                    </table>
                </v-col>
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
        item:{
            nickname : 'BBluee',
            iconSrc : require('@/assets/images/937.png'), 
            lank: {
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
    })
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
.v-btn {
    margin: 10px !important;
}
.v-chip{
    padding-left: 10px !important;
    padding-right: 10px !important;
}
.v-card {
    margin: 5px !important;
    padding: 5px 10px 5px 10px !important;
}
</style> 
