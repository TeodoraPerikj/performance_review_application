import React from 'react';
import {useHistory} from "react-router-dom";

const TaskTerm = (props) => {

    return (
        <tr>
            <td scope={"col"}>{props.term.title}</td>
            <td scope={"col"}>{props.term.description}</td>
            {/*<td scope={"col"}>{props.term.creator.username}</td>*/}
            <td scope={"col"}>{props.term.status.toString()}</td>
            <td scope={"col"}>{props.term.startDate}</td>
            <td scope={"col"}>{props.term.dueDate}</td>
            <td scope={"col"}>{props.term.estimationDays}</td>
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
                   href={`/taskInfo/${props.term.id}`}
                   onClick={() => props.onView(props.term.id)}>
                    View Task
                </a>
            </td>
        </tr>
    );
}

export default TaskTerm;