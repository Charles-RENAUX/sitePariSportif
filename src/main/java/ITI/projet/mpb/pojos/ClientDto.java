package ITI.projet.mpb.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientDto
{
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String pseudo;
    private double solde;
    private Integer idRole;
    private Integer modif;

    @JsonCreator
    public ClientDto(@JsonProperty(value="id",required = true)Integer id,
                     @JsonProperty(value="nom",required = true)String nom,
                     @JsonProperty(value="prenom",required = true)String prenom,
                     @JsonProperty(value="email",required = true)String email,
                     @JsonProperty(value="pseudo",required = true)String pseudo,
                     @JsonProperty(value="solde",required = true)double solde,
                     @JsonProperty(value="idRole",required = true)Integer idRole,
                     @JsonProperty(value="modif",required = true)Integer modif)
    {
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.pseudo=pseudo;
        this.solde=0;
        this.idRole=idRole;
        this.modif=modif;
    }

    public Integer getId(){return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }


    public Integer getModif() {
        return modif;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public void setModif(Integer modif) {
        this.modif = modif;
    }
}
