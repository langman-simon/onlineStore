package com.hyperion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(name = "adresse_livraison", nullable = false, length = 150)
    private String adresseLivraison;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(name = "mot_de_passe", nullable = false, length = 60)
    private String motDePasse;

    @Column(name = "telephone_secondaire", length = 20)
    private String telephoneSecondaire;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getTelephoneSecondaire() { return telephoneSecondaire; }
    public void setTelephoneSecondaire(String telephoneSecondaire) { this.telephoneSecondaire = telephoneSecondaire; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
}