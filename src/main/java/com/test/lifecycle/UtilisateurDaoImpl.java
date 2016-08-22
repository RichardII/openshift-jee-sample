package com.test.lifecycle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.test.entity.Utilisateur;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UtilisateurDaoImpl implements UtilisateurDao {

    private DaoFactory daoFactory;

    private static UtilisateurDaoImpl singleton = null;

    UtilisateurDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public static UtilisateurDao getInstanceSingleton(DaoFactory daoFactory) {

        if (singleton == null) {
            singleton = new UtilisateurDaoImpl(daoFactory);

        } else {
            return singleton;
        }
        return singleton;
    }

    @Override
    public void create(Utilisateur utilisateur) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO utilisateur_tmp(login, password, nom, prenom, date_validate) VALUES(?, ?, ?, ?, ?);");
            preparedStatement.setString(1, utilisateur.getLogin());
            preparedStatement.setString(2, utilisateur.getPassword());
            preparedStatement.setString(3, utilisateur.getNom());
            preparedStatement.setString(4, utilisateur.getPrenom());

            //DATE : http://jmdoudoux.developpez.com/cours/developpons/java/chap-utilisation_dates.php
            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            System.out.println("DEBUG: create: DateFormat=" + shortDateFormat);
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            System.out.println("DEBUG: create: " + sdf);

            calendar.add(Calendar.MONTH, 1);
            System.out.println("DEBUG: create: " + sdf.format(calendar.getTime()));
            /*
            Pour les opérations sur les dates, Calendar dispose des constantes suivantes:
Calendar.DATE: ajouter/soustraire un jour
Calendar.MONTH: ajouter/soustraire un mois
Calendar.YEAR: ajouter/soustraire une année
En 2ème paramètre de la fonction Add de Calendar, un nombre positif ajouter une durée, un nombre négatif soustrait une durée.

             */
            preparedStatement.setString(5, sdf.format(calendar.getTime()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * methode appelée lors de la validation par mail de l'inscription
     *
     * @param utilisateur
     */
    public void createValidate(String login) {

        // Si la date de peremption de la requête est valide
        // fait le transfert de la table tmp vers la table permanente
        // et supprime l'enregistrement dans la table tmp si tout c'est bien passé
        // Si problème alors envoi une page de explications 
    }

    public Utilisateur read(String login) {

        Connection connexion = null;

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setLogin("login");
        utilisateur.setPassword("password");
        utilisateur.setNom("nom");
        utilisateur.setPrenom("prenom");

        try {
            connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            String query = "SELECT login, password, nom, prenom FROM utilisateur  WHERE login='" + login + "';";
            ResultSet resultat = statement.executeQuery(query);

            System.out.println("com.octest.dao.UtilisateurDaoImpl.read()" + " " + "query=  " + query);
            System.out.println("com.octest.dao.UtilisateurDaoImpl.read()" + " " + resultat);

            ////////////////////////////////////////// ATTENTION zone dangereuse !! -------------------
            if (resultat.next()) { // ATTENTION il faut METTRE ce IF :  http://stackoverflow.com/questions/2120255/resultset-exception-before-start-of-result-set
                String login2 = resultat.getString("login");
                String password = resultat.getString("password");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");

                utilisateur.setLogin(login2);
                utilisateur.setPassword(password);
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);

            }

            System.out.println("UtilisateurDaoImpl.read=" + utilisateur);

        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            System.out.println("****************EXCEPTION:   UtilisateurDaoImpl.read()*************************");
            e.printStackTrace();
        }
        return utilisateur;
    }

    @Override
    public void update(Utilisateur utilisateur) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();

            String login = utilisateur.getLogin();
            String password = utilisateur.getPassword();

            String nom = utilisateur.getNom();
            String prenom = utilisateur.getPrenom();

            System.out.println("DEBUG: UtilisateurDaoImpl.update() : " + utilisateur);

            preparedStatement = connexion.prepareStatement("UPDATE utilisateur SET login= ?, password= ?, nom= ?, prenom= ? WHERE login=? ;");

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nom);
            preparedStatement.setString(4, prenom);
            preparedStatement.setString(5, login);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("com.octest.dao.UtilisateurDaoImpl.update()" + "EXCEPTION SQLException ---------------------");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String login) {
        Connection connexion = null;

        try {
            connexion = daoFactory.getConnection();

            String query = "DELETE FROM utilisateur WHERE login = ?";

            PreparedStatement preparedStmt = connexion.prepareStatement(query);

            preparedStmt.setString(1, login);

            preparedStmt.execute();

            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Utilisateur> search(String req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Utilisateur> lister() {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT login, password, nom, prenom FROM utilisateur;");

            while (resultat.next()) {

                String login = resultat.getString("login");
                String password = resultat.getString("password");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");

                Utilisateur utilisateur = new Utilisateur();

                utilisateur.setLogin(login);
                utilisateur.setPassword(password);
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);

                utilisateurs.add(utilisateur);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    @Override
    public boolean login(String login, String password) {

        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        System.out.println("com.octest.dao.UtilisateurDaoImpl.login() LOGIN=" + login + "  PASSWORD=" + password);

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT  login, password FROM utilisateur;");

            while (resultat.next()) {

                String login1 = resultat.getString("login");
                String password1 = resultat.getString("password");

                //EXCEPTION :  java.lang.NullPointerException
                if ((login != null && password != null)) {
                    if ((login.equals(login1)) && (password.equals(password1))) {
                        return true;
                    }
                }
            }
            connexion.close();
            
        } catch (SQLException e) {
            System.out.println("com.octest.dao.UtilisateurDaoImpl.login()=" + " EXCEPTION SQLException +++++++++++");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void validerInscriptionParMail(String mail) {

        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT login, password, nom, prenom, date_validate FROM utilisateur_tmp WHERE login='" + mail + "';");

            if (resultat.next()) {
                String login = resultat.getString("login");
                String password = resultat.getString("password");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String date_validate = resultat.getString("date_validate");

                System.out.println("date_validate= " + date_validate);

                Utilisateur utilisateur = new Utilisateur();

                utilisateur.setLogin(login);
                utilisateur.setPassword(password);
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);

                System.out.println("utilisateur =" + utilisateur);

                PreparedStatement preparedStatement = null;

                preparedStatement = connexion.prepareStatement("INSERT INTO utilisateur(login, password, nom, prenom) VALUES(?, ?, ?, ?);");
                preparedStatement.setString(1, utilisateur.getLogin());
                preparedStatement.setString(2, utilisateur.getPassword());
                preparedStatement.setString(3, utilisateur.getNom());
                preparedStatement.setString(4, utilisateur.getPrenom());
                preparedStatement.execute();

                // DELETE FROM `test_mvc_crud`.`utilisateur_tmp` WHERE `utilisateur_tmp`.`login` = \'toto@free.fr\'» 
                String query = "DELETE FROM utilisateur_tmp WHERE login = ?";

                PreparedStatement preparedStmt = connexion.prepareStatement(query);

                preparedStmt.setString(1, mail);

                preparedStmt.execute();

                connexion.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String pwdPerdu(String mail) {
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        String password = null;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT  password FROM utilisateur WHERE  login='" + mail + "';");

            if (resultat.next()) {

                password = resultat.getString("password");
            }

            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public boolean verifExistLoginTemp( String mail) {

        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
 
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT  login FROM utilisateur_tmp ;");

            while (resultat.next()) {

                String login1 = resultat.getString("login");
        
                if ((mail != null && login1 != null)) {
                    if ((mail.equals(login1))) {
                        return true;
                    }
                }
            }

            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return false;
    }

        public boolean verifExistLogin( String mail) {

        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
 
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT  login FROM utilisateur ;");

            while (resultat.next()) {

                String login1 = resultat.getString("login");
        
                if ((mail != null && login1 != null)) {
                    if ((mail.equals(login1))) {
                        return true;
                    }
                }
            }

            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return false;
    }
    
}
