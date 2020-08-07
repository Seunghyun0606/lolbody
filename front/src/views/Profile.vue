<template>
    <v-container fluid class="mt-12">
	<v-row align="center" justify="center">
	<v-app id="sandbox">
	<v-main>
	<table width="1000px">
		<tr>
			<!-- 여기서부터 좌측 공간 -->
			<td style="vertical-align: top" width="34%">
				<v-card class="ma-1 mb-2 bg_card"  outlined height="150px" :loading="triger.isLoading">
					
					<!-- 유저프로필 -->
					<v-row>
						<v-col cols="3">
							<div class="avatar mt-3 ml-3 text-center">
								<v-avatar size="70">
									<v-img :src="require('@/assets/images/profileicon/'+profileDatas.profileIconId+'.png')" @error="errorImage" v-if="profileDatas.profileIconId != 'null'"/>
								</v-avatar>
								<span class="level fs-10">{{profileDatas.summonerLevel}}</span>
							</div>
						</v-col>
						<v-col cols="8">
							<div class="pt-4 pl-4">
								<v-card-title class="headline nickname" v-text="profileDatas.summonerName"/>
								<v-btn class="mt-2 mr-1 py-3 px-2 fs-14 refresh-btn" color="info" @click="renewalRecord">전적 갱신</v-btn>
								<span class="leastUpdate fs-10">최근 업데이트: 방금 전</span>
							</div>
						</v-col>
					</v-row>

					<!-- 배지 컴포넌트가 들어가야함. -->
					<v-row>
						<ProfileBedge class="ml-2"/>
						<ProfileBedge class="ml-2"/>
						<ProfileBedge class="ml-2"/>
						<ProfileBedge class="ml-2"/>
						<ProfileBedge class="ml-2 mt-1"/>
						<ProfileBedge class="ml-2 mt-1"/>
						<ProfileBedge class="ml-2 mt-1"/>
						<ProfileBedge class="ml-2 mt-1"/>
					</v-row>
				</v-card>
				
				<!-- 랭크, 일반 등 -->
				<v-card class="ma-1 mb-2 bg_card"  outlined height="300px" algin="center">
					<ul class="options">
						<li><a v-bind:class="{option_action: triger.rankGameActive}" @click="changeRankGame">랭크</a></li>
						<li><a v-bind:class="{option_action: triger.nomalGameActive}" @click="changeNomarlGame">일반</a></li>
					</ul>
				
					<div class="mt-2 text-center" v-if="now.src != null"> 
						<div class="icon pa-1 d-inline-block">
							<span class="rank fs-14">{{now.rank}}</span>
							<img :src="require('@/assets/images/tier/'+now.src+'.png')" @error="errorImage" class="d-block mx-auto" height= "75px" v-if="now.src != 'null'"/>
							<v-card-text>
								<span class="fs-14">{{Math.round(now.totalRecord.winRate*100)/100}}% (<span class="fc_blue fs-13">{{now.totalRecord.wins}}승</span> <span class="fc_red fs-13">{{now.totalRecord.losses}}패</span>)</span>
							</v-card-text>
						</div>
				
						<div class="icon pa-1 d-inline-block">
							<v-avatar class="ma-3" size="70">
								<v-img :src="require('@/assets/images/champion/'+now.mostCham+'.png')" @error="errorImage" alt="모스트 픽" v-if="now.mostCham != 'null'"/>
							</v-avatar>
							<v-card-text>
								<span class="fs-14">{{Math.round(now.mostChamRecord.winRate*100)/100}}% (<span class="fc_blue fs-13">{{now.mostChamRecord.wins}}승</span> <span class="fc_red fs-13">{{now.mostChamRecord.losses}}패</span>)</span>
							</v-card-text>
						</div>
				
						<div class="icon pa-1 d-inline-block">
							<v-avatar class="ma-3" size="50">
								<v-img :src="require('@/assets/images/position/'+now.mostLine+'.png')" @error="errorImage" v-if="now.mostLine != 'null'"/>
							</v-avatar>
							<v-card-text>
								<span class="fs-14">{{Math.round(now.mostLineRecord.winRate*100)/100}}% (<span class="fc_blue fs-13">{{now.mostLineRecord.wins}}승</span> <span class="fc_red fs-13">{{now.mostLineRecord.losses}}패</span>)</span>
							</v-card-text>
						</div>
				
						<div class="icon pa-1 d-inline-block">
							<v-avatar class="ma-3" size="50">
								<v-img :src="require('@/assets/images/position/'+now.secondLine+'.png')" @error="errorImage" v-if="now.secondLine != 'null'"/>
							</v-avatar>
							<v-card-text>
								<span class="fs-14">{{Math.round(now.secondLineRecord.winRate*100)/100}}% (<span class="fc_blue fs-13">{{now.secondLineRecord.wins}}승</span> <span class="fc_red fs-13">{{now.secondLineRecord.losses}}패</span>)</span>
							</v-card-text>
						</div>
					</div>
				
					<div class="mt-2 text-center" v-if="now.src == null">
						<p>전적이 없습니다.</p>
					</div>
				</v-card>

				<!-- 듀오 전적이나 최근 자주한 챔피언? -->
				<v-card class="ma-1 mb-2 bg_card"  outlined height="300px" algin="center">
					<div>
						여기에 뭐 들어갈지 고민해봐야할듯. 너무 빈공간이라 하나 쓰면 좋겠음
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
				<div class="d-inline-block">
					<v-card class="ma-1 mb-2 bg_card float-left" width="260.5px" height="160px" outlined>
						<div class="ml-7">

							<ProfileRadarChart/>

						</div>
					</v-card>
				</div>


				<!-- 롤비티아이 부분 -->
				<div class="d-inline-block">
					<v-card class="ma-1 mb-2 bg_card float-right" width="380.5px" height="160px" outlined>
						<div class="d-inline">
							<span>유저 성향</span>
						</div>
					</v-card>
				</div>


				<!-- 게임 기록 부분 -->
				<ProfileGameHistory/>
			</td>
		</tr>
	</table>
	</v-main>
	</v-app>
	</v-row>
