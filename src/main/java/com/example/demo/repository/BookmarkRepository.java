package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bookmark;
public interface BookmarkRepository  extends JpaRepository<Bookmark, Integer>{

}
