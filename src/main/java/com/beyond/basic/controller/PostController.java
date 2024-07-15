package com.beyond.basic.controller;


import com.beyond.basic.service.PostService;
import com.beyond.basic.domain.PostResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/list")
//    public String postList(Model model){
    @ResponseBody
    public List<PostResDto> postList(){
//        List<PostResDto> postResDtoList = postService.postList();
//        model.addAttribute("postList", postResDtoList);
        return postService.postList();
//        return "post/post-list";
    }
}
