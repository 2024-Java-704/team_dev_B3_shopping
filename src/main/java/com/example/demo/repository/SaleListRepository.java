package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SaleList;

public interface SaleListRepository extends JpaRepository<SaleList, Integer> {
	List<SaleList> findByItemStatus(Integer Status);

	List<SaleList> findByStudentIdIsNot(Integer studentId);

	List<SaleList> findByItemStatusIs(Integer itemStatus);

	List<SaleList> findByStudentId(Integer studentId);
}
