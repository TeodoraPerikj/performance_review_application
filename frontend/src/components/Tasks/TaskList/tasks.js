import React from "react";
import TaskTerm from "../TaskTerm/taskTerm";
import ReactPaginate from 'react-paginate'
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";

class Tasks extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            page: 0,
            size: 10,
            activeUser: "",
            ownedTask: false
        }
    }

    render() {

        let user = PerformanceReviewRepository.getActiveUser()
            .then((data) => {

                this.setState({
                    activeUser: data.data.username,
                    ownedTask: data.data.ownedTask
                })

            }).catch((error) => {
                console.log(error)
            });

        const offset = this.state.size * this.state.page;
        const nextPageOffset = offset + this.state.size;
        const pageCount = Math.ceil(this.props.tasks.length / this.state.size);
        const tasksPage = this.getTasksPage(offset, nextPageOffset);

        if(this.state.activeUser === null || this.state.activeUser === "")
        {
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
                                    <th className={"fs-6"} scope={"col"}>Start Date</th>
                                    <th className={"fs-6"} scope={"col"}>Due Date</th>
                                    <th className={"fs-6"} scope={"col"}>Estimation Days</th>
                                    <th className={"fs-6"} scope={"col"}>Status</th>
                                    <th className={"fs-6"} scope={"col"}>Assignees</th>
                                    <th className={"fs-6"} scope={"col"}>Comments</th>
                                </tr>
                                </thead>
                                <tbody>
                                {tasksPage}
                                </tbody>
                            </table>
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

        else {
            // let canAddTask;
            //
            // if(!this.state.ownedTask){
            //     console.log(this.state.ownedTask)
            //    canAddTask = <div className={"col mb-3"}>
            //         <div className={"row"}>
            //             <div className={"col-sm-12 col-md-12"}>
            //                 <a href={"/tasks/add"} className={"btn btn-success"}>Add Task</a>
            //             </div>
            //         </div>
            //     </div>
            // }

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
                                <div className={"col mb-3"}>
                                    <div className={"row"}>
                                        <div className={"col-sm-12 col-md-12"}>
                                            <a href={"/tasks/add"} className={"btn btn-success"}>Add Task</a>
                                        </div>
                                    </div>
                                </div>
                                </tbody>
                            </table>
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