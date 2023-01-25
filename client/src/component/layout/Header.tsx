import axios from 'axios';
import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import logo from "../../static/image/jm_logo.png";
import { useCookies } from 'react-cookie';

function Header() {
    const [cookies,setCookie,removeCookie] = useCookies(['JSESSIONID']);
    return (
        <header className="border-b-gray-700 h-200">
            <Navbar bg="white" expand="lg">
                <Container>
                    <Navbar.Brand href="/">
                        <img src={logo} alt="Logo Image" className="w-16" />
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="/manager" className='text-26'>지원관리</Nav.Link>
                            <Nav.Link href="/diary">다이어리</Nav.Link>
                            {
                            localStorage.getItem("isLogin") ? 
                            <Nav.Link onClick={async ()=>{
                                try{
                                    await axios({
                                        method: "post",
                                        url: "/api/v1/user/logout"
                                    });
                                    localStorage.removeItem("isLogin");
                                    window.location.reload();
                                }catch(err){
                                    alert("로그아웃에 실패했습니다. 잠시 후 다시 시도해주세요");
                                }
                                
                            }}>로그아웃</Nav.Link>
                            :  
                            <NavDropdown
                                    title="로그인"
                                    id="basic-nav-dropdown"
                                >
                                    <NavDropdown.Item href="/login">
                                        로그인
                                    </NavDropdown.Item>
                                    <NavDropdown.Item href="/signUp">
                                        회원가입
                                    </NavDropdown.Item>
                                </NavDropdown>
                        }
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </header>
    );
}

export default Header;
