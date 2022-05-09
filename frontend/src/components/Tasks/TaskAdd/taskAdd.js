import React from 'react';
import {useHistory} from "react-router-dom";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const TaskAdd = (props) => {


    // Redirect
    const history = useHistory();

    const [formData, updateFormData] = React.useState({
        title: "",
        description: "",
        startDate: "",
        dueDate: "",
        estimationDays: "",
        assignees: "",
        creator: ""

    });

    const handleChange = (e) => {
      updateFormData({
          ...formData,
          [e.target.name]: e.target.value.trim()
      })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        debugger;

        const title = formData.title;
        const description = formData.description;
        const startDate = formData.startDate;
        const dueDate = formData.dueDate;
        const estimationDays = formData.estimationDays;
        const assignees = formData.assignees;
        const creator = formData.creator;

        //props.onAddTask(title, description, startDate, dueDate, estimationDays, assignees, creator);

        PerformanceReviewRepository.addTask(title, description, startDate, dueDate, estimationDays, assignees, creator)
            .then(() => {
                window.open("/tasks","_self")
            })
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
                                   placeholder="Enter task title"
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Description</label>
                            <input type="text"
                                   className="form-control"
                                   id="description"
                                   name="description"
                                   required
                                   placeholder="Description"
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="startDate">Start Date</label>
                            <input type="datetime-local"
                                   className="form-control"
                                   id="startDate"
                                   name="startDate"
                                   required
                                   placeholder="Start Date"
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="dueDate">Due Date</label>
                            <input type="datetime-local"
                                   className="form-control"
                                   id="dueDate"
                                   name="dueDate"
                                   required
                                   placeholder="Due Date"
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="estimationDays">Estimation Days</label>
                            <input type="text"
                                   className="form-control"
                                   id="estimationDays"
                                   name="estimationDays"
                                   required
                                   placeholder="Estimation Days"
                                   onChange={handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="creator">Creator</label>
                            <select name="creator" className="form-control" onChange={handleChange}>
                                {props.users.map((term) =>
                                    <option value={term.username}>{term.username}</option>
                                )}

                            </select>
                        </div>
                        <div className="form-group">
                            <label>Choose Assignees</label>
                            <select name="assignees" multiple
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
                        {/*/!*href={"/tasks"} onClick={onFormSubmit}*!/*/}
                        <button type="submit" className="btn btn-primary">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default TaskAdd;