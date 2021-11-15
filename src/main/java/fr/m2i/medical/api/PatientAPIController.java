package fr.m2i.medical.api;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.services.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientAPIController {
     PatientService ps;

    public PatientAPIController( PatientService ps) {
        this.ps = ps;
    }

    @GetMapping(value = "", produces = "application/json")
    public Iterable<PatientEntity> getAll() {
        return ps.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public PatientEntity get(@PathVariable int id) {
        return ps.findPatient(id);
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public void delete(@PathVariable int id) {
        ps.delete(id);
    }
}
