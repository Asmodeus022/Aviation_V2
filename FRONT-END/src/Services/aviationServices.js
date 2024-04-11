import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/api'

// Fetch
export const listTools = (id) => axios.get(REST_API_BASE_URL + '/tools/barcode/' + id)

export const toolById = (toolId) => axios.get(REST_API_BASE_URL + '/tools/' + toolId)

export const allTools = () => axios.get(REST_API_BASE_URL + '/tools')

export const addTool = async(toolData) => {
    const formData = new FormData();
    formData.append('file', toolData.image);
    formData.append('toolDto', JSON.stringify({
        toolName: toolData.name,
        barcodeId: toolData.barcodeId,
        status: toolData.status
    }));

    console.log(toolData)

    try {
        console.log("adding...")
        const response = await axios.post(`${REST_API_BASE_URL}/tools`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
    } catch (error) {
        
        console.error('Error adding toolId:', error);
        throw error;
        
        
    }
}

export const updateTool = async(toolId, toolData) => {
    const formData = new FormData();
    formData.append('file', toolData.image);
    formData.append('toolDtoObj', JSON.stringify({
        toolName: toolData.name,
        barcodeId: toolData.barcodeId,
        status: toolData.status
    }));

    try {
        console.log("updating...")
        const response = await axios.put(`${REST_API_BASE_URL}/tools/${toolId}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
    } catch (error) {
        
        console.error('Error updating toolId:', error);
        throw error;
        
        
    }
}

export const deleteTool = async(id) => axios.delete(REST_API_BASE_URL + "/tools/" + id)

export const allBarrowed = () => axios.get(REST_API_BASE_URL + '/transaction/all')

export const getAllUsers = () => axios.get(REST_API_BASE_URL + "/users/all")

// Post
export const addTransaction = (data) => axios.post(REST_API_BASE_URL + "/transaction/add-transaction", data)

export const updateTransaction = (data) => axios.put(REST_API_BASE_URL + "/transaction/" + data.id, data)

export const addUser = (userData) => axios.post(REST_API_BASE_URL + '/users', userData)
