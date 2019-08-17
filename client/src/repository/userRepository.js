import {HTTP} from './http-constants'

export default {

    getConnectedUser(response, error) {
        return HTTP.get('api/users/00000000-0000-0000-0000-000000000000/').then(response).catch(error);
    }
}
