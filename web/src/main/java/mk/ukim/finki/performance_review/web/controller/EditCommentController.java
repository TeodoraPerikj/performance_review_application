package mk.ukim.finki.performance_review.web.controller;

import mk.ukim.finki.performance_review.model.Comment;
import mk.ukim.finki.performance_review.model.Task;
import mk.ukim.finki.performance_review.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.performance_review.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/editComment")
public class EditCommentController {

    private final CommentService commentService;

    public EditCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String editCommentPage1(@PathVariable Long id,
                                  @RequestParam(required = false) String error, Model model){

        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        Comment comment = null;
        if(this.commentService.findById(id).isPresent())
            comment = this.commentService.findById(id).get();

        model.addAttribute("changeComment", comment);

        return "editComment";
    }

    @PostMapping("/{id}")
    public String editComment1(@PathVariable Long id, @RequestParam String editComment,
                              @SessionAttribute Task workTask){

        Comment comment = null;

        try {
            if(this.commentService.edit(id, editComment).isPresent())
                comment = this.commentService.edit(id, editComment).get();
        }catch (InvalidArgumentsException exception){

            String url = "/editComment/"+workTask.getId()+"?error="+exception.getMessage();

            return "redirect:"+url;
        }

        Long taskId = comment.getTask().getId();

        return "redirect:/workOnTask/"+taskId;
    }

}
