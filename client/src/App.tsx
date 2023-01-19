import React from 'react';
import './App.css';
import "bootstrap/dist/css/bootstrap.css"; // Import precompiled Bootstrap css
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./component/layout/Header";
import Main from "./component/page/Main";
import Login from "./component/page/Login";
import SignUp from "./component/page/SignUp";
import Reservation from "./component/page/Reservation";
function App() {
  return (
    <div className="App">
        <BrowserRouter>
          <Header/>
          <Routes>
              <Route path="/" element={<Main/>}></Route>
              <Route path="/login" element={<Login/>}></Route>
              <Route path="/signUp" element={<SignUp/>}></Route>
              <Route path="/reservation" element={<Reservation/>}></Route>
          </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
