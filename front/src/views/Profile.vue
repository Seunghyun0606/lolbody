<template>
    <v-container fluid class="bg_main">
	<v-row align="center" justify="center" >
	<v-app id="sandbox">
	<v-main>
	<LoadError :error="error"  v-if="error != 0" />
	<table width="1012px" height="835px" class="bg_main" v-else>
		<tr>
			<!-- 여기서부터 좌측 공간 -->
			<td style="vertical-align: top" width="333px">
				<v-card class="ma-1 bg_card" :class="[profileDatas.soloRank.tier]" outlined height="160px" :loading="triger.isLoading">
					<img :src="imageload('tier_banner/'+profileDatas.soloRank.tier+'.png')" class="profilebanner" width="135px">
					<!-- 유저프로필 -->
					<v-row class="mt-6">
						<v-col cols="4">
                            <div class="avatar mt-1 ml-3 text-center">
								<v-avatar size="70">
									<v-img :src="triger.overprofile ? imageload('profileicon/'+profileDatas.profileIconId+'.png') : imageload('tier/'+profileDatas.soloRank.tier+'.png')" @mouseenter="triger.overprofile = false" @mouseleave="triger.overprofile = true" alt="icon" />
								</v-avatar>
								<span class="level fs-10">{{triger.overprofile ? profileDatas.summonerLevel : (profileDatas.soloRank.rank ? profileDatas.soloRank.rank : "U/R")}}</span>
							</div>
						</v-col>
						<v-col cols="8">
							<div class="pt-4 pl-4">
								<v-card-title class="headline nickname" v-text="profileDatas.summonerName"/>
								<v-btn class="mt-2 mr-1 py-3 px-2 fs-14 refresh-btn" color="#30BA8C" @click="renewalUserData(profileDatas.summonerName)" outlined>전적 갱신</v-btn>
								<span class="fs-10 d-block">최근 업데이트: {{ updateTime }}</span>
							</div>
						</v-col>

                        <v-btn class="px-1 fs-9 lolbody-btn" color="#30BA8C" outlined @click="goLolbody(profileDatas.summonerName)">LoL Body</v-btn>
                        <!--<v-col cols="1">
                            <v-avatar size="45" class="profiletier">
                                <v-img :src="imageload('tier/'+profileDatas.tier+'.png')" />
                            </v-avatar>
                            <img :src="imageload('tier/'+nowProfileDatas.src+'.png')" class="mt-4 d-block mx-auto" height= "40px" />
                        </v-col>-->
					</v-row>
				</v-card>
				
				<!-- 랭크, 일반 등 -->
				<v-card class="ma-1 mb-2 bg_card" :loading="triger.MatchDataLoading" outlined height="300px" width="333px" algin="center">
					<ul class="options">
						<li><a :class="{option_action: triger.rankGameActive}" @click="changeRankGame">랭크</a></li>
						<li><a :class="{option_action: triger.nomalGameActive}" @click="changeNomarlGame">일반</a></li>
                        <li><a :class="{option_action: triger.howlingAbyssActive}" @click="changeHowlingAbyss">칼바람</a></li>
					</ul>
                    <RadarChart :radarData="radarData[radaridx]"/>
				</v-card>

				<v-card class="ma-1 mb-2 bg_card scroll" :loading="triger.MatchDataLoading" outlined height="568px" algin="center">
					<ProfileBadge v-for="(badge, idx) in badgeSet" :key="idx+'_badge'" :badge="badge" />
					<!-- <ProfileBadge /> -->
				</v-card>
			</td>
			<!-- 여기서부터 우측 공간 -->
			<td style="vertical-align: top">
				<v-card class="text-center ma-1 mb-2 bg_card" width="655px" outlined>
					<ul class="options">
						<li><a v-bind:class="{option_action: triger.KDAActive}" @click="changeKDA">KDA</a></li>
						<li><a v-bind:class="{option_action: triger.totalPointActive}" @click="changeTotalPointDate">총점</a></li>
					</ul>
					<div class="px-5">
						<ProfileLineChart :series="LineSeries" :category="LineCategory" :yaxis="LineYaxis"/>
					</div>
				</v-card>

				<!-- RadarChart -->
				<!-- 수정본, 전체 게임 승률 -->
				<div class="d-inline-block">
					<v-card class="mx-1 mt-1 bg_card float-left" :loading="triger.MatchDataLoading" width="200px" height="160px" outlined>
						<!-- <div class="ml-7">
							<RadarChart/>
						</div> -->
						<ProfileTotalWinRateChart/>
					</v-card>
				</div>

				<!-- 롤비티아이 부분 -->
				<!-- 수정본, 각 게임 모드별 승률 -->
				<div class="d-inline-block">
					<v-card class="mx-1 mt-1 bg_card float-left" :loading="triger.MatchDataLoading" width="240px" height="160px" outlined>
						<!-- <div class="d-inline">
							<span>유저 성향</span>
						</div> -->
						<ProfileEachWinRateChart/>
					</v-card>
				</div>

				<!-- 수정본, 챔피언 승률 639px-->
				<div class="d-inline-block">
					<v-card class="mx-1 mt-1 bg_card float-right" :loading="triger.MatchDataLoading" width="199px" height="160px" outlined>
						<ProfileChampRate/>
					</v-card>
				</div>

                <div class="scroll gamehistory" >
					<!-- <MultiLoading :loading="triger.MatchDataLoading" :color="'grey'" :size="'50px'"></MultiLoading> -->
                    <ProfileGameHistory/>
                    <v-btn class="mx-1 mb-2" :loading="triger.MatchDataLoading || triger.moreLoading" color="#2B353D" width="653px" height="50px" @click="getMatchDatas(profileDatas.summonerName, ++numOfMatch)" v-if="loadAllMatchDatas" outlined>
                        더보기
                    </v-btn>
                    <div class="mx-1 mb-2 py-3 black_border text-center" widht="651px"  v-else><p>이번 시즌의 모든 전적을 불러왔습니다.</p></div>
                </div>
			</td>
		</tr>
	</table>
    
	</v-main>
	</v-app>
	</v-row>
