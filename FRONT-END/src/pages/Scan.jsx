import React, { useState, useEffect } from 'react'
import Header from '../components/Header'
import { listTools } from '../Services/aviationServices'
import axios from 'axios';

const Scan = () => {

    const [scannedData, setScannedData] = useState('');
    const [barcodeId, setBarcodeId] = useState('');
    const [barcodeImage, setBarcodeImage] = useState(null);

    useEffect(() => {
        document.addEventListener('keydown', handleKeyDown);
        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, []);

    const generateBarcode = async () => {
        try {
            const formData = new FormData();
            formData.append('barcodeId', barcodeId);
            formData.append('width', 100);
            formData.append('height', 50);
            formData.append('margin', 10);

            const response = await axios.post(
                'http://localhost:8080/api/barcode/generateBarcode',
                formData,
                {
                    responseType: 'arraybuffer'
                }
            );

            const base64String = btoa(
                new Uint8Array(response.data)
                    .reduce((data, byte) => data + String.fromCharCode(byte), '')
            );

            setBarcodeImage(`data:image/png;base64,${base64String}`);
        } catch (error) {
            console.error('Error generating barcode:', error);
        }
    };
      
    const saveBarcodeImage = () => {
        const downloadLink = document.createElement('a');
        downloadLink.href = barcodeImage;
        downloadLink.download = 'barcode.png';
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
    };
    
    const handleKeyDown = (event) => {
        if (event.target.id === 'barcodeInput') {
            if (event.keyCode === 13) {
                event.preventDefault();
                
                const scannedData = event.target.value;
                handleScan(scannedData);
                
                event.target.value = '';
            }
        }
    };
    

    const handleScan = async (scannedData) => {
        try {
            const response = await listTools(scannedData);
            setScannedData(response.data.barcodeId)
        } catch (error) {
            console.error('Error:', error);
        }
    };


    return (
        <div>
            <Header title="Scan" />
            <input type="text" id="barcodeInput" />
            <p>Scanned Barcode: {scannedData}</p>

            <div>
                <input
                    type="text"
                    placeholder="Enter Barcode ID"
                    value={barcodeId}
                    onChange={(e) => setBarcodeId(e.target.value)}
                />
                <button onClick={generateBarcode}>Generate Barcode</button>
                {barcodeImage && (
                    <div>
                        <img src={barcodeImage} alt="Barcode" />
                        <button onClick={saveBarcodeImage}>Save Barcode</button>
                    </div>
                )}
            </div>
        </div>
    )
}

export default Scan
