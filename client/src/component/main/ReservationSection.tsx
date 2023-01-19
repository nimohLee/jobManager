import { useEffect, useState } from 'react';
import DatePickerComponent from './DatePickerComponent';

function ReservationSection() {
  const [checkInDate, setCheckInDate] = useState(new Date());
  const [checkOutDate, setCheckOutDate] = useState(new Date());

  useEffect(()=>{
    if (checkInDate > checkOutDate ) setCheckOutDate(checkInDate);
  },[checkInDate]);

  
  return (
    <section className="text-center" id='reservation-section'>
         <h2>Reservation</h2>
         <div className='flex justify-around'>
           <div>
             <h3>Check In</h3>
             <DatePickerComponent dateType='checkIn' startDate={checkInDate} setDate={setCheckInDate}/>
           </div>
           <div>
             <h3>Check Out</h3>
             <DatePickerComponent dateType='checkOut' startDate={checkOutDate} setDate={setCheckOutDate}/>
           </div>
         </div>
    </section>
  )
}

export default ReservationSection;