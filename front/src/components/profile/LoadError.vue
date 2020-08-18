<template>
    <div class="text-center bg_fail" width="1010px" height="835px">
        <div v-if="error == 404">
            <h1>404 Not Found</h1>
            <h1>잠시 후에 다시 요청해주세요.</h1>
        </div>

        <div v-else-if="error == 403">
            <h1>존재하지 않는 소환자 입니다.</h1>
        </div>

        <div v-else-if="error == 429">
            <h1>요청이 과다합니다. </h1>
            <h1>잠시 후에 다시 요청해주세요.</h1>
        </div>
    </div>
</template>

<script>
export default {
    name: 'LoadError',
    props: ['error'],
}

</script>

<style>

</style>