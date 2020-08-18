<template>
  <a id="kakao-link-btn" @click="onClickTwitterButton">
  <img
    src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
  />
</a>
</template>

<script>
export default {
  name: 'TwitterButton',
  data() {
    return {
      username: '',
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

<style>

</style>