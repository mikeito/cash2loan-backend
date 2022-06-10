package com.cash2loan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(unique = true, nullable = true)
    private String title;
    private String description;
    private String image_path;  // make nullable
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    private Date created_at;
    private Date updated_at;
}
