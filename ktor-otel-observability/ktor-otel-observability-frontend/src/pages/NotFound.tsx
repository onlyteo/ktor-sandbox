import React, {FC, ReactElement} from "react";
import {Container} from "react-bootstrap";

export const NotFound: FC = (): ReactElement => {
    return (
        <Container>
            <div className="starter-template">
                <div className="jumbotron">
                    <h2>Not Found</h2>
                </div>
            </div>
        </Container>
    );
};
