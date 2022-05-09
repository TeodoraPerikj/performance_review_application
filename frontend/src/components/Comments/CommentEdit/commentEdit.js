import React,{useEffect} from "react";
import PerformanceReviewRepository from "../../../repository/performanceReviewRepository";
import {useParams} from "react-router";

const CommentEdit = (props) => {

    const {id} = useParams();
    const params =  new URLSearchParams(window.location.search)

    const taskId = params.get("taskId");
    const comment = params.get("comment");

    const [formData, updateFormData] = React.useState({
        editComment: ""
    })

    useEffect(() => {

        PerformanceReviewRepository.getComment(id)
            .then((data) => {
                updateFormData({
                    editComment: data.data.comment
                })
            }).catch((error) => {
            console.log(error)
        });

    }, [])

    const handleChange = (e) => {

        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const editComment = formData.editComment !== "" ? formData.editComment : comment;

        PerformanceReviewRepository.changeComment(id, editComment)
            .then(() => {
                window.open(`/workOnTask/${taskId}`,"_self")
            })

    }


    return (
        <div className="container">
            <div className="row">
                <div className="col-md-5">
                    <form onSubmit={onFormSubmit}>

                        <div className="form-group">
                            <label htmlFor="editComment">Edit Comment</label>
                            <input type="text"
                                   className="form-control"
                                   id="editComment"
                                   name="editComment"
                                   onChange={handleChange}
                                   placeholder={formData.editComment}/>
                        </div>

                        <button type="submit" className="btn btn-primary">Change</button>
                        <a href={`/workOnTask/${taskId}`} type="button" className="btn btn-primary">Back</a>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default CommentEdit;