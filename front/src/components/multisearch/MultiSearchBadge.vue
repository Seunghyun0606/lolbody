<template>  
    <span class="test-badge">{{ badge[index] }}</span>
</template>

<script>
export default {
  name: "MultiSearchBadge",
  props: {
    index: Number,
  },
  data() {
    return {
      badge: [
        "SSAFY",
        "화이팅",
        "롤바디",
        "최고야",
        "학살자",
      ]
    }
  },
  
}
</script>

<style>


.test-badge {
  margin-right: 5px;
  padding: 2px 5px 2px;
  border: 1px solid #e5f4ff;
  background-color: #e5f4ff;
  color: blue;
  border-radius: 10%;
  width: 20px;
  font-size: 10px;
  font-weight: 700;
}

</style>