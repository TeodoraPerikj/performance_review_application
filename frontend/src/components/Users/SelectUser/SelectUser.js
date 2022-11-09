import React, {useEffect} from 'react';
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const SelectUser = (props) => {

    const [formData, updateFormData] = React.useState({
        username : "",
        activeUser: "",
        role: ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    useEffect(() => {

        const getActiveUser = PerformanceReviewRepository.getActiveUser()
            .then((data) => {

                updateFormData({
                    activeUser: data.data.username,
                    role: data.data.role
                })
            }).catch((error) => {
                console.log(error)
            });

    }, [])

    const onFormSubmit = (e) => {
        e.preventDefault();

        let username = ""

        if(formData.role === "ROLE_ADMIN") {

            if (formData.username === "" || props.users.length === 1) {
                username = props.users[0].username
            } else {
                username = formData.username
            }
        }else {
            username = formData.activeUser
        }

        window.open(`/myTasks/${username}`, "_self")
    }

    let chooseUser;

    if(formData.role === "ROLE_ADMIN"){
        chooseUser = <div className="form-group">
            <label><b>Choose User</b></label>
            <select name="username"
                    className="form-control" onChange={handleChange}>
                {props.users.map((term) =>
                    <option value={term.username}>{term.username}</option>
                )}

            </select>
        </div>
    }else {
        chooseUser = <div className="form-group">
            <label><b>Active User is</b></label>
            <input type="text"
                   className="form-control"
                   id="activeUser"
                   name="activeUser"
                   placeholder={formData.activeUser}
                   disabled={true}/>
        </div>
    }

    if(formData.activeUser === null){
        window.open("/login", "_self")
    }
    else {

        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-5">
                        <form onSubmit={onFormSubmit}>
                            {chooseUser}
                            <button type="submit" className="btn btn-success">Submit</button>
                            <a href={'/tasks'} style={{marginLeft: "10px"}}
                               className={"btn btn-primary"}>Back</a>
                        </form>
                    </div>
                </div>
            </div>
        );
    }

}

export default SelectUser;