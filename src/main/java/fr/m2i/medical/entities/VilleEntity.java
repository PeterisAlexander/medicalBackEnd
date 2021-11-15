package fr.m2i.medical.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ville", schema = "medical", catalog = "")
public class VilleEntity {
    private int id;
    private String pays;
    private String nom;
    private String codePostal;

    public VilleEntity() {
    }

    public VilleEntity(int id, String pays, String nom, String codePostal) {
        this.id = id;
        this.pays = pays;
        this.nom = nom;
        this.codePostal = codePostal;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pays")
    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "code_postal")
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VilleEntity that = (VilleEntity) o;
        return id == that.id && Objects.equals(pays, that.pays) && Objects.equals(nom, that.nom) && Objects.equals(codePostal, that.codePostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pays, nom, codePostal);
    }

    @Override
    public String toString() {
        return "VilleEntity{" +
                "id=" + id +
                ", pays='" + pays + '\'' +
                ", nom='" + nom + '\'' +
                ", codePostal='" + codePostal + '\'' +
                '}';
    }
}
