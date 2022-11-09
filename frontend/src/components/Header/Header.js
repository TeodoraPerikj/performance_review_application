import React, {useEffect} from 'react';
import PerformanceReviewRepository from "../../repository/performanceReviewRepository";

const Header = (props) => {

    const [formData, updateFormData] = React.useState({
        username : "",
        activeUser: "",
        role: ""
    });

    useEffect(() => {

        const getActiveUser = PerformanceReviewRepository.getActiveUser()
            .then((data) => {

                updateFormData({
                    activeUser: data.data.username,
                    role: data.data.role
                })
            }).catch((error) => {
                console.log(error)
            });

    }, [])

    const onLogout = (e) => {
        e.preventDefault();

        PerformanceReviewRepository.logout()
            .then(() => {
                window.open("/login", "_self")
            })
    }

    let loginOrLogout;
    let accessToUsers;
    let accessToPerformance;

    if(formData.activeUser === "" || formData.activeUser === null){
        loginOrLogout = <form className="form-inline mt-2 mt-md-0 ml-3">
            <a href={"/login"} className="btn btn-outline-info my-2 my-sm-0">Login</a>
        </form>
    }else {
        loginOrLogout = <form className="form-inline mt-2 mt-md-0 ml-3" onSubmit={onLogout}>
            <button type={"submit"} className="btn btn-outline-info my-2 my-sm-0">Logout</button>
        </form>

        if (formData.role === "ROLE_ADMIN") {
            accessToUsers = <li className="nav-item active">
                    <a className={"text-decoration-none text-secondary me-2"} href={"/users"}>Users</a>
                </li>
            accessToPerformance = <li className="nav-item active">
                    <a className={"text-decoration-none text-secondary me-2"} href={"/selectParameters"}>Show
                        Performance</a>
                </li>
        }
    }

    return (
        <header>
            <nav className="navbar navbar-expand-md navbar-dark navbar-fixed bg-dark px-4">
                <a className="navbar-brand" href={"/tasks"}>Performance Review Application</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
                        aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                </button>
                <div className="collapse navbar-collapse" id="navbarCollapse">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item active">
                            <a className={"text-decoration-none text-secondary me-2"} href={"/tasks"}>Tasks</a>
                        </li>
                        <li className="nav-item active">
                            <a className={"text-decoration-none text-secondary me-2"} href={"/selectUser"}>My Tasks</a>
                        </li>
                        {accessToUsers}
                        {accessToPerformance}
                    </ul>
                    {loginOrLogout}
                </div>
            </nav>
        </header>

    );
}

export default Header;