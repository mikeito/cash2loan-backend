package com.cash2loan.services;

import com.cash2loan.domain.Post;
import com.cash2loan.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImplementation implements PostService{
    private final PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
//        log.info("Saving new post {} to db", post.getTitle());
        post.setCreated_at(Date.valueOf(LocalDate.now()));
        post.setUpdated_at(Date.valueOf(LocalDate.now()));
        log.info("Saving new post {} to db", post);
        return post;
//        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long postId, Post post) {
        Post _post = getPost(postId);
        _post.setTitle(post.getTitle());
        _post.setImage_path(post.getImage_path());
        _post.setDescription(post.getDescription());
        _post.setUpdated_at(Date.valueOf(LocalDate.now()));
        return postRepository.save(_post);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(Long postId) {
        Optional<Post> _postOptional = postRepository.findById(postId);
        Post post = _postOptional.orElseThrow(() ->
                new RuntimeException("Record not found")
        );
        return post;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = getPost(postId);
        postRepository.delete(post);
    }
}
