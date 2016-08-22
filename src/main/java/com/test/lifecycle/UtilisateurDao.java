package com.test.lifecycle;

import java.util.List;
import com.test.entity.Utilisateur;


/**
 * CRUD (pour create, read, update, delete) (parfois appelé SCRUD avec un "S" pour search)
 * Permet également de lister les éléments de la table utilisateur
 * Et aussi de se loguer 
 * 
 * @author karl
 */
public interface UtilisateurDao {
    void create( Utilisateur utilisateur );
    public Utilisateur read(String id);
    public void update(Utilisateur utilisateur);
    void delete(String id);
    List<Utilisateur> search(String req);
    List<Utilisateur> lister();
    public boolean login(String login, String password);
    public void validerInscriptionParMail(String mail);
    public String pwdPerdu(String mail);
    public boolean verifExistLoginTemp( String mail); 
    public boolean verifExistLogin( String mail);
}