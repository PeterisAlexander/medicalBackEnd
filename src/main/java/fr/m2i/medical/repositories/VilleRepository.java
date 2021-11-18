package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VilleRepository<T> extends CrudRepository<VilleEntity, Integer> {
    public List<VilleEntity> findByNomContains(String search );

    Page<T> findAll(Pageable pageable);
}
