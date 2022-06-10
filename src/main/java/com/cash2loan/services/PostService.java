package com.cash2loan.services;

import com.cash2loan.domain.Post;

import java.util.List;

public interface PostService {
    Post savePost(Post post);
    Post updatePost(Long postId, Post post);
    List<Post> getPosts();
    Post getPost(Long postId);

    void deletePost(Long postId);



}
