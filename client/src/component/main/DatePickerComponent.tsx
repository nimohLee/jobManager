import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
type InfoProps = {
    dateType: "checkIn" | "checkOut";
    startDate: Date;
    setDate: React.Dispatch<React.SetStateAction<Date>>;
};
function DatePickerComponent(props: InfoProps) {
    return (
        <div className='border-solid border-black border h-16 text-xl mt-5 py-0 px-8 text-center w-80 rounded-xl flex items-center'>
        <DatePicker
            dateFormat="yyyy년 MM월 dd일"
            selected={props.startDate}
            onChange={(date: Date) => props.setDate(date)}
            selectsStart={false}
            minDate={props.startDate}
        />
        </div>
    );
}

export default DatePickerComponent;
