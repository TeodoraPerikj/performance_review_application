import React from 'react';

const comments = (props) => {
    return (
        <div className={"container mm-4 mt-5 mb-2"}>
            <div className={"row"}>
                <div className={"row"}>
                    <table>
                        <thead>
                        <tr>
                            <th className={"fs-3"} scope={"col"}>Comments</th>
                            <th scope={"col"}>Task</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.comments.map((term) => {
                            return (
                                <tr>
                                    <td>{term.comment}</td>
                                    <td>{term.title}</td>
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

export default comments;