
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>ton mail existe coco</h1>

        <% if (session.getAttribute("loginExist")!= null && session.getAttribute("loginExist").equals("true")) {
                out.println("le mail exist dans la table utilisateur, votre nom: " + session.getAttribute("nom"));
            } 
        %>

        <% if (session.getAttribute("loginExistTemp")!= null && session.getAttribute("loginExistTemp").equals("true")) {
                out.println("le mail exist dans la table utilisateur_Temp, votre nom: " + session.getAttribute("nom"));
           }
        %>        

    </body>
</html>
