import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/api'

export const listTools = (id) => axios.get(REST_API_BASE_URL + '/tools/barcode/' + id)

export const toolById = (toolId) => axios.get(REST_API_BASE_URL + '/tools/' + toolId)

export const allTools = () => axios.get(REST_API_BASE_URL + '/tools')

export const allBarrowed = () => axios.get(REST_API_BASE_URL + '/transaction/all')
