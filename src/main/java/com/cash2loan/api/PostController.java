package com.cash2loan.api;

import com.cash2loan.domain.Post;
import com.cash2loan.services.PostService;
import com.cash2loan.utils.FileUploadUtil;
import com.cash2loan.utils.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().body(postService.getPosts());
    }

    // Save
    @PostMapping("/post/save")
    public ResponseEntity<Post> savePost(
            @RequestParam("image_path") MultipartFile multipartFile,
            @RequestBody Post post
    ) throws IOException {
        log.info("Data is: {}", post);
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        FileUploadUtil.saveFile(fileName, multipartFile);

        post.setImage_path(fileName);
        return ResponseEntity.created(Utilities.Util_uri("api/post/save")).body(postService.savePost(post));
    }

    // Update
    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    // get single post
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }



    // delete method
    public ResponseEntity<HttpStatus> deletePost(@RequestBody Post post) {
        postService.deletePost(post.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
