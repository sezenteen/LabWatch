package com.example.labwatch.service.implement;

import com.example.labwatch.model.Computer;
import com.example.labwatch.repository.ComputerRepository;
import com.example.labwatch.service.ComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerServiceImpl implements ComputerService {

    private final ComputerRepository computerRepository;

    @Override
    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    @Override
    public Computer getComputerById(Long id) {
        return computerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Компьютер олдсонгүй!"));
    }

    @Override
    public Computer createComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    @Override
    public Computer updateComputer(Long id, Computer computer) {
        Computer existingComputer = getComputerById(id);

        existingComputer.setComputerName(computer.getComputerName());
        existingComputer.setLabRoom(computer.getLabRoom());
        existingComputer.setIpAddress(computer.getIpAddress());
        existingComputer.setStatus(computer.getStatus());
        existingComputer.setLastSeen(computer.getLastSeen());

        return computerRepository.save(existingComputer);
    }

    @Override
    public void deleteComputer(Long id) {
        computerRepository.deleteById(id);
    }

    @Override
    public List<Computer> getOnlineComputers() {
        return computerRepository.findByStatus("ONLINE");
    }

    @Override
    public List<Computer> getOfflineComputers() {
        return computerRepository.findByStatus("OFFLINE");
    }

    @Override
    public void heartbeat(Long computerId) {
        Computer computer = getComputerById(computerId);
        computer.setStatus("ONLINE");
        computer.setLastSeen(LocalDateTime.now());
        computerRepository.save(computer);
    }

    @Override
    public void markOffline(Long computerId) {
        Computer computer = getComputerById(computerId);
        computer.setStatus("OFFLINE");
        computer.setLastSeen(LocalDateTime.now());
        computerRepository.save(computer);
    }

    @Override
    public List<Computer> getComputersByLab(String labRoom) {
        return computerRepository.findByLabRoom(labRoom);
    }

    @Override
    public long countAllComputers() {
        return computerRepository.count();
    }

    @Override
    public long countOnlineComputers() {
        return computerRepository.countByStatus("ONLINE");
    }

    @Override
    public long countOfflineComputers() {
        return computerRepository.countByStatus("OFFLINE");
    }
}
