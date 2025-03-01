package com.example.richpeople.Repository;

import com.example.richpeople.Entities.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {
    List<Human> findByAreasId(Long areaId);
    List<Human> findDistinctByAreasIdIn(List<Long> areaIds);
}
