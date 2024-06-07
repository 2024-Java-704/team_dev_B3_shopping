package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BoughtHistory;
public interface BoughtHistoryRepository extends JpaRepository<BoughtHistory, Integer>{

}
