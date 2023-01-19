import {Container, Nav, Navbar, NavDropdown} from 'react-bootstrap';
import logo from '../../hotel_logo.png';

function Header() {
    return (
        <Navbar bg="white" expand="lg" className="border-b-gray-700">
            <Container>
                <Navbar.Brand href="#home">
                    <img src={logo} alt="Logo Image" className="w-40"/>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="#home">Reservation</Nav.Link>
                        <Nav.Link href="#link">MyBooking</Nav.Link>
                        <NavDropdown title="Member" id="basic-nav-dropdown">
                            <NavDropdown.Item href="#action/3.1">Log in</NavDropdown.Item>
                            <NavDropdown.Item href="#action/3.2">
                                Sign Up
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;