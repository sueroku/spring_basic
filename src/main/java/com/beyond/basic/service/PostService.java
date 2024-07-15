package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResDto> postList(){
        List<PostResDto> postResDtosList = new ArrayList<>();
        List<Post> postList = postRepository.findAll();
        for(Post p : postList){
            postResDtosList.add(p.listFromEntity());
            System.out.println("저자의 이메일은" + p.getMember().getEmail());
        }
        return postResDtosList;
    }
}
