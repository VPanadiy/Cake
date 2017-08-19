package com.Aleksandr.Cake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Aleksandr.Cake.model.Reader;

public interface ReaderRepository extends JpaRepository<Reader, String> {
	
}
