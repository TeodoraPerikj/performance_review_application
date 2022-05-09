import React, {useEffect} from 'react';
import {useParams} from "react-router";
import PerformanceReviewRepository from "../../repository/performanceReviewRepository";

const MyTasks = (props) => {

    const {username} = useParams();

    const [formData, updateFormData] = React.useState({
        myToDoTasks : [],
        myDoneTasks : [],
        myCanceledTasks : [],
        myInProgressTasks : [],
        ownedTasks : [],
        TODOTasks : 0,
        openTasks : 0,
        doneTasks : 0,
        canceledTasks : 0
    });

    useEffect(() => {

        PerformanceReviewRepository.getUserByUsername(username)
            .then((data) => {
                updateFormData({
                    myToDoTasks: data.data.myToDoTasks,
                    myDoneTasks: data.data.myDoneTasks,
                    myCanceledTasks: data.data.myCanceledTasks,
                    myInProgressTasks: data.data.myInProgressTasks,
                    ownedTasks: data.data.ownedTasks,
                    TODOTasks: data.data.numberOfTasks[0],
                    openTasks: data.data.numberOfTasks[1],
                    doneTasks: data.data.numberOfTasks[2],
                    canceledTasks: data.data.numberOfTasks[3]
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

    return (
        <div>
        <h3>Info</h3>

    <div className="container">
        <div className="row">
            <div className="col-md-5">
                <div className="form-group">
                    <label htmlFor="TODOTasks"><b>Number of Tasks To Do:</b></label>
                    <span className="form-control"
                          id="TODOTasks">
                        {formData.TODOTasks}
                    </span>
                </div>

                <div className="form-group">
                    <label htmlFor="openTasks"><b>Number of Open Tasks:</b></label>
                    <span className="form-control"
                          id="openTasks">
                        {formData.openTasks}
                    </span>
                </div>

                <div className="form-group">
                    <label htmlFor="doneTasks"><b>Number of Done Tasks:</b></label>
                    <span className="form-control"
                          id="doneTasks">
                        {formData.doneTasks}
                    </span>
                </div>

                <div className="form-group">
                    <label htmlFor="canceledTasks"><b>Number of Canceled Tasks:</b></label>
                    <span className="form-control"
                          id="canceledTasks">
                        {formData.canceledTasks}
                    </span>
                </div>

            </div>
        </div>
    </div>

    <h3>List My Tasks</h3>

    <div className="container mb-4">
        <div className="row">
            <h3>Assigned Tasks</h3>
            <div className="table-responsive">
                <table>
                    <thead>
                    <tr>
                        <th>Task Title</th>
                        <th>Description</th>
                        {/*<th>Created By</th>*/}
                        <th>Start Date</th>
                        <th>Due Date</th>
                        <th>Estimation Days</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>

                    {formData.myToDoTasks.map((myToDoTask) => {
                       return (
                           <tr>

                            <td>{myToDoTask.title}</td>
                            <td>{myToDoTask.description}</td>
                            {/*<td>{myToDoTask.creator.username}</td>*/}
                            <td>{myToDoTask.startDate}</td>
                            <td>{myToDoTask.dueDate}</td>
                            <td>{myToDoTask.estimationDays}</td>
                            <td>{myToDoTask.status}</td>

                            <td scope={"col"}>
                                {/*<Link className={"btn btn-info"}*/}
                                {/*onClick={() => props.onEdit(term.id)}*/}
                                {/*to={`/tasks/edit-task/${term.id}`}>Edit</Link>*/}
                                <a title={"Edit"} className={"btn btn-primary"}
                                    // href={`/tasks/add/${props.term.id}`}>
                                   // onClick={() => props.onEdit(myToDoTask.id)}>
                                    href={`/tasks/edit/${myToDoTask.id}`}>
                                    Edit
                                </a>
                            </td>
                            <td scope={"col"}>
                                <a title={"Delete"} className={"btn btn-danger"}
                                   onClick={() => props.onDelete(myToDoTask.id)}>
                                    Delete
                                </a>
                            </td>

                        </tr>
                       );
                    })}

                    {formData.myInProgressTasks.map((myInProgressTask) => {
                        return (
                            <tr>

                            <td>{myInProgressTask.title}</td>
                            <td>{myInProgressTask.description}</td>
                            {/*<td>{myInProgressTask.creator.username}</td>*/}
                            <td>{myInProgressTask.startDate}</td>
                            <td>{myInProgressTask.dueDate}</td>
                            <td>{myInProgressTask.estimationDays}</td>
                            <td>{myInProgressTask.status}</td>

                            <td scope={"col"}>
                                {/*<Link className={"btn btn-info"}*/}
                                {/*onClick={() => props.onEdit(term.id)}*/}
                                {/*to={`/tasks/edit-task/${term.id}`}>Edit</Link>*/}
                                <a title={"Edit"} className={"btn btn-primary"}
                                    // href={`/tasks/add/${props.term.id}`}>
                                   // onClick={() => props.onEdit(myInProgressTask.id)}>
                                    href={`/tasks/edit/${myInProgressTask.id}`}>
                                    Edit
                                </a>
                            </td>
                            <td scope={"col"}>
                                <a title={"Delete"} className={"btn btn-danger"}
                                   onClick={() => props.onDelete(myInProgressTask.id)}>
                                    Delete
                                </a>
                            </td>

                        </tr>
                        );
                    })}

                    {formData.myDoneTasks.map((myDoneTask) => {
                        return (
                            <tr>

                            <td>{myDoneTask.title}</td>
                            <td>{myDoneTask.description}</td>
                            {/*<td>{myDoneTask.creator.username}</td>*/}
                            <td>{myDoneTask.startDate}</td>
                            <td>{myDoneTask.dueDate}</td>
                            <td>{myDoneTask.estimationDays}</td>
                            <td>{myDoneTask.status}</td>

                            <td scope={"col"}>
                                {/*<Link className={"btn btn-info"}*/}
                                {/*onClick={() => props.onEdit(term.id)}*/}
                                {/*to={`/tasks/edit-task/${term.id}`}>Edit</Link>*/}
                                <a title={"Edit"} className={"btn btn-primary"}
                                    // href={`/tasks/add/${props.term.id}`}>
                                   // onClick={() => props.onEdit(myDoneTask.id)}>
                                    href={`/tasks/edit/${myDoneTask.id}`}>
                                    Edit
                                </a>
                            </td>
                            <td scope={"col"}>
                                <a title={"Delete"} className={"btn btn-danger"}
                                   onClick={() => props.onDelete(myDoneTask.id)}>
                                    Delete
                                </a>
                            </td>

                        </tr>
                        );
                    })}

                    {formData.myCanceledTasks.map((myCancelTask) => {
                        return (
                            <tr>

                            <td>{myCancelTask.title}</td>
                            <td>{myCancelTask.description}</td>
                            {/*<td>{myCancelTask.creator.username}</td>*/}
                            <td>{myCancelTask.startDate}</td>
                            <td>{myCancelTask.dueDate}</td>
                            <td>{myCancelTask.estimationDays}</td>
                            <td>{myCancelTask.status}</td>

                            <td scope={"col"}>
                                {/*<Link className={"btn btn-info"}*/}
                                {/*onClick={() => props.onEdit(term.id)}*/}
                                {/*to={`/tasks/edit-task/${term.id}`}>Edit</Link>*/}
                                <a title={"Edit"} className={"btn btn-primary"}
                                    // href={`/tasks/add/${props.term.id}`}>
                                   // onClick={() => props.onEdit(myCancelTask.id)}>
                                    href={`/tasks/edit/${myCancelTask.id}`}>
                                    Edit
                                </a>
                            </td>
                            <td scope={"col"}>
                                <a title={"Delete"} className={"btn btn-danger"}
                                   onClick={() => props.onDelete(myCancelTask.id)}>
                                    Delete
                                </a>
                            </td>

                        </tr>
                        );
                    })}
                    </tbody>
                </table>
            </div>
            <div className="col mb-3">
                <div className="row">
                    <div className="col-sm-12 col-md-12">
                        <a href={"/tasks/add"} className="btn btn-block btn-dark">Add new task</a>
                    </div>
                </div>
            </div>

            <h3>Owned Tasks</h3>
            <div className="table-responsive">
                <table>
                    <thead>
                    <tr>
                        <th>Task Title</th>
                        <th>Description</th>
                        {/*<th>Created By</th>*/}
                        <th>Start Date</th>
                        <th>Due Date</th>
                        <th>Estimation Days</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>

                    {formData.ownedTasks.map((ownedTask) => {
                        return (
                            <tr>

                            <td>{ownedTask.title}</td>
                            <td>{ownedTask.description}</td>
                            {/*<td>{ownedTask.creator.username}</td>*/}
                            <td>{ownedTask.startDate}</td>
                            <td>{ownedTask.dueDate}</td>
                            <td>{ownedTask.estimationDays}</td>
                            <td>{ownedTask.status}</td>

                            <td scope={"col"}>
                                {/*<Link className={"btn btn-info"}*/}
                                {/*onClick={() => props.onEdit(term.id)}*/}
                                {/*to={`/tasks/edit-task/${term.id}`}>Edit</Link>*/}
                                <a title={"Edit"} className={"btn btn-primary"}
                                    // href={`/tasks/add/${props.term.id}`}>
                                   // onClick={() => props.onEdit(ownedTask.id)}>
                                    href={`/tasks/edit/${ownedTask.id}`}>
                                    Edit
                                </a>
                            </td>
                            <td scope={"col"}>
                                <a title={"Delete"} className={"btn btn-danger"}
                                   onClick={() => props.onDelete(ownedTask.id)}>
                                    Delete
                                </a>
                            </td>

                        </tr>
                        );
                    })}
                    </tbody>
                </table>
            </div>
        </div>
    </div>
        </div>
);
}

export default MyTasks;