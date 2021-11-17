package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends CrudRepository<PatientEntity, Integer> {
    Iterable<PatientEntity> findByNom(@Param("search") String nom);
}
