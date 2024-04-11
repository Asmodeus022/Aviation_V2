import React, { useState, useEffect,  } from 'react'
import { toolById, updateTool, addTool, deleteTool } from '../../Services/aviationServices'


const Tool_Form = ({ toolId, onClose }) => {
    const [toolImage, setToolImage] = useState(null);
    const [toolBarcodeId, setToolBarcodeId] = useState('')
    const [toolName, setToolName] = useState('')
    const [toolStatus, setToolStatus] = useState('AVAILABLE')

    useEffect(()=> {
        if (toolId) {
            toolById(toolId).then((response) => {
                const { toolName, barcodeId, status } = response.data;
                setToolBarcodeId(barcodeId);
                setToolName(toolName);
                setToolStatus(status);
            }).catch(error => {
                console.error(error);
            })
        }
    }, [toolId])

    function modalTitle() {
        if (toolId) {
            return "Edit Tool";
        } else {
            return "Add Tool";
        }
    }

    function saveTool() {
        const toolData = {
            image: toolImage ? toolImage: null,
            name: toolName,
            barcodeId: toolBarcodeId,
            status: toolStatus
        }

        console.log(toolId)
        console.log(toolData)

        if (toolId) {
            updateTool(toolId, toolData)
                .then(response => {
                    console.log("Tool updated successfully:", response.data);
                    onClose();
                })
                .catch(error => {
                    console.error("Error updating tool:", error);
                });
        } else {
            addTool(toolData)
                .then(response => {
                    console.log("Tool added successfully:", response.data);
                    onClose();
                })
                .catch(error => {
                    console.error("Error adding tool:", error);
                });
        }
    }

    function handleDeleteTool() {
        const isConfirmed = window.confirm("Are you sure you want to delete this product?");
        if (isConfirmed) {
            deleteTool(toolId)
                .then(() => {
                    onClose();
                })
                .catch((error) => {
                    if (error.response && error.response.status === 404) {
                        onClose();
                    } else {
                        console.error("Error deleting tool:", error);
                    }
                });
        }
    }
    

    function handleCloseFunction() {
        setToolImage(null);
        setToolName('');
        setToolBarcodeId('');
        setToolStatus('');
        onClose();
    }

    return (
        <div className="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="staticBackdropLabel"> { modalTitle() } </h1>
                        <button type="button" className="btn-close" onClick={() => handleCloseFunction()} data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <form action="">
                            <div className='form-group mb-2'>
                                <label className='form-label' htmlFor="">Add Image:</label>
                                <input
                                    type="file"
                                    accept="image/*"
                                    name='toolImage'
                                    className='form-control'
                                    onChange={(e) => setToolImage(e.target.files[0])}
                                />
                            </div>
                            <div className='form-group mb-2'>
                                <label className='form-label' htmlFor="">Tool Name:</label>
                                <input
                                    type="text"
                                    placeholder='Enter tool name'
                                    name='toolName'
                                    value={toolName}
                                    className='form-control'
                                    onChange={(e) => setToolName(e.target.value)}
                                />
                            </div>
                            <div className='form-group mb-2'>
                                <label className='form-label' htmlFor="">Tool BarcodeId:</label>
                                <input
                                    type="text"
                                    placeholder='Enter tool barcodeId'
                                    name='toolBarcodeId'
                                    value={toolBarcodeId}
                                    className='form-control'
                                    onChange={(e) => setToolBarcodeId(e.target.value)}
                                />
                                </div>
                                <div className='form-group mb-2'>
                                    <label className='form-label' htmlFor="toolStatus">Tool Status:</label>
                                    <select
                                        name='toolStatus'
                                        value={toolStatus}
                                        className='form-control'
                                        onChange={(e) => setToolStatus(e.target.value)}
                                    >
                                        <option value="AVAILABLE">AVAILABLE</option>
                                        <option value="OUT OF STOCK">OUT OF STOCK</option>
                                    </select>
                                </div>
                        </form>
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button className='btn btn-danger' onClick={() => handleDeleteTool()} data-bs-dismiss="modal" >delete</button>
                        <div>
                            <button className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button className='btn btn-success ms-2' onClick={saveTool} data-bs-toggle="modal">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Tool_Form
