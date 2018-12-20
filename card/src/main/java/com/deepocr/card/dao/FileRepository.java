package com.deepocr.card.dao;

import com.deepocr.card.Entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Entity,Integer> {

    Entity findByGuid(String guid);

}
