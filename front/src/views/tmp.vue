<template>
    <v-container fluid class="mt-12">
	<v-row align="center" justify="center">
	<v-app id="sandbox">
	<v-main>
	<table width="1010px" height="835px">
		<tr>
			<!-- 여기서부터 좌측 공간 -->
			<td style="vertical-align: top" width="332px">
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

                        <v-btn class="px-1 fs-9 lolbody-btn" color="#30BA8C" outlined>LoL Body</v-btn>
                        <!--<v-col cols="1">
                            <v-avatar size="45" class="profiletier">
                                <v-img :src="imageload('tier/'+profileDatas.tier+'.png')" />
                            </v-avatar>
                            <img :src="imageload('tier/'+nowProfileDatas.src+'.png')" class="mt-4 d-block mx-auto" height= "40px" />
                        </v-col>-->
					</v-row>
				</v-card>
				
				<!-- 랭크, 일반 등 -->
				<v-card class="ma-1 mb-2 bg_card" outlined height="300px" width="332px" algin="center">
					<ul class="options">
						<li><a :class="{option_action: triger.rankGameActive}" @click="changeRankGame">랭크</a></li>
						<li><a :class="{option_action: triger.nomalGameActive}" @click="changeNomarlGame">일반</a></li>
                        <li><a :class="{option_action: triger.howlingAbyssActive}" @click="changeHowlingAbyss">칼바람</a></li>
					</ul>
                    <RadarChart :idx="radaridx"/>
				</v-card>

				<v-card class="ma-1 mb-2 bg_card scroll" outlined height="347px" algin="center">
					<!-- <ProfileBadge v-for="(badge, idx) in badgeMap" :key="idx+'_badge'" :badge="badge"/> -->
					<ProfileBadge />
				</v-card>
			</td>
			<!-- 여기서부터 우측 공간 -->
			<td style="vertical-align: top">
				<!-- <v-card class="text-center ma-1 mb-2 bg_card" outlined>
					<ul class="options">
						<li><a v-bind:class="{option_action: triger.LPActive}" @click="changeLP">KDA</a></li>
						<li><a v-bind:class="{option_action: triger.totalPointActive}" @click="changeTotalPointDate">총점</a></li>
					</ul>
					<div class="px-5">
						<ProfileLineChart/>
					</div>
				</v-card> -->

				<!-- RadarChart -->
				<!-- 수정본, 전체 게임 승률 -->
				<div class="d-inline-block">
					<v-card class="mx-1 mt-1 bg_card float-left" width="200px" height="160px" outlined>
						<!-- <div class="ml-7">
							<RadarChart/>
						</div> -->
						<ProfileTotalWinRateChart/>
					</v-card>
				</div>

				<!-- 롤비티아이 부분 -->
				<!-- 수정본, 각 게임 모드별 승률 -->
				<div class="d-inline-block">
					<v-card class="mx-1 mt-1 bg_card float-left" width="240px" height="160px" outlined>
						<!-- <div class="d-inline">
							<span>유저 성향</span>
						</div> -->
						<ProfileEachWinRateChart/>
					</v-card>
				</div>

				<!-- 수정본, 챔피언 승률 639px-->
				<div class="d-inline-block">
					<v-card class="mx-1 mt-1 bg_card float-right" width="199px" height="160px" outlined>
						<ProfileChampRate/>
					</v-card>
				</div>

                <div class="scroll gamehistory" >
                    <ProfileGameHistory/>
                    <v-btn class="mx-1 mb-2" color="#2B353D" width="649px" height="50px" @click="getMatchDatas(profileDatas.summonerName, ++numOfMatch)" outlined>
                        더보기
                    </v-btn>
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
//import ProfileLineChart from '@/components/profile/ProfileLineChart';
//import ProfileRadarChart from "@/components/profile/ProfileRadarChart"
//import ProfileGameHistory from '@/components/profile/ProfileGameHistory';

//import ProfileBadge from "@/components/profile/ProfileBadge";

import Loading from "@/components/profile/Loading";
import LoadError from "@/components/profile/LoadError";
//import ProfileGameData from "@/components/profile/ProfileGameData";

