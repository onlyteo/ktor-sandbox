import React, {FC, ReactElement, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import {GreetingAlert} from "../fragments/GreetingAlert";
import {GreetingForm} from "../fragments/GreetingForm";
import {initialDelayedGreeting} from "../types";

export const Home: FC = (): ReactElement => {
    const [pendingGreeting, setPendingGreeting] = useState(initialDelayedGreeting)

    return (
        <Container>
            <div className="description-box px-3 py-5 rounded-3">
                <Row>
                    <Col>
                        <h2 className="emphasized-text">Welcome to this Ktor example!</h2>
                        <p>This example shows OpenTelemetry and Micrometer observability</p>
                    </Col>
                </Row>
                <Row className="mt-5">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingForm delayedGreeting={pendingGreeting} setDelayedGreeting={setPendingGreeting}/>
                    </Col>
                    <Col></Col>
                </Row>
                <Row className="mt-4">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingAlert delayedGreeting={pendingGreeting}/>
                    </Col>
                    <Col></Col>
                </Row>
            </div>
        </Container>
    );
}
