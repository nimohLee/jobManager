import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import logo from "../../static/image/hotel_logo.png";
import { Link } from "react-scroll";
function Header() {
    return (
        <header className="border-b-gray-700 h-200">
            <Navbar bg="white" expand="lg">
                <Container>
                    <Navbar.Brand href="/">
                        <img src={logo} alt="Logo Image" className="w-40" />
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                                <Link
                                    activeClass="active"
                                    to="reservation-section"
                                    spy={true}
                                    smooth={true}
                                    duration={100}
                                    style={{cursor:'pointer'}}
                                >
                                    Reservation
                                </Link>
                            {/* <Nav.Link href="/reservation">Reservation</Nav.Link> */}
                            <Nav.Link href="/myBook">MyBooking</Nav.Link>
                            <NavDropdown title="Member" id="basic-nav-dropdown">
                                <NavDropdown.Item href="/login">
                                    Login
                                </NavDropdown.Item>
                                <NavDropdown.Item href="/signUp">
                                    SignUp
                                </NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </header>
    );
}

export default Header;
