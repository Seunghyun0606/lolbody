<template>
  <v-container>
    <v-row 
        class="text-center"
        justify="center">
      <v-col cols="8">
        <v-card>
          <v-toolbar
            color="primary"
            dark
            flat
          >
            <v-text-field
              append-icon=""
              class="mx-4"
              flat
              hide-details
              label="Summoner ID"
              prepend-inner-icon="search"
              solo-inverted
            ></v-text-field>
            <v-btn 
            small 
            color="primary lighten-2"
            >Search</v-btn>
          </v-toolbar>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "IndexSearchBar",
  
}
</script>

<style>

</style>