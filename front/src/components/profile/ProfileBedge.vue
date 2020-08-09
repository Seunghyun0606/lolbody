<template>
  <span :tooltip="tooltip_content" class="basic-bedge">
    뱃지
  </span>
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

.basic-bedge {
  border-radius: 8px;
  padding: 4px;
  color: white;
  background-color: rgb(112, 213, 253);
  width: 60px;
  text-align: center;
}

.basic-bedge:hover {
  background-color: rgb(155, 221, 247);
}

</style>