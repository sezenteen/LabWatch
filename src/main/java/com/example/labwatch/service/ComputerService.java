package com.example.labwatch.service;

import com.example.labwatch.model.Computer;

import java.util.List;

public interface ComputerService {

    List<Computer> getAllComputers();
    Computer getComputerById(Long id);

    Computer createComputer(Computer computer);
    Computer updateComputer(Long id, Computer computer);

    void deleteComputer(Long id);

//    monitoring
    List<Computer> getOnlineComputers();
    List<Computer> getOfflineComputers();
    void heartbeat(Long computerId);

//    filtering
    List<Computer> getComputersByLab(String labRoom);

//    admin
    long countAllComputers();
    long countOnlineComputers();
    long countOfflineComputers();
}
