import React from 'react';

const TaskTerm = (props) => {

    let commentItems;

    if (props.term.comments.length === 0 || props.term.comments.length === 1) {
        commentItems = <tr>No Comments</tr>
    } else {

        commentItems = props.term.comments.map((findComment) => {
            return <tr>
                <td>{findComment.comment}</td>
            </tr>
        })
    }

    let assigneesItems;

    if (props.term.assignees.length === 0 || props.term.assignees.length === 1) {
        assigneesItems = <tr>{props.term.assignees.toString()}</tr>
    } else {

        assigneesItems = props.term.assignees.map((assignee) => {
            return <tr>
                <td>{assignee}</td>
            </tr>
        })
    }

    let dateToStart = props.term.startDate.replace("T", " ");
    let dateToFinish = props.term.dueDate.replace("T", " ");

    return (
        <tr>
            <td scope={"col"}>{props.term.title}</td>
            <td scope={"col"}>{props.term.description}</td>
            <td scope={"col"}>{props.term.creator}</td>
            <td scope={"col"}>{props.term.status.toString()}</td>
            <td scope={"col"}>{dateToStart}</td>
            <td scope={"col"}>{dateToFinish}</td>
            <td scope={"col"}>{props.term.estimationDays}</td>
            <td scope={"col"}>{props.term.status}</td>
            <td scope={"col"}>{assigneesItems}</td>
            <td scope={"col"}>{commentItems}</td>
            <td scope={"col"}>
                <a title={"Edit"} className={"btn btn-primary"}
                   href={`/tasks/edit/${props.term.id}`}>
                    Edit
                </a>
            </td>
            <td scope={"col"}>
                <a title={"Delete"} className={"btn btn-danger"}
                   onClick={() => props.onDelete(props.term.id)}>
                    Delete
                </a>
            </td>
            <td scope={"col"}>
                <a title={"View Task"} className={"btn btn-secondary"}
                   href={`/tasks/taskInfo/${props.term.id}`}>
                    {/*onClick={() => props.onView(props.term.id)}*/}
                    View Task
                </a>
            </td>
        </tr>
    );
}

export default TaskTerm;