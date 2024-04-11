import { useEffect, useState } from 'react'
import { listTools, updateTransaction } from '../../Services/aviationServices';

const ReturnForm = ({ returnData, onClose }) => {
    const [returnedTools, setReturnTools] = useState([]);
    const [scanning, setScanning] = useState(false);
    const [users, setUsers] = useState([]);
    const [barcodeInput, setBarcodeInput] = useState();
    const [error, setError] = useState();
    const [errorMessage, setErrorMessage] = useState();
    const [data, setData] = useState();

    useEffect(() => {
        document.addEventListener('keydown', handleKeyDown);
        console.log(returnData)

        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, []);

    useEffect(() => {
        console.log(returnedTools, 'rerender');
    }, [returnedTools])

    const handleKeyDown = (event) => {
        if (event.target.id === 'returnBarcodeId') {
            if (event.keyCode === 13) {
                event.preventDefault();
                
                const scannedData = event.target.value;
                const returnedToolsList = [];

                returnData.borrowedTools.forEach((borrowedTool) => {
                    if (borrowedTool.toolBarcodeId === scannedData) {
                        returnedToolsList.push(borrowedTool);
                    }
                })

                setReturnTools(returnedToolsList);

                event.target.value = '';

                document.getElementById("returnBarcodeId").blur();
            }
        }
    };

    const handleClickScan = () => {
        setScanning(true);
        setErrorMessage();
        document.getElementById('returnBarcodeId').focus();
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

    const onClickSubmit =  async (data) => {
        data.status = "COMPLETED";
        console.log(data, "submit")
        
        try {
            let response = await updateTransaction(data);
            console.log(response);
            onClose();
        }
        catch(error) {
            console.error(error);
        }
    }

    return (
        <div className="modal fade" id="returnFormModal" data-bs-keyboard="false" tabIndex="-1" aria-labelledby="mainModalLabel" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered modal-xl">
                <div className="modal-content">
                    <div className="modal-header">
                        <div className="modal-title">
                            Return tools
                        </div>
                    </div>
                    <div className="modal-body">
                        {
                            returnData &&
                            <>
                            <ul>
                            {returnData.borrowedTools.map(borrowedTool => (
                                <li key={borrowedTool.borrowedToolId}>
                                    {`${borrowedTool.borrowedToolId} ${borrowedTool.toolName} (${borrowedTool.toolBarcodeId})`}
                                </li>
                            ))}
                            </ul>
                            {/* <input 
                            type='text' 
                            id='returnBarcodeId' 
                            name='returnBarcodeId' 
                            style={{opacity: 0, width: 0, overflow: "hidden"}} 
                            onChange={(e) => {
                                setBarcodeInput(e.target.value);
                            }}
                            onBlur={handleBlur} 
                            value={barcodeInput}/>
                            <button type='button' className='btn btn-primary col' onClick={handleClickScan}>Scan tool</button> */}
                            </>
                        }
                        {
                            scanning &&
                            <p className='text-succcess'>Scanning...</p>
                        }

                        {
                            returnedTools.length > 0 &&
                            <ul>
                            {returnedTools.map((returnedTool, index) => (
                                <li key={index}>{returnedTool.borrowedTool.id}{returnedTool.toolName}</li>
                            ))}
                            </ul>
                        }

                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" form='transactionForm' className='btn btn-success' data-bs-dismiss="modal" onClick={() => onClickSubmit(returnData)}>Return</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ReturnForm