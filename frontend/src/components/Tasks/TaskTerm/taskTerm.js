import React, {useEffect} from 'react';
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const TaskTerm = (props) => {

    // let commentItems;
    //
    // if (props.term.comments.length === 0) {
    //     commentItems = <tr>No Comments</tr>
    // } else {
    //
    //     commentItems = props.term.comments.map((findComment) => {
    //         return <tr>
    //             <td>{findComment.comment}</td>
    //         </tr>
    //     })
    // }

    // let assigneesItems;
    //
    // if (props.term.assignees.length === 0) {
    //     assigneesItems = <tr>{props.term.assignees.toString()}</tr>
    // } else {
    //
    //     assigneesItems = props.term.assignees.map((assignee) => {
    //         return <tr>
    //             <td>{assignee}</td>
    //         </tr>
    //     })
    // }
    const [formData, updateFormData] = React.useState({
        activeUser: "",
        role: ""
    });

    useEffect(() => {

        const getActiveUser = PerformanceReviewRepository.getActiveUser()
            .then((data) => {

                updateFormData({
                    activeUser: data.data.username,
                    role: data.data.role
                })
            }).catch((error) => {
                console.log(error)
            });

    }, [])


    let dateToStart = props.term.startDate.replace("T", " ");
    let dateToFinish = props.term.dueDate.replace("T", " ");

    const onFormSubmit = (e) => {
        e.preventDefault()

        PerformanceReviewRepository.deleteTask(props.term.id).then(() => {
            window.open("/tasks","_self")
        })
    }

    let canEditTask;

    if(formData.activeUser === props.term.creator){
        canEditTask = <td scope={"col"}>
            <a title={"Edit"} className={"btn btn-primary"} style={{width: '100%'}}
               href={`/tasks/edit/${props.term.id}`}>
                Edit
            </a>
        </td>
    }

    let canDeleteTask;

    if(formData.activeUser === props.term.creator || formData.role === "ROLE_ADMIN"){
        canDeleteTask = <td scope={"col"}>

            <form onSubmit={onFormSubmit}>

                <button type="submit" className="btn btn-danger" style={{width: '100%'}}>Delete</button>

            </form>
        </td>
    }

    let canViewTask;

    if(formData.activeUser !== null && formData.activeUser !== ""){
        canViewTask = <td scope={"col"}>
            <a title={"View Task"} className={"btn btn-secondary"} style={{width: '100%'}}
               href={`/tasks/taskInfo/${props.term.id}`}>
                View Task
            </a>
        </td>
    }

    return (
        <tr>
            <td scope={"col"}>{props.term.title}</td>
            <td scope={"col"}>{props.term.description}</td>
            <td scope={"col"}>{props.term.creator}</td>
            <td scope={"col"}>{dateToStart}</td>
            <td scope={"col"}>{dateToFinish}</td>
            <td scope={"col"}>{props.term.estimationDays}</td>
            <td scope={"col"}>{props.term.status}</td>
            <td scope={"col"}>{props.term.assignee}</td>
            {/*<td scope={"col"}>{commentItems}</td>*/}
            <td scope={"col"}>{props.term.comment}</td>
            {canEditTask}
            {canDeleteTask}
            {canViewTask}
        </tr>
    );
}

export default TaskTerm;