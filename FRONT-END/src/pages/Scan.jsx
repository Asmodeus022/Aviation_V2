import React, { useState, useEffect } from 'react'
import Header from '../components/Header'
import { listTools } from '../Services/aviationServices'

const Scan = () => {

    const [scannedData, setScannedData] = useState('');

    useEffect(() => {
        document.addEventListener('keydown', handleKeyDown);
        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, []);

    const handleKeyDown = (event) => {
        if (event.target.id === 'barcodeInput') {
            if (event.keyCode === 13) {
                event.preventDefault();
                
                const scannedData = event.target.value;
                console.log(scannedData)
                handleScan(scannedData);
                
                event.target.value = '';
            }
        }
    };
    

    const handleScan = async (scannedData) => {
        try {
            const response = await listTools(scannedData);
            setScannedData(response.data.barcodeId)
            console.log('Response from backend:', response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    };


    return (
        <div>
            <Header title="Scan" />
            <input type="text" id="barcodeInput" />
            <p>Scanned Barcode: {scannedData}</p>
        </div>
    )
}

export default Scan
