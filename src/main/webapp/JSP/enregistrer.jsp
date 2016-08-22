
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
   <head>
        <meta charset="utf-8" />
        <title>Inscription</title>
        <link type="text/css" rel="stylesheet" href="/test_mvc_crud/CSS/form.css" />
    </head>
    <body>

        <h1>Enregistrement</h1>

        <form method="post" action="ServletController">
            
            <input type="hidden" name="commande" value="create" id="commande" />

            <fieldset>

                <legend>Inscription</legend>

                <p>Vous pouvez vous inscrire via ce formulaire.</p>


                <label for="email">Adresse email <span class="requis">*</span></label>

                <input type="email" id="email" name="email" value="<c:out value="${param.email5}"/>" size="20" maxlength="60" />

                <span class="erreur">${erreurs['email5']}</span>

                <br />


                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>

                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />

                <span class="erreur">${erreurs['motdepasse']}</span>

                <br />


                <label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>

                <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />

                <span class="erreur">${erreurs['confirmation']}</span>

                <br />


                <label for="nom">Nom d'utilisateur</label>

                <input type="text" id="nom" name="nom" value="<c:out value="${param.nom}"/>" size="20" maxlength="20" />

                <span class="erreur">${erreurs['nom']}</span>

                <br />
                
                 <label for="nom">Prenom d'utilisateur</label>

                <input type="text" id="prenom" name="prenom" value="<c:out value="${param.prenom}"/>" size="20" maxlength="20" />

                <span class="erreur">${erreurs['prenom']}</span>

                <br />

                <input type="submit" value="Inscription" class="sansLabel" />

                <br />

                <p class="${empty erreurs ? 'succes' : 'erreur'}">${resultat}</p>

            </fieldset>

        </form>

                
                
                
                
                
                
               <h2>Login si déjà enregistré : </h2> 
      
        <form method="post" action="ServletController">
            <input type="hidden" name="commande" value="login" id="commande" />

            <input type="submit" name="Login" id="login" value="Login"/>

        </form>

    </body>
</html>