package fr.m2i.medical.services;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

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

    private void checkPatient(PatientEntity p) throws InvalidObjectException {
        if((p.getNom().length() & p.getPrenom().length()) <= 2) {
            throw new InvalidObjectException("Nom et Prénom du patient invalide");
        }

        if(p.getVille().toString().length() <= 2) {
            throw new InvalidObjectException("Ville du patient invalide");
        }

        if(p.getAdresse().length() < 10) {
            throw new InvalidObjectException("Adresse trop courte, adresse invalide");
        }
    }

    public void delete(int id) {
        pr.deleteById(id);
    }

    public void addPatient(PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        pr.save(p);
    }

    public void editPatient( int id , PatientEntity p) throws InvalidObjectException , NoSuchElementException {
        checkPatient(p);
        try{
            PatientEntity pExistante = pr.findById(id).get();

            pExistante.setNom( p.getNom() );
            pExistante.setPrenom( p.getPrenom() );
            pExistante.setAdresse( p.getAdresse() );
            pExistante.setVille(p.getVille());
            pExistante.setDatenaissance(p.getDatenaissance());
            pr.save( pExistante );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }
}
