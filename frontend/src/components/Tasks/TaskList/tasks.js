import React from "react";
import TaskTerm from "../TaskTerm/taskTerm";

const tasks = (props) => {

    return (
        <div className={"container mm-4 mt-5  mb-2"}>
            <div className={"row"}>
                <div className={"row"}>
                    <table>
                        <thead>
                        <tr>
                            <th className={"fs-4"} scope={"col"}>Task Title</th>
                            <th className={"fs-4"} scope={"col"}>Description</th>
                            {/*<th className={"fs-4"} scope={"col"}>Creator</th>*/}
                            <th className={"fs-4"} scope={"col"}>Status</th>
                            <th className={"fs-4"} scope={"col"}>Start Date</th>
                            <th className={"fs-4"} scope={"col"}>Due Date</th>
                            <th className={"fs-4"} scope={"col"}>Estimation Days</th>
                            <th className={"fs-4"} scope={"col"}>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.tasks.map((term) => {
                            return (
                                <TaskTerm term={term} onDelete={props.onDelete} onEdit={props.onEdit}
                                onView={props.onViewTask}/>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
                <div className={"col mb-3"}>
                    <div className={"row"}>
                        <div className={"col-sm-12 col-md-12"}>
                            <a href={"/tasks/add"}>Add Task</a>
                            {/*<Link className={"btn btn-block btn-dark"} to={"/tasks/add"}>*/}
                            {/*    Add Task*/}
                            {/*</Link>*/}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default tasks;