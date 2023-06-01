package com.API.Model.repositories;

import com.API.Model.Entities.TurnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<TurnEntity,Integer> {
}
