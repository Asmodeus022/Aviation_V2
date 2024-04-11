import React, { useEffect, useState } from 'react'
import Header from '../components/Header'
import { allBarrowed } from '../Services/aviationServices'
import TransactionForm from '../components/forms/TransactionForm'
import ResponseDialog from '../components/modals/ResponseDialog'
import ReturnForm from '../components/forms/ReturnForm'


const Borrowed_Tools = () => {
    const [tools, setTools] = useState([])
    const [selectedToolId, setSelectedToolId] = useState(null)
    const [refresh, setRefresh]= useState(false);
    const [selectedTransaction, setSelectedTransaction] = useState(null);

    useEffect(() => {
        getAllTools()
    }, [refresh])

    async function getAllTools() {
        try {
            const response = await allBarrowed()
            setTools(response.data)
            console.log(response.data)
        } catch (error) {
            console.error(error)
            setError('Failed to fetch products. Please try again.')
        }
    }

    function handleAddBorrowed() {
        setSelectedToolId(null);
    }
    

    function handleUpdateBorrowed(toolId) {
        setSelectedToolId(toolId)
    }

    function handleDeleteBorrowed(toolId) {
        const isConfirmed = window.confirm("Are you sure you want to delete this row?");
        if (isConfirmed) {
            deleteProduct(toolId)
                .then(() => {
                    setRefresh(prevRefresh => !prevRefresh);
                })
                .catch(error => {
                    console.error("Error deleting product:", error);
                    setError("Failed to delete the row. Please try again.");
                });
        }
    }

    const handleClickReturn = (tool) => {
        console.log(tool);
        setSelectedTransaction(tool);
    }

    const handleOnClose = () => {
        setRefresh(prevRefresh => !prevRefresh);
    }


    return (
        <div>
            <Header title="Borrowed_Tools" />
            <div className="content">
                <div className='d-flex justify-content-end mx-3 mb-3'>
                    <button className='btn btn-primary' onClick={() => handleAddBorrowed()} data-bs-toggle="modal" data-bs-target="#transaction-modal">Add Borrowed</button>
                </div>

                <div className='mx-3'>
                    <table className='table table-striped table-bordered'>
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>User</th>
                                <th>Borrowed Tools</th>
                                <th>Status</th>
                                <th>Borrowed Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tools.map(tool => (
                                <tr key={tool.id}>
                                    <td>{tool.id}</td>
                                    <td>{`${tool.user.fullname} (${tool.user.email})`}</td>
                                    <td>
                                        <ul>
                                            {tool.borrowedTools.map(borrowedTool => (
                                                <li key={borrowedTool.borrowedToolId}>
                                                    {`${borrowedTool.toolName} (${borrowedTool.toolBarcodeId})`}
                                                </li>
                                            ))}
                                        </ul>
                                    </td>
                                    <td>{tool.status}</td>
                                    <td>{tool.borrowedDate}</td>
                                    <td>
                                        <button 
                                        className='btn btn-primary' 
                                        data-bs-toggle="modal" 
                                        data-bs-target="#returnFormModal"
                                        onClick={() => handleClickReturn(tool)}
                                        >Return</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <TransactionForm />
            {
                selectedTransaction !== null &&
                <ReturnForm returnData={selectedTransaction} onClose={() => handleOnClose()} />
            }
        </div>
    )
}

export default Borrowed_Tools
