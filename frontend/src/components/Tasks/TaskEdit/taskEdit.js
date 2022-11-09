import React, {useEffect} from 'react';
import {useParams} from "react-router-dom";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const TaskEdit = (props) => {

    const {id} = useParams();

    const [formData, updateFormData] = React.useState({
        title: "",
        description: "",
        startDate: "",
        dueDate: "",
        estimationDays: "",
        assignee: "",
        creator: "",
        activeUser: ""
    });

    useEffect(() => {

        debugger;
        const selectedTask = PerformanceReviewRepository.getTask(id)
            .then((data) => {
                updateFormData({
                    title: data.data.title,
                    description: data.data.description,
                    startDate: data.data.startDate,
                    dueDate: data.data.dueDate,
                    estimationDays: data.data.estimationDays,
                    assignee: data.data.assignee,
                    creator: data.data.creator,
                    activeUser: data.data.activeUser
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

    const onFormSubmit = (e) => {
        e.preventDefault();

        const title = formData.title !== "" ? formData.title : props.task.title;
        const description = formData.description !== "" ? formData.description : props.task.description;
        const startDate = formData.startDate !== "" ? formData.startDate : props.task.startDate;
        const dueDate = formData.dueDate !== "" ? formData.dueDate : props.task.dueDate;
        const estimationDays = formData.estimationDays !== "" ? formData.estimationDays : props.task.estimationDays;
        const assignee = formData.assignee;
        const creator = formData.creator;

        let element = document.getElementById("errorText")

        if(title === "" || description === "" || startDate === "" || dueDate === "" || estimationDays === ""
            || assignee === "" || creator === ""){
            element.innerText = "Invalid Arguments Exception. All fields must be fulfilled!"
        } else {
            PerformanceReviewRepository.editTask(id, title, description, startDate, dueDate, estimationDays, assignee, creator)
                .then(() => {
                    window.open("/tasks", "_self")
                }).catch((error) => {

                    if (error.toString().startsWith("Error: Request failed with status code 400")) {
                        element.innerText = "Estimation Days are greater than the days to work on a task!"
                        console.log(error)
                    } else if(error.toString().startsWith("Error: Request failed with status code 404")) {
                        element.innerText = "Start Date cannot be after Due Date!"
                        console.log(error)
                    } else {
                        element.innerText = "You should select assignee!"
                    }
                }
            )
        }
    }

    if (formData.activeUser === formData.creator) {

        return (
            <div className="container">
                <div><span id={"errorText"} className={"text-danger"}></span></div>
                <div className="row">
                    <div className="col-md-5">
                        <form onSubmit={onFormSubmit}>
                            <div className="form-group">
                                <label htmlFor="title">Task Title</label>
                                <input type="text"
                                       className="form-control"
                                       id="title"
                                       name="title"
                                       placeholder={formData.title}
                                       onChange={handleChange}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="description">Description</label>
                                <input type="text"
                                       className="form-control"
                                       id="description"
                                       name="description"
                                       placeholder={formData.description}
                                       onChange={handleChange}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="startDate">Start Date</label>
                                <input type="datetime-local"
                                       className="form-control"
                                       id="startDate"
                                       name="startDate"
                                       value={formData.startDate}
                                       onChange={handleChange}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="dueDate">Due Date</label>
                                <input type="datetime-local"
                                       className="form-control"
                                       id="dueDate"
                                       name="dueDate"
                                       value={formData.dueDate}
                                       onChange={handleChange}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="estimationDays">Estimation Days</label>
                                <input type="number"
                                       className="form-control"
                                       id="estimationDays"
                                       name="estimationDays"
                                       placeholder={formData.estimationDays}
                                       onChange={handleChange}/>
                            </div>
                            <div className="form-group">
                                <label>Choose Assignees</label>
                                <select name="assignee" value={formData.assignee}
                                        className="form-control" onChange={handleChange}>
                                    {props.users.map((term_user) =>
                                        <option value={term_user.username}>{term_user.username}</option>
                                    )};

                                </select>
                            </div>

                            <button type="submit" className="btn btn-primary">Submit</button>
                        </form>

                        <a href={'/tasks'} className={"btn btn-danger"}>Back</a>
                    </div>
                </div>
            </div>
        );
    }
    else{
        window.open("/tasks", "_self")
    }
}

export default TaskEdit;