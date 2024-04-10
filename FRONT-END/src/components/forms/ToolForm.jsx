import React, { useState, useEffect,  } from 'react'
import { toolById } from '../../Services/aviationServices'


const Tool_Form = ({ toolId, onClose }) => {
    const [toolImage, setToolImage] = useState(null);
    const [toolBarcodeId, setToolBarcodeId] = useState('')
    const [toolName, setToolName] = useState('')
    const [toolStatus, setToolStatus] = useState('')

    useEffect(()=> {
        if (toolId) {
            toolById(toolId).then((response) => {
                const { name, barcodeId, status } = response.data;
                setToolBarcodeId(barcodeId);
                setToolName(name);
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

        if (toolId) {
            updateTool(toolData, toolId)
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
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button className='btn btn-success' onClick={saveTool} data-bs-toggle="modal">Submit</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Tool_Form
