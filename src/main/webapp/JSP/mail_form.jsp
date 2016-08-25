<%-- 
    http://atatorus.developpez.com/tutoriels/java/envoyer-recevoir-des-mails-avec-javamail/
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send an e-mail</title>
</head>
<body>
    <form  method="post"  action="ServletController">
        
            <input type="hidden" name="commande" value="envoyer_mail" id="commande" />
           <input type="hidden" name="listfrom" value="false" id="commande" />         
        <table border="0" width="35%" align="center">
            <caption><h2>Send New E-mail</h2></caption>
            <tr>
                <td width="50%">Destinataire address </td>
                <td><input type="text" name="emailTo" value="saisaisir votre mail"   size="50"/></td>
            </tr>
            <tr>
                <td>Subject </td>
                <td><input type="text" name="subject" value="subject" size="50"/></td>
            </tr>
            <tr>
                <td>Content </td>
                <td><textarea rows="10" cols="39" name="content" value="content"></textarea> </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                <input type="submit" value="Send"/></td>
            </tr>
        </table>
       
    </form>
            
            
            Attention : dans les consoles web des xxxBox ADSL, configurer :  Blocage du protocole SMTP sortant
</body>
</html>
