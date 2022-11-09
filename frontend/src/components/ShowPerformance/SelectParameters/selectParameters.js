import React, {useEffect} from "react";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const SelectParameters = (props) => {

    const [formData, updateFormData] = React.useState({
        chosenUsername: "",
        chosenType: "",
        dateFrom: "",
        dateTo: "",
        users: [],
        values: [],
        activeUser: "",
        role: ""
    })

    useEffect(() => {
        PerformanceReviewRepository.getActiveUser()
            .then((data) => {
                formData.activeUser = data.data.username
                formData.role = data.data.role
            }).catch((error) => {
                console.log(error)
        })
        formData.values = ["Weekly", "Monthly", "Yearly"]
    }, [])

    const handleChange = (e) => {

        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        let chosenUsername = "";

        if(formData.chosenUsername === "" || props.users.length === 1){
            chosenUsername = props.users[0].username;
        }else{
            chosenUsername = formData.chosenUsername;
        }

        let chosenType = "";

        if(formData.chosenType === ""){
            chosenType = formData.values[0];
        }else{
            chosenType = formData.chosenType;
        }

        const dateFrom = formData.dateFrom;
        const dateTo = formData.dateTo;

        window.open(`/showPerformance?chosenUsername=${chosenUsername}&chosenType=${chosenType}&dateFrom=${dateFrom}&dateTo=${dateTo}`,"_self")
    }

    if(formData.activeUser === null){
        window.open("/login", "_self")
    }
    else if(formData.role === "ROLE_USER"){
        window.open("/tasks", "_self")
    }
    else {
        return (

            <div>
                <form onSubmit={onFormSubmit}>
                    <h1>View Performance for User:</h1>

                    <select name="chosenUsername" className="form-control" onChange={handleChange}>
                        {props.users.map((term_user) =>
                            <option value={term_user.username}>{term_user.username}</option>
                        )};
                    </select>

                    <h1>And Type</h1>
                    <select name="chosenType" className="form-control" onChange={handleChange}>
                        {formData.values.map((value) =>
                            <option value={value.toString()}>{value.toString()}</option>
                        )}
                    </select>

                    <h1>If your type is Weekly choose date from and date to</h1>

                    <label htmlFor="dateFrom">Date From</label>
                    <input id="dateFrom" name="dateFrom" type="date" onChange={handleChange}/>

                    <label htmlFor="dateTo">Date To</label>
                    <input id="dateTo" name="dateTo" type="date" onChange={handleChange}/>

                    <button type="submit" className={"btn btn-success"}>Submit</button>

                </form>

                <a type="button" className="btn btn-primary" href={"/tasks"}>Back</a>
            </div>
        );
    }

}

export default SelectParameters;