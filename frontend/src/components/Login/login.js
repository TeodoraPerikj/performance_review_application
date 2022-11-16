import React from 'react';
import PerformanceReviewRepository from "../../repository/performanceReviewRepository";

const Login = (props) => {

    const [formData, login] = React.useState({
        username : "",
        password : "",
        activeUser : ""

    });

    const change = (e) => {
        login({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {

        e.preventDefault();

        const username = formData.username
        const password = formData.password

        let element = document.getElementById("errorText")

        if(username === "" || password === ""){
            element.innerText = "Invalid Arguments Exception. All fields must be fulfilled!"
        }
        else {
            element.innerText = ""
            PerformanceReviewRepository.login(username, password)
                .then((data) => {
                    console.log(data.data)
                    formData.activeUser = data.data.username
                    window.open('/tasks', '_self')
                }).catch((error) => {
                    element.innerText = "Invalid User Credentials Exception. Your username or password is incorrect!"
                    console.log(error)
            });
        }
    }

    return (
        <div className="container">
            <div><span id={"errorText"} className={"text-danger"}></span></div>

            <form className="form-signin mt-xl-5" onSubmit={onFormSubmit}>
                <h2 className="form-signin-heading">Sign in</h2>
                <p>
                    <label htmlFor="username" className="sr-only">Username</label>
                    <input type="text" id="username" name="username" onChange={change}
                           className="form-control" placeholder="Username" required autoFocus=""/>
                </p>
                <p>
                    <label htmlFor="password" className="sr-only">Password</label>
                    <input type="password" id="password" name="password" onChange={change}
                           className="form-control" placeholder="Password" required/>
                </p>

                <button className="btn btn-primary" type="submit">Sign in</button>
            </form>
            <a href={"/register"} className="btn btn-light">Register here</a>
        </div>
    );
}
export default Login;