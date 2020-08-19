<template>
  <a id="facebook-link-btn" @click="onClickFacebookButton">
    <img class="image"
      :src="require('@/assets/facebook.png')"
    />
  </a>
</template>

<script>
export default {
  name: 'FacebookButton',
  data() {
    return {
      username: '',
      url: 'https://lolbody.gq/Lolbody/',
      badges: ['#학살자', '#농부', '#오지라퍼', '#대도둑']
    }
  },
  methods: {
    onClickFacebookButton() {
      // 기능이 한정적임, 링크 공유하고 글 쓸땐 직접써야함
        window.open("https://www.facebook.com/sharer/sharer.php"
        +"?u=" + this.url + this.username
        )
    }
  },
}
</script>

<style scoped>

.image {
  width: 52px;
  /* height: 50px; */
}

</style>