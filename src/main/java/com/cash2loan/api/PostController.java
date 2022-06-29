package com.cash2loan.api;

import com.cash2loan.domain.AppUser;
import com.cash2loan.domain.Post;
import com.cash2loan.services.AppUserService;
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
    private final AppUserService appUserService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().body(postService.getPosts());
    }

    // Save
    @PostMapping(path = "/post")
    public ResponseEntity<?> savePost(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String email,
            @RequestParam("image_path") MultipartFile multipartFile
    ) throws IOException {
//        File resource = new ClassPathResource("public/uploaded-images/ejara.png").getFile();
////        Path uploadDirectory = Paths.get(resource);
//        String text = resource.getAbsolutePath();
//        String uploadDirectory = Paths.get("uploaded-images").toRealPath().toString();
//        return ResponseEntity.ok().body("file:///"+uploadDirectory);
        AppUser appUser = appUserService.getUser(email);
        int user_id = Math.toIntExact(appUser.getId());

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
        @RequestParam String email,
//        @RequestParam String old_image_path,
        @RequestParam(value = "image_path", required = false) MultipartFile multipartFile
    ) throws IOException {
        AppUser appUser = appUserService.getUser(email);
        int user_id = Math.toIntExact(appUser.getId());
//        String fileName;
//        Post post = new Post();
        Post post = postService.getPost(id);
        post.setTitle(title);
        post.setDescription(description);
//        post.setUserid(user_id);

//        if(!multipartFile.isEmpty()) {
        if(multipartFile != null) {
            if(!multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                String randomUID = UUID.randomUUID().toString();
                String newFileName = randomUID.concat(fileName.substring(fileName.lastIndexOf(".")));

                FileUploadUtil.saveFile(newFileName, multipartFile);

                post.setImage_path(newFileName);
            }
        }

//        post.setImage_path(old_image_path);
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
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long id) throws IOException {
        Post post = postService.getPost(id);



//        Files.createFile(Paths.get("src/test/resources/fileToDelete_jdk7.txt"));
        Files.delete(Paths.get("uploaded-images/"+post.getImage_path()));
//        Path fileToDeletePath = Paths.get("src/test/resources/fileToDelete_jdk7.txt");
//        Files.delete(fileToDeletePath);
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
