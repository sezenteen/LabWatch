package com.example.labwatch.repository;

import com.example.labwatch.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    List<Computer> findByStatus(String status);
    List<Computer> findByLabRoom(String labRoom);
    long countByStatus(String status);
}
