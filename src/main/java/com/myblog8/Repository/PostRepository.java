package com.myblog8.Repository;

import com.myblog8.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository  extends JpaRepository <Post , Long> {
}
