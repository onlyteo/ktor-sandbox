import React, {FC, ReactElement} from "react";
import {Container} from "react-bootstrap";

export const Home: FC = (): ReactElement => {
    return (
        <Container>
            <div className="description-box px-3 py-5 rounded-3">
                <h2 className="emphasized-text">Welcome to this Ktor example!</h2>
                <p>This example shows a Kafka Streams event driven architecture</p>
            </div>
        </Container>
    );
};
