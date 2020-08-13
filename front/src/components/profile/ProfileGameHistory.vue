<template>
    <v-container>
        <v-flex v-for="(matchData, idx) in matchDatas" :key="idx + '_matchData'">
            <v-card class="ma-1 mb-2 vertical-align-center" :class="{ bg_win : matchData[matchData.myTeam].win, bg_fail : !matchData[matchData.myTeam].win, bg_gray : matchData.noGame}" outlined>
                <table height="100px">
                    <tr>
                        <td class="text-center" width="80px">
                            <v-row justify="center">
                                <div class="pa-1 text-center">
                                    <small class="d-block fs-11 font-weight-bold">{{queues[matchData.queue].shortName}}</small>
                                    <small class="d-block fs-11">{{calcDate(matchData.timestamp)}}</small>
                                </div>
                            </v-row>
                            <v-row justify="center">
                                <hr class="mb-2 mt-1" style="border:1px color:silver;" width="30%">
                            </v-row>
                            <v-row justify="center">
                                <small class="fs-11 font-weight-bold" v-if="matchData.noGame">다시하기</small>
                                <small class="fs-11 font-weight-bold" :class="{ fc_blue : matchData[matchData.myTeam].win, fc_red: !matchData[matchData.myTeam].win}" v-else>{{matchData[matchData.myTeam].win ? '승리' : '패배'}}</small>
                            </v-row>
                            <v-row justify="center">
                                <small class="fs-11">{{changeTime(matchData.duration)}}</small>
                            </v-row>
                        </td>

                        <td class="text-center" width="110px">
                            <div class="float-left pr-1">
                                <v-avatar size="48">
                                    <img :src="imageload('champion/' + matchData[matchData.myTeam].teammate[matchData.myIndex].champ + '.png')" />
                                </v-avatar>
                            </div>
                            <div class="float-left">
                                <div>
                                    <img :src="imageload('spell/' + matchData[matchData.myTeam].teammate[matchData.myIndex].spell1 + '.png')"  class="vertical-align-bottom mr-1" width="22px"/>
                                    <img :src="imageload('perk/' + matchData[matchData.myTeam].teammate[matchData.myIndex].perk.toLowerCase() + '.png')"  class="vertical-align-bottom border-rounded bg_black" width="22px"/>
                                </div>
                                <div>
                                    <img :src="imageload('spell/' + matchData[matchData.myTeam].teammate[matchData.myIndex].spell2 + '.png')" class="vertical-align-bottom mr-1" width="22px"/>
                                    <img :src="imageload('perk/' + matchData[matchData.myTeam].teammate[matchData.myIndex].perkStyle.toLowerCase() + '.png')" class="vertical-align-bottom border-rounded bg_gray_op" width="22px"/>
                                </div>
                            </div>
                            <small class="d-block clear fs-12 pt-2 font-weight-bold">{{champoins.data[matchData[matchData.myTeam].teammate[matchData.myIndex].champ].name}}</small>
                        </td>

                        <td class="text-center" width="90px">
                            <div class="fs-15 font-weight-bold">
                                <span>{{matchData[matchData.myTeam].teammate[matchData.myIndex].kills}}</span> /
                                <span class="fc_red">{{matchData[matchData.myTeam].teammate[matchData.myIndex].deaths}}</span> /
                                <span>{{matchData[matchData.myTeam].teammate[matchData.myIndex].assists}}</span>
                            </div>
                            <div class="fs-13">
                                <span v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].kda == 'Infinity'">Perfect</span>
                                <span v-else>평점 {{Math.round(matchData[matchData.myTeam].teammate[matchData.myIndex].kda*100)/100}}</span>
                            </div>
                            <div class="fs-13">
                                <span>킬관여 {{Math.round(matchData[matchData.myTeam].teammate[matchData.myIndex].ka)}}%</span>
                            </div>
                        </td>
                        <td class="text-center" width="85px">
                            <span class="d-block fs-12">레벨 {{matchData[matchData.myTeam].teammate[matchData.myIndex].level}}</span>
                            <span class="d-block fs-12">{{matchData[matchData.myTeam].teammate[matchData.myIndex].cs}} ({{Math.round(matchData[matchData.myTeam].teammate[matchData.myIndex].csPerMin*100)/100}}) CS</span>
                        </td>

                        <td width="90px">
                            <table>
                                <tr>
                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item0 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item0 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item1 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item1 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>
                                    
                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item2 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item2 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                    <td width="22px" rowspan="2" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item6 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item6 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item3 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item3 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item4 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item4 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>
                                    
                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item5 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item5 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" class="item-border" v-else>
                                    </td>

                                </tr>
                            </table>
                        </td>

                        <td width="160px">
                            <div class="fs-0 ml-2">
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.blueTeam.teammate[0].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.blueTeam.teammate[0].name)">{{matchData.blueTeam.teammate[0].name}}</a></div>
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.redTeam.teammate[0].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.redTeam.teammate[0].name)">{{matchData.redTeam.teammate[0].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.blueTeam.teammate[1].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.blueTeam.teammate[1].name)">{{matchData.blueTeam.teammate[1].name}}</a></div>
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.redTeam.teammate[1].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.redTeam.teammate[1].name)">{{matchData.redTeam.teammate[1].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.blueTeam.teammate[2].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.blueTeam.teammate[2].name)">{{matchData.blueTeam.teammate[2].name}}</a></div>
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.redTeam.teammate[2].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.redTeam.teammate[2].name)">{{matchData.redTeam.teammate[2].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.blueTeam.teammate[3].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.blueTeam.teammate[3].name)">{{matchData.blueTeam.teammate[3].name}}</a></div>
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.redTeam.teammate[3].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.redTeam.teammate[3].name)">{{matchData.redTeam.teammate[3].name}}</a></div>
                            
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.blueTeam.teammate[4].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.blueTeam.teammate[4].name)">{{matchData.blueTeam.teammate[4].name}}</a></div>
                                <div class="summonerNametd"><img :src="imageload('champion/' + matchData.redTeam.teammate[4].champ + '.png')" class="vertical-align-bottom" width="16px"/></div>
                                <div class="summonerNametd"><a class="fs-12px textover" @click="toProfile(matchData.redTeam.teammate[4].name)">{{matchData.redTeam.teammate[4].name}}</a></div>
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
	// 	this.getMatchDatas();
	// 	console.log(this.matchDatas[0][this.matchDatas[0].myTeam].win);
	// 	//console.log(new Date(1595840710509));
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
            window.open(this.$router.resolve('/Profile/' + nickname).href, '_blank');
        },
		imageload(URL){
            try{
                return require('@/assets/images/'+ URL);
            }catch{
                return require('@/assets/images/error.png');
            }
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