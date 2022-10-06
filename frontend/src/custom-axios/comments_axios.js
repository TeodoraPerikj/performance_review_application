import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:10110/api",
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
})

export default instance;