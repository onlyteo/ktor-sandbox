import React, {FC, ReactElement, useCallback, useReducer} from "react";
import {gql, useMutation} from '@apollo/client';
import {Col, Container, Row} from "react-bootstrap";
import {GreetingAlert, GreetingForm} from "../fragments";
import {greetingReducer, initialGreetingState} from "../state/reducers";
import {Greeting, Person} from "../types";

const POST_GREETING = gql`
    mutation PostGreeting($name: String!) {
        postGreeting(person: { name: $name }) {
            message
        }
    }
`;

export const Home: FC = (): ReactElement => {
    const [greetingState, greetingDispatch] = useReducer(greetingReducer, initialGreetingState);
    const [postGreeting] = useMutation(POST_GREETING);

    const getGreeting = useCallback((person: Person) => {
        greetingDispatch({status: 'LOADING'})
        postGreeting({variables: person})
                .then((response) => {
                    const greeting: Greeting = {message: response.data.postGreeting.message}
                    greetingDispatch({status: 'SUCCESS', data: greeting})
                })
                .catch(error => {
                    console.log("ERROR", error)
                    greetingDispatch({status: 'FAILED'})
                });
    }, [greetingDispatch, postGreeting]);

    return (
            <Container>
                <div className="description-box px-3 py-5 rounded-3">
                    <Row>
                        <Col>
                            <h2 className="emphasized-text">Welcome to this Ktor example!</h2>
                            <p>This example shows a React frontend and Ktor GraphQL API</p>
                        </Col>
                    </Row>
                    <Row className="mt-5">
                        <Col></Col>
                        <Col xs={5}>
                            <GreetingForm greetingState={greetingState} getGreeting={getGreeting}/>
                        </Col>
                        <Col></Col>
                    </Row>
                    <Row className="mt-4">
                        <Col></Col>
                        <Col xs={5}>
                            <GreetingAlert greetingState={greetingState}/>
                        </Col>
                        <Col></Col>
                    </Row>
                </div>
            </Container>
    );
}
