import React from "react";
import { Button } from "react-bootstrap";

function NotFound() {
    return (
        <div className='flex justify-center mb-20'>
            <a href="/">
                <Button
                    variant="secondary"
                    className='w-40 h-10'
                >
                    홈으로
                </Button>
            </a>
        </div>
    );
}

export default NotFound;
