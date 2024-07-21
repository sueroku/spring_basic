package com.beyond.basic.controller;


import com.beyond.basic.repository.PostRepository;
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
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
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





    private final PostRepository postRepository;
    //    lazy(지연로딩), eager(즉시로딩) 테스트
    @GetMapping("/post/member/all")
    @ResponseBody
    public void postMemberAll(){
        System.out.println(postRepository.findAll());
//        postRepository.findById(1L).orElse(null).getMember().getEmail();
    }
}