</v-container>
</template>

<script>
//import axios from 'axios';
//import RadarChart from '@/components/profile/RadarChart';
// import ProfileLineChart from '@/components/profile/ProfileLineChart';
// import ProfileRadarChart from "@/components/profile/ProfileRadarChart"
// import ProfileGameHistory from '@/components/profile/ProfileGameHistory';
import ProfileBedge from "@/components/profile/ProfileBedge"


//import { mapActions } from "vuex"
import { mapState } from "vuex"
//import { mapGetters } from    "vuex"

export default {
	name:'Profile',
	components:{
		//RadarChart,
    // Logo: () => ({
    //   component: new Promise(resolve => setTimeout(() => resolve(import(/* webpackChunkName: 'logo' */'../components/Logo.vue')), 5000)),
    //   loading: Loading
    // })
		ProfileLineChart: () => ({
			component: new Promise(resolve => setTimeout(() => resolve(import(/* webpackChunkName: 'logo' */'@/components/profile/ProfileLineChart.vue')), 500)),
		}),
		ProfileRadarChart: () => ({
			component: new Promise(resolve => setTimeout(() => resolve(import(/* webpackChunkName: 'logo' */'@/components/profile/ProfileRadarChart.vue')), 2500)),
		}),
		ProfileGameHistory: () => ({
			component: new Promise(resolve => setTimeout(() => resolve(import(/* webpackChunkName: 'logo' */'@/components/profile/ProfileGameHistory.vue')), 2500)),
		}),
		ProfileBedge,
	},
	data: ()=>({
		triger:{
			totalPointActive: false,
			LPActive: true,
			nomalGameActive : false,
			rankGameActive : true,
			isLoading : true
		},
		now:{},
		item:{}
	}),
	beforeRouteUpdate (to, from, next) {
		const userName = this.$route.params.userName;
		this.getProfileDatas(userName);
		next()
	},
	created(){
		const userName = this.$route.params.userName;
		//const userName =  window.location.href.split("/");
		//console.log(userName);
		this.getProfileDatas(userName);
	},
	computed: {
		...mapState([
			'profileDatas',
		]),
		//     ...mapGetters([
		//             'profileDatas'
		//     ]),
	},
	methods:{
		// ...mapActions([
		//         'getProfileDatas',
		// ]),
		loadDatas(){
			const userName = this.$route.params.userName;
			this.getProfileDatas(userName);
		},
		async getProfileDatas(userName){
			await this.$store.dispatch('getProfileDatas', userName);
			this.now = this.profileDatas.rankedRecord;
			if(this.profileDatas.tier === null){
				this.profileDatas.tier= 'unranked';
				this.profileDatas.rank = 'unranked';
				this.changeNomarlGame();
			}else {
				this.now.src = this.profileDatas.tier;
				this.now.rank = this.profileDatas.rank;
			}
			this.getMatchDatas();

			this.getRadarChartDatas(userName);

			this.triger.isLoading = false;
		},
		async getMatchDatas(){
			await this.$store.dispatch('getMatchDatas', {
				userName: this.profileDatas.summonerName, 
				num : 1
			});
			
			this.isLoading = false;
			//console.log("loading done");
		},

		// 승현, radar Chart data에 들어갈 데이터 여기서 vuex에 넣어주고 컴포넌트에서 부를 예정
		getRadarChartDatas(userName) {
			this.$store.dispatch('getRadarChartDatas', userName)

			this.isLoading = false;
		},
		// 새로고침시 리다이렉트하면서 전적갱신.
		renewalRecord() {
			this.$router.go()
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
			this.now = this.profileDatas.blindRecord;
			this.now.src = 'unranked';
			this.now.rank = 'unranked';
		},
		changeRankGame(){
			this.triger.nomalGameActive = false;
			this.triger.rankGameActive = true;
			this.now = this.profileDatas.rankedRecord;
			this.now.src = this.profileDatas.tier;
			this.now.rank = this.profileDatas.rank;
		},
		changeLP(){
			this.triger.LPActive = true;
			this.triger.totalPointActive = false;
		},
		changeTotalPointDate(){
			this.triger.LPActive = false;
			this.triger.totalPointActive = true;
		},
		errorImage(event){
			event.target.src = require('@/assets/images/error.png');
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
.rank{
	font-family: cursive;
	color: rgb(231, 197, 0);
	font-weight: 500;
	text-shadow: -1px 0 #000000, 0 1px #5e5e5e, 1px 0 #5e5e5e, 0 -1px #5e5e5e;
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

.icon {
	width: 130px;
}


</style> 
