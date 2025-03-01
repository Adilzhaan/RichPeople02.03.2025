package com.example.richpeople.Service;

import com.example.richpeople.Entities.Human;
import com.example.richpeople.Repository.HumanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService {

    private final HumanRepository humanRepository;

    public HumanService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }

    public List<Human> getAllHumans() {
        return humanRepository.findAll();
    }

    public List<Human> getHumansByArea(List<Long> areaIds) {
        return humanRepository.findDistinctByAreasIdIn(areaIds);
    }

}
