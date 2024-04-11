import React, { useEffect, useState } from 'react'

const ResponseDialog = ({ data, error, handleClickScan, handleClickAddTool }) => {
    const [title, setTitle] = useState();

    useEffect(() => {
        if (data !== undefined && error === undefined) {
            setTitle("Tool found");
        }
        else if (error !== undefined) {
            setTitle("Tool not found");
        }
    }, [data, error])


  return (
    <>
        {
            data &&
            <div className="card mb-3">
                <div className="card-header">
                    <div className="card-title">
                        {title}
                    </div>
                </div>
                <div className="card-body">
                    <div className="card-text">
                        Tool Name: 
                        {data.toolName}
                    </div>
                    <div className="card-text">
                        Status: 
                        {data.status}
                    </div>
                </div>
            </div>
        }
        <div className="d-flex justify-content-around">
            {
                data && error === undefined &&
                <button type='button' className='btn btn-success col' onClick={handleClickAddTool}>Add tool</button>
            }
            <button type='button' className='btn btn-primary col' onClick={handleClickScan}>Scan tool</button>
        </div>
    </>
  )
}

export default ResponseDialog