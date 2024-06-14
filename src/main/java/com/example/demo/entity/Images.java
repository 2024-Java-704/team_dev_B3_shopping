package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class Images {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column(name = "name")
	    private String name; // 画像ファイル名
	    @Column(name = "file_path")
	    private String filePath; // 画像ファイルのパス
	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

	}
