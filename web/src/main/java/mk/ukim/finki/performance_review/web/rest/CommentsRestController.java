package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.dto.CommentsDto;
import mk.ukim.finki.performance_review.service.CommentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comments")
public class CommentsRestController {

    private final CommentService commentService;

    public CommentsRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentsDto> showComments(){
        return this.commentService.showCommentsDto();
    }
}
