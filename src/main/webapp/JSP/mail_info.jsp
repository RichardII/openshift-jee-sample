<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demande enregister</title>
    </head>
    <body>
        <h1>
            Validation de l'inscripyion par mail.
        </h1>
        
        <c:out value="${session}"/>   
       
       <%= session.getAttribute("nom") %> , <%= session.getAttribute("prenom") %> 
       
       
        <p>
            
            <% SimpleDateFormat dateHeureFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm"); 
        
              out.println(dateHeureFormat);
             %>
        </p>
        
        <p> Date :  <%= dateHeureFormat %></p>
        <p>
             Nous avons bien reçu votre demande d'inscription. 
            Veuillez consulter le mail envoyé. 
            Valider en allant sur l'URL fourni. Merci 
        </p>
        
        
        Infos :  <c:out value="${param.email}"/>
                 <c:out value="${param.nom}"/>
    </body>
</html>
