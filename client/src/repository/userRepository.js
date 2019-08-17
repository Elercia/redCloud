import {HTTP} from 'http-constants'

export default class UserRepository {

    getConnectedUser(response, error) {
        return HTTP.get('users/00000000-0000-0000-0000-000000000000/').then(response).catch(error);
    }
}
