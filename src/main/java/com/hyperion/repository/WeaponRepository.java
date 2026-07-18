package com.hyperion.repository;

import com.hyperion.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {

    List<Weapon> findByCategoryIgnoreCase(String category);

    Optional<Weapon> findByReference(String reference);
}