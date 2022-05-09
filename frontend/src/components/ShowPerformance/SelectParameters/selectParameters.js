import React, {useEffect} from "react";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const SelectParameters = (params) => {

    const [formData, updateFormData] = React.useState({
        chosenUsername: "",
        chosenType: "",
        dateFrom: "",
        dateTo: "",
        users: [],
        values: []
    })

    useEffect(() => {
        PerformanceReviewRepository.fetchUsers()
            .then((data) => {
                formData.users = data.data
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

        const chosenUsername = formData.chosenUsername;
        const chosenType = formData.chosenType;
        const dateFrom = formData.dateFrom;
        const dateTo = formData.dateTo;

        // debugger;
        //
        // const dateFrom = from.replace("T", " ");
        // const dateTo = to.replace("T", " ");
        //
        // console.log(dateFrom)
        // console.log(dateTo)

        window.open(`/showPerformance?chosenUsername=${chosenUsername}&chosenType=${chosenType}&dateFrom=${dateFrom}&dateTo=${dateTo}`,"_self")
    }

return (

    <div>
        <form onSubmit={onFormSubmit}>
            <h1>View Performance for User:</h1>

            <select name="chosenUsername" className="form-control" onChange={handleChange}>
                {formData.users.map((term_user) =>
                    <option value={term_user.username}>{term_user.username}</option>
                )};

            </select>

            <h1>And Type</h1>
            <select name="chosenType" className="form-control" onChange={handleChange}>
                {formData.values.map((vrednost) =>
                    <option value={vrednost.toString()}>{vrednost.toString()}</option>
                )}
            </select>

            <h1>If your type is Weekly choose date from and date to</h1>

            <label htmlFor="dateFrom">Date From</label>
            <input id="dateFrom" name="dateFrom" type="date" onChange={handleChange}/>

            <label htmlFor="dateTo">Date To</label>
            <input id="dateTo" name="dateTo" type="date" onChange={handleChange}/>

            <button type="submit">Submit</button>

        </form>

        <a type="button" className="btn btn-primary" href={"/tasks"}>Back</a>
    </div>
);

}

export default SelectParameters;