package com.megamaker.storeservice.repository;

import com.megamaker.storeservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
