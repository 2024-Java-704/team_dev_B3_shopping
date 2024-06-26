package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
	List<Bookmark> findByStudentId(Integer studentId);

	Bookmark findBySalelistId(Integer id);

	Bookmark findByStudentIdAndSalelistId(Integer id, Integer id2);

	List<Bookmark> findBySalelistIdAndStudentId(Integer salelistId, Integer studentId);

	//List<Bookmark> findBySalelistIdAndStudentId(Integer id, Integer id2);

}
