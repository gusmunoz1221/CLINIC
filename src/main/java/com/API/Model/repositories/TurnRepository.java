package com.API.Model.repositories;

import com.API.Model.Entities.TurnEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<TurnEntity,Integer> {
    boolean existsById(int id);
    Page findById(int id, Pageable pageable);
}
