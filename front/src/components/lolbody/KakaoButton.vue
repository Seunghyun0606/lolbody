<template>
  <a id="kakao-link-btn" @click="onClickKakaoButton">
    <img class="image"
      :src="require('@/assets/kakao.png')"
    />
  </a>
</template>

<script>
import Kakao from '@/plugins/kakao.js'
Kakao.init('7b7a42d9cf2509fe7d9c7db809ef3807')

export default {
  name: 'KakaoButton',
  props: {
    username: String,
  },
  data() {
    return {
      url: 'https://lolbody.gq/Lolbody/',
      badges: ['#학살자', '#농부', '#오지라퍼', '#대도둑']
    }
  },
  methods: {
    onClickKakaoButton() {
      Kakao.Link.sendDefault({
      objectType: 'feed',
      content: {
        // 여기 두 줄이 카톡에서 보임
        title: this.username + '님의 LoLBody 분석결과',
        description: this.badges.toString().replace(/,/gi, ' '),
        imageUrl: 'https://lolbody.gq/lolbody-logo2.png',
        //   'http://k.kakaocdn.net/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png',
        link: {
          mobileWebUrl: this.url + this.username,
          webUrl: this.url + this.username,
        },
      },
      // buttons: [
      //   {
      //     title: '웹으로 보기',
      //     link: {
      //       mobileWebUrl: this.url + this.username,
      //       webUrl: this.url + this.username,
      //     },
      //   },
      //   {
      //     title: '앱으로 보기',
      //     link: {
      //       mobileWebUrl: this.url + this.username,
      //       webUrl: this.url + this.username,
      //     },
      //   },
      // ],
    })
    }
  }
}
</script>

<style scoped>

.image {
  width: 36px;
  padding-top: 1px;
  /* margin-right: 12px; */
  /* height: 50px; */
}

</style>