import React from 'react';
import {useHistory} from "react-router-dom";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const SelectUser = (props) => {

    const history = useHistory();

    const [formData, updateFormData] = React.useState({
        username : ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        debugger;
        e.preventDefault();

        const username = formData.username;
        //const username = "user1";
        console.log(username)

        window.open(`/myTasks/${username}`, "_self")

        // PerformanceReviewRepository.getUserByUsername(username)
        //     .then((data) => {
        //         window.open(`/myTasks/${username}`, "_self")
        //     })

        // props.onSelect(username);
        // history.push("/myTasks");
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-5">
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label>Choose User</label>
                            <select name="username"
                                    className="form-control" onChange={handleChange}>
                                {/*<option th:each="user : ${users}"*/}
                                {/*        th:value="${user?.getUsername()}"*/}
                                {/*        th:text="${user?.getUsername()}">*/}
                                {/*</option>*/}

                                {props.users.map((term) =>
                                    <option value={term.username}>{term.username}</option>
                                )}

                            </select>
                        </div>

                        <button type="submit" className="btn btn-success">Submit</button>
                    </form>
                    <a href={'/tasks'} className={"btn btn-primary"}>Back</a>
                </div>
            </div>
        </div>
    );

}

export default SelectUser;