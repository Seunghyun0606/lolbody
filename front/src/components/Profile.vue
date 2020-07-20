<template>
        <v-container fluid>
            <v-row algin="center" justify="center">
                <v-col cols="10">
                    <table width="100%">
                        <tr>
                            <td width="30%">
                                <v-card height="150px" algin="center">
                                    <div>
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src= "item.iconSrc"/>
                                        </v-avatar>
                                    </div>
                                    <v-card-title class="headline" v-text="item.nickname"/>
                                </v-card>
                            </td>
                            <td rowspan="2">
                                <v-card>
                                    <v-row>
                                        <v-col cols="1">
                                            <v-btn class="graph_btn">
                                                LP
                                            </v-btn>
                                        </v-col>
                                        <v-col cols="1">
                                            <v-btn class="graph_btn"> 
                                                총점
                                            </v-btn>
                                        </v-col>
                                    </v-row>
                                </v-card>
                                
                                <v-card style="margin-top:10px">
                                    <RadarChart/>
                                </v-card>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <v-card height="150px" algin="center">
                                    <div>
                                        <v-avatar class="ma-3" size="70">
                                            <v-img :src="item.lankSrc" />
                                        </v-avatar>
                                    </div>
                                </v-card>
                            </td>
                            <td>
                            </td>
                        </tr>
                    </table>
                </v-col>
            </v-row>
        </v-container>

</template>

<script>
import RadarChart from '@/components/RadarChart';

export default {
    name:'Profile',
    components:{
        RadarChart
    },
    data: ()=>({
        item:{
            nickname : 'BBluee',
            iconSrc : require('@/assets/images/937.png'), 
            lankSrc: require('@/assets/images/Emblem_Challenger.png'),

        }
    })
}
</script>

<style>
* {
    padding : 0px;
    margin : 0px;
}
div {
    display : inline;
}
h2 {
    display: inline;
}
.graph_btn {
    margin-left: 10px;
}
td{
    vertical-align: top;
}
</style> 
