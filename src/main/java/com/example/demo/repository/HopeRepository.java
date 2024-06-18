package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Hope;

public interface HopeRepository extends JpaRepository<Hope, Integer> {
	List<Hope>findByStatus(Integer Status);

	List<Hope> findByStudentIdAndTitle(Integer id, String title);

}
