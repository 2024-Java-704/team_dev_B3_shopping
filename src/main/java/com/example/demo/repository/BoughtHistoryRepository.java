package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BoughtHistory;
public interface BoughtHistoryRepository extends JpaRepository<BoughtHistory, Integer>{
	List<BoughtHistory> findByDelivery(Integer delivery);
	List<BoughtHistory> findBySalelistId(Integer salelistId);
	List<BoughtHistory> findByStudentIdOrderByIdDesc(Integer studentId);
	List<BoughtHistory> findByStudentId(Integer studentId);

}
