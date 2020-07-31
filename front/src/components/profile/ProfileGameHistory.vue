<template>
    <v-container>
        <v-flex v-for="(matchData, idx) in matchDatas" :key="idx + '_matchData'">
            <v-card class="ma-1 mb-2 vertical-align-center" :class="{ bg_win : matchDatas[idx][matchDatas[idx].myTeam].win, bg_fail : !matchDatas[idx][matchDatas[idx].myTeam].win, bg_gray : matchDatas[idx].noGame}" outlined>
                <table height="100px">
                    <tr>
                        <td class="text-center" width="80px">
                            <v-row justify="center">
                                <div class="pa-1 text-center">
                                    <small class="d-block fs-11 font-weight-bold">{{queues[matchDatas[idx].queue].shortName}}</small>
                                    <small class="d-block fs-11">{{calcDate(matchDatas[idx].timestamp)}}</small>
                                </div>
                            </v-row>
                            <v-row justify="center">
                                <hr class="mb-2 mt-1" style="border:1px color:silver;" width="30%">
                            </v-row>
                            <v-row justify="center">
                                <small class="fs-11 font-weight-bold" v-if="matchDatas[idx].noGame">다시하기</small>
                                <small class="fs-11 font-weight-bold" :class="{ fc_blue : matchDatas[idx][matchDatas[idx].myTeam].win, fc_red: !matchDatas[idx][matchDatas[idx].myTeam].win}" v-else>{{matchDatas[idx][matchDatas[idx].myTeam].win ? '승리' : '패배'}}</small>
                            </v-row>
                            <v-row justify="center">
                                <small class="fs-11">{{changeTime(matchDatas[idx].duration)}}</small>
                            </v-row>
                        </td>

                        <td class="text-center" width="110px">
                            <div class="float-left">
                                <v-avatar class="mr-1" size="40">
                                    <img :src="require('@/assets/images/champion/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].champ + '.png')"/>
                                </v-avatar>
                            </div>
                            <div class="float-left">
                                <div>
                                    <img :src="require('@/assets/images/spell/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].spell1 + '.png')" class="vertical-align-bottom mr-1" width="22px"/>
                                    <img :src="require('@/assets/images/perk/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].perk.toLowerCase() + '.png')" class="vertical-align-bottom border-rounded bg_black" width="22px"/>
                                </div>
                                <div>
                                    <img :src="require('@/assets/images/spell/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].spell2 + '.png')" class="vertical-align-bottom mr-1" width="22px"/>
                                    <img :src="require('@/assets/images/perk/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].perkStyle.toLowerCase() + '.png')" class="vertical-align-bottom border-rounded bg_gray_op" width="22px"/>
                                </div>
                            </div>
                            <small class="d-block clear fs-12 font-weight-bold">{{champoins.data[matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].champ].name}}</small>
                        </td>

                        <td class="text-center" width="90px">
                            <div class="fs-15 font-weight-bold">
                                <span>{{matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].kills}}</span>/
                                <span class="fc_red">{{matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].deaths}}</span>/
                                <span>{{matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].assists}}</span>
                            </div>
                            <div class="fs-13">
                                <span v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].kda == 'Infinity'">Perfect</span>
                                <span v-else>{{Math.round(matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].kda*100)/100}}:1 평점</span>
                            </div>
                            <div class="fs-13">
                                <span>킬관여 {{Math.round(matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].ka)}}%</span>
                            </div>
                        </td>
                        <td class="text-center" width="85px">
                            <span class="d-block fs-12">레벨 {{matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].level}}</span>
                            <span class="d-block fs-12">{{matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].cs}} ({{Math.round(matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].csPerMin*100)/100}}) CS</span>
                        </td>

                        <td width="90px">
                            <table>
                                <tr>
                                    <td width="22px" v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item0 != 0">
                                        <img :src="require('@/assets/images/item/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item0 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                    <td width="22px" v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item1 != 0">
                                        <img :src="require('@/assets/images/item/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item1 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>
                                    
                                    <td width="22px" v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item2 != 0">
                                        <img :src="require('@/assets/images/item/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item2 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                    <td width="22px" v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item6 != 0">
                                        <img :src="require('@/assets/images/item/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item6 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="22px" v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item3 != 0">
                                        <img :src="require('@/assets/images/item/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item3 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                    <td width="22px" v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item4 != 0">
                                        <img :src="require('@/assets/images/item/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item4 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>
                                    
                                    <td width="22px" v-if="matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item5 != 0">
                                        <img :src="require('@/assets/images/item/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].item5 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                    <td width="22px" height="22px" class="item-border">
                                    </td>
                                </tr>
                            </table>
                        </td>

                        <td width="160px">
                            <div class="fs-0">
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[0].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].blueTeam.teammate[0].name)">{{matchDatas[idx].blueTeam.teammate[0].name}}</a></div>
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[0].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].redTeam.teammate[0].name)">{{matchDatas[idx].redTeam.teammate[0].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[1].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].blueTeam.teammate[1].name)">{{matchDatas[idx].blueTeam.teammate[1].name}}</a></div>
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[1].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].redTeam.teammate[1].name)">{{matchDatas[idx].redTeam.teammate[1].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[2].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].blueTeam.teammate[2].name)">{{matchDatas[idx].blueTeam.teammate[2].name}}</a></div>
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[2].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].redTeam.teammate[2].name)">{{matchDatas[idx].redTeam.teammate[2].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[3].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].blueTeam.teammate[3].name)">{{matchDatas[idx].blueTeam.teammate[3].name}}</a></div>
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[3].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].redTeam.teammate[3].name)">{{matchDatas[idx].redTeam.teammate[3].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[4].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].blueTeam.teammate[4].name)">{{matchDatas[idx].blueTeam.teammate[4].name}}</a></div>
                                <div class="summonerNametd"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[4].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchDatas[idx].redTeam.teammate[4].name)">{{matchDatas[idx].redTeam.teammate[4].name}}</a></div>
                            </div>
                        </td>
                    </tr>
                </table>
            </v-card>
        </v-flex>
    </v-container>
