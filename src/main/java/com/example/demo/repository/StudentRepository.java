package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	List<Student>findByNumber(String number);
	List<Student> findByEmail(String Email) ;
	List<Student>findByEmailAndPass(String email,String pass);
	List<Student>findByNumberAndPass(String number, String pass);
	List<Student>findByStatusIs(Integer status);
	List<Student>findByNameLike(String name);
	List<Student>findByNameContaining(String name);

}
