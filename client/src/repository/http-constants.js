import axios from 'axios'

let baseURL

if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
    baseURL = 'http://localhost:3000/'
} else {
    baseURL = 'http://api.example.com'
}

export const HTTP = axios.create(
    {
        baseURL: baseURL,
        headers: {
            'Authorization': 'Bearer ',
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
            'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization',
        }
    }
)


export function is4XX(status) {
    return status >= 400 && status < 500
}

export function is5XX(status) {
    return status >= 500 && status < 600
}

