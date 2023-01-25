import React from 'react';
import './App.css';
import "bootstrap/dist/css/bootstrap.css"; // Import precompiled Bootstrap css
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from "./component/layout/Header";
import Main from "./component/page/Main";
import Login from "./component/page/Login";
import SignUp from "./component/page/SignUp";
import Manager from './component/page/Manager';
import NotFound from './component/page/NotFound';
import SubHeader from './component/layout/SubHeader';
import AddApply from './component/page/AddApply';
import Profile from './component/page/Profile';
import NaverMap from './component/module/NaverMap';
function App() {
  return (
    <div className="App">
        <BrowserRouter>
          <Header/>
          <Routes>
              <Route path="/" element={<SubHeader children={<Main/>} title="메인"/>}></Route>
              <Route path="/map" element={<SubHeader children={<NaverMap/>} title="지도"/>}></Route>
              <Route path="/profile" element={<SubHeader children={<Profile/>} title="프로필 설정"/>}></Route>
              <Route path="/login" element={<SubHeader children={<Login/>} title="로그인"/>}></Route>
              <Route path="/signUp" element={<SubHeader children={<SignUp/>} title="회원가입"/>}></Route>
              <Route path="/manager" element={<SubHeader children={<Manager/>} title="지원 내역"/>}></Route>
              <Route path="/addApply" element={<SubHeader children={<AddApply/>} title="지원 등록"/>}></Route>
              <Route path='*' element={<NotFound/>}></Route>
          </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
