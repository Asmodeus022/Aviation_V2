import React, { useState, useEffect } from 'react'
import Header from '../components/Header'
import { allTools } from '../Services/aviationServices'
import Tool_Form from '../components/forms/ToolForm'

const Tools = () => {
    const [tools, setTools] = useState([])
    const [selectedToolId, setSelectedToolId] = useState(null)

    useEffect(() => {
        getAllTools()
    }, [])

    async function getAllTools() {
        try {
            const response = await allTools()
            setTools(response.data)
            console.log(response.data)
        } catch (error) {
            console.error(error)
            setError('Failed to fetch products. Please try again.')
        }
    }

    function handleAddTool() {
        setSelectedToolId(null);
    }
    

    function handleToolUpdate(toolId) {
        console.log(toolId);
        setSelectedToolId(toolId)
    }

    return (
        <div>
            <Header title="Tools" />
            <div className="content">
                <Tool_Form toolId={selectedToolId} onClose={handleToolUpdate}/>

                <div className='d-flex justify-content-end mx-3 mb-3'>
                    <button className='btn btn-primary' onClick={() => handleAddTool} data-bs-toggle="modal" data-bs-target="#staticBackdrop">Add Tool</button>
                </div>

                <div className='mx-3'>
                    <div className="row">
                        {tools.map(tool => (
                            <div key={tool.id} className="col-lg-3 col-md-4 col-sm-6 mb-4">
                                <div className="card" style={{minHeight: 200}} onClick={() => handleToolUpdate(tool.id)} data-bs-toggle="modal" data-bs-target="#staticBackdrop">
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
