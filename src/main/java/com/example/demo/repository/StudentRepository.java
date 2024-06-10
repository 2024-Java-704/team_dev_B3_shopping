package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	List<Student>findByNumber(String number);
	List<Student>findByEmailAndPass(String email,String paas);
	List<Student>findByNumberAndPass(String number, String pass);

}
