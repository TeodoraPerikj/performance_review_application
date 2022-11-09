import axios from '../custom-axios/axios';
import tasks_axios from '../custom-axios/tasks_axios';
import users_axios from '../custom-axios/users_axios';
import comments_axios from '../custom-axios/comments_axios';

const PerformanceReviewService = {

    fetchTasks: () => {
        return tasks_axios.get("/tasks");
    },

    fetchUsers: () => {
        return users_axios.get("/showUsersDto");
    },

    fetchComments: () => {
        return comments_axios.get("/comments");
    },

    fetchNotAssignedUsers: () => {
        return users_axios.get("/notAssignedUsers");
    },

    deleteTask: (id) => {
        return tasks_axios.delete(`/tasks/delete/${id}`);
    },

    addTask: (title, description, startDate, dueDate, estimationDays, assignee, creator) => {
        return tasks_axios.post("/tasks/add" , {
            "title" : title,
            "description" : description,
            "startDate" : startDate,
            "dueDate" : dueDate,
            "estimationDays" : estimationDays,
            "assignee" : assignee,
            "creator" : creator
        });
    },

    editTask: (id,title, description, startDate, dueDate, estimationDays, assignee, creator) => {
        debugger;
        return tasks_axios.put(`/tasks/add/${id}`,{
            "title" : title,
            "description" : description,
            "startDate" : startDate,
            "dueDate" : dueDate,
            "estimationDays" : estimationDays,
            "assignee": assignee,
            "creator" : creator
        });
    },

    getTask: (id) => {
        return tasks_axios.get(`/tasks/edit-task/${id}`);
    },

    login: (username, password) => {
        return users_axios.post("/login", {
            "username" : username,
            "password" : password
        })
    },

    logout: () => {
        return users_axios.get("/logout")
    },

    getActiveUser: () => {
        return  users_axios.get("/login")
    },

    changeRole: (username, role) => {
        return users_axios.post(`/changeRole?username=${username}&role=${role}`)
    },

    getUserByUsername: (username) => {

        return users_axios.get(`/findUser?username=${username}`);
    },

    viewTask: (id) => {
        return tasks_axios.get(`/taskInfo/${id}`);
    },

    workOnTask: (id) => {
        return tasks_axios.get(`/workOnTask/${id}`);
    },

    leaveComment: (id, username, comment) => {
        return comments_axios.post(`/comments/${id}/leaveComment?username=${username}&comment=${comment}`)
    },

    showUserPerformance: (chosenUsername, chosenType, dateFrom, dateTo) => {
        return users_axios.get(`/showUserPerformance?chosenUsername=${chosenUsername}&chosenType=${chosenType}&dateFrom=${dateFrom}&dateTo=${dateTo}`)
    },

    deleteComment: (id) => {
        return comments_axios.delete(`/comments/${id}/deleteComment`);
    },

    getComment: (id) => {
        return comments_axios.get(`/editComment/${id}`);
    },

    changeComment: (id, editComment) => {
        return comments_axios.put(`/editComment/${id}?editComment=${editComment}`);
    },

    finishTask: (id) => {
        return tasks_axios.post(`/workOnTask/${id}/finishTask`);
    },

    cancelTask: (id) => {
        return tasks_axios.post(`/workOnTask/${id}/cancelTask`);
    },

    registerUser: (name, surname, username, password, repeatedPassword) => {
        return users_axios.post("/register", {
            "name": name,
            "surname": surname,
            "username": username,
            "password": password,
            "repeatedPassword": repeatedPassword
        })
    }

}

export default PerformanceReviewService;