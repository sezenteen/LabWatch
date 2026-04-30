package com.example.labwatch.controller;

import com.example.labwatch.model.Computer;
import com.example.labwatch.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/computers")
@RequiredArgsConstructor
public class ComputerController {

    private final ComputerService computerService;

    @GetMapping
    public List<Computer> getAllComputers() {
        return computerService.getAllComputers();
    }

    @GetMapping("/{id}")
    public Computer getComputerById(@PathVariable Long id) {
        return computerService.getComputerById(id);
    }

    @PostMapping
    public Computer createComputer(@RequestBody Computer computer){
        return computerService.createComputer(computer);
    }

    @PutMapping("/{id}")
    public Computer updateComputer(@PathVariable Long id, @RequestBody Computer computer){
        return computerService.updateComputer(id, computer);
    }

    @DeleteMapping("/{id}")
    public void deleteComputer(@PathVariable Long id){
        computerService.deleteComputer(id);
    }
}
