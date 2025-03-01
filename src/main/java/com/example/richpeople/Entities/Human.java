package com.example.richpeople.Entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "humans")
public class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double capital; // добавлено поле капитал

    @ManyToMany
    @JoinTable(
            name = "human_area",
            joinColumns = @JoinColumn(name = "human_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private Set<Area> areas = new HashSet<>();

    // Конструкторы
    public Human() {}

    public Human(String name, double capital) {
        this.name = name;
        this.capital = capital;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    // Утилитные методы для добавления и удаления связей
    public void addArea(Area area) {
        this.areas.add(area);
        area.getHumans().add(this);
    }

    public void removeArea(Area area) {
        this.areas.remove(area);
        area.getHumans().remove(this);
    }
}
