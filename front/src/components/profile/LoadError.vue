<template>
    <v-container width="1010px">
        
        <p>Error</p>
        <div v-if="error == 404">
            <p>Error</p>
        </div>

        <div v-if="error == 204">
            <p>존재하지 않는 소환자 입니다.</p>
        </div>

        <div v-if="error == 429">
            <p>요청이 과다합니다. 잠시 후에 다시 요청해주세요.</p>
        </div>
    </v-container>
    
</template>

<script>
export default {
    name: 'LoadError',
    props: ['error'],
}

</script>

<style>

</style>