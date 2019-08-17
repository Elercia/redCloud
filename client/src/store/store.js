import Vue from 'vue'
import Vuex from 'vuex'

import userRepository from '../repository/userRepository'
import {SET_CONNECTED_USER, SET_ERROR} from "./mutation-type";
import {GET_ERROR, GET_USER} from "./getter-type";

Vue.use(Vuex)

export const store = new Vuex.Store({
    debug: true,
    strict: true,
    state: {
        user: null,
        error: null
    },
    // call by action to change state
    mutations: {
        [SET_CONNECTED_USER](state, user) {
            state.user = user
        },
        [SET_ERROR](state, error) {
            state.error = error
        }
    },
    // get in vue
    getters: {
        [GET_USER]: state => state.user,
        [GET_ERROR]: state => state.error
    },
    // call api
    actions: {
        getConnectedUser: (store) => {
            userRepository.getConnectedUser((response) => {
                store.commit(SET_CONNECTED_USER, response.data)
            }, (error) => {

                let errorObject = {
                    errorMessage: 'Erreur inconnue',
                    errorStatus: -1
                }

                if (!error) {
                    errorObject.errorMessage = error.data.errorMessage
                    errorObject.errorStatus = error.errorStatus
                }
                store.commit(SET_ERROR, errorObject)
            })
        }
    }
})
