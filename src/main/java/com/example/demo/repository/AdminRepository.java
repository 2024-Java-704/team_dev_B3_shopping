package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	List<Admin>findByAdminNameAndAdminPass(String adminName, String adminPass);

}
