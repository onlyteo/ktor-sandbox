import React, {FC, ReactElement} from "react";
import {Nav, Navbar, NavDropdown} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {
    faCircleInfo,
    faCircleUser,
    faHouse,
    faRightFromBracket,
    faScrewdriverWrench
} from "@fortawesome/free-solid-svg-icons";
import {User} from "../types";

library.add(faCircleInfo, faCircleUser, faHouse, faRightFromBracket, faScrewdriverWrench);

interface HomeProps {
    user: User
}

export const Header: FC<HomeProps> = (props: HomeProps): ReactElement => {
    const {sub: username} = props.user;

    return (
        <header className="header mb-5">
            <Navbar variant="dark" expand="lg" className="p-4">
                <Navbar.Brand className="mx-4" href="/">
                    <h1>
                        <FontAwesomeIcon className="navbar-logo emphasized-text" icon="screwdriver-wrench"/><span
                        className="navbar-title ms-3">Ktor Sandbox</span>
                    </h1>
                </Navbar.Brand>
                <Nav className="ms-auto">
                    <NavDropdown title={<FontAwesomeIcon icon="circle-user"/>} className="px-3" menuVariant="dark"
                                 align="end">
                        <NavDropdown.Item disabled={true}>{username}</NavDropdown.Item>
                        <NavDropdown.Divider/>
                        <NavDropdown.Item href="/logout">
                            <FontAwesomeIcon icon="right-from-bracket"/>
                        </NavDropdown.Item>
                    </NavDropdown>
                </Nav>
            </Navbar>
        </header>
    );
};
