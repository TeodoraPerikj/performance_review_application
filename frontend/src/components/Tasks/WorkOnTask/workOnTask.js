import React, {useEffect} from "react";
import {useHistory, useParams} from "react-router";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

const WorkOnTask = (props) => {

    const history = useHistory();
    const {id} = useParams();
    let commentItems;

    const [formData, updateFormData] = React.useState({
        title: "",
        description: "",
        startDate: "",
        dueDate: "",
        estimationDays: "",
        status: "",
        assignees: "",
        comments: "",
        comment: ""
    });

    useEffect(() => {

        PerformanceReviewRepository.workOnTask(id)
            .then((data) => {
                updateFormData({
                    title: data.data.task.title,
                    description: data.data.task.description,
                    startDate: data.data.task.startDate,
                    dueDate: data.data.task.dueDate,
                    estimationDays: data.data.task.estimationDays,
                    status: data.data.task.status,
                    assignees: data.data.assignedUsers,
                    comments: data.data.comments
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

    const leaveComment = (e) => {
        e.preventDefault();
        debugger;
        const comment = formData.comment;
        console.log(formData.comments)

        PerformanceReviewRepository.leaveComment(id, 'user2', comment)
            .then(() => {

                const newList = formData.comments.concat(comment)
                console.log(newList)

                formData.comments = newList;

                window.open(`/workOnTask/${id}`,"_self")

                // commentItems = newList.map((comment1) => {
                //     return <tr>{comment1.toString()}</tr>
                // })
                //
                // console.log(commentItems);
                //
                // history.push(`/workOnTask/${id}`);
            })

    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        history.push(`/tasks/taskInfo/${id}`);
    }

    const markAsDone = (e) => {
        e.preventDefault();

        PerformanceReviewRepository.finishTask(id).then(() => {
            window.open("/tasks","_self")
        })
    }

    const cancelTask = (e) => {
        e.preventDefault();

        PerformanceReviewRepository.cancelTask(id).then(() => {
            window.open("/tasks","_self")
        })
    }

    let items;

    if (formData.assignees.length === 0 || formData.assignees.length === 1) {
        items = <li>{formData.assignees}</li>;
    } else {
        items = formData.assignees.map((user) => {
            return <li>{user}</li>;
        })
    }


    if (formData.comments.length === 0) {
        commentItems = <tr>{formData.comments.toString()}</tr>
    } else {

        commentItems = formData.comments.map((findComment) => {
            return <tr>
                <td>{findComment.username}</td>
                <td>{findComment.dateTime}</td>
                <td>{findComment.comment}</td>
                <td> <a title={"Edit"} className={"btn btn-primary"}
                        href={`/comments/edit/${findComment.id}?taskId=${id}&comment=${findComment.comment}`}>
                    Edit
                </a>
                </td>
                <td>
                    <a title={"Delete"} className={"btn btn-danger"}
                       onClick={() => props.onDeleteComment(findComment.id)}>
                        Delete
                    </a>
                </td>
            </tr>
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
                        <label htmlFor="days"><b>Estimation Days:</b></label>
                        <span className="form-control"
                              id="days">{formData.estimationDays}</span>
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
                    {/*          th:text="${workTask.getCreator().getUsername()}"></span>*/}
                    {/*</div>*/}
                    <div className="form-group">
                        <label><b>Assignees:</b></label>
                        <ul>
                            {items}
                        </ul>
                    </div>

                    <h2>Comments</h2>
                    <div className="table-responsive">
                        {/*    <table th:if="${workTask.getComments != null}">*/}
                        {/*        <thead>*/}
                        {/*        <tr>*/}
                        {/*            <th>Created By</th>*/}
                        {/*            <th>Date Created</th>*/}
                        {/*            <th>Comment</th>*/}
                        {/*        </tr>*/}
                        {/*        </thead>*/}
                        {/*        <tbody>*/}

                        {/*        <tr th:each="comment : ${commentsForTask}">*/}

                        {/*            <td th:text="${comment.getUser().getUsername()}"></td>*/}
                        {/*            <td th:text="${#temporals.format(comment.getDateTime(), 'dd-MM-yyyy HH:mm')}"></td>*/}
                        {/*            <td th:text="${comment.getComment()}">*/}

                        {/*                <td className="text-right">*/}
                        {/*                    <!--                        th:if="${comment.getUser().getUsername().equals(session.user.getUsername())}"-->*/}
                        {/*                    <form*/}
                        {/*                        th:if="${comment.getUser().getUsername().equals(#request.getRemoteUser())}"*/}
                        {/*                        th:action="@{/workOnTask/{id}/deleteComment (id=${comment.getId()})}"*/}
                        {/*                        th:method="POST">*/}

                        {/*                        <button type="submit" className="btn btn-sm btn-danger"><i*/}
                        {/*                            className="fa fa-trash">Delete</i>*/}
                        {/*                        </button>*/}

                        {/*                    </form>*/}

                        {/*                    <a th:if="${comment.getUser().getUsername().equals(#request.getRemoteUser())}"*/}
                        {/*                       th:href="@{/editComment/{id} (id=${comment.getId()})}"*/}
                        {/*                       className="btn btn-sm btn-info"><i className="fa fa-trash">Edit</i></a>*/}
                        {/*                </td>*/}
                        {/*        </tr>*/}
                        {/*        </tbody>*/}
                        {/*    </table>*/}
                        {/*</div>*/}

                        <table>
                            <thead>
                            <tr>
                                <th>Created By</th>
                                <th>Date Created</th>
                                <th>Comment</th>
                            </tr>
                            </thead>
                            <tbody>
                                {commentItems}
                            </tbody>
                        </table>
                    </div>

                    <div>
                        <div className="form-group">
                            <form onSubmit={leaveComment}>

                                <label htmlFor="comment"><b>Add Comment</b></label>
                                <input id="comment" name="comment" onChange={handleChange}
                                       required placeholder="Leave a comment"/>
                                <button onClick={`/workOnTask/${id}`}>Leave a Comment</button>
                                {/*<a href={`/workOnTask/${id}`}>Leave a Comment</a>*/}
                            </form>
                        </div>

                        <div className={'row'}>
                            <form onSubmit={onFormSubmit}>
                                <a href={`/tasks/taskInfo/${id}`} className={'btn btn-primary'}>Back</a>
                            </form>
                        </div>

                        <div className={'row'}>
                                <form onSubmit={markAsDone}>

                                    <button onClick={`/workOnTask/${id}`} type="submit">Mark As Done</button>

                                </form>

                                <form onSubmit={cancelTask}>

                                    <button onClick={`/workOnTask/${id}`} type="submit">Cancel Task </button>

                                </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default WorkOnTask;


{/*    <table th:if="${workTask.getComments != null}">*/}
{/*        <thead>*/}
{/*        <tr>*/}
{/*            <th>Created By</th>*/}
{/*            <th>Date Created</th>*/}
{/*            <th>Comment</th>*/}
{/*        </tr>*/}
{/*        </thead>*/}
{/*        <tbody>*/}

{/*        <tr th:each="comment : ${commentsForTask}">*/}

{/*            <td th:text="${comment.getUser().getUsername()}"></td>*/}
{/*            <td th:text="${#temporals.format(comment.getDateTime(), 'dd-MM-yyyy HH:mm')}"></td>*/}
{/*            <td th:text="${comment.getComment()}">*/}

{/*                <td className="text-right">*/}
{/*                    <!--                        th:if="${comment.getUser().getUsername().equals(session.user.getUsername())}"-->*/}
{/*                    <form*/}
{/*                        th:if="${comment.getUser().getUsername().equals(#request.getRemoteUser())}"*/}
{/*                        th:action="@{/workOnTask/{id}/deleteComment (id=${comment.getId()})}"*/}
{/*                        th:method="POST">*/}

{/*                        <button type="submit" className="btn btn-sm btn-danger"><i*/}
{/*                            className="fa fa-trash">Delete</i>*/}
{/*                        </button>*/}

{/*                    </form>*/}

{/*                    <a th:if="${comment.getUser().getUsername().equals(#request.getRemoteUser())}"*/}
{/*                       th:href="@{/editComment/{id} (id=${comment.getId()})}"*/}
{/*                       className="btn btn-sm btn-info"><i className="fa fa-trash">Edit</i></a>*/}
{/*                </td>*/}
{/*        </tr>*/}
{/*        </tbody>*/}
{/*    </table>*/}
{/*</div>*/}
