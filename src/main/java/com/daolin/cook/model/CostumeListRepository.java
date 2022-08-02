package com.daolin.cook.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumeListRepository extends JpaRepository<CostumeList, Long> {
    CostumeList findByName(String name);

}
