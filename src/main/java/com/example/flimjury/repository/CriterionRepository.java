package com.example.flimjury.repository;

import com.example.flimjury.entity.Criterion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriterionRepository extends JpaRepository<Criterion, Long> {
}
