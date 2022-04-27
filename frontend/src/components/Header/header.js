import React from 'react';

const header = (params) => {

    let authenticate
    if (localStorage.getItem("JWT")) {
        authenticate = (<button className="btn btn-outline-info my-2 my-sm-0"
                                onClick={() => localStorage.removeItem("JWT")}>Logout</button>);
    } else {
        authenticate = (<a className="btn btn-outline-info my-2 my-sm-0" href={"/login"}>Login</a>);
    }


    return (
        <header>
            <nav className="navbar navbar-expand-md navbar-dark navbar-fixed bg-dark px-4">
                <a className="navbar-brand" href="/tasks">Performance Review Application</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
                        aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarCollapse">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item active">
                            {/*<Link className="nav-link" to={"/tasks"}>Tasks</Link>*/}
                            <a className={"text-decoration-none text-secondary me-2"} href={"/tasks"}>Tasks</a>
                        </li>
                        <li className="nav-item active">
                            {/*<Link className="nav-link" to={"/users"}>Users</Link>*/}
                            <a className={"text-decoration-none text-secondary me-2"} href={"/users"}>Users</a>
                        </li>
                        <li className="nav-item active">
                            {/*<Link className={"nav-link"} to={"/comments"}>Comments</Link>*/}
                            <a className={"text-decoration-none text-secondary me-2"} href={"/comments"}>Comments</a>
                        </li>
                        <li className="nav-item active">
                            {/*<Link className={"nav-link"} to={"/comments"}>Comments</Link>*/}
                            <a className={"text-decoration-none text-secondary me-2"} href={"/myTasks"}>My Tasks</a>
                        </li>
                    </ul>
                    <form className="form-inline mt-2 mt-md-0 ml-3">
                        {/*<Link className="btn btn-outline-info my-2 my-sm-0" to={"/login"}>Login</Link>*/}
                        <a href={"/login"} className="btn btn-outline-info my-2 my-sm-0">Login</a>
                        {/*{authenticate}*/}
                    </form>
                </div>
            </nav>
        </header>

    );
}

export default header;