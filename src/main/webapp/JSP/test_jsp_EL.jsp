
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
            <p>
        <% 
    	/* Création d'une liste de légumes et insertion de quatre éléments */
    	java.util.List<String> legumes5 = new java.util.ArrayList<String>();
        legumes5.add( "poireau" );
        legumes5.add( "haricot" );
        legumes5.add( "carotte");
        legumes5.add( "pomme de terre" );
        request.setAttribute( "legumes" , legumes5 );
        %>

        <!-- Les quatre syntaxes suivantes retournent le deuxième élément de la liste de légumes  -->
        ${ legumes.get(1) }<br />
        ${ legumes[3] }<br />
        ${ legumes['0'] }<br />
        ${ legumes["0"] }<br />
    </p>
        
    </body>
</html>
