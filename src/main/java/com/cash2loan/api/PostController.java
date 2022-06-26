package com.cash2loan.api;

import com.cash2loan.domain.Post;
import com.cash2loan.services.PostService;
import com.cash2loan.utils.FileUploadUtil;
import com.cash2loan.utils.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
//        List<Post> post = postService.getPosts();
//        post.
        return ResponseEntity.ok().body(postService.getPosts());
    }

    // Save
    @PostMapping(path = "/post")
    public ResponseEntity<?> savePost(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer user_id,
            @RequestParam("image_path") MultipartFile multipartFile
    ) throws IOException {
//        File resource = new ClassPathResource("public/uploaded-images/ejara.png").getFile();
////        Path uploadDirectory = Paths.get(resource);
//        String text = resource.getAbsolutePath();
//        String uploadDirectory = Paths.get("uploaded-images").toRealPath().toString();
//        return ResponseEntity.ok().body("file:///"+uploadDirectory);

        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setUserid(user_id);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        long size = multipartFile.getSize();
        String randomUID = UUID.randomUUID().toString();
        String newFileName = randomUID.concat(fileName.substring(fileName.lastIndexOf(".")));

        FileUploadUtil.saveFile(newFileName, multipartFile);

        post.setImage_path(newFileName);
        log.info("*********Data is: {}", newFileName);

        return ResponseEntity.created(Utilities.Util_uri("api/post")).body(postService.savePost(post));
    }

    // Update
    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer user_id,
            @RequestParam("image_path") MultipartFile multipartFile
            )
    {
        log.info("*********ID is: {}", id);
        log.info("*********Data is: {}", title);
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    // get single post
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }



    // delete method
    @DeleteMapping("/post/{id}")
    public ResponseEntity<HttpStatus> deletePost(@RequestBody Post post) {
        postService.deletePost(post.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
