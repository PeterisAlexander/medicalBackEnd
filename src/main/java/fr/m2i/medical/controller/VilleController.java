package fr.m2i.medical.controller;


import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ville")
public class VilleController {

    @Autowired
    private VilleService vs;

    // http://localhost:8080/ville
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
        Page<VilleEntity> page = vs.listAll(currentPage);
        int totalItems = page.getNumberOfElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);

        List<VilleEntity> listVilles = page.getContent();
        model.addAttribute("villes", listVilles);

        return "ville/list_ville";
    }

    // http://localhost:8080/ville/add
    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("ville" , new VilleEntity() );
        return "ville/addEdit_ville";
    }

    @PostMapping(value = "/add")
    public String addPost( HttpServletRequest request , Model model ){
        // Récupération des paramètres envoyés en POST
        String nom = request.getParameter("nom");
        String codePostal = request.getParameter("codePostal");
        String pays = request.getParameter("pays");

        // Préparation de l'entité à sauvegarder
        VilleEntity v = new VilleEntity( nom , codePostal , pays );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            vs.addVille( v );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
            model.addAttribute("ville" , v );
            model.addAttribute("error" , e.getMessage() );
            return "ville/addEdit_ville";
        }
        return "redirect:/ville?success=true";
    }

    @RequestMapping( method = { RequestMethod.GET , RequestMethod.POST} , value = "/edit/{id}" )
    public String editGetPost( Model model , @PathVariable int id ,  HttpServletRequest request ){
        System.out.println( "Add Edit Ville" + request.getMethod() );

        if( request.getMethod().equals("POST") ){
            // Récupération des paramètres envoyés en POST
            String nom = request.getParameter("nom");
            String codePostal = request.getParameter("codePostal") ;
            String pays = request.getParameter("pays");

            // Préparation de l'entité à sauvegarder
            VilleEntity v = new VilleEntity( nom , codePostal , pays );

            // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
            try{
                vs.editVille( id , v );
            }catch( Exception e ){
                v.setId(  -1 ); // hack
                System.out.println( e.getMessage() );
                model.addAttribute("ville" , v );
                model.addAttribute("error" , e.getMessage() );
                return "ville/addEdit_ville";
            }
            return "redirect:/ville?success=true";
        }else{
            try{
                model.addAttribute("ville" , vs.findVille( id ) );
            }catch ( NoSuchElementException e ){
                return "redirect:/ville?error=Ville%20introuvalble";
            }

            return "ville/addEdit_ville";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete( @PathVariable int id ){
        String message = "?success=true";
        try{
            vs.delete(id);
        }catch ( Exception e ){
            message = "?error=Ville%20introuvalble";
        }
        return "redirect:/ville"+message;
    }

    public VilleService getVservice() {
        return vs;
    }

    public void setVservice(VilleService vservice) {
        this.vs = vservice;
    }

}