</v-container>
</template>

<script>
import ProfileLineChart from '@/components/profile/ProfileLineChart';
//import ProfileRadarChart from "@/components/profile/ProfileRadarChart"
//import ProfileGameHistory from '@/components/profile/ProfileGameHistory';

//import ProfileBadge from "@/components/profile/ProfileBadge";

import Loading from "@/components/profile/Loading";
import LoadError from "@/components/profile/LoadError";
//import ProfileGameData from "@/components/profile/ProfileGameData";

import ProfileTotalWinRateChart from "@/components/profile/ProfileTotalWinRateChart"
import ProfileEachWinRateChart from "@/components/profile/ProfileEachWinRateChart"
import ProfileChampRate from "@/components/profile/ProfileChampRate"

// import MultiLoading from '@/components/multisearch/MultiLoading.vue'
//import { mapActions } from "vuex"
//import { mapState } from "vuex"
//import { mapGetters } from    "vuex"

export default {
	name:'Profile',
	components:{
		// ProfileLineChart: () => ({
		// 	component: new Promise(resolve => setTimeout(() => resolve(import(/* webpackChunkName: 'logo' */'@/components/profile/ProfileLineChart.vue')), 500)),
		// }),
		// ProfileRadarChart: () => ({
		// 	component: new Promise(resolve => setTimeout(() => resolve(import(/* webpackChunkName: 'logo' */'@/components/profile/ProfileRadarChart.vue')), 2500)),
		// }),
		ProfileTotalWinRateChart,
		ProfileEachWinRateChart,
		ProfileChampRate,
		// MultiLoading,

		ProfileLineChart,
		LoadError,
        RadarChart:() => ({
            component: import("@/components/profile/ProfileRadarChart"),
            loading: Loading,
            error: LoadError,
            delay: 0,
            timeout: 3000
        }),
		ProfileGameHistory:() => ({
            component: import("@/components/profile/ProfileGameHistory"),
            loading: Loading,
            error: LoadError,
            delay: 0,
            timeout: 3000
        }),
        ProfileBadge:() => ({
			component: import("@/components/profile/ProfileBadge"),
            loading: Loading,
            error: LoadError,
            delay: 0,
            timeout: 3000
		}),
        //ProfileGameData:() => ({
        //    component: import("@/components/profile/ProfileGameData"),
        //    loading: Loading,
        //    error: LoadError,
        //    delay: 0,
        //    timeout: 3000
        //}),
	},
	data(){
        return {
            triger:{
                totalPointActive: false,
                KDAActive: true,
                nomalGameActive : false,
                rankGameActive : true,
                howlingAbyssActive : false,
				overprofile : true,
				
				isLoading : true,
				MatchDataLoading : true,
				moreLoading : true
            },
            //now:{},
            radaridx: 0,
            numOfMatch: 1,
            LineYaxis: {},
            LineSeries: [],
            LineCategory: {}
        }
    },
	mounted(){
		const userName = this.$route.params.userName;
		this.getProfileDatas(userName);
        this.getMatchDatas(userName, 1);
        this.changeKDA();
	},
	computed: {
        badgeSet(){
            return this.$store.getters.getBadgeSet
        },
        radarData(){
            let tmp = this.$store.getters.getProfileRadarChart;
            let r = [];
            for(let idx in this.$store.getters.getProfileRadarChart){
                let result = [0, 0, 0];
                if(!isNaN(tmp[idx][0]))
                    result[0] = tmp[idx][0];
                if(!isNaN(tmp[idx][1]))
                    result[1] = tmp[idx][1];
                if(!isNaN(tmp[idx][2]))
                    result[2] = tmp[idx][2];
                r.push(result);
            }
            return r;
        },
        linechartdata(){
            return this.$store.state.linechartdata
        },
        loadAllMatchDatas(){
            return this.$store.state.loadAllMatchDatas
        },
		error(){
			return this.$store.state.error
		},
		badgeMap(){
			return this.$store.getters.getBadgeMap
		},
        profileDatas(){
            return this.$store.getters.getProfileDatas
        },
        matchDatas(){
            return this.$store.state.matchDatas
		},
		updateTime() {
			let time = this.profileDatas.timestamp
			let calcDate = function(time) {
				let now = new Date();
				let gametime = new Date(time);
				let result = "";
				let diff = now.getTime() - gametime.getTime();
				if(Math.floor(diff/(1000*60*60*24)) > 0){
                    result = (gametime.getMonth()+1) + "/" + gametime.getDate();
                }
                else{
                    if(Math.floor(diff/(1000*60)) < 1 ) 
                        result = '방금전';
                    else if(Math.floor(diff/(1000*60)) < 60)
                        result = Math.floor(diff/(1000*60)) + '분 전';
                    else
                        result = Math.floor(diff/(1000*60*60)) +"시간 전";
                }
                return result;
            }
			return calcDate(time)
		},
	},
	methods:{
		// 롤바디 페이지 넘어가는 로직
		goLolbody(userName) {
			this.$router.push('/Lolbody/' + userName)
		},

		async getProfileDatas(userName){
			await this.$store.dispatch('getProfileDatas', userName);
            
			this.triger.isLoading = false;
		},
		async getMatchDatas(userName, n){
			this.triger.moreLoading = true;
            await this.$store.dispatch('getMatchDatas', {
                'userName': userName, 
                num : n
            });
            
			this.triger.moreLoading = false;
            this.triger.MatchDataLoading = false;

            let toggle = [true, true, true];
            let rad = this.radarData;
            for(let idx in this.radarData){
                if((isNaN(rad[idx][0]) || rad[idx][0] == 0)&& (isNaN(rad[idx][1]) || rad[idx][0] == 0)&& (isNaN(rad[idx][2]) || rad[idx][0] == 0)){
                    toggle[idx] = false;
                }
            }
            if(toggle[0])
                this.changeRankGame()
            else if(toggle[1])
                this.changeNomarlGame()
            else if(toggle[2])
                this.changeHowlingAbyss()
            //this.$store.commit('setProfileLinechartdata', this.matchDatas);
		},
		
		// radar Chart data에 들어갈 데이터 여기서 vuex에 넣어주고 컴포넌트에서 부를 예정
		async getRadarChartDatas(userName) {
			await this.$store.dispatch('getProfileRadarChartDatas', userName);
		},
		// 전적갱신 전적 리스트 미구현상태
		async renewalUserData(userName) {
            await this.$store.dispatch('renewalUserData', userName);
            this.getMatchDatas(userName, 1);
		},
		changeRankGame(){
            let rad = this.radarData;
            if((isNaN(rad[0][0]) || rad[0][0] == 0)&& (isNaN(rad[0][1]) || rad[0][0] == 0)&& (isNaN(rad[0][2]) || rad[0][0] == 0)){
                alert("최근 " + this.matchDatas.length + "개의 게임 중 랭크 게임 전적이 없습니다.")
            }else{
                this.triger.nomalGameActive = false;
                this.triger.rankGameActive = true;
                this.triger.howlingAbyssActive = false;
                this.radaridx = 0;
            }
        },
		changeNomarlGame(){
            let rad = this.radarData;
            if((isNaN(rad[1][0]) || rad[1][0] == 0)&& (isNaN(rad[1][1]) || rad[1][0] == 0)&& (isNaN(rad[1][2]) || rad[1][0] == 0)){
                alert("최근 " + this.matchDatas.length + "개의 게임 중 일반 게임 전적이 없습니다.")
            }else{
                this.triger.nomalGameActive = true;
                this.triger.rankGameActive = false;
                this.triger.howlingAbyssActive = false;
                this.radaridx = 1;
            }
		},
        changeHowlingAbyss(){
            let rad = this.radarData;
            if((isNaN(rad[2][0]) || rad[2][0] == 0)&& (isNaN(rad[2][1]) || rad[2][0] == 0)&& (isNaN(rad[2][2]) || rad[2][0] == 0)){
                alert("최근 " + this.matchDatas.length + "개의 게임 중 칼바람 나락 전적이 없습니다.")
            }else{
                this.triger.nomalGameActive = false;
                this.triger.rankGameActive = false;
                this.triger.howlingAbyssActive = true;
                this.radaridx = 2;
            }
        },
		changeKDA(){
			this.triger.KDAActive = true;
            this.triger.totalPointActive = false;
            this.LineYaxis = this.linechartdata[0].yaxis;
            this.LineSeries = [{
                name: this.linechartdata[0].series.name,
                data: this.linechartdata[0].series.data
            }];
            this.LineCategory = this.linechartdata[0].category;
		},
		changeTotalPointDate(){
			this.triger.KDAActive = false;
            this.triger.totalPointActive = true;
            this.LineYaxis = this.linechartdata[1].yaxis;
            this.LineSeries = [{
                name: this.linechartdata[1].series.name,
                data: this.linechartdata[1].series.data
            }];
            this.LineCategory = this.linechartdata[1].category;
        },
        imageload(URL){
            try{
                return require('@/assets/images/'+ URL);
            }catch{
                return require('@/assets/images/error.png');
            }
        },
        round(num){
            try{
                return Math.round(num*100)/100;
            }catch{
                return 0;
            }
        }
	},
	created() {
        this.$store.commit('toggleNavSearch', false)
    }
}
</script>

