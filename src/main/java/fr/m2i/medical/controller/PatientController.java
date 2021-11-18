package fr.m2i.medical.controller;


import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.services.PatientService;
import fr.m2i.medical.services.VilleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService ps;

    private final VilleService vs;

    public PatientController(PatientService ps, VilleService vs) {
        this.ps = ps;
        this.vs = vs;
    }

    // http://localhost:8080/patient
    @GetMapping(value = "")
    public String list(Model model, HttpServletRequest request){
        String search = request.getParameter("search");
        model.addAttribute( "search" , search );

        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );

        return listByPage(model, 1);
    }

    @GetMapping("/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<PatientEntity> page = ps.listAll(currentPage);
        int totalItems = page.getNumberOfElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        List<PatientEntity> listPatients = page.getContent();
        model.addAttribute("listPatients", listPatients);

        return "patient/list_patient";
    }

    // http://localhost:8080/patient/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("villes" , vs.findAll() );
        model.addAttribute("patient" , new PatientEntity() );
        return "patient/addEdit_patient";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){
        // Récupération des paramètres envoyés en POST
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String naissance = request.getParameter("naissance");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int ville = Integer.parseInt(request.getParameter("ville"));

        // Préparation de l'entité à sauvegarder
        VilleEntity v = new VilleEntity();
        v.setId(ville);
        PatientEntity p = new PatientEntity( 0 , nom , prenom , Date.valueOf( naissance ) , email , telephone , adresse , v );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            ps.addPatient( p );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
            model.addAttribute("patient" , p );
            model.addAttribute("villes" , vs.findAll() );
            model.addAttribute("error" , e.getMessage() );
            return "patient/addEdit_patient";
        }
        return "redirect:/patient?success=true";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit( Model model , @PathVariable int id ){
        model.addAttribute("villes" , vs.findAll() );
        try {
            model.addAttribute("patient", ps.findPatient(id));
        }catch( NoSuchElementException e){
            return "redirect:/patient?error=Patient%20introuvalble";
        }

        return "patient/addEdit_patient";
    }

    @PostMapping(value = "/edit/{id}")
    public String editPost(  Model model , HttpServletRequest request , @PathVariable int id ){
        // Récupération des paramètres envoyés en POST
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String naissance = request.getParameter("naissance");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int ville = Integer.parseInt(request.getParameter("ville"));

        // Préparation de l'entité à sauvegarder
        VilleEntity v = new VilleEntity();
        v.setId(ville);
        PatientEntity p = new PatientEntity( 0 , nom , prenom , Date.valueOf( naissance ) , email , telephone , adresse , v );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            ps.editPatient( id , p );
        }catch( Exception e ){
            model.addAttribute("villes" , vs.findAll() );
            System.out.println( e.getMessage() );
            model.addAttribute( "patient" , p );
            model.addAttribute("error" , e.getMessage());
            return "patient/addEdit_patient";
        }
        return "redirect:/patient?success=true";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            ps.delete(id);
        }catch( Exception e ){
            message = "?error=Patient%20introuvable";
        }

        return "redirect:/patient" + message;
    }

}