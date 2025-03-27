import React from "react";
import {createRoot} from 'react-dom/client';
import {ApolloProvider} from '@apollo/client';
import App from "./App";
import {apolloClient} from "./state/client";
import "bootstrap/dist/css/bootstrap.min.css";
import "./index.css";

const root = createRoot(document.getElementById("root"));

root.render(
        <React.StrictMode>
            <ApolloProvider client={apolloClient}>
                <App/>
            </ApolloProvider>
        </React.StrictMode>,
);
