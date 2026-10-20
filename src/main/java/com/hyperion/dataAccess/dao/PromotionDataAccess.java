package com.hyperion.dataAccess.dao;

import com.hyperion.model.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionDataAccess {

    List<Promotion> findAll();

    Optional<Promotion> findById(Long id);

    Promotion save(Promotion promotion);

    void deleteById(Long id);
}