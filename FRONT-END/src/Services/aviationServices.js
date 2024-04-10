import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/api'

export const listTools = (id) => axios.get(REST_API_BASE_URL + '/tools/' +id)