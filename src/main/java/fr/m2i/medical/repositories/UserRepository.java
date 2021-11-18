package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username); //username

}