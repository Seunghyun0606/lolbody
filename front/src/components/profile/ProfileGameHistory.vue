<template>
    <v-container>
        <v-flex v-for="(matchData, idx) in matchDatas" :key="idx + '_matchData'">
            <v-card class="mx-1 mb-2 vertical-align-center overflow-hidden z-index1" :class="{ bg_win : matchData[matchData.myTeam].win, bg_fail : !matchData[matchData.myTeam].win, bg_gray : matchData.noGame}" outlined>
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
                                    <td width="22px" v-else>
                                        <img :src="imageload('item/empty.png')" class="item-border empty" width="22px"/>
                                    </td>

                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item1 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item1 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" v-else>
                                        <img :src="imageload('item/empty.png')" class="item-border empty" width="22px"/>
                                    </td>
                                    
                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item2 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item2 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" v-else>
                                        <img :src="imageload('item/empty.png')" class="item-border empty" width="22px"/>
                                    </td>

                                    <td width="22px" rowspan="2" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item6 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item6 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" v-else>
                                        <img :src="imageload('item/empty.png')" class="item-border empty" width="22px"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item3 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item3 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" v-else>
                                        <img :src="imageload('item/empty.png')" class="item-border empty" width="22px"/>
                                    </td>

                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item4 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item4 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" v-else>
                                        <img :src="imageload('item/empty.png')" class="item-border empty" width="22px"/>
                                    </td>
                                    
                                    <td width="22px" v-if="matchData[matchData.myTeam].teammate[matchData.myIndex].item5 != 0">
                                        <img :src="imageload('item/' + matchData[matchData.myTeam].teammate[matchData.myIndex].item5 + '.png')" class="item-border" width="22px"/>
                                    </td>
                                    <td width="22px" v-else>
                                        <img :src="imageload('item/empty.png')" class="item-border empty" width="22px"/>
                                        <!--<div :style="{ 'background-image': 'url('+ imageload('item/empty.png') +')' }" class="empty item-border" width="22px"/>-->
                                    </td>

                                </tr>
                            </table>
                        </td>

                        <td width="168px">
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
                        <td>
                            <v-btn class="infobtn" color="#2B353D" @click="matchData.display = !matchData.display" outlined>∨</v-btn>
                        </td>
                    </tr>
                </table>
                <v-row v-if="matchData.display" style="background-color: #fff;" width="650px">
                    <v-col cols="6">
                        <barchart :team="matchData.blueTeam.teammate"/>
                        <div class="tooltip" :class="['blueplayer'+idx]" v-for="(p, idx) in matchData.blueTeam.teammate" :key="idx + '_blue_player'">
                            <img :src="imageload('champion/' + p.champ + '.png')" class="player-icon" />
                            <span class="tooltiptext">{{p.name}}</span>
                        </div>
                    </v-col>

                    <v-col cols="6">
                        <barchart :team="matchData.redTeam.teammate"/>
                        <div class="tooltip" :class="['redplayer'+idx]" v-for="(p, idx) in matchData.redTeam.teammate" :key="idx + '_red_player'">
                            <img :src="imageload('champion/' + p.champ + '.png')" class="player-icon" />
                            <span class="tooltiptext">{{p.name}}</span>
                        </div>
                    </v-col>
                </v-row>
            </v-card>
        </v-flex>
    </v-container>
</template>

<script>
//import { mapActions } from "vuex"
import { mapState } from "vuex"
import champoins from "@/assets/data/champion.json";
import queues from "@/assets/data/queues.json";
import barchart from "@/components/profile/barchart";

export default {
    name: "ProfileGameHistory",
    components:{
        barchart
    },
    data: function() {
        return {
            champoins,
            queues,
            isLoading : true
        }
    },
	//mounted(){
	// 	this.getMatchDatas();
	// 	console.log(this.matchDatas[0][this.matchDatas[0].myTeam].win);
    // 	//console.log(new Date(1595840710509));

	//},
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
            //if(Math.floor(diff/(1000*3600*24)) > 0){
            //    result = (gametime.getMonth()+1) + "/" + gametime.getDate();
            //}else{
            //    if ( Math.floor(diff%(1000*3600*24)/(1000*3600)) <= 1 ) 
            //        result = Math.floor(diff/(1000*60*24)) + '분 전'
            //    else
            //        result = Math.floor(diff%(1000*3600*24)/(1000*3600)) +"시간 전";
            //}
            //return result;
            if(Math.floor(diff/(1000*60*60*24)) > 0){
                result = (gametime.getMonth()+1) + "/" + gametime.getDate();
            }else{
                if(Math.floor(diff/(1000*60)) < 1 ) 
                    result = '방금전';
                else if(Math.floor(diff/(1000*60)) < 60)
                    result = Math.floor(diff/(1000*60)) + '분 전';
                else
                    result = Math.floor(diff/(1000*60*60)) +"시간 전";
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
        },
        seemore(key){
            this.key = !key;
        },
        //addDisplayNone(){
        //    trigerseemore.push('none');
        //}
    }
};
</script>

<style>
.tooltip {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}

.tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: #272727;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
  
    /* Position the tooltip */
    position: absolute;
    z-index: 1;
    top: -5px;
    left: 105%;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
}

.z-index1{
    z-index: 1;
}

.blueplayer0{
    position: absolute;
    top: 154px;
    left: 12px;
}
.blueplayer1{
    position: absolute;
    top: 184px;
    left: 12px;
}
.blueplayer2{
    position: absolute;
    top: 214px;
    left: 12px;
}
.blueplayer3{
    position: absolute;
    top: 244px;
    left: 12px;
}
.blueplayer4{
    position: absolute;
    top: 274px;
    left: 12px;
}
.redplayer0{
    position: absolute;
    top: 154px;
    left: 348px;
}
.redplayer1{
    position: absolute;
    top: 184px;
    left: 348px;
}
.redplayer2{
    position: absolute;
    top: 214px;
    left: 348px;
}
.redplayer3{
    position: absolute;
    top: 244px;
    left: 348px;
}
.redplayer4{
    position: absolute;
    top: 274px;
    left: 348px;
}
.player-icon {
    position: absolute;
    border-radius: 70%;
    height: 25px;
    border : rgb(200, 170, 110) 1px solid;
}
.empty{
    height: 22px;
    opacity: 0.1;
}
.infobtn{
    height: 100% !important;
    min-width: 10px !important;
    padding : 0 !important;
    float: right;
}
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