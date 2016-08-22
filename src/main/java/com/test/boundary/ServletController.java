package com.test.boundary;

import com.test.lifecycle.UtilisateurDao;
import com.test.lifecycle.DaoFactory;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.entity.Utilisateur;
import com.test.mail.SendMail;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletController
 * https://openclassrooms.com/courses/creez-votre-application-web-avec-java-ee/outils-et-environnement-de-developpement
 *
 */
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {

    public static final String CHAMP_EMAIL = "email5";
    public static final String CHAMP_PASS = "motdepasse";
    public static final String CHAMP_CONF = "confirmation";
    public static final String CHAMP_NOM = "nom";
    public static final String CHAMP_PRENOM = "prenom";
    public static final String ATT_ERREURS = "erreurs";
    public static final String ATT_RESULTAT = "resultat";

    private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;

    private com.test.control.EnregistrerMBJSP modelEnregistrerJSP = new com.test.control.EnregistrerMBJSP();

    @Override
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* Récupération et destruction de la session en cours */
        HttpSession session = request.getSession();

        String commande = request.getParameter("commande");
        String login = request.getParameter("login");
        System.out.println("DEBUG :-------------------> commande=" + commande);
        System.out.println("DEBUG :-------------------> login=" + login);

        if (commande != null && commande.equals("mail_create")) {
            doPost(request, response);
            return;
        }

        request.setAttribute("utilisateurs", utilisateurDao.lister());
        System.out.println(" DEBUG : Test.java : messagemessagemessagexxxxxxxxxxxxxxxxxxxxxxx " + utilisateurDao.lister());

        List<Utilisateur> utilisateurs = utilisateurDao.lister();

        System.out.println("Test SERVLET : ==> Liste des elements dans la table utilisateur");
        for (Utilisateur temp : utilisateurs) {
            System.out.println(temp);
        }

        this.getServletContext().getRequestDispatcher("/JSP/enregistrer.jsp").forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Utilisé pour définir la COMMAND dans  switch (...) case :
        String commande = request.getParameter("commande");
        String login = request.getParameter("login");
        System.out.println("debuggg++++++++++++++++++++++++commande= " + commande);
        System.out.println("debuggg+++++++++++++++++++++++++++login= " + login);

        switch (commande) {

            case "create": {

                /* Récupération de la session en cours */
                HttpSession session = request.getSession();
                session.setAttribute("nom", request.getParameter(CHAMP_NOM));
                session.setAttribute("prenom", request.getParameter(CHAMP_PRENOM));

                String resultat;
                Map<String, String> erreurs = new HashMap<String, String>();
                /* Récupération des champs du formulaire. */
                String email2 = request.getParameter(CHAMP_EMAIL);
                String motDePasse = request.getParameter(CHAMP_PASS);
                String confirmation = request.getParameter(CHAMP_CONF);
                String nom = request.getParameter(CHAMP_NOM);
                String prenom = request.getParameter(CHAMP_PRENOM);

                /* Validation du champ email2. */
                try {
                    modelEnregistrerJSP.validationEmail(email2);
                } catch (Exception e) {
                    erreurs.put(CHAMP_EMAIL, e.getMessage());
                }

                /* Validation des champs mot de passe et confirmation. */
                try {
                    modelEnregistrerJSP.validationMotsDePasse(motDePasse, confirmation);
                } catch (Exception e) {
                    erreurs.put(CHAMP_PASS, e.getMessage());
                }

                /* Validation du champ nom. */
                try {
                    modelEnregistrerJSP.validationNom(nom);
                } catch (Exception e) {
                    erreurs.put(CHAMP_NOM, e.getMessage());
                }

                boolean loginExistTemp = utilisateurDao.verifExistLoginTemp(email2);

                if (loginExistTemp == true) {
                    session.setAttribute("loginExistTemp", "true");
                    this.getServletContext().getRequestDispatcher("/JSP/mail_exist.jsp").forward(request, response);
                    return;
                }

                boolean loginExist = utilisateurDao.verifExistLogin(email2);

                if (loginExist == true) {
                    session.setAttribute("loginExist", "true");
                    this.getServletContext().getRequestDispatcher("/JSP/mail_exist.jsp").forward(request, response);
                    return;
                }

                /* Initialisation du résultat global de la validation. */
                if (erreurs.isEmpty()) {
                    resultat = "Succès de l'inscription.";

                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setLogin(email2);
                    utilisateur.setPassword(motDePasse);
                    utilisateur.setNom(nom);
                    utilisateur.setPrenom(prenom);

                    utilisateurDao.create(utilisateur);

                    // ENVOYER UN MAIL de validation d'inscrition
                    new SendMail().sendMail(utilisateur);

                    request.setAttribute("Message", "resultMessageMail");
                    request.setAttribute("email", email2);
                    request.setAttribute("nom", nom);
                    request.setAttribute("prenom", prenom);

                    request.setAttribute("session", session);

                    getServletContext().getRequestDispatcher("/JSP/mail_info.jsp").forward(request, response);

                } else {

                    resultat = "Échec de l'inscription.";
                    /* Stockage du résultat et des messages d'erreur dans l'objet request */
                    request.setAttribute(ATT_ERREURS, erreurs);
                    request.setAttribute(ATT_RESULTAT, resultat);

                    /* Transmission de la paire d'objets request/response à notre JSP */
                    this.getServletContext().getRequestDispatcher("/JSP/enregistrer.jsp").forward(request, response);
                }

            }
            break;

            case "mail_create": {  // utilisé pour la validation de l'inscription par mail

                String mail = request.getParameter("login");
                utilisateurDao.validerInscriptionParMail(mail);
                this.getServletContext().getRequestDispatcher("/JSP/login.jsp").forward(request, response);

            }
            break;

            case "read": {
                // READ
            }
            break;

            case "update": {
                // 0. detecte que la requete provient de update.jsp et non pas de lister.jsp    
                String cmd = request.getParameter("cmd");

                if (cmd.equals("lister.jsp")) {
                    // 1. recuperer l'id de l'utilisateur qui vient de lister.jsp    
                    String id = request.getParameter("id");

                    System.out.println("com.octest.servlets.Test.doPost() ID= " + id);

                    // 2. avec l'id de l'utlisateur faire un read dans la base mysql
                    // 3. recuperer l'id, nom prenom login password de l'utilisateur selectionner et les envoyer dans request vers update.jsp
                    Utilisateur utilisateur = utilisateurDao.read(id);

                    request.setAttribute("login", utilisateur.getLogin());
                    request.setAttribute("password", utilisateur.getPassword());
                    request.setAttribute("nom", utilisateur.getNom());
                    request.setAttribute("prenom", utilisateur.getPrenom());

                    this.getServletContext().getRequestDispatcher("/JSP/update.jsp").forward(request, response);

                } else if (cmd.equals("update.jsp")) {

                    Utilisateur utilisateur = new Utilisateur();

                    utilisateur.setLogin(request.getParameter("login"));
                    utilisateur.setPassword(request.getParameter("password"));
                    utilisateur.setNom(request.getParameter("nom"));
                    utilisateur.setPrenom(request.getParameter("prenom"));

                    utilisateurDao.update(utilisateur);

                    request.setAttribute("utilisateurs", utilisateurDao.lister());
                    this.getServletContext().getRequestDispatcher("/JSP/lister.jsp").forward(request, response);

                }
            }
            break;

            case "delete": {
                String id = request.getParameter("id");
                utilisateurDao.delete(id);

                request.setAttribute("utilisateurs", utilisateurDao.lister());

                this.getServletContext().getRequestDispatcher("/JSP/lister.jsp").forward(request, response);

            }
            break;

            case "login": {

                login = request.getParameter("login");
                String password = request.getParameter("password");
                if (login == null && password == null) {
                    this.getServletContext().getRequestDispatcher("/JSP/login.jsp").forward(request, response);
                    return; // ATTENTION il faut obliger le control de flow de sortir de la methode doGet
                    // SI NON 
                    //java.lang.IllegalStateException: Cannot forward after response has been committed
                    //at org.apache.catalina.core.ApplicationDispatcher.doDispatch(ApplicationDispatcher.java:448)
                    //
                }
                boolean b = utilisateurDao.login(login, password);
                System.out.println("com.octest.servlets.Test.doPost() utilisateurDao.login= " + b);
                if (b == true) {
                    request.setAttribute("utilisateurs", utilisateurDao.lister());
                    this.getServletContext().getRequestDispatcher("/JSP/lister.jsp").forward(request, response);

                } else if (b == false) {

                    this.getServletContext().getRequestDispatcher("/JSP/enregistrer.jsp").forward(request, response);

                }
            }
            break;

            case "pwdperdu": {

                new SendMail().sendMailPwdperdu(request.getParameter("login"));

                this.getServletContext().getRequestDispatcher("/JSP/mail_pwdperdu.jsp").forward(request, response);

            }
            break;

            case "logout": {
                HttpSession session = request.getSession();
                session.invalidate();

                this.getServletContext().getRequestDispatcher("/JSP/enregistrer.jsp").forward(request, response);
            }

            case "envoyer_mail": {

                if (request.getParameter("listfrom") != null && request.getParameter("listfrom").equals("true")) {
                    getServletContext().getRequestDispatcher("/JSP/mail_form.jsp").forward(request, response);
                    return;
                } else {

                    new SendMail().sendMailAdvance(request.getParameter("emailTo"), request.getParameter("subject"), request.getParameter("content"));

                    getServletContext().getRequestDispatcher("/JSP/mail_info.jsp").forward(request, response);
                }

            }

            default: {
            }
            break;
        }
    }
}
