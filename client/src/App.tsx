import React, { useEffect } from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.css"; // Import precompiled Bootstrap css
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./component/layout/Header";
import Footer from "./component/layout/Footer";
import Main from "./component/page/Main";
import Login from "./component/page/Login";
import SignUp from "./component/page/SignUp";
import Manager from "./component/page/Manager";
import NotFound from "./component/page/NotFound";
import SubHeader from "./component/layout/SubHeader";
import AddApply from "./component/page/AddApply";
import Profile from "./component/page/Profile";
import JobPosting from './component/page/JobPosting';
function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Header />
                <Routes>
                    <Route
                        path="/"
                        element={<SubHeader children={<Main />} title="메인" />}
                    ></Route>
                    <Route
                        path="/profile"
                        element={
                            <SubHeader
                                children={<Profile />}
                                title="프로필 설정"
                            />
                        }
                    ></Route>
                    <Route
                        path="/login"
                        element={
                            <SubHeader children={<Login />} title="로그인" />
                        }
                    ></Route>
                    <Route
                        path="/signUp"
                        element={
                            <SubHeader children={<SignUp />} title="회원가입" />
                        }
                    ></Route>
                    <Route
                        path="/manager"
                        element={
                            <SubHeader
                                children={<Manager />}
                                title="지원 내역"
                            />
                        }
                    ></Route>
                    <Route
                        path="/addApply"
                        element={
                            <SubHeader
                                children={<AddApply />}
                                title="지원 등록"
                            />
                        }
                    ></Route>
                    <Route
                        path="/jobPost"
                        element={
                            <SubHeader
                                children={<JobPosting />}
                                title="지원 공고"
                            />
                        }
                    ></Route>
                    <Route path="*" element={<SubHeader
                                children={<NotFound />}
                                title="존재하지 않는 페이지입니다"
                            />}></Route>
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    );
}

export default App;
