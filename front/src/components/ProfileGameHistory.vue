<template>
  <v-card
    color="#385F73"
    dark
    width="800px"
    >
    <v-container class="">
      <v-row 
        no-gutters
        align="center"
        >
        <v-col
          class="py-0"
          cols="1"
          justify="center"
        >
          <v-row
          justify="center">
            <v-card
              class="pa-2"
              outlined
              tile
              color="#385F73"
            >
            일반<br/>
            <small>1일전</small>
            </v-card>
          </v-row>
          <v-progress-linear
            color="red darken-2"
            rounded
            value="100"
            height="1px"
          ></v-progress-linear>
          <v-row
            justify="center">
            <v-card
              class="pa-2"
              outlined
              tile
              color="#385F73"
            >
              승리
            </v-card>
          </v-row>
        </v-col>
        <v-col
          class=""
          cols="1"
          justify="center"
          align="center"
          >
          <img
          :src="require('@/assets/Ashe.png')"
          class=""
          height=""
        />
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
export default {
  name: "ProfileGameHistory",

}
</script>

<style>

</style>