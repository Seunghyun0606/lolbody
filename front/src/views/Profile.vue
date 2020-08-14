<template>
    <v-container fluid class="mt-12">
	<v-row align="center" justify="center">
	<v-app id="sandbox">
	<v-main>
	<table width="1000px" v-if="profileDatas != '' && matchDatas != ''">
		<tr>
			<!-- 여기서부터 좌측 공간 -->
			<td style="vertical-align: top" width="34%">
				<v-card class="ma-1 mb-2 bg_card"  outlined height="150px" :loading="triger.isLoading">
					
					<!-- 유저프로필 -->
					<v-row>
						<v-col cols="3">
							<div class="avatar mt-3 ml-3 text-center">
								<v-avatar size="70">
									<v-img :src="imageload('profileicon/'+profileDatas.profileIconId+'.png')" alt="icon" />
								</v-avatar>
								<span class="level fs-10">{{profileDatas.summonerLevel}}</span>
							</div>
						</v-col>
						<v-col cols="8">
							<div class="pt-4 pl-4">
								<v-card-title class="headline nickname" v-text="profileDatas.summonerName"/>
								<v-btn class="mt-2 mr-1 py-3 px-2 fs-14 refresh-btn" color="info" @click="renewalUserData(profileDatas.summonerName)">전적 갱신</v-btn>
								<span class="leastUpdate fs-10">최근 업데이트: {{ updateTime }}</span>
							</div>
						</v-col>
					</v-row>

					<!-- 배지 컴포넌트가 들어가야함. -->
					<v-row>
						<ProfileBedge class="ml-2"/>
					</v-row>
				</v-card>
				
				<!-- 랭크, 일반 등 -->
				<v-card class="ma-1 mb-2 bg_card"  outlined height="300px" algin="center">
					<ul class="options">
						<li><a v-bind:class="{option_action: triger.rankGameActive}" @click="changeRankGame">랭크</a></li>
						<li><a v-bind:class="{option_action: triger.nomalGameActive}" @click="changeNomarlGame">일반</a></li>
					</ul>
                    <ProfileGameData/>
				</v-card>

				<!-- 듀오 전적이나 최근 자주한 챔피언? -->
				<v-card class="ma-1 mb-2 bg_card"  outlined height="300px" algin="center">
					<div>
						<!-- 여기에 뭐 들어갈지 고민해봐야할듯. 너무 빈공간이라 하나 쓰면 좋겠음 -->
					</div>
				</v-card>
			</td>
			<!-- 여기서부터 우측 공간 -->
			<td style="vertical-align: top">
				<v-card class="text-center ma-1 mb-2 bg_card" outlined>
					<ul class="options">
						<li><a v-bind:class="{option_action: triger.LPActive}" @click="changeLP">KDA</a></li>
						<li><a v-bind:class="{option_action: triger.totalPointActive}" @click="changeTotalPointDate">총점</a></li>
					</ul>

					<!-- LineChart -->
					<div class="px-5">

						<ProfileLineChart/>

					</div>
				</v-card>

				<!-- RadarChart -->
				<!-- 수정본, 전체 게임 승률 -->
				<div class="d-inline-block">
					<v-card class="ma-1 bg_card float-left" width="320.5px" height="160px" outlined>
						<!-- <div class="ml-7">
							<RadarChart/>
						</div> -->

						<ProfileWinRate/>


					</v-card>
				</div>

				<!-- 롤비티아이 부분 -->
				<!-- 수정본, 챔피언 승률 -->
				<div class="d-inline-block">
					<v-card class="ma-1 bg_card float-right" width="320.5px" height="160px" outlined>
						<!-- <div class="d-inline">
							<span>유저 성향</span>
						</div> -->


						<ProfileChampRate/>

					</v-card>
				</div>

				<!-- 게임 기록 부분 -->
				<ProfileGameHistory/>
                <v-btn class="mx-1 mb-2" width="649px" height="50px" @click="getMatchDatas(++numOfMatch)" outlined>
                    더보기
                </v-btn>
			</td>
		</tr>
	</table>
    <v-card v-if="triger.isLoading" :loading="triger.isLoading" class="text-center" width="1000px" height="50px">Loading</v-card>
    <p v-if="profileDatas == '' || matchDatas == ''">등록되지 않은 소환사입니다.</p>
	</v-main>
	</v-app>
	</v-row>
