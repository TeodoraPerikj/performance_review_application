import React, {useEffect} from 'react';
import {useHistory, useParams} from "react-router-dom";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const TaskEdit = (props) => {

    const history = useHistory();
    const {id} = useParams();

    const [formData, updateFormData] = React.useState({
        title: "",
        description: "",
        startDate: "",
        dueDate: "",
        estimationDays: "",
        assignees: ""

    });

    useEffect(() => {
        console.log(id);

        const selectedTask = PerformanceReviewRepository.getTask(id)
            .then((data) => {
                console.log(data)
                updateFormData({
                    title: data.data.title,
                    description: data.data.description,
                    startDate: data.data.startDate,
                    dueDate: data.data.dueDate,
                    estimationDays: data.data.estimationDays,
                    assignees: data.data.assignees
                })
            }).catch((error) =>{
                console.log(error)
            });

    }, [])

    const handleChange = (e) => {
        debugger;
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        debugger;
        const title = formData.title !== "" ? formData.title : props.task.title;
        const description = formData.description !== "" ? formData.description : props.task.description;
        const startDate = formData.startDate !== "" ? formData.startDate : props.task.startDate;
        const dueDate = formData.dueDate !== "" ? formData.dueDate : props.task.dueDate;
        const estimationDays = formData.estimationDays !== "" ? formData.estimationDays : props.task.estimationDays;
        const assignees = formData.assignees !== "" ? formData.assignees : props.task.assignees;

        props.onEditTask(props.task.id, title, description, startDate, dueDate, estimationDays, assignees);
        history.push("/tasks");
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-5">
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label htmlFor="title">Task Title</label>
                            <input type="text"
                                   className="form-control"
                                   id="title"
                                   name="title"
                                   required
                                   placeholder={formData.title}
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Description</label>
                            <input type="text"
                                   className="form-control"
                                   id="description"
                                   name="description"
                                   required
                                   placeholder={formData.description}
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="startDate">Start Date</label>
                            <input type="datetime-local"
                                   className="form-control"
                                   id="startDate"
                                   name="startDate"
                                   required
                                   placeholder={formData.startDate}
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="dueDate">Due Date</label>
                            <input type="datetime-local"
                                   className="form-control"
                                   id="dueDate"
                                   name="dueDate"
                                   required
                                   placeholder={formData.dueDate}
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="estimationDays">Estimation Days</label>
                            <input type="number"
                                   className="form-control"
                                   id="estimationDays"
                                   name="estimationDays"
                                   required
                                   placeholder={formData.estimationDays}
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label>Choose Assignees</label>
                            <select name="assignees" multiple="multiple"
                                    className="form-control" onChange={handleChange}>
                                {/*<option th:each="user : ${users}"*/}
                                {/*        th:value="${user?.getUsername()}"*/}
                                {/*        th:text="${user?.getUsername()}">*/}
                                {/*</option>*/}

                                {props.users.map((term_user) =>
                                    <option value={term_user.username}>{term_user.username}</option>
                                )};

                            </select>
                        </div>

                        <button type="submit" className="btn btn-primary">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default TaskEdit;