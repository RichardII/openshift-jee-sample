<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <h1>Liste des utilisateurs</h1>

    <c:out value="${session}"/>   

    <%= session.getAttribute("nom")%>

    <ul>
        <c:forEach var="utilisateur" items="${ utilisateurs }">
            <li> <p> <c:out value="${ utilisateur.login }" />
                     <c:out value="${ utilisateur.password }" />
                     <c:out value="${ utilisateur.nom }" />
                     <c:out value="${ utilisateur.prenom }" /> 
                    
                     
                    

                <form method="post" action="ServletController">

                    <input type="hidden" name="commande" value="delete" id="commande" />

                    <input type="hidden" name="id"  value="${ utilisateur.login }" id="id" />

                    <input type="submit" value="supprimer"/>

                </form>

                <form method="post" action="ServletController">

                    <input type="hidden" name="commande" value="update" id="commande" />
                    <input type="hidden" name="cmd" value="lister.jsp" id="cmd" />

                    <input type="hidden" name="id"  value="${ utilisateur.login }" id="id" />

                    <input type="submit" value="update"/>

                </form>

                </p> 
            </li>

        </c:forEach>

    </ul>  

       <form method="post" action="ServletController">

        <input type="hidden" name="commande" value="envoyer_mail" id="commande" />
        
        <input type="hidden" name="listfrom" value="true" id="commande" />
        
        <input type="submit" value="SendMail"/>

    </form> 
    
    
    <form method="post" action="ServletController">

        <input type="hidden" name="commande" value="logout" id="commande" />

        <input type="submit" value="Logout"/>

    </form>

    
    
</html>
