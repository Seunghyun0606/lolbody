<template>  
    <span class="test-bedge">{{ bedge[index] }}</span>
</template>

<script>
export default {
  name: "MultiSearchBedge",
  props: {
    index: Number,
  },
  data() {
    return {
      bedge: [
        "SSAFY",
        "화이팅",
        "롤바디",
        "최고야",
        "학살자",
      ]
    }
  }
}
</script>

<style>


.test-bedge {
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