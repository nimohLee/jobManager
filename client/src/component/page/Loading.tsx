import React from "react";
import { Spinner } from 'react-bootstrap';

function Loading() {
    return (
        <div className='w-screen h-screen background-color: transparent;'>
            <div className="flex justify-center items-center h-80">
                <Spinner animation="border" className="float-center" />
            </div>
        </div>
    );
}

export default Loading;