</v-container>
</template>

<script>
import ProfileLineChart from '@/components/profile/ProfileLineChart';
//import ProfileRadarChart from "@/components/profile/ProfileRadarChart"
//import ProfileGameHistory from '@/components/profile/ProfileGameHistory';
import ProfileBedge from "@/components/profile/ProfileBedge";
import Loading from "@/components/profile/Loading";
import LoadError from "@/components/profile/LoadError";
//import ProfileGameData from "@/components/profile/ProfileGameData";

import ProfileWinRate from "@/components/profile/ProfileWinRate"
import ProfileChampRate from "@/components/profile/ProfileChampRate"


//import { mapActions } from "vuex"
import { mapState } from "vuex"
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
		ProfileChampRate,
		ProfileWinRate,

		ProfileLineChart,
		// RadarChart:() => ({
    //         component: import("@/components/profile/ProfileRadarChart"),
    //         loading: Loading,
    //         error: LoadError,
    //         delay: 0,
    //         timeout: 3000
    //     }),
		ProfileGameHistory:() => ({
            component: import("@/components/profile/ProfileGameHistory"),
            loading: Loading,
            error: LoadError,
            delay: 0,
            timeout: 3000
        }),
        ProfileBedge,
        ProfileGameData:() => ({
            component: import("@/components/profile/ProfileGameData"),
            loading: Loading,
            error: LoadError,
            delay: 0,
            timeout: 3000
        }),
	},
	data(){
        return {
            triger:{
                totalPointActive: false,
                LPActive: true,
                nomalGameActive : false,
                rankGameActive : true,
                isLoading : true
            },
            //now:{},
            numOfMatch: 1
        }
    },
	mounted(){
		const userName = this.$route.params.userName;
		this.getProfileDatas(userName);
	},
	computed: {
		...mapState([
            'profileDatas',
            'nowProfileDatas',
            'matchDatas'
		]),
		updateTime() {
			let time = this.profileDatas.timestamp
			// console.log(time)
			let calcDate = function(time) {
				let now = new Date();
				let gametime = new Date(time);
				let result = "";
				let diff = now.getTime() - gametime.getTime();
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
				// console.log(result)
				return result;
			}
			// let changeTime = function(time){
			// 	return Math.floor(time/60)+ "분 " + (time - Math.floor(time/60)*60) +"초"
			// }
			// console.log(calcDate(time))

			return calcDate(time)
		},
		//     ...mapGetters([
		//             'profileDatas'
        //     ]),
	},
	methods:{
		async getProfileDatas(userName){
			await this.$store.dispatch('getProfileDatas', userName);
            //this.now = this.profileDatas.rankedRecord;
            if(this.profileDatas == '')
                return;
			if(this.profileDatas.tier == null){
				this.changeNomarlGame();
			}else {
                this.changeRankGame();
			}
			this.getMatchDatas(1);
            this.getRadarChartDatas(userName);
			this.triger.isLoading = false;
		},
		async getMatchDatas(n){
            await this.$store.dispatch('getMatchDatas', {
                userName: this.profileDatas.summonerName, 
                num : n
            });
            console.log(this.matchDatas);
		},
		
		// radar Chart data에 들어갈 데이터 여기서 vuex에 넣어주고 컴포넌트에서 부를 예정
		async getRadarChartDatas(userName) {
			await this.$store.dispatch('getProfileRadarChartDatas', userName);
		},
		// 전적갱신 전적 리스트 미구현상태
		async renewalUserData(userName) {
            await this.$store.dispatch('renewalUserData', userName);
            this.getMatchDatas(1);
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
            this.$store.commit('setNowProfileDatas', this.profileDatas.blindRecord);
			//this.nowProfileDatas = this.profileDatas.blindRecord;
			this.nowProfileDatas.src = 'unranked';
			this.nowProfileDatas.rank = 'unranked';
		},
		changeRankGame(){
			this.triger.nomalGameActive = false;
            this.triger.rankGameActive = true;
            this.$store.commit('setNowProfileDatas', this.profileDatas.rankedRecord);
			//this.nowProfileDatas = this.profileDatas.rankedRecord;
			this.nowProfileDatas.src = this.profileDatas.tier;
			this.nowProfileDatas.rank = this.profileDatas.rank;
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

</style> 
