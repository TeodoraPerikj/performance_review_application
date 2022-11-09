import React from 'react';
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const RegisterUser = (props) => {

    const [formData, updateFormData] = React.useState({
        name : "",
        surname : "",
        username : "",
        password : "",
        repeatedPassword : ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const name = formData.name;
        const surname = formData.surname;
        const username = formData.username;
        const password = formData.password;
        const repeatedPassword = formData.repeatedPassword;

        let element = document.getElementById("errorText")

        if(name === "" || surname === "" || username === "" || password === "" || repeatedPassword === ""){
            element.innerText = "Invalid Arguments Exception. All fields must be fulfilled!"
        }
        else {
            element.innerText = ""

            PerformanceReviewRepository.registerUser(name, surname, username, password, repeatedPassword)
                .then(() => {
                    window.open("/login", "_self")
                }).catch((error) => {

                if (error.toString().startsWith("Error: Request failed with status code 400")) {
                    element.innerText = "Passwords do not match!"
                    console.log(error)
                }
                else {
                    element.innerText = "Username Already Exists!"
                    console.log(error)
                }
            })
        }
    }

    return (
        <div className="container">
            <div><span id={"errorText"} className={"text-danger"}></span></div>

        <form onSubmit={onFormSubmit}>
            <h2>Register</h2>
            <p>
                <label htmlFor="username" className="sr-only">Username</label>
                <input type="text" id="username" name="username" onChange={handleChange}
                       className="form-control" placeholder="Username" required autoFocus=""/>
            </p>
            <p>
                <label htmlFor="password" className="sr-only">Password</label>
                <input type="password" id="password" name="password" onChange={handleChange}
                       className="form-control" placeholder="Password" required/>
            </p>
            <p>
                <label htmlFor="repeatedPassword" className="sr-only">Repeat Password</label>
                <input type="password" id="repeatedPassword" name="repeatedPassword" onChange={handleChange}
                       className="form-control" placeholder="Repeat Password" required/>
            </p>
            <p>
                <label htmlFor="name" className="sr-only">Name</label>
                <input type="text" id="name" name="name" onChange={handleChange}
                       className="form-control" placeholder="Name" required autoFocus=""/>
            </p>
            <p>
                <label htmlFor="surname" className="sr-only">Surname</label>
                <input type="text" id="surname" name="surname" onChange={handleChange}
                       className="form-control" placeholder="Surname" required autoFocus=""/>
            </p>
            <button className="btn btn-primary btn-block" type="submit">Sign up</button>
        </form>

        <a href={'/login'} className="btn btn-block btn-light">Already have an account? Login here!</a>
    </div>
)
}

export default RegisterUser;