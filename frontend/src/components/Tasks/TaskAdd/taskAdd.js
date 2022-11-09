import React, {useEffect} from 'react';
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const TaskAdd = (props) => {

    const [formData, updateFormData] = React.useState({
        title: "",
        description: "",
        startDate: "",
        dueDate: "",
        estimationDays: "",
        assignee: "",
        creator: "",
        activeUser: {}
    });

    useEffect(() => {

        const getActiveUser = PerformanceReviewRepository.getActiveUser()
            .then((data) => {

                updateFormData({
                    activeUser: data.data
                })
            }).catch((error) => {
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

        const title = formData.title;
        const description = formData.description;
        const startDate = formData.startDate;
        const dueDate = formData.dueDate;
        const estimationDays = formData.estimationDays;

        let assignee;

        if(props.users.length === 1){
            assignee = props.users[0].username
        }
        else {
            assignee = formData.assignee;
        }

        const creator = formData.activeUser.username;

        let element = document.getElementById("errorText")

        if(title === "" || description === "" || startDate === "" || dueDate === "" || estimationDays === ""
        || assignee === "" || creator === ""){
            element.innerText = "Invalid Arguments Exception. All fields must be fulfilled!"
        }
        else {
            element.innerText = ""
            PerformanceReviewRepository.addTask(title, description, startDate, dueDate, estimationDays, assignee, creator)
                .then(() => {
                    window.open("/tasks", "_self")
                }).catch((error) => {

                    if (error.toString().startsWith("Error: Request failed with status code 400")) {
                        element.innerText = "Estimation Days are greater than the days to work on a task!"
                        console.log(error)
                    } else if(error.toString().startsWith("Error: Request failed with status code 404")) {
                        element.innerText = "Start Date cannot be after Due Date!"
                        console.log(error)
                    }
                    else {
                        element.innerText = "You should select assignee!"
                    }
                }
            )
        }
    }

    if(formData.activeUser.ownedTask){
        window.open("/tasks", "_self")
    }
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
                            <input type="text"
                                   className="form-control"
                                   id="creator"
                                   name="creator"
                                   placeholder={formData.activeUser.username}
                                   disabled={true}/>
                        </div>
                        <div className="form-group">
                            <label>Choose Assignees</label>

                            <select name="assignee"
                                    className="form-control" onChange={handleChange}>

                                {props.users.map((term) =>
                                <option value={term.username}>{term.username}</option>
                                )}

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

export default TaskAdd;