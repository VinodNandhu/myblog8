package com.myblog8.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private String content;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
        private List<Comment> comments;
}
