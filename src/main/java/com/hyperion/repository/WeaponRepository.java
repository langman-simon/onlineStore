package com.hyperion.repository;

import com.hyperion.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {

    List<Weapon> findByOrderByNameAsc();

    List<Weapon> findByCategoryIdOrderByNameAsc(Long categoryId);

    void removeWeaponById(Long idWeapon);

}