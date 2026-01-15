import Axios from 'axios';
var root = process.env.API_ROOT;
const axios = Axios.create();

// Request interceptor
axios.interceptors.request.use((config) => {
    // Reassemble URL before request
    config.url = root + config.url;
    return config;
});

export default axios;