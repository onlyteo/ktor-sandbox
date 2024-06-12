import React, {FC, ReactElement, useEffect, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import useWebSocket, {ReadyState} from 'react-use-websocket';
import {GreetingAlert, GreetingForm} from "../fragments";
import {Greeting, initialDelayedGreeting} from "../types";

export const Home: FC = (): ReactElement => {
    const [delayedGreeting, setDelayedGreeting] = useState(initialDelayedGreeting)
    const {lastJsonMessage, readyState} = useWebSocket<Greeting>("ws://localhost:8080/ws/greetings", {
        onOpen: (event) => {
            console.log("WebSocket connection opened", event);
        },
        onMessage: (event) => {
            console.log("WebSocket received message", event);
        },
        onError: (event) => {
            console.log("WebSocket connection error", event);
        },
        onClose: (event) => {
            console.log("WebSocket connection closed", event);
        },
        shouldReconnect: () => false,
    });

    useEffect(() => {
        console.log("WebSocket connection state", ReadyState[readyState]);
    }, [readyState]);

    useEffect(() => {
        console.log("WebSocket message", lastJsonMessage);
        if (lastJsonMessage) {
            setDelayedGreeting({...delayedGreeting, message: lastJsonMessage.message})
        }
    }, [delayedGreeting, setDelayedGreeting, lastJsonMessage]);

    return (
        <Container>
            <div className="description-box px-3 py-5 rounded-3">
                <Row>
                    <Col>
                        <h2 className="emphasized-text">Welcome to this Ktor example!</h2>
                        <p>This example shows a Kafka Streams event driven architecture</p>
                    </Col>
                </Row>
                <Row className="mt-5">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingForm delayedGreeting={delayedGreeting} setDelayedGreeting={setDelayedGreeting}/>
                    </Col>
                    <Col></Col>
                </Row>
                <Row className="mt-4">
                    <Col></Col>
                    <Col xs={5}>
                        <GreetingAlert delayedGreeting={delayedGreeting}/>
                    </Col>
                    <Col></Col>
                </Row>
            </div>
        </Container>
    );
}
