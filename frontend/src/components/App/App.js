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
                            <Route path={"/tasks/taskInfo/:id"} exact render={() =>
                            <TaskInfo taskInfo={this.state.taskInfo}/>}/>
                            <Route path={"/tasks/edit/:id"} exact render={() =>
                                <TaskEdit users={this.state.users} onEditTask={this.editTask}/>}/>
                            <Route path={"/tasks/add"} exact render={() =>
                                <TaskAdd users={this.state.users} onAddTask={this.addTask}/>}/>
                            <Route path={"/tasks"} exact render={() =>
                                <Tasks tasks={this.state.tasks} onDelete={this.deleteTask}
                                       onEdit={this.getTaskById} onViewTask={this.viewTask}/>}/>
                            <Route path={"/users"} exact render={() =>
                                <Users users={this.state.users}/>}/>
                            <Route path={"/comments"} exact render={() =>
                                <Comments comments={this.state.comments}/>}/>
                            <Route path={"/login"} exact render={() => <Login onLogin={this.login}/>}/>
                            <Route path={"/myTasks"} exact render={() =>
                                <MyTasks userInfoForTask={this.state.selectedUserInfo} onEdit={this.editTask}
                                         onDelete={this.deleteTask}/> }/>
                            <Route path={"/selectUser"} exact render={() =>
                                <SelectUser users={this.state.users} onSelect={this.getUserByUsername}/>}/>
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

    addTask = (title, description, startDate, dueDate, estimationDays, assignees) => {
        PerformanceReviewRepository.addTask(title, description, startDate, dueDate, estimationDays, assignees)
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

}

export default App;
