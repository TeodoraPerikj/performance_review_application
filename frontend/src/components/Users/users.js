import React from 'react';

const users = (props) => {
    return (
        <div className={"container mm-4 mt-5  mb-2"}>
            <div className={"row"}>
                <div className={"row"}>
                    <table>
                        <thead>
                        <tr>
                            <th className={"fs-3"} scope={"col"}>Name</th>
                            <th className={"fs-3"} scope={"col"}>Surname</th>
                            <th className={"fs-3"} scope={"col"}>Username</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.users.map((term) => {
                            return (
                                <tr>
                                    <td>{term.name}</td>
                                    <td>{term.surname}</td>
                                    <td>{term.username}</td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default users;