<style scoped>
* {
	padding : 0px !important;
	margin : 0px !important;
}

.gamehistory{
	height: 660px;
}
.scroll {
    overflow-x: hidden  !important;
    overflow-y: scroll  !important;
    scrollbar-width: none  !important;
    -ms-overflow-style: none  !important;
}

/* width */
.scroll::-webkit-scrollbar {
  width: 4px;
}
/* Track */
.scroll::-webkit-scrollbar-track {
  background: #f1f1f1; 
}
/* Handle */
.scroll::-webkit-scrollbar-thumb {
  background: #888; 
}
/* Handle on hover */
.scroll::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.v-avatar {
	border : rgb(200, 170, 110) 1px solid;
}
.avatar{
	height: 84px;
}
.profilebanner{
    position: absolute;
    left: -6px;
    top: 15px;
}
.level {
    display: inline-block;
    width: 30px;
	border : gold 1px solid;
	border-radius: 50px;
	padding: 0 !important;
	color:gold;
	font-weight: bold;
	position: relative !important;
	top: -15px !important;
	background-color: rgb(26, 25, 25);
}
.lolbody-btn {
    position: absolute;
    top: 136px;
	left: 264px;
    height: 20px !important;
}
.refresh-btn {
    height: 30px !important;
}
.leastUpdate{
	position: absolute;
	transform: translate(0, 16px);
}
.v-chip{
	padding: 2px 10px 0 10px !important;
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
    width: 110px;
}
.options li a {
	height: 40px;
	display: block;
	color: rgba(255, 255, 255, 0.6);
	text-align: center;
	padding: 9px 0 0 0 !important;
	text-decoration: none;
	background-color: #272727;
	box-shadow: 2px 2px 2px rgb(161, 161, 161) inset;
}
.options li a:hover:not(.option_action) {
	background-color: rgb(160, 160, 161);
}
.option_action {
	background-color: #33A39E !important;
	box-shadow: 2px 2px 2px rgb(161, 161, 161) !important;
}

.black_border{
    border-radius: 3px;
    border :#2B353D solid 1px;
}
.IRON {
    border :rgb(42, 39, 40) solid 1px;
}
.BRONZE {
    border :rgb(205, 175, 140) solid 1px;
}
.SILVER {
    border :rgb(172, 190, 196) solid 1px;
}
.GOLD {
    border :rgb(237, 197, 106) solid 1px;
}
.PLATINUM {
    border :rgb(150, 201, 200) solid 1px;
}
.DIAMOND {
    border :rgb(224, 138, 249) solid 1px;
}
.MASTER {
    border :rgb(84, 41, 112) solid 1px;
}
.GRANDMASTER {
    border :rgb(255, 49, 49) solid 1px;
}
.CHALLENGER {
    border :rgb(24, 136, 255) solid 1px;
}
</style> 
