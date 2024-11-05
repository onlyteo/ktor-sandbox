import React, {FC, ReactElement, useEffect, useReducer} from "react";
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {Footer, Header} from "./fragments";
import {Home, NotFound} from "./pages";
import {State, User} from "./types";
import {GET} from "./state/client";
import {userInitialState, userReducer} from "./state/reducers";
import {Spinner} from "react-bootstrap";

interface LayoutProps {
    userState: State<User>
}

const Layout: FC<LayoutProps> = (props: LayoutProps): ReactElement => {
    const {loading, data: user} = props.userState

    if (loading) {
        console.log("LOADING")
        return <Spinner animation="border"/>
    } else {
        console.log("READY")
        return (
            <>
                <Header user={user}/>
                <Outlet/>
                <Footer/>
            </>
        );
    }
}

const App: FC = (): ReactElement => {
    const [userState, userDispatch] = useReducer(userReducer, userInitialState)

    useEffect(() => {
        GET<User>("/api/user")
            .then(response => userDispatch({status: 'SUCCESS', data: response.data}))
            .catch(error => {
                console.log("ERROR", error)
                userDispatch({status: 'FAILED'})
            })
    }, [])

    const router = createBrowserRouter([
        {
            element: <Layout userState={userState}/>,
            children: [
                {
                    path: "/",
                    element: <Home/>,
                },
                {
                    path: "*",
                    element: <NotFound/>,
                }
            ]
        }
    ])

    return <RouterProvider router={router}/>
}

export default App;