import React from "react";
import TaskTerm from "../TaskTerm/taskTerm";
import ReactPaginate from 'react-paginate'

// const tasks = (props) => {
//
//     return (
//         <div className={"container mm-4 mt-5  mb-2"}>
//             <div className={"row"}>
//                 <div className={"row"}>
//                     <table>
//                         <thead>
//                         <tr>
//                             <th className={"fs-4"} scope={"col"}>Task Title</th>
//                             <th className={"fs-4"} scope={"col"}>Description</th>
//                             {/*<th className={"fs-4"} scope={"col"}>Creator</th>*/}
//                             <th className={"fs-4"} scope={"col"}>Status</th>
//                             <th className={"fs-4"} scope={"col"}>Start Date</th>
//                             <th className={"fs-4"} scope={"col"}>Due Date</th>
//                             <th className={"fs-4"} scope={"col"}>Estimation Days</th>
//                             <th className={"fs-4"} scope={"col"}>Actions</th>
//                         </tr>
//                         </thead>
//                         <tbody>
//                         {props.tasks.map((term) => {
//                             return (
//                                 <TaskTerm term={term} onDelete={props.onDelete} onEdit={props.onEdit}
//                                 onView={props.onViewTask}/>
//                             );
//                         })}
//                         </tbody>
//                     </table>
//                 </div>
//                 <div className={"col mb-3"}>
//                     <div className={"row"}>
//                         <div className={"col-sm-12 col-md-12"}>
//                             <a href={"/tasks/add"}>Add Task</a>
//                             {/*<Link className={"btn btn-block btn-dark"} to={"/tasks/add"}>*/}
//                             {/*    Add Task*/}
//                             {/*</Link>*/}
//                         </div>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     );
// }
//
// export default tasks;

class Tasks extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            page: 0,
            size: 10
        }
    }

    render() {
        const offset = this.state.size * this.state.page;
        const nextPageOffset = offset + this.state.size;
        const pageCount = Math.ceil(this.props.tasks.length / this.state.size);
        const tasksPage = this.getTasksPage(offset, nextPageOffset);

        return (
            <div className={"container mm-4 mt-5  mb-2"}>
                <div className={"row"}>
                    <div className={"row"}>
                        <table>
                            <thead>
                            <tr>
                                <th className={"fs-6"} scope={"col"}>Task Title</th>
                                <th className={"fs-6"} scope={"col"}>Description</th>
                                <th className={"fs-6"} scope={"col"}>Creator</th>
                                <th className={"fs-6"} scope={"col"}>Status</th>
                                <th className={"fs-6"} scope={"col"}>Start Date</th>
                                <th className={"fs-6"} scope={"col"}>Due Date</th>
                                <th className={"fs-6"} scope={"col"}>Estimation Days</th>
                                <th className={"fs-6"} scope={"col"}>Status</th>
                                <th className={"fs-6"} scope={"col"}>Assignees</th>
                                <th className={"fs-6"} scope={"col"}>Comments</th>
                                <th className={"fs-6"} scope={"col"}>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {tasksPage}
                            </tbody>
                        </table>
                    </div>
                    <div className={"col mb-3"}>
                        <div className={"row"}>
                            <div className={"col-sm-12 col-md-12"}>
                                <a href={"/tasks/add"}>Add Task</a>
                            </div>
                        </div>
                    </div>
                </div>

                <ReactPaginate previousLabel={"back"}
                               nextLabel={"next"}
                               breakLabel={<a href="/#">...</a>}
                               breakClassName={"break-me"}
                               pageClassName={"ml-1"}
                               pageCount={pageCount}
                               marginPagesDisplayed={2}
                               pageRangeDisplayed={5}
                               onPageChange={this.handlePageClick}
                               containerClassName={"pagination m-6 justify-content-center"}
                               activeClassName={"active"}/>
            </div>
        )
    }

    handlePageClick = (data) => {
    let selected = data.selected;
    this.setState({
    page: selected
    })
    }

    getTasksPage = (offset, nextPageOffset) => {
        return this.props.tasks.map((term, index) => {
            return (
                <TaskTerm term={term} onDelete={this.props.onDelete} onEdit={this.props.onEdit}
                          onView={this.props.onViewTask}/>
            );
        }).filter((task, index) => {
            return index >= offset && index < nextPageOffset;
        })
    }
}

export default Tasks;