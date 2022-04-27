import React from 'react';

const myTasks = (props) => {

    return (
        <div>
        <h1>Info</h1>

    {/*<div className="container">*/}
    {/*    <div className="row">*/}
    {/*        <div className="col-md-5">*/}
    {/*            <div className="form-group">*/}
    {/*                <label htmlFor="TODOTasks"><b>Number of Tasks To Do:</b></label>*/}
    {/*                <span className="form-control"*/}
    {/*                      id="TODOTasks">*/}
    {/*                    {props.numberOfTasks.get(0)}*/}
    {/*                </span>*/}
    {/*            </div>*/}

    {/*            <div className="form-group">*/}
    {/*                <label htmlFor="openTasks"><b>Number of Open Tasks:</b></label>*/}
    {/*                <span className="form-control"*/}
    {/*                      id="openTasks">{props.numberOfTasks.get(1)}</span>*/}
    {/*            </div>*/}

    {/*            <div className="form-group">*/}
    {/*                <label htmlFor="doneTasks"><b>Number of Done Tasks:</b></label>*/}
    {/*                <span className="form-control"*/}
    {/*                      id="doneTasks">{props.numberOfTasks.get(2)}</span>*/}
    {/*            </div>*/}

    {/*            <div className="form-group">*/}
    {/*                <label htmlFor="canceledTasks"><b>Number of Canceled Tasks:</b></label>*/}
    {/*                <span className="form-control"*/}
    {/*                      id="canceledTasks">{props.numberOfTasks.get(3)}</span>*/}
    {/*            </div>*/}

    {/*        </div>*/}
    {/*    </div>*/}
    {/*</div>*/}

    <h1>List My Tasks</h1>

    <div className="container mb-4">
        <div className="row">
            <h1>Assigned Tasks</h1>
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

                    {props.userInfoForTask.myToDoTasks.map((myToDoTask) => {
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
                                   onClick={() => props.onEdit(myToDoTask.id)}>
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

                    {props.userInfoForTask.myInProgressTasks.map((myInProgressTask) => {
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
                                   onClick={() => props.onEdit(myInProgressTask.id)}>
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

                    {props.userInfoForTask.myDoneTasks.map((myDoneTask) => {
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
                                   onClick={() => props.onEdit(myDoneTask.id)}>
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

                    {props.userInfoForTask.myCanceledTasks.map((myCancelTask) => {
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
                                   onClick={() => props.onEdit(myCancelTask.id)}>
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

            <h1>Owned Tasks</h1>
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

                    {props.userInfoForTask.ownedTasks.map((ownedTask) => {
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
                                   onClick={() => props.onEdit(ownedTask.id)}>
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

export default myTasks;