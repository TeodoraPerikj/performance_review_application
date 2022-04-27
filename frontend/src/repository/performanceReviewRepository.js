import axios from '../custom-axios/axios';

const PerformanceReviewService = {

    fetchTasks: () => {
        return axios.get("/tasks");
    },

    fetchUsers: () => {
        return axios.get("/myTasks/showUsersDto");
    },

    fetchComments: () => {
        return axios.get("/comments");
    },

    deleteTask: (id) => {
        return axios.delete(`/tasks/delete/${id}`);
    },

    addTask: (title, description, startDate, dueDate, estimationDays, assignees) => {
        debugger;
        return axios.post("/tasks/add" , {
            "title" : title,
            "description" : description,
            "startDate" : startDate,
            "dueDate" : dueDate,
            "estimationDays" : estimationDays,
            "assignees" : assignees
        });
    },

    editTask: (id,title, description, startDate, dueDate, estimationDays, assignees) => {
        debugger;
        return axios.put(`/tasks/add/${id}`,{
            "title" : title,
            "description" : description,
            "startDate" : startDate,
            "dueDate" : dueDate,
            "estimationDays" : estimationDays,
            "assignees" : assignees
        });
    },

    getTask: (id) => {
        return axios.get(`/tasks/edit-task/${id}`);
    },

    login: (username, password) => {
        return axios.post("/login", {
            "username" : username,
            "password" : password
        })
    },

    getUserByUsername: (username) => {
        debugger;
        return axios.get(`/findUser?username=${username}`)
    },

    viewTask: (id) => {
        debugger;
        let taskInfo = axios.get(`/taskInfo/${id}?username=user2`);
        return taskInfo;
    }
}

export default PerformanceReviewService;