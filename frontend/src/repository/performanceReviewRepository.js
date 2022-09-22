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
        debugger
        let task = axios.delete(`/tasks/delete/${id}`);

        return task
    },

    addTask: (title, description, startDate, dueDate, estimationDays, assignees, creator) => {
        return axios.post("/tasks/add" , {
            "title" : title,
            "description" : description,
            "startDate" : startDate,
            "dueDate" : dueDate,
            "estimationDays" : estimationDays,
            "assignees" : assignees,
            "creator" : creator
        });
    },

    editTask: (id,title, description, startDate, dueDate, estimationDays, assignees, creator) => {
        debugger;
        return axios.put(`/tasks/add/${id}`,{
            "title" : title,
            "description" : description,
            "startDate" : startDate,
            "dueDate" : dueDate,
            "estimationDays" : estimationDays,
            "assignees" : assignees,
            "creator" : creator
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

        return axios.get(`/findUser?username=${username}`);
    },

    viewTask: (id) => {

        return axios.get(`/taskInfo/${id}?username=user2`);
    },

    workOnTask: (id) => {
        return axios.get(`/workOnTask/${id}`);
    },

    leaveComment: (id, username, comment) => {
        debugger;
        return axios.post(`/workOnTask/${id}/leaveComment?username=${username}&comment=${comment}`)
    },

    showUserPerformance: (chosenUsername, chosenType, dateFrom, dateTo) => {
        debugger;
        console.log(chosenUsername)
        console.log(chosenType)
        console.log(dateFrom)
        console.log(dateTo)

        return axios.get(`/showUserPerformance?chosenUsername=${chosenUsername}&chosenType=${chosenType}&dateFrom=${dateFrom}&dateTo=${dateTo}`)

        // return axios.get("/showUserPerformance", {
        //     "chosenUsername": chosenUsername,
        //     "chosenType": chosenType,
        //     "dateFrom": dateFrom,
        //     "dateTo": dateTo
        // })
    },

    deleteComment: (id) => {
        return axios.delete(`/workOnTask/${id}/deleteComment`);
    },

    getComment: (id) => {
        return axios.get(`/editComment/${id}`);
    },

    changeComment: (id, editComment) => {
        return axios.put(`/editComment/${id}?editComment=${editComment}`);
    },

    finishTask: (id) => {
        return axios.post(`/workOnTask/${id}/finishTask`);
    },

    cancelTask: (id) => {
        return axios.post(`/workOnTask/${id}/cancelTask`);
    }

}

export default PerformanceReviewService;