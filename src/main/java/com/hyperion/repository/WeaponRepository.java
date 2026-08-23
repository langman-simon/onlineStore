package com.hyperion.repository;

import com.hyperion.model.Weapon;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {

    List<Weapon> findByOrderByNameAsc();

    List<Weapon> findByCategoryIdOrderByNameAsc(Long categoryId);

    List<Weapon> findByCategoryId(Long categoryId, Sort sort);

    List<Weapon> findByNameContainingIgnoreCase(String name, Sort sort);

    List<Weapon> findByCategoryIdAndNameContainingIgnoreCase(
            Long categoryId,
            String name,
            Sort sort
    );

    void removeWeaponById(Long idWeapon);

    Optional<Weapon> findByReference(String reference);
}
