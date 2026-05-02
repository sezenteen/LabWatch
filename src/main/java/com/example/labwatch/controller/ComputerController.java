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

    @GetMapping("/online")
    public List<Computer> getOnlineComputers() {
        return computerService.getOnlineComputers();
    }

    @GetMapping("/offline")
    public List<Computer> getOfflineComputers() {
        return computerService.getOfflineComputers();
    }

    @GetMapping("/lab/{labRoom}")
    public List<Computer> getComputersByLab(@PathVariable String labRoom) {
        return computerService.getComputersByLab(labRoom);
    }

    @GetMapping("/count")
    public long countAllComputers() {
        return computerService.countAllComputers();
    }

    @GetMapping("/count/online")
    public long countOnlineComputers() {
        return computerService.countOnlineComputers();
    }

    @GetMapping("/count/offline")
    public long countOfflineComputers() {
        return computerService.countOfflineComputers();
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

    @PostMapping("/{id}/heartbeat")
    public Computer heartbeat(@PathVariable Long id) {
        computerService.heartbeat(id);
        return computerService.getComputerById(id);
    }

    @PostMapping("/{id}/offline")
    public Computer markOffline(@PathVariable Long id) {
        computerService.markOffline(id);
        return computerService.getComputerById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteComputer(@PathVariable Long id){
        computerService.deleteComputer(id);
    }
}
