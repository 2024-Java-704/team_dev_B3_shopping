package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer>{

	List<Staff> findByStaffNumber(String staffNumber);
}
