import axios from '../custom-axios/axios';
import tasks_axios from '../custom-axios/tasks_axios';
import users_axios from '../custom-axios/users_axios';
import comments_axios from '../custom-axios/comments_axios';

const PerformanceReviewService = {

    fetchTasks: () => {
        debugger
        var tasks =  tasks_axios.get("/tasks");
        console.log(tasks)
        return tasks;
    },

    fetchUsers: () => {
        return users_axios.get("/showUsersDto");
    },

    fetchComments: () => {
        return comments_axios.get("/comments");
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
            //////                 ASSIGNEE
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
           // "assignees" : assignees,
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

    getUserByUsername: (username) => {

        return users_axios.get(`/findUser?username=${username}`);
    },

    viewTask: (id) => {
/////////////////// user2
        //// deleted username from the url

        ///// username is from the active one
        return tasks_axios.get(`/taskInfo/${id}?username=user1`);
    },

    workOnTask: (id) => {
        return tasks_axios.get(`/workOnTask/${id}`);
    },

    leaveComment: (id, username, comment) => {
        debugger;
        return comments_axios.post(`/comments/${id}/leaveComment?username=${username}&comment=${comment}`)
    },

    showUserPerformance: (chosenUsername, chosenType, dateFrom, dateTo) => {
        debugger;
        console.log(chosenUsername)
        console.log(chosenType)
        console.log(dateFrom)
        console.log(dateTo)

        return users_axios.get(`/showUserPerformance?chosenUsername=${chosenUsername}&chosenType=${chosenType}&dateFrom=${dateFrom}&dateTo=${dateTo}`)

        // return axios.get("/showUserPerformance", {
        //     "chosenUsername": chosenUsername,
        //     "chosenType": chosenType,
        //     "dateFrom": dateFrom,
        //     "dateTo": dateTo
        // })
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