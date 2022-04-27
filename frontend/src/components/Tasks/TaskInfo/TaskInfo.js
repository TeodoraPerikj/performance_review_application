import React from 'react';

const TaskInfo = (props) => {

    return (

        <div className="container">
            <div className="row">
                <div className="col-md-5">
                    <div className="form-group">
                        <label htmlFor="title"><b>Task Title:</b></label>
                        <span className="form-control"
                              id="title">{props.taskInfo.task.title}</span>
                    </div>

                    <div className="form-group">
                        <label htmlFor="description"><b>Description:</b></label>
                        <span className="form-control"
                              id="description">{props.taskInfo.task.title}</span>
                    </div>

                    <div className="form-group">
                        <label htmlFor="startDate"><b>Start Date:</b></label>
                        <span className="form-control"
                              id="startDate">{props.taskInfo.task.startDate}</span>
                    </div>
                    <div className="form-group">
                        <label htmlFor="dueDate"><b>Due Date:</b></label>
                        <span className="form-control"
                              id="dueDate">{props.taskInfo.task.dueDate}</span>
                    </div>
                    <div className="form-group">
                        <label htmlFor="days"><b>Estimation Days:</b></label>
                        <span className="form-control"
                              id="days">{props.taskInfo.task.estimationDays}</span>
                    </div>
                    <div className="form-group">
                        <label htmlFor="status"><b>Status:</b></label>
                        <span className="form-control"
                              id="status">{props.taskInfo.task.status}</span>
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
                            {props.taskInfo.assignedUsers.map((user) => {
                                return <li>{user.toString()}</li>;
                            })}
                        </ul>
                    </div>

                </div>
            </div>
        </div>

    // <div>
    //     <span style="font-weight: bold" th:if="${textAndType.equals('Not opened yet!')}"
    //           th:text="${textAndType}"></span>
    //     <button th:if="${textAndType.equals('Not opened yet!')}"
    //             th:href="@{/workOnTask/{id} (id=${selectedTask?.getId()})}"
    //             disabled>
    //         Start Task
    //     </button>
    // </div>
    //
    // <div>
    //     <span style="font-weight: bold" th:if="${textAndType.equals('Task is closed!')}"
    //           th:text="${textAndType}"></span>
    //     <button th:if="${textAndType.equals('Task is closed!')}"
    //             th:href="@{/workOnTask/{id} (id=${selectedTask?.getId()})}"
    //             disabled>
    //         Start Task
    //     </button>
    // </div>
    //
    // <div>
    //     <a th:if="${textAndType.equals('Start Task')}"
    //        th:href="@{/workOnTask/{id} (id=${selectedTask?.getId()})}"
    //        th:text="${textAndType}"></a>
    // </div>
    //
    // <div>
    //     <form th:if="${textAndType.equals('Join Task')}"
    //           th:action="@{/taskInfo/{id}/joinTask (id=${selectedTask?.getId()})}"
    //           th:method="POST">
    //
    //         <button type="submit" th:text="${textAndType}"></button>
    //
    //     </form>
    // </div>
    //
    // <div>
    //     <a th:if="${textAndType.equals('Continue Task')}"
    //        th:href="@{/workOnTask/{id} (id=${selectedTask?.getId()})}"
    //        th:text="${textAndType}"></a>
    // </div>
    //
    // <div>
    //     <span style="font-weight: bold" th:if="${textAndType.equals('Finished')}" th:text="${textAndType}"></span>
    //     <button th:if="${textAndType.equals('Finished')}"
    //             th:href="@{/workOnTask/{id} (id=${selectedTask?.getId()})}"
    //             disabled>
    //         Start Task
    //     </button>
    // </div>
    //
    // <div>
    //     <span style="font-weight: bold" th:if="${textAndType.equals('Task has been canceled')}"
    //           th:text="${textAndType}"></span>
    //     <button th:if="${textAndType.equals('Task has been canceled')}"
    //             th:href="@{/workOnTask/{id} (id=${selectedTask?.getId()})}"
    //             disabled>
    //         Start Task
    //     </button>
    // </div>
    //
    // <a type="button" className="btn btn-primary" th:href="@{/tasks}">Back</a>
    //
    // <h3 style="color: red" th:if="${hasError}" th:text="${error}"></h3>

);
}

export default TaskInfo;