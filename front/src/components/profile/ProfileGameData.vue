<template>
<div>
    <div class="mt-2 text-center" v-if="nowProfileDatas.mostCham != null"> 
        <div class="icon pa-1 d-inline-block">
            <span class="rank fs-14">{{nowProfileDatas.rank}}</span>
            <img :src="imageload('tier/'+nowProfileDatas.src+'.png')" class="d-block mx-auto" height= "75px" />
            <v-card-text>
                <span class="fs-14" >{{round(nowProfileDatas.totalRecord.winRate)}}% (<span class="fc_blue fs-13">{{nowProfileDatas.totalRecord.wins}}승</span> <span class="fc_red fs-13">{{nowProfileDatas.totalRecord.losses}}패</span>)</span>
            </v-card-text>
        </div>

        <div class="icon pa-1 d-inline-block">
            <v-avatar class="ma-3" size="70">
                <v-img :src="imageload('champion/'+nowProfileDatas.mostCham+'.png')" alt="모스트 픽" />
            </v-avatar>
            <v-card-text>
                <span class="fs-14" >{{round(nowProfileDatas.mostChamRecord.winRate)}}% (<span class="fc_blue fs-13">{{nowProfileDatas.mostChamRecord.wins}}승</span> <span class="fc_red fs-13">{{nowProfileDatas.mostChamRecord.losses}}패</span>)</span>
            </v-card-text>
        </div>

        <div class="icon pa-1 d-inline-block">
            <v-avatar class="ma-3" size="50">
                <v-img :src="imageload('position/'+nowProfileDatas.mostLine+'.png')" />
            </v-avatar>
            <v-card-text>
                <span class="fs-14" >{{round(nowProfileDatas.mostLineRecord.winRate)}}% (<span class="fc_blue fs-13">{{nowProfileDatas.mostLineRecord.wins}}승</span> <span class="fc_red fs-13">{{nowProfileDatas.mostLineRecord.losses}}패</span>)</span>
            </v-card-text>
        </div>

        <div class="icon pa-1 d-inline-block">
            <v-avatar class="ma-3" size="50">
                <v-img :src="imageload('position/'+nowProfileDatas.secondLine+'.png')" />
            </v-avatar>
            <v-card-text>
                <span class="fs-14" >{{round(nowProfileDatas.secondLineRecord.winRate)}}% (<span class="fc_blue fs-13">{{nowProfileDatas.secondLineRecord.wins}}승</span> <span class="fc_red fs-13">{{nowProfileDatas.secondLineRecord.losses}}패</span>)</span>
            </v-card-text>
        </div>
    </div>
    <div class="mt-2 text-center" v-if="nowProfileDatas.mostCham == null">
        <p>전적이 없습니다.</p>
    </div>
</div>
</template>

<script>
import { mapState } from "vuex"

export default {
    name: "ProfileGameData",
    computed: {
        ...mapState([
            'nowProfileDatas',
        ]),
    },
    mounted(){
        console.log(this.nowProfileDatas.src);
    },
    methods:{
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
.rank{
	font-family: cursive;
	color: rgb(231, 197, 0);
	font-weight: 500;
	text-shadow: -1px 0 #000000, 0 1px #5e5e5e, 1px 0 #5e5e5e, 0 -1px #5e5e5e;
}
.icon {
	width: 130px;
}
</style>