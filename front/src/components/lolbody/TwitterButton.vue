<template>
  <a id="twitter-link-btn" @click="onClickTwitterButton">
    <img class="image"
      :src="require('@/assets/twitter.png')"
    />
  </a>
</template>

<script>
export default {
  name: 'TwitterButton',
  props: {
    username: String
  },
  data() {
    return {
      url: 'https://lolbody.gq/Lolbody/',
      badges: ['#학살자', '#농부', '#오지라퍼', '#대도둑']
    }
  },
  methods: {
    onClickTwitterButton() {
        window.open("https://twitter.com/intent/tweet"
        +"?via=LoLBody"
        +"&text="+this.username + ' 님의 LoLBody 분석결과 ' // Title in this html document
        +"&url="+this.url + this.username
        +"&hashtags="+this.badges.toString().replace(/#/gi, '')
        // , "_blank"
        // , 'width=600,height=400,resizable=yes,scrollbars=yes'
        )
    }
  },
}
</script>

<style scoped>

.image {
  width: 40px;
}

</style>