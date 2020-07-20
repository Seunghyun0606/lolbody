import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    headers: [
      {
        text: '소환사 정보',
        align: 'start',
        sortable: false,
        value: 'name',
      },
      { text: '솔로 랭크', value: 'solo' },
      { text: '자유 랭크', value: 'free' },
      { text: '포지션', value: 'line' },
      { text: '최근 전적', value: 'latest_game' },
      { text: '모스트 챔피언', value: 'most_champ' },
    ],
    multiserach_infos: [
      {
        name: '소환사 1',
        solo: 159,
        free: 6.0,
        line: 24,
        latest_game: 4.0,
        most_champ: '1%',
      },
      {
        name: '소환사 2',
        solo: 159,
        free: 6.0,
        line: 24,
        latest_game: 4.0,
        most_champ: '1%',
      },
      {
        name: '소환사 3',
        solo: 159,
        free: 6.0,
        line: 24,
        latest_game: 4.0,
        most_champ: '1%',
      },
      {
        name: '소환사 4',
        solo: 159,
        free: 6.0,
        line: 24,
        latest_game: 4.0,
        most_champ: '1%',
      },
      {
        name: '소환사 5 ',
        solo: 159,
        free: 6.0,
        line: 24,
        latest_game: 4.0,
        most_champ: '1%',
      },
    ],
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
