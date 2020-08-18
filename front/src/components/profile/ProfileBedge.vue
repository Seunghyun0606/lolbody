<template>
    <div class="ma-1 pa-1 bedge d-inline-block" :tooltip="tooltip_content">
        <span class="sharp"># </span>
        <span class="bedge-content">뱃지</span>
    </div>
</template>

<script>
export default {
  name: 'ProfileBedge',
  data() {
    return {
      tooltip_content: '설명 설명 설명aaaaaaaaaaaaaaaaaaaaaaaaaaaa'
    }
  }

}

</script>

<style>
.sharp{
    color: #8C8C8C;
    text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
    -moz-text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
    -webkit-text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
}
.bedge-content{
    color: black;
}

.bedge {
    border-radius: 4px;
    background-color: rgb(112, 213, 253);
    text-align: center;
}

.basic-bedge:hover {
  background-color: rgb(155, 221, 247);
}

</style>