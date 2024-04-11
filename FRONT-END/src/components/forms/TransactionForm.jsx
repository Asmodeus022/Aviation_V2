import React, { useState, useEffect, useRef } from 'react'
import { addTransaction, getAllUsers, listTools } from '../../Services/aviationServices'
import ResponseDialog from '../modals/ResponseDialog';
import { Modal } from 'bootstrap';

const TransactionForm = () => {
    const [users, setUsers] = useState([]);
    const [scanning, setScanning] = useState(false);
    const [barcodeInput, setBarcodeInput] = useState();
    const [data, setData] = useState();
    const [error, setError] = useState();
    const [errorMessage, setErrorMessage] = useState();
    const [addedTools, setAddedTools] = useState([]);
    const modalRef = useRef();

    useEffect(() => {
        setErrorMessage();
    }, [addedTools])

    useEffect(() => {
        document.addEventListener('keydown', handleKeyDown);
        fetchUsers();
        
        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, []);

    const fetchUsers = async () => {
        try {
            let response = await getAllUsers();
            setUsers(response.data);
            console.log(response.data)
        } catch(error) {
        }
    }

    const handleKeyDown = (event) => {
        if (event.target.id === 'toolBarcodeId') {
            if (event.keyCode === 13) {
                event.preventDefault();
                
                const scannedData = event.target.value;
                handleScan(scannedData);
                
                event.target.value = '';

                document.getElementById("toolBarcodeId").blur();
            }
        }
    };

    const handleClickScan = () => {
        setScanning(true);
        setErrorMessage();
        document.getElementById('toolBarcodeId').focus();
    }

    const handleScan = async (scannedData) => {
        try {
            const response = await listTools(scannedData);
            setData(response.data)
            setError();
        } catch (error) {
            setError(error)
            setData();
            console.error('Error:', error);
        }
    };


    const handleBlur = () => {
        setBarcodeInput("");
        setScanning(false);
    }

    const handleCloseFunction = () => {

    }

    const handleClickAddTool = () => {
        if (data !== undefined) {
            if (addedTools.length > 0) {
                let includedIds = [];
                addedTools.forEach((addedTool) => {
                    includedIds.push(addedTool.id);
                })
                if (includedIds.includes(data.id)) {
                    setErrorMessage("Already added");
                }
                else {
                    setErrorMessage();
                    setAddedTools([...addedTools, data])
                    setData();
                }
            }
            else {
                setAddedTools([...addedTools, data])
                setData();
            }
        }
    }

    const handleClickRemoveTool = () => {
        setAddedTools(addedTools.slice(0, -1));
    }

    const onSubmitTransactionForm = async (event) => {
        event.preventDefault();


        let formData = new FormData(event.target);
        let data = {};
        formData.forEach((value, key) => {
            if (key === "borrowedTools" || key == "user") {
                data[key] = JSON.parse(value);
            }
            else {
                data[key] = value
            }
        })
        delete data.toolBarcodeId;
       
        try {
            let response = await addTransaction(data);
            console.log(response);
            setAddedTools([]);
            event.target.reset();
        }
        catch(error) {
            console.error(error);
        }
    }

    
    return (
        <>
        <div ref={modalRef} className="modal fade" id="transaction-modal" data-bs-backdrop="static" data-bs-keyboard="false" tabIndex="-1" aria-labelledby="mainModalLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered modal-xl">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="staticBackdropLabel"> Lending Transaction </h1>
                        <button type="button" className="btn-close" onClick={() => handleCloseFunction()} data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <form id="transactionForm" onSubmit={onSubmitTransactionForm}>
                            <div className='row'>
                                <div className="col">
                                    <div className='form-group mb-2'>
                                        <label className='form-label' htmlFor="user">Borrower/User:</label>
                                        <select className='form-select' name='user'>
                                            {
                                                users &&
                                                users.map((user, index) => (
                                                    <option key={index} value={JSON.stringify(user)}>
                                                        {user.fullname}
                                                    </option>
                                                ))
                                            }
                                        </select>
                                    </div>
                                </div>
                                <input 
                                type='text' 
                                id='toolBarcodeId' 
                                name='toolBarcodeId' 
                                style={{opacity: 0, width: 0, overflow: "hidden"}} 
                                onChange={(e) => {
                                    setBarcodeInput(e.target.value);
                                }}
                                onBlur={handleBlur} 
                                value={barcodeInput}/>
                            </div>
                            <div className="row">
                                <div className="col">
                                    <label className='form-label invisible' htmlFor='toolSearch'>Search tool</label>
                                        <div className="container mb-4">
                                        {
                                            scanning && <p className="text-success position-fixed">Scanning...</p>
                                        }
                                        {
                                            errorMessage && <p className="text-danger position-fixed">{errorMessage}</p>
                                        }
                                        </div>
                                        <ResponseDialog 
                                            data={data} 
                                            error={error} 
                                            handleClickScan={handleClickScan}
                                            handleClickAddTool={handleClickAddTool}
                                        />
                                </div>
                            </div>
                            <div className='form-group mb-2'>
                                <input type='hidden' name="borrowedTools" value={JSON.stringify(addedTools)} />
                                <label className='form-label'>Borrowed tools:</label>
                                <ul className='list-group'>
                                    {
                                        addedTools.length > 0 &&
                                        addedTools.map((tool, index) => (
                                            <li className='list-group-item' key={index}>
                                                <div className='d-flex justify-content-around'>
                                                    <p>
                                                        {tool.toolName}
                                                    </p>
                                                    <p>
                                                        {tool.status}
                                                    </p>
                                                    <p>
                                                        {tool.barcodeId} 
                                                    </p>
                                                    <button 
                                                    className='btn btn-danger'
                                                    onClick={handleClickRemoveTool} 
                                                    >
                                                        Remove 
                                                    </button>
                                                </div>
                                            </li>
                                        ))
                                    }
                                </ul> 
                            </div>
                        </form>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" form='transactionForm' className='btn btn-success' data-bs-dismiss="modal">Submit</button>
                    </div>
                </div>
            </div>
        </div>
        </>
    )
}

export default TransactionForm