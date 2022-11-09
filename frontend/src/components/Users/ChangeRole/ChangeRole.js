import React, {useEffect} from 'react';
import {useParams} from "react-router-dom";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const ChangeRole = (props) => {

    const {username} = useParams();

    const [formData, updateFormData] = React.useState({
        roles: [],
        role: "",
        activeUser: ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    useEffect(() => {
        PerformanceReviewRepository.getActiveUser()
            .then((data) => {
                formData.activeUser = data.data.username
            })
        formData.roles = ["ROLE_USER", "ROLE_ADMIN"]
    }, [])

    const onFormSubmit = (e) => {
        e.preventDefault();

        let role = "";

        if(formData.role === ""){
            role = formData.roles[0];
        }else{
            role = formData.role;
        }

        PerformanceReviewRepository.changeRole(username, role)
            .then(() => {
                window.open("/users","_self")
            })
    }

    let change = `Change Role for User with username ${username}`

    if(formData.activeUser === null){
        window.open("/login", "_self")
    }
    else if(formData.role === "ROLE_USER"){
        window.open("/tasks", "_self")
    }
    else {

        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-5">

                        <label><b>{change}</b></label>
                        <form onSubmit={onFormSubmit}>

                            <select name="role" className="form-control" onChange={handleChange}>
                                {formData.roles.map((newRole) =>
                                    <option value={newRole.toString()}>{newRole.toString()}</option>
                                )}
                            </select>

                            <button type="submit" className="btn btn-success">Submit</button>
                        </form>
                        <a href={'/users'} className={"btn btn-primary"}>Back</a>

                    </div>
                </div>
            </div>
        );
    }
}

export default ChangeRole;