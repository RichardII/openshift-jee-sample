<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>

        <form method="post" action="ServletController">
            <input type="hidden" name="commande" value="login" id="commande" />

            <p>
                <label for="login">login : </label>
                <input type="text" name="login" id="login" />
            </p>
            <p>
                <label for="password">password : </label>
                <input type="text" name="password" id="password" />
            </p>       

            <input type="submit" name="Login" id="id" value="Login"/>
        </form>

        
        <form method="post" action="ServletController">
            <input type="hidden" name="commande" value="pwdperdu" id="commande" />

            <p>
                <label for="login">login : </label>
                <input type="text" name="login" id="login" />
            </p>

            <input type="submit" name="Login" id="login" value="mot de passe oublié, envoyez moi un mail"/>

        </form>

    </body>
</html>
