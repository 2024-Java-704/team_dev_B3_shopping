package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BoughtCertificate;

public interface BoughtCertificateRepository extends JpaRepository<BoughtCertificate, Integer>{

	void save(Integer salelistId);

}
