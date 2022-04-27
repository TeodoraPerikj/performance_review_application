import React from 'react';
import {useHistory} from "react-router-dom";
import PerformanceReviewService from "../../repository/performanceReviewRepository";
import PerformanceReviewRepository from "../../repository/performanceReviewRepository";

const Login = (props) => {

    const history = useHistory();

    const [formData, login] = React.useState({
        username : "",
        password : ""

    });

    const change = (e) => {
        login({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        debugger;
        e.preventDefault();

        PerformanceReviewRepository.login(formData.username, formData.password).then(resp => {
            localStorage.setItem("JWT", resp.data);
            props.onLogin()
            history.push("/tasks");
        })

    }

    return (
        <div className="container">
            <form className="form-signin mt-xl-5" onSubmit={onFormSubmit}>
                <h2 className="form-signin-heading">Sign in</h2>
                <p>
                    <label htmlFor="username" className="sr-only">Username</label>
                    <input type="text" id="username" name="username" onChange={change}
                           className="form-control" placeholder="Username" required="" autoFocus=""/>
                </p>
                <p>
                    <label htmlFor="password" className="sr-only">Password</label>
                    <input type="password" id="password" name="password" onChange={change}
                           className="form-control" placeholder="Password" required=""/>
                </p>

                <button className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
            <a href={"/register"} className="btn btn-block btn-light">Register here</a>
        </div>
    );
}
export default Login;