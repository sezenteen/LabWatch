package com.example.labwatch.repository;

import com.example.labwatch.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByComputerID_Id(Long computerID);

    List<Alert> findTop10ByOrderByCreatedAtDesc();

}
