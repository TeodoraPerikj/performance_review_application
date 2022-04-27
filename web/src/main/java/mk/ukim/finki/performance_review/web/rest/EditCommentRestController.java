package mk.ukim.finki.performance_review.web.rest;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/editComment")
public class EditCommentRestController {

    private final CommentService commentService;

    public EditCommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> editCommentPage2(@PathVariable Long id){

        return this.commentService.findById(id)
                .map(comment -> ResponseEntity.ok().body(comment))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> editComment2(@PathVariable Long id, @RequestParam String editComment){

        return this.commentService.edit(id, editComment)
                .map(comment -> ResponseEntity.ok().body(comment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
