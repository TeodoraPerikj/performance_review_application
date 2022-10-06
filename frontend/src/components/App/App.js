import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Switch, Redirect, Route} from 'react-router-dom';
import Tasks from "../Tasks/TaskList/tasks";
import PerformanceReviewRepository from "../../repository/performanceReviewRepository";
import Users from "../Users/users";
import Comments from "../Comments/comments";
import Header from "../Header/header";
import TaskAdd from "../Tasks/TaskAdd/taskAdd";
import TaskEdit from "../Tasks/TaskEdit/taskEdit";
import Login from "../Login/login";
import MyTasks from "../MyTasks/myTasks";
import SelectUser from "../Users/SelectUser/SelectUser";
import TaskInfo from "../Tasks/TaskInfo/TaskInfo";
import WorkOnTask from "../Tasks/WorkOnTask/workOnTask";
import SelectParameters from "../ShowPerformance/SelectParameters/selectParameters";
import UserPerformance from "../ShowPerformance/UserPerformance/userPerformance";
import CommentEdit from "../Comments/CommentEdit/commentEdit";
import RegisterUser from "../Users/RegisterUser/RegisterUser";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tasks: [],
            users: [],
            comments: [],
            activeUser: {},
            selectedUserInfo: {},
            taskInfo: {}
        }
    }

    render() {
        return (

            <Router>
                <Header/>

                <main>
                    <div className={"container"}>
                        <Switch>
                            <Route path={"/comments/edit/:id"} exact render={() => <CommentEdit/>}/>
                            <Route path={"/workOnTask/:id"} exact render={() =>
                                <WorkOnTask onDeleteComment={this.deleteComment}/>}/>
                            <Route path={"/tasks/taskInfo/:id"} exact render={() => <TaskInfo/>}/>
                            <Route path={"/tasks/edit/:id"} exact render={() =>
                                <TaskEdit users={this.state.users} onEditTask={this.editTask}/>}/>
                            <Route path={"/tasks/add"} exact render={() =>
                                <TaskAdd users={this.state.users} onAddTask={this.addTask}/>}/>
                            <Route path={"/tasks"} exact render={() =>
                                <Tasks tasks={this.state.tasks} onDelete={this.deleteTask}
                                       onEdit={this.getTaskById} onViewTask={this.viewTask}/>}/>
                            <Route path={"/users"} exact render={() =>
                                <Users users={this.state.users}/>}/>
                            <Route path={"/register"} exact render={() =>
                                <RegisterUser/>}/>
                            <Route path={"/comments"} exact render={() =>
                                <Comments comments={this.state.comments}/>}/>
                            <Route path={"/selectParameters"} exact render={() =>
                                <SelectParameters users={this.state.users}/>}/>
                            <Route path={"/login"} exact render={() => <Login onLogin={this.login}/>}/>
                            <Route path={"/selectUser"} exact render={() => <SelectUser users={this.state.users}/>}/>
                            <Route path={"/showPerformance"} exact render={() => <UserPerformance/>}/>
                            <Route path={"/myTasks/:username"} exact render={() => <MyTasks/>}/>

                             {/*<MyTasks userInfoForTask={this.state.selectedUserInfo} onEdit={this.editTask}*/}
                             {/*            onDelete={this.deleteTask}/> }/>*/}
                             {/*<Route path={"/selectUser"} exact render={() =>*/}
                             {/*    <SelectUser users={this.state.users} onSelect={this.getUserByUsername}/>}/>*/}
                            <Redirect to={"/tasks"}/>
                        </Switch>
                    </div>
                </main>

            </Router>
        );
    }

    componentDidMount() {
        this.loadTasks();
        this.loadUsers();
        this.loadComments();
    }

    loadTasks = () => {
        PerformanceReviewRepository.fetchTasks()
            .then((data) => {
                console.log(data.data)
                this.setState({
                    tasks: data.data
                })
            });
    }

    loadUsers = () => {
        PerformanceReviewRepository.fetchUsers()
            .then((data) => {
                this.setState({
                    users: data.data
                })
            });
    }

    loadComments = () => {
        PerformanceReviewRepository.fetchComments()
            .then((data) => {
                this.setState({
                    comments: data.data
                })
            });
    }

    deleteTask = (id) => {
        PerformanceReviewRepository.deleteTask(id)
            .then(() => {
                this.loadTasks();
            })
    }

    addTask = (title, description, startDate, dueDate, estimationDays, assignees, creator) => {
        PerformanceReviewRepository.addTask(title, description, startDate, dueDate, estimationDays, assignees, creator)
            .then(() => {
                this.loadTasks();
            });
    }
    
    getTaskById = (id) => {
        PerformanceReviewRepository.getTask(id)
            .then((data) => {
                this.setState({
                    selectedTask: data.data
                })
            })
    }

    editTask = (id, title, description, startDate, dueDate, estimationDays, assignees) => {
        debugger;
        PerformanceReviewRepository.editTask(id, title, description, startDate, dueDate, estimationDays, assignees)
            .then(() => {
                this.loadTasks();
            })
    }

    login = (username, password) => {
        PerformanceReviewRepository.login(username, password)
            .then((data) => {
                this.setState({
                    activeUser : data.data
                })
            });
    }

    getUserByUsername = (username) => {
        debugger;
        PerformanceReviewRepository.getUserByUsername(username)
            .then((data) => {
                this.setState({
                    selectedUserInfo : data.data
                })
            })
    }

    viewTask = (id) => {
        debugger;
        PerformanceReviewRepository.viewTask(id)
            .then((data) => {
                debugger;
                this.setState({
                    taskInfo: data.data
                })
            })
    }

    deleteComment = (id) => {
        PerformanceReviewRepository.deleteComment(id)
            .then(() => {
                this.loadComments()
            })
    }

    finishTask = (id) => {
        PerformanceReviewRepository.finishTask(id)
            .then(() => {
                this.loadTasks()
            })
    }
}

export default App;
