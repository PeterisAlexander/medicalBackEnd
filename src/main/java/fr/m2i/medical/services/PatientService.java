package fr.m2i.medical.services;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private PatientRepository pr;

    public PatientService(PatientRepository pr) {
        this.pr = pr;
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public PatientEntity findPatient(int id) {
        return pr.findById(id).get();
    }

    public void delete(int id) {
        pr.deleteById(id);
    }
}
