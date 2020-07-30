<template>
    <v-container v-if="!isLoading">
        <v-flex v-for="(matchData, idx) in matchDatas" :key="idx + '_matchData'">
            <v-card class="text-center ma-1 mb-2" :class="{ bg_win : matchDatas[idx][matchDatas[idx].myTeam].win, bg_fail : !matchDatas[idx][matchDatas[idx].myTeam].win}" outlined>
                <table height="100px">
                    <tr>
                        <td width="80px">
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
                                <small class="fs-11 font-weight-bold">{{matchDatas[idx][matchDatas[idx].myTeam].win ? '승리' : '패배'}}</small>
                            </v-row>
                            <v-row justify="center">
                                <small class="fs-11">{{changeTime(matchDatas[idx].duration)}}</small>
                            </v-row>
                        </td>

                        <td width="120px">
                            <div class="float-left">
                                <v-avatar size="40">
                                    <img :src="require('@/assets/images/champion/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].champ + '.png')"/>
                                </v-avatar>
                            </div>
                            <div class="float-left">
                                <div>
                                    <img :src="require('@/assets/images/spell/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].spell1 + '.png')" width="22px"/>
                                    <img :src="require('@/assets/images/perk/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].perk.toLowerCase() + '.png')" width="22px"/>
                                </div>
                                <div>
                                    <img :src="require('@/assets/images/spell/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].spell2 + '.png')" width="22px"/>
                                    <img :src="require('@/assets/images/perk/' + matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].perkStyle.toLowerCase() + '.png')" width="22px"/>
                                </div>
                            </div>
                            <small class="d-block clear fs-12 font-weight-bold">{{champoins.data[matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].champ].name}}</small>
                        </td>

                        <td width="90px">
                            <div class="fs-15 font-weight-bold">
                                <span>{{matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].kills}}</span>/
                                <span>{{matchDatas[idx][matchDatas[idx].myTeam].teammate[matchDatas[idx].myIndex].deaths}}</span>/
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
                        <td width="85px">
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

                                    <td width="22px" class="item-border">
                                    </td>
                                </tr>
                            </table>
                        </td>

                        <td width="160px">
                            <table class="align-middle" width="160px">
                                <tr class="align-middle" width="88px">
                                    <td class="align-middle" height="16px"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[0].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].blueTeam.teammate[0].name}}</span></td>
                                    <td><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[0].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].redTeam.teammate[0].name}}</span></td>
                                </tr>

                                <tr>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[1].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].blueTeam.teammate[1].name}}</span></td>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[1].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].redTeam.teammate[1].name}}</span></td>
                                </tr>

                                <tr>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[2].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].blueTeam.teammate[2].name}}</span></td>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[2].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].redTeam.teammate[2].name}}</span></td>
                                </tr>

                                <tr>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[3].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].blueTeam.teammate[3].name}}</span></td>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[3].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].redTeam.teammate[3].name}}</span></td>
                                </tr>

                                <tr>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].blueTeam.teammate[4].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].blueTeam.teammate[4].name}}</span></td>
                                    <td class="align-middle"><img :src="require('@/assets/images/champion/' + matchDatas[idx].redTeam.teammate[4].champ + '.png')" width="16px"/></td>
                                    <td><span class="fs-12 textover">{{matchDatas[idx].redTeam.teammate[4].name}}</span></td>
                                </tr>
                            </table>
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
            useridx: [],
            champoins,
            queues,
            isLoading : true
        }
    },
    mounted(){
        this.getMatchDatas();
        console.log(this.matchDatas[0][this.matchDatas[0].myTeam].win);
        //console.log(new Date(1595840710509));
    },
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
        async getMatchDatas(){
            await this.$store.dispatch('getMatchDatas', {
                userName: this.profileDatas.summonerName, 
                num : 1
            });
            
            for(let i = 0; i < this.matchDatas.length; i++) {
                for(let j = 0; j < this.matchDatas[i].length; j++){
                    if(this.matchDatas[i][j].name == this.profileDatas.summonerName){
                        this.useridx.push(j);
                        break;
                    }
                }
            }
            this.isLoading = false;
            console.log("loading done");
        },
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
        }
    }
};
</script>

<style>
.bg_blue{
    background-color: rgba(0, 0, 255, 0.25) !important;
}
.bg_red{
    background-color: rgba(255, 0, 0, 0.25) !important;
}
.clear {
    clear: both;
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
.fs-12{
    font-size: 0.75em;
}
.fs-11{
    font-size: 0.688em;
}
.fs-10{
    font-size: 0.625em;
}
.textover {
    overflow: hidden !important;
    text-overflow: ellipsis !important;
    white-space: nowrap !important;
    width: 60px !important;
    height : 24px !important; 
    display: block;
}
.item-border{
    border-radius: 4px;
    background-color: rgba(133, 133, 133, 0.25);
    vertical-align: bottom;
}
.bg_fail {
  background-color: #ffe1e1 !important;
}

.bg_win {
  background-color: #d1e5ff !important;
}
</style>