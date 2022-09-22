import React, {useEffect} from 'react';
import PerformanceReviewRepository from '../../../repository/performanceReviewRepository';
import {useHistory, useParams} from "react-router";

const TaskInfo = (props) => {

    const history = useHistory();
    const {id} = useParams();

    const [formData, updateFormData] = React.useState({
        title: "",
        description: "",
        startDate: "",
        dueDate: "",
        estimationDays: "",
        status: "",
        assignees: "",
        textAndType: "",
        href: "",
    });

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
                    assignees: data.data.assignedUsers,
                    textAndType: data.data.textAndType
                })
            }).catch((error) => {
                console.log(error)
            });

    }, [])

    const onFormSubmit = (e) => {
        e.preventDefault();

        history.push("/tasks");
    }

    const formStartTask = (e) => {
        e.preventDefault();

        history.push(`/workOnTask/${id}`)
    }

    const formJoinTask = (e) => {
        e.preventDefault();

        formData.assignees += 'user2';

        history.push(`/workOnTask/${id}`)
    }

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
        debugger
        formData.href = `/workOnTask/${id}`;
        button = <form onSubmit={formStartTask}>
            <a className={'btn btn-primary'} href={`/workOnTask/${id}`}>{'Start task'}</a>
        </form>
    } else if (formData.textAndType === 'Join Task') {
        formData.href = `/workOnTask/${id}`;
        button = <form onSubmit={formJoinTask}>
            <a className={'btn btn-primary'} href={formData.href}>{formData.textAndType}</a>
        </form>
    } else if (formData.textAndType === 'Continue Task') {
        formData.href = `/workOnTask/${id}`;
        button = <form onSubmit={formStartTask}>
            <a className={'btn btn-primary'} href={formData.href}>{formData.textAndType}</a>
        </form>
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

    let items;

    if (formData.assignees.length === 0) {
       // formData.assignees.length === 1
        items = <li>{formData.assignees}</li>;
    } else {
        items = formData.assignees.map((user) => {
            return <li>{user}</li>;
        })
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
                              id="startDate">{formData.startDate}</span>
                    </div>
                    <div className="form-group">
                        <label htmlFor="dueDate"><b>Due Date:</b></label>
                        <span className="form-control"
                              id="dueDate">{formData.dueDate}</span>
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
                    {/*<div className="form-group">*/}
                    {/*    <label htmlFor="creator"><b>Creator:</b></label>*/}
                    {/*    <span className="form-control"*/}
                    {/*          id="creator"*/}
                    {/*          th:text="${selectedTask.getCreator().getUsername()}"></span>*/}
                    {/*</div>*/}

                    <div className="form-group">
                        <label><b>Assignees:</b></label>
                        <ul>
                            {items}
                        </ul>
                    </div>

                </div>
            </div>
            <div className={'row'}>
                <form onSubmit={onFormSubmit}>
                    <a href={'/tasks'} type="submit" className={'btn btn-primary'}>Back</a>
                </form>
            </div>

            <div>
             <span>
                 {formData.textAndType}
             </span>
                {button}
            </div>

        </div>
    );
}

export default TaskInfo;