<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Mise à jour (update)</h1>
        
         <c:out value="${session}"/>   
       
       <%= session.getAttribute("nom") %> , <%= session.getAttribute("prenom") %> 
       
       
         
        <form method="post" action="ServletController">
            <input type="hidden" name="commande" value="update" id="commande" />
            <input type="hidden" name="cmd" value="update.jsp" id="cmd" />
            <input type="hidden" name="id" value="<%= request.getAttribute("id") %>" id="id" /> 
         
            <p>
                <label for="nom">Nom : </label>
                <input type="text" name="nom" value="<%= request.getAttribute("nom") %>" id="nom" />
            </p>
            <p>
                <label for="prenom">Prénom : </label>
                <input type="text" name="prenom"  value="<%= request.getAttribute("prenom") %>" id="prenom" />
            </p>

            <p>
                <label for="login">login : </label>
                <input type="text" name="login" value="<%= request.getAttribute("login") %>" id="login" />
            </p>
            <p>
                <label for="password">password : </label>
                <input type="text" name="password" value="<%= request.getAttribute("password") %>" id="password" />
            </p>       

            <input type="submit" name="Update" id="id" value="Update"/>
        </form>
        
    </body>
</html>
