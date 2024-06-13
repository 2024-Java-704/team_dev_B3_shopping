package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bookinfo;

public interface BookinfoRepository extends JpaRepository<Bookinfo, Integer> {
	
	List<Bookinfo> findByIdAndTitleContaining(Integer id, String title);

	Bookinfo findByIsbn(String isbn);

}