import ProfileTotalWinRateChart from "@/components/profile/ProfileTotalWinRateChart"
import ProfileEachWinRateChart from "@/components/profile/ProfileEachWinRateChart"
import ProfileChampRate from "@/components/profile/ProfileChampRate"


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

		// ProfileLineChart,
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
                LPActive: true,
                nomalGameActive : false,
                rankGameActive : true,
                howlingAbyssActive : false,
                isLoading : true,
                overprofile : true,
            },
            //now:{},
            radaridx: 0,
            numOfMatch: 1
        }
    },
	mounted(){
		const userName = this.$route.params.userName;
		this.getProfileDatas(userName);
		this.getMatchDatas(userName, 1);
        this.triger.isLoading = false;
    },
	computed: {
		badgeMap(){
			return this.$store.getters.getBadgeMap
		},
        profileDatas(){
            return this.$store.state.profileDatas
        },
        matchDatas(){
            return this.$store.state.matchDatas
		},
		updateTime() {
			let now = new Date();
			let gametime = new Date(this.profileDatas.timestamp);
			let result = "";
			let diff = Math.abs(now.getTime() - gametime.getTime());
			console.log(diff)
			if(Math.floor(diff/(1000*3600*24)) > 0){
					result = (gametime.getMonth()+1) + "/" + gametime.getDate();
			}else{
				let diff1 = Math.floor(diff%(1000*3600*24)/(1000*3600));
				if ( diff1 === 0 ) {
					result = '방금 전';
				}
				else if ( diff1 === 1 ) {
					result = '약 ' + Math.floor(diff/(1000*60*24)) + '분 전'
				}
				else {
					result = '약 ' + diff1 +"시간 전";
				}
			}
			return result;
		},
	},
	methods:{
		async getProfileDatas(userName){
			await this.$store.dispatch('getProfileDatas', userName);
            //this.now = this.profileDatas.rankedRecord;
            if(this.profileDatas == '')
                return;
            this.setSearchHistory() // 이거 추가
			// this.getMatchDatas(1);
            //this.getRadarChartDatas(userName);
			// this.triger.isLoading = false;
		},
		async getMatchDatas(userName, n){
            await this.$store.dispatch('getMatchDatas', {
                'userName': userName, 
                num : n
			});
            //this.$store.commit('setProfileLineChartOption', this.matchDatas);
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

		getRankData(){
			//LineChart에 데이터    전달
		},
		getTotalPointData(){
			//LineChart에 데이터    전달
		},
		changeNomarlGame(){
			this.triger.nomalGameActive = true;
            this.triger.rankGameActive = false;
            this.triger.howlingAbyssActive = false;
			//this.nowProfileDatas = this.profileDatas.blindRecord;
			//this.nowProfileDatas.src = 'unranked';
            //this.nowProfileDatas.rank = 'unranked';
            this.radaridx = 1;
		},
		changeRankGame(){
			this.triger.nomalGameActive = false;
            this.triger.rankGameActive = true;
            this.triger.howlingAbyssActive = false;
			//this.nowProfileDatas = this.profileDatas.rankedRecord;
			//this.nowProfileDatas.src = this.profileDatas.soloRank.tier;
            //this.nowProfileDatas.rank = this.profileDatas.rank;
            this.radaridx = 0;
        },
        changeHowlingAbyss(){
            this.triger.nomalGameActive = false;
            this.triger.rankGameActive = false;
            this.triger.howlingAbyssActive = true;
            this.radaridx = 2;
        },
		changeLP(){
			this.triger.LPActive = true;
			this.triger.totalPointActive = false;
		},
		changeTotalPointDate(){
			this.triger.LPActive = false;
			this.triger.totalPointActive = true;
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
        },
        /////////////////// 여기부터
        setSearchHistory() {
            if (this.profileDatas.summonerName.length > 0) {
                let searchHistory = window.localStorage.getItem('searchHistory')
                // searchHistory가 존재하지 않는 경우
                if (searchHistory === null) {
                    searchHistory = []
                // searchHistory가 존재하고 길이가 0보다 큰 경우, 즉 저장된 값이 존재하는 경우
                } else if (searchHistory.length > 0) {
                    searchHistory = JSON.parse(window.localStorage.getItem('searchHistory'))
                    // 이미 히스토리에 저장된 닉네임인경우 함수 종료
                    if (searchHistory.includes(this.profileDatas.summonerName)) {
                        return
                    }
                    if (searchHistory.length >= 6) {
						// 최대 9개의 히스토리만 가지고 있도록 6개 이상인 경우 하나 빼버림
						while (searchHistory.length >= 6) {
							searchHistory.shift()
						}
                    }
                // searchHistory가 존재하지만 빈 string인 경우
                } else {
                    searchHistory = []
                }
                searchHistory.push(this.profileDatas.summonerName)
                window.localStorage.setItem('searchHistory', JSON.stringify(searchHistory))
            }
        }
        ////////////////// 여기까지
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
    overflow-x: hidden;
    overflow-y: scroll;
    scrollbar-width: none;
    -ms-overflow-style: none;
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
