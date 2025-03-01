package com.example.richpeople.Service;

import com.example.richpeople.Entities.Area;
import com.example.richpeople.Repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    public Area getAreaById(Long areaId) {
        return areaRepository.findById(areaId)
                .orElseThrow(() -> new NoSuchElementException("Сфера с ID " + areaId + " не найдена"));
    }
}
