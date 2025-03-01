package com.example.richpeople.Controllers;

import com.example.richpeople.Service.AreaService;
import com.example.richpeople.Service.HumanService;
import com.example.richpeople.Entities.Area;
import com.example.richpeople.Entities.Human;
import com.example.richpeople.Repository.AreaRepository;
import com.example.richpeople.Repository.HumanRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/humans")
public class HumanController {
    private final AreaService areaService;
    private final HumanService humanService;
    private final HumanRepository humanRepository;
    private final AreaRepository areaRepository;

    public HumanController(HumanRepository humanRepository, AreaRepository areaRepository, AreaService areaService, HumanService humanService) {
        this.humanRepository = humanRepository;
        this.areaRepository = areaRepository;
        this.areaService = areaService;
        this.humanService = humanService;
    }

    @PostConstruct
    @Transactional
    public void initAreas() {
        if (areaRepository.count() == 0) {
            areaRepository.saveAll(List.of(
                    new Area("IT"),
                    new Area("Нефть"),
                    new Area("Финансы"),
                    new Area("Медицина"),
                    new Area("Строительство"),
                    new Area("Образование")
            ));
        }
    }

    @GetMapping("/main-page")
    public String listHumans(Model model) {
        List<Human> humans = humanRepository.findAll();
        List<Area> areas = areaRepository.findAll();
        model.addAttribute("humans", humans);
        model.addAttribute("areas", areas);
        return "humans";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("human", new Human());
        model.addAttribute("areas", areaRepository.findAll());
        return "form";
    }

    @Transactional
    @PostMapping("/save")
    public String createHuman(@ModelAttribute Human human, @RequestParam(value = "areaIds", required = false) List<Long> areaIds) {
        if (areaIds == null || areaIds.isEmpty()) {
            throw new IllegalArgumentException("Необходимо выбрать хотя бы одну сферу!");
        }

        Set<Area> selectedAreas = new HashSet<>(areaRepository.findAllById(areaIds));
        human.setAreas(selectedAreas);
        humanRepository.save(human);
        return "redirect:/humans/main-page";
    }
    @GetMapping("/by-area")
    public String getHumansByArea(@RequestParam(value = "areaIds", required = false) List<Long> areaIds, Model model) {
        if (areaIds == null || areaIds.isEmpty()) {
            return "redirect:/humans/main-page"; // Если ничего не выбрано, показываем всех людей
        }

        List<Area> selectedAreas = areaRepository.findAllById(areaIds);
        List<Human> humansByArea = humanService.getHumansByArea(areaIds);
        List<Area> areas = areaService.getAllAreas(); // Загружаем все сферы

        model.addAttribute("areas", areas);
        model.addAttribute("selectedAreas", selectedAreas);
        model.addAttribute("humans", humansByArea);

        return "humans";
    }
}
