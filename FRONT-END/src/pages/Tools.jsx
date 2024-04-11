import React, { useState, useEffect } from 'react'
import Header from '../components/Header'
import { allTools } from '../Services/aviationServices'
import Tool_Form from '../components/forms/ToolForm'

const Tools = () => {
    const [tools, setTools] = useState([])
    const [selectedToolId, setSelectedToolId] = useState(null)
    const [refresh, setRefresh] = useState(false);
    const [error, setError] = useState(null)

    useEffect(() => {
        getAllTools()
    }, [refresh])

    async function getAllTools() {
        try {
            const response = await allTools()
            setTools(response.data)
            setError(null)
            console.log(response.data)
        } catch (error) {
            console.error(error)
            setError('Failed to fetch products. Please try again.')
        }
    }

    function handleAddTool() {
        setSelectedToolId(null);
    }
    

    function handleUpdateTool(toolId) {
        setSelectedToolId(toolId)
    }

    function handleClose(error) {
        console.log(error)
        if (error) {
            console.error("Error occurred:", error);
            setError(error);
        } else {
            setRefresh(prevRefresh => !prevRefresh);
        }
    }

    return (
        <div>
            <Header title="Tools" />
            <div className="content">
                <Tool_Form toolId={selectedToolId} onClose={(error) => handleClose(error)} />

                <div className='d-flex justify-content-end mx-3 mb-3'>
                    <button className='btn btn-primary' onClick={() => handleAddTool()} data-bs-toggle="modal" data-bs-target="#staticBackdrop">Add Tool</button>
                </div>

                {error && <div className="alert alert-danger" role="alert">{error}</div>}

                <div className='mx-3'>
                    <div className="row">
                        {tools.map(tool => (
                            <div key={tool.id} className="col-lg-3 col-md-4 col-sm-6 mb-4">
                                <div className="card" style={{minHeight: 200}} onClick={() => handleUpdateTool(tool.id)} data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                                    <img src={tool.pictureUrl} className="card-img-top" alt={tool.name} style={{ height: '150px', objectFit: 'cover' }} />
                                    <div className="card-body">
                                        <h5 className="card-title" style={{ overflow: 'hidden', whiteSpace: 'nowrap', textOverflow: 'ellipsis' }}>{tool.toolName}</h5>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Tools