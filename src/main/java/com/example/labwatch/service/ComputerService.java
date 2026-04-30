package com.example.labwatch.service;

import com.example.labwatch.model.Computer;

import java.util.List;

public interface ComputerService {

    List<Computer> getAllComputers();
    Computer getComputerById(Long id);

    Computer createComputer(Computer computer);
    Computer updateComputer(Long id, Computer computer);

    void deleteComputer(Long id);
}
