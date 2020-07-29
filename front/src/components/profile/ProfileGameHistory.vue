<template>
    <v-container>
        <v-flex v-for="(matchData, idx) in matchDatas" :key="idx + '_matchData'">
            <v-card class="text-center ma-1 mb-2" :class="{ bg_blue : matchDatas[idx][useridx[idx]].win, bg_red : !matchDatas[idx][useridx[idx]].win}" elevation="0" outlined>
                <table height="100px">
                    <tr>
                        <td width="20%">
                            <v-row justify="center">
                                <div class="pa-1 text-center">
                                    <small class="d-block">{{queues[matchDatas[idx][useridx[idx]].queue].shortName}}</small>
                                    <small class="d-block">1일전</small>
                                </div>
                            </v-row>
                            <v-row justify="center">
                                <hr style="border:1px color:silver;" width="30%">
                            </v-row>
                            <v-row justify="center">
                                <small>{{matchDatas[idx][useridx[idx]].win ? '승리' : '패배'}}</small>
                            </v-row>
                        </td>
                        <td width="20%">
                            <div class="float-left">
                                <v-avatar size="40">
                                    <img :src="require('@/assets/images/champion/' + matchDatas[idx][useridx[idx]].champ + '.png')"/>
                                </v-avatar>
                            </div>
                            <div class="float-left">
                                <div>
                                    <img :src="require('@/assets/images/spell/' + matchDatas[idx][useridx[idx]].spell1 + '.png')" width="22px"/>
                                    <img :src="require('@/assets/images/perk/' + matchDatas[idx][useridx[idx]].perk.toLowerCase() + '.png')" width="22px"/>
                                </div>
                                <div>
                                    <img :src="require('@/assets/images/spell/' + matchDatas[idx][useridx[idx]].spell2 + '.png')" width="22px"/>
                                    <img :src="require('@/assets/images/perk/' + matchDatas[idx][useridx[idx]].perkStyle.toLowerCase() + '.png')" width="22px"/>
                                </div>
                            </div>
                            <small class="d-block">{{champoins.data[matchDatas[idx][useridx[idx]].champ].name}}</small>
                        </td>
                        <td width="20%">
                            <div>
                                <span>{{matchDatas[idx][useridx[idx]].kills}}</span>/<span>{{matchDatas[idx][useridx[idx]].deaths}}</span>/<span>{{matchDatas[idx][useridx[idx]].assists}}</span>
                            </div>
                            <div>
                                <span>{{Math.round(matchDatas[idx][useridx[idx]].kda*100)/100}}:1 평점</span>
                            </div>
                            <div>
                                <span>킬관여{{Math.round(matchDatas[idx][useridx[idx]].ka)}}%</span>
                            </div>
                        </td>
                        <td>
                        </td>
                    </tr>
                </table>
            </v-card>
        </v-flex>
    </v-container>
</template>

<script>
import { mapActions } from "vuex"
import { mapState } from "vuex"
import champoins from "@/assets/data/champion.json";
import queues from "@/assets/data/queues.json";

export default {
    name: "ProfileGameHistory",
    data: function() {
        return {
            useridx: [],
            champoins,
            queues
        }
    },
    created(){
        this.$store.dispatch('getMatchDatas', {
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
        //console.log(new Date(1595840710509));
        //this.getMatchDatas('parkjamal', 1);
    },
    computed: {
      ...mapState([
        'profileDatas',
        'matchDatas'
      ]),
    },
    methods:{
        ...mapActions([
            'getMatchDatas'
        ]),
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
</style>