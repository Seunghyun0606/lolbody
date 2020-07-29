<template>
    <v-container>
        <v-flex v-for="(matchData, idx) in matchDatas" :key="idx + '_matchData'">
            <v-card class="text-center ma-1 mb-2" elevation="0" outlined>
                <table height="100px">
                    <tr>
                        <td width="20%">
                            <v-row justify="center">
                                <div class="pa-1 text-center">
                                    <small class="d-block">일반</small>
                                    <small class="d-block">1일전</small>
                                </div>
                            </v-row>
                            <v-row justify="center">
                                <hr style="border:1px color:silver;" width="30%">
                            </v-row>
                            <v-row justify="center">
                                <small>승리</small>
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
                                    <img :src="require('@/assets/images/perk/' + matchDatas[idx][useridx[idx]].perkStyle + '.png')" width="22px"/>
                                </div>
                            </div>
                            <small class="d-block">{{matchDatas[idx][useridx[idx]].champ}}</small>
                        </td>
                        <td>
                            <p>{{matchDatas[0][useridx[0]].name}}</p>
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

export default {
    name: "ProfileGameHistory",
    data: function() {
        return {
            useridx: []
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
            'getMatchDatas',
        ]),
    }
};
</script>

<style>

</style>