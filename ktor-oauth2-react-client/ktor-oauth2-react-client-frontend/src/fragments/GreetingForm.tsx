import React, {ChangeEvent, FC, FormEvent, ReactElement, useCallback, useEffect, useReducer, useState} from "react";
import {
    DelayedGreeting,
    initialDelayedGreeting,
    Person,
    receivedDelayedGreeting,
    waitingDelayedGreeting
} from "../types";
import {Button, Form} from "react-bootstrap";
import {POST} from "../state/client";
import {greetingReducer, initialGreetingState} from "../state/reducers";

interface FormData {
    name: string
}

const initialFormData: FormData = {name: ""}

export interface GreetingFormProps {
    delayedGreeting: DelayedGreeting,
    setDelayedGreeting: React.Dispatch<React.SetStateAction<DelayedGreeting>>
}

export const GreetingForm: FC<GreetingFormProps> = (props: GreetingFormProps): ReactElement => {
    const {delayedGreeting, setDelayedGreeting} = props;
    const [greetingState, greetingDispatch] = useReducer(greetingReducer, initialGreetingState);
    const [formData, setFormData] = useState(initialFormData)

    useEffect(() => {
        console.log("Form", delayedGreeting)
        if (delayedGreeting.waiting && !greetingState.loading) {
            console.log("Received", greetingState)
            setDelayedGreeting({...receivedDelayedGreeting, message: "Hello!"})
        }
    }, [delayedGreeting, setDelayedGreeting, greetingState]);

    const onChange = useCallback((e: ChangeEvent<HTMLInputElement>) => {
        console.log("onChange", delayedGreeting);
        if (delayedGreeting.received) {
            setDelayedGreeting(initialDelayedGreeting)
        }
        setFormData({name: e.target.value});
    }, [delayedGreeting, setDelayedGreeting]);

    const onSubmit = useCallback((e: FormEvent<HTMLFormElement>) => {
        console.log("onSubmit", delayedGreeting)
        e.preventDefault();
        setFormData(initialFormData)
        greetingDispatch({status: 'LOADING'})
        POST<void, Person>("/api/greetings", {name: formData.name})
            .then(() => {
                setDelayedGreeting(waitingDelayedGreeting)
                greetingDispatch({status: 'SUCCESS'})
            })
            .catch(error => {
                console.log("ERROR", error)
                greetingDispatch({status: 'FAILED'})
            });
    }, [delayedGreeting, setDelayedGreeting, greetingDispatch, formData, setFormData]);

    return (
        <Form onSubmit={onSubmit}>
            <Form.Group controlId="nameInput">
                <Form.Control type="text" size="lg" placeholder="Enter your name here"
                              value={formData.name}
                              onChange={onChange}/>
            </Form.Group>
            <div className="mt-3 d-grid d-flex justify-content-end">
                <Button type="submit" variant="primary"
                        disabled={delayedGreeting.waiting || formData.name.trim().length < 1}>Submit</Button>
            </div>
        </Form>
    );
}