</template>

<script>
//import { mapActions } from "vuex"
import { mapState } from "vuex"
import champoins from "@/assets/data/champion.json";
import queues from "@/assets/data/queues.json";

export default {
    name: "ProfileGameHistory",
    data: function() {
        return {
            champoins,
            queues,
            isLoading : true
        }
    },
    // mounted(){
    //     this.getMatchDatas();
    //     console.log(this.matchDatas[0][this.matchDatas[0].myTeam].win);
    //     //console.log(new Date(1595840710509));
    // },
    computed: {
      ...mapState([
        'profileDatas',
        'matchDatas'
      ]),
    },
    methods:{
        // ...mapActions([
        //     'getMatchDatas'
        // ]),
        // async getMatchDatas(){
        //     await this.$store.dispatch('getMatchDatas', {
        //         userName: this.profileDatas.summonerName, 
        //         num : 1
        //     });
            
        //     this.isLoading = false;
        //     console.log("loading done");
        // },
        calcDate(time) {
            let now = new Date();
            let gametime = new Date(time);
            let result = "";
            let diff = now.getTime() - gametime.getTime();
            if(Math.floor(diff/(1000*3600*24)) > 0){
                result = (gametime.getMonth()+1) + "/" + gametime.getDate();
            }else{
                diff = Math.floor(diff%(1000*3600*24)/(1000*3600));
                result = diff +"시간 전";
            }
            return result;
        },
        changeTime(time){
            return Math.floor(time/60)+ "분 " + (time - Math.floor(time/60)*60) +"초"
        },
        toProfile(nickname){
            this.$router.push('/Profile/' + nickname);
        }
    }
};
</script>

<style>

.textover {
    overflow: hidden !important;
    text-overflow: ellipsis !important;
    white-space: nowrap !important;
    width: 60px !important;
    display: block;
    height: 16px;
    text-decoration: none;
    color : black !important;
}
.item-border{
    border-radius: 4px;
    background-color: rgba(133, 133, 133, 0.25);
    vertical-align: bottom;
}

.summonerNametd {
    display: inline-block;
    vertical-align: center;
    margin-bottom: 1px;
}

</style>