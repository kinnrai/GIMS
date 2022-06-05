import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
    state: {
        editableTabsValue: 'Index',
        editableTabs: [
            {
                title: '首页',
                name: 'Index',
            }
        ]
    },
    getters: {},
    mutations: {
        addTab(state, tab) {
            const index = state.editableTabs.findIndex(item => item.name === tab.name)
            if (index === -1) {
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name,
                });
            }
            state.editableTabsValue = tab.name;
        },
        resetState(state) {
            state.editableTabsValue = 'Index'
            state.editableTabs = [
                {
                    title: '首页',
                    name: 'Index',
                }
            ]
        }
    },
    actions: {},
    modules: {}
}
