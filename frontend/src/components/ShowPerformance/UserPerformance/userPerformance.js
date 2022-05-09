import React, {useEffect} from "react";
import {useParams} from "react-router";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const UserPerformance = (props) => {

    const parameters = new URLSearchParams(window.location.search)

    console.log(parameters)

    const chosenUsername = parameters.get("chosenUsername")
    const chosenType = parameters.get("chosenType")
    const dateFrom = parameters.get("dateFrom")
    const dateTo = parameters.get("dateTo")

    console.log(chosenUsername)
    console.log(chosenType)
    console.log(dateFrom)
    console.log(dateTo)

    const [formData, updateFormData] = React.useState({
        user: "",
        TODOTasks: "",
        openTasks: "",
        doneTasks: "",
        canceledTasks: "",
        calculatedPerformance: 0.0,
        type: "",
        rate: ""
    });

    useEffect(() => {

        debugger;
        console.log(chosenUsername)
        PerformanceReviewRepository.showUserPerformance(chosenUsername, chosenType, dateFrom, dateTo)
            .then((data) => {
                updateFormData({
                    user: data.data.user.username,
                    TODOTasks: data.data.numberOfTasks[0],
                    openTasks: data.data.numberOfTasks[1],
                    doneTasks: data.data.numberOfTasks[2],
                    canceledTasks: data.data.numberOfTasks[3],
                    calculatedPerformance: data.data.calculatedPerformance,
                    type: data.data.type,
                    rate: data.data.calculatedPerformanceAndRating[0]
                })
            }).catch((error) =>{
                console.log(error)
            });

    }, [])

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    // const onFormSubmit = (e) => {
    //     e.preventDefault();
    //     debugger;
    //     const title = formData.title !== "" ? formData.title : props.task.title;
    //     const description = formData.description !== "" ? formData.description : props.task.description;
    //     const startDate = formData.startDate !== "" ? formData.startDate : props.task.startDate;
    //     const dueDate = formData.dueDate !== "" ? formData.dueDate : props.task.dueDate;
    //     const estimationDays = formData.estimationDays !== "" ? formData.estimationDays : props.task.estimationDays;
    //     const assignees = formData.assignees !== "" ? formData.assignees : props.task.assignees;
    //     const creator = formData.creator;
    //
    //     PerformanceReviewRepository.editTask(id, title, description, startDate, dueDate, estimationDays, assignees, creator)
    //         .then(() => {
    //             // history.push("/tasks")
    //             window.open("/tasks","_self")
    //         })
    // }

    let actualCalculatedPerformance;

    if (formData.rate === "Do not have assigned tasks to see performance") {
        actualCalculatedPerformance = "0.0";
    }else {
        actualCalculatedPerformance = formData.calculatedPerformance;
    }

    return (
        <div>
                <div>
                    <h1>{formData.type}</h1>

                    <div className="container">
                        <div className="row">
                            <div className="col-md-5">
                                <div className="form-group">
                                    <label htmlFor="userTODOTasks"><b>Number of Tasks To Do:</b></label>
                                    <span className="form-control"
                                          id="TODOTasks">
                                        {formData.TODOTasks}
                                    </span>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="userOpenTasks"><b>Number of Open Tasks:</b></label>
                                    <span className="form-control"
                                          id="openTasks">
                                        {formData.openTasks}
                                    </span>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="userDoneTasks"><b>Number of Done Tasks:</b></label>
                                    <span className="form-control"
                                          id="doneTasks">
                                        {formData.doneTasks}
                                    </span>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="userCanceledTasks"><b>Number of Canceled Tasks:</b></label>
                                    <span className="form-control"
                                          id="canceledTasks">
                                        {formData.canceledTasks}
                                    </span>
                                </div>

                            </div>
                        </div>
                    </div>

                    <h3>
                        The performance of the user is calculated by the number of tasks that
                        he has completed divided by the total number of tasks assigned.
                    </h3>

                    <h3>With this percentage of completed tasks</h3>
                    <h3>{actualCalculatedPerformance}</h3>
                    <h3>he is rated as</h3>
                    <h3>{formData.rate}</h3>

                </div>

            <a type="button" className="btn btn-primary" href={"/selectParameters"}>Back</a>

        </div>
    );

}

export default UserPerformance;