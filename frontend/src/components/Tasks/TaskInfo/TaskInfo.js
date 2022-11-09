import React, {useEffect} from 'react';
import PerformanceReviewRepository from '../../../repository/performanceReviewRepository';
import {useParams} from "react-router";

const TaskInfo = (props) => {

    const {id} = useParams();

    const [formData, updateFormData] = React.useState({
        title: "",
        description: "",
        startDate: "",
        dueDate: "",
        estimationDays: "",
        status: "",
        assignee: "",
        textAndType: "",
        href: "",
        activeUser: "",
        creator: "",
        role: ""
    });

    let dateToStart = formData.startDate.replace("T", " ");
    let dateToFinish = formData.dueDate.replace("T", " ");

    useEffect(() => {

        const selectedTask = PerformanceReviewRepository.viewTask(id)
            .then((data) => {

                updateFormData({
                    title: data.data.task.title,
                    description: data.data.task.description,
                    startDate: data.data.task.startDate,
                    dueDate: data.data.task.dueDate,
                    estimationDays: data.data.task.estimationDays,
                    status: data.data.task.status,
                    assignee: data.data.assignee,
                    textAndType: data.data.textAndType,
                    creator: data.data.creator,
                    activeUser: data.data.activeUser,
                    role: data.data.role
                })
            }).catch((error) => {
                console.log(error)
            });

    }, [])

    let button;

    if (formData.textAndType === 'Not opened yet!') {
        formData.href = `/workOnTask/${id}`;
        button = <button onClick={formData.href} disabled>
            Start Task
        </button>

    } else if (formData.textAndType === 'Task is closed!') {
        formData.href = `/workOnTask/${id}`;
        button = <button onClick={formData.href} disabled>
            Start Task
        </button>
    } else if (formData.textAndType === 'Start Task') {
        formData.href = `/workOnTask/${id}`;
        button = <form><a className={'btn btn-primary'} href={`/workOnTask/${id}`}>{'Start task'}</a></form>
    } else if (formData.textAndType === 'Join Task') {
        formData.href = `/workOnTask/${id}`;
        button = <form><a className={'btn btn-primary'} href={formData.href}>{formData.textAndType}</a></form>
    } else if (formData.textAndType === 'Continue Task') {
        formData.href = `/workOnTask/${id}`;
        button = <form><a className={'btn btn-primary'} href={formData.href}>{formData.textAndType}</a></form>
    } else if (formData.textAndType === 'Finished') {
        formData.href = `/workOnTask/${id}`;
        button = <button onClick={formData.href} disabled>
            Start Task
        </button>
    } else if (formData.textAndType === 'Task has been canceled') {
        formData.href = `/workOnTask/${id}`;
        button = <button onClick={formData.href} disabled>
            Start Task
        </button>
    }

    //let items;

    // if (formData.assignees.length === 0) {
    //    // formData.assignees.length === 1
    //     items = <li>{formData.assignees}</li>;
    // } else {
    //     items = formData.assignees.map((user) => {
    //         return <li>{user}</li>;
    //     })
    // }

    let content;

    if (formData.role === "ROLE_ADMIN" && formData.activeUser !== formData.creator
        && formData.activeUser !== formData.assignee){

        formData.href = `/workOnTask/${id}`;

        content = <div>
            <a href={formData.href} >
                View Task
            </a>
        </div>
    }
    else if(formData.activeUser === formData.creator || formData.activeUser === formData.assignee){

        content = <div><span>
                 {formData.textAndType}
             </span>
            {button}
        </div>
    }
    else {
        content = <div>
            <p className={"text-danger"}><b>You cannot work on this task because
                you are not the creator or not assigned to this task.</b></p>
        </div>
    }

    return (

        <div className="container">
            <div className="row">
                <div className="col-md-5">
                    <div className="form-group">
                        <label htmlFor="title"><b>Task Title:</b></label>
                        <span className="form-control"
                              id="title">{formData.title}</span>
                    </div>

                    <div className="form-group">
                        <label htmlFor="description"><b>Description:</b></label>
                        <span className="form-control"
                              id="description">{formData.description}</span>
                    </div>

                    <div className="form-group">
                        <label htmlFor="startDate"><b>Start Date:</b></label>
                        <span className="form-control"
                              id="startDate">{dateToStart}</span>
                    </div>
                    <div className="form-group">
                        <label htmlFor="dueDate"><b>Due Date:</b></label>
                        <span className="form-control"
                              id="dueDate">{dateToFinish}</span>
                    </div>
                    <div className="form-group">
                        <label htmlFor="estimationDays"><b>Estimation Days:</b></label>
                        <span className="form-control"
                              id="estimationDays">{formData.estimationDays}</span>
                    </div>
                    <div className="form-group">
                        <label htmlFor="status"><b>Status:</b></label>
                        <span className="form-control"
                              id="status">{formData.status.toString()}</span>
                    </div>

                    <div className="form-group">
                        <label htmlFor="creator"><b>Creator:</b></label>
                        <span className="form-control"
                              id="creator">{formData.creator}</span>
                    </div>

                    <div className="form-group">
                        <label><b>Assignees:</b></label>
                        <ul>
                            {/*{items}*/}
                            <li>{formData.assignee}</li>
                        </ul>
                    </div>

                </div>
            </div>
            <div className={'row'}>

                <form>
                <a href={'/tasks'} type="submit" className={'btn btn-primary'}>Back</a>
                </form>
            </div>

            {content}

        </div>
    );
}

export default TaskInfo;