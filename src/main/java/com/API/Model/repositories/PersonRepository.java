package com.API.Model.repositories;

import com.API.Model.Entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Integer> {
    boolean existsByDni(String dni);
    PersonEntity findByDni(String dni);
}
