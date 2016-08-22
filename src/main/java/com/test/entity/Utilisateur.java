package com.test.entity;

 

public class Utilisateur {
    
     private String login;
    private String password;
    private String nom;
    private String prenom;
   
    public void setLogin(String login) {
        this.login = login;
    }
     
    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
    
    @Override
    public String toString() {
        return "Utilisateur{ login="+login+", password="+password+", nom="+nom+", prenom="+prenom+"}";
    }
    
    
}