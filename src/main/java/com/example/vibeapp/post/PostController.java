package com.example.vibeapp.post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 5;
        model.addAttribute("posts", postService.getPosts(page, pageSize));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postService.getTotalPages(pageSize));
        return "post/posts";
    }

    @GetMapping("/posts/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.getPost(id));
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String createForm() {
        return "post/post_new_form";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.getPostForEdit(id));
        return "post/post_edit_form";
    }

    @PostMapping("/posts/add")
    public String create(Post post) {
        postService.createPost(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/save")
    public String update(@PathVariable("id") Long id, String title, String content) {
        postService.updatePost(id, title, content);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
