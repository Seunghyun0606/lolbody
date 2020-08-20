<template>
<v-col cols=6 >
  <v-row class="ml-0">
    <div class="typewriter intro-text">
      <span>나의 롤 특성을 알려면? LOLBODY!</span>
    </div>

  </v-row>
  <v-row class="ml-0">
    <v-carousel
      cycle
      height="165"
      hide-delimiter-background
      hide-delimiters
      show-arrows-on-hover
      :show-arrows="false"
      interval="1500"
    >
      <v-carousel-item
        v-for="(slide, i) in slides"
        :key="i"
      >
        <!-- <v-sheet
          :color="colors[i]"
          height="100%"
        > -->
          <!-- <v-row
            class="fill-height"
            align="center"
            justify="center"
          > -->
            <img style="width: 100%;" :src="slide">

            <!-- <div class="display-3">{{ slide }}</div> -->
          <!-- </v-row> -->
        <!-- </v-sheet> -->
      </v-carousel-item>
    </v-carousel>

  </v-row>
</v-col>
</template>

<script>
export default {
  name: "IndexIntroText",
  data() {
    return {
      colors: [
          'indigo',
          'warning',
          'pink darken-2',
        ],
        slides: [
          // require(`@/assets/index/1.png`),
          // require(`@/assets/index/2.png`),
          // require(`@/assets/index/3.png`),
          // require(`@/assets/index/4.png`),
        ],

    }
  },
  methods: {
    pushData() {
      this.slides.push(require(`@/assets/index/1.png`))
      this.slides.push(require(`@/assets/index/2.png`))
      this.slides.push(require(`@/assets/index/3.png`))
      this.slides.push(require(`@/assets/index/4.png`))
    }

  },
  mounted() {
    this.pushData()

  }

}
</script>

<style scoped>

.intro-text {
  background: #333;
  padding: 10px;
  display: flex;
  justify-content: center;
  width: 100%;
}

.typewriter span {
  color: #fff;
  font-weight: 600;
  font-family: monospace;
  overflow: hidden; /* Ensures the content is not revealed until the animation */
  border-right: .15em solid orange; /* The typwriter cursor */
  white-space: nowrap; /* Keeps the content on a single line */
  margin: 0 auto; /* Gives that scrolling effect as the typing happens */
  letter-spacing: .15em; /* Adjust as needed */
  animation: 
    typing 3.5s steps(30, end),
    blink-caret .5s step-end infinite;
}

/* The typing effect */
@keyframes typing {
  from { width: 0 }
  to { width: 90% }
}

/* The typewriter cursor effect */
@keyframes blink-caret {
  from, to { border-color: transparent }
  50% { border-color: orange }
}

</style>