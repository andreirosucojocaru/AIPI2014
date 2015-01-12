package ro.pub.cs.aipi.lab07.graphicuserinterface;

import java.io.PrintWriter;

import ro.pub.cs.aipi.lab07.general.Constants;

public class LoginGraphicUserInterface {
    
    public LoginGraphicUserInterface() { }
    
    public static void displayLoginGraphicUserInterface(boolean isLoginError, PrintWriter printWriter) {
    	String content = new String();
        content += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n";
        content += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
        content += "    <head>\n";
        content += "        <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n";
        content += "        <title>"+Constants.APPLICATION_NAME+"</title>\n";
        content += "        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/bookstore.css\" />\n";  
        content += "        <link rel=\"icon\" type=\"image/x-icon\" href=\"./images/favicon.ico\" />\n";  
        content += "    </head>\n";
        content += "    <body style=\"text-align: center\">\n";
        content += "        <h2>"+Constants.APPLICATION_NAME.toUpperCase()+"</h2>\n";
        content += "        <form action=\"LoginServlet\" method=\"post\">\n";
        content += "            <div id=\"wrapperabsolute\">\n";
        content += "                <div id=\"wrappertop\"></div>\n";
        content += "                <div id=\"wrappermiddle\">\n";
        content += "                    <h2>"+Constants.SIGNIN+"</h2>\n";
        content += "                    <div id=\"username_input\">\n";
        content += "                        <div id=\"field_inputleft\"></div>\n";
        content += "                        <div id=\"field_inputmiddle\">\n";
        content += "                            <input type=\"text\" name=\""+Constants.USER_NAME+"\" id=\"url\" value=\""+Constants.USER_NAME+"\" onclick=\"this.value = ''\">\n";
        content += "                            <img id=\"url_field\" src=\"./images/user_interface/username.png\" alt=\"\">\n";
        content += "                        </div>\n";
        content += "                        <div id=\"field_inputright\"></div>\n";
        content += "                    </div>\n";
        content += "                    <div id=\"password_input\">\n";
        content += "                        <div id=\"field_inputleft\"></div>\n";
        content += "                        <div id=\"field_inputmiddle\">\n";
        content += "                            <input type=\"password\" name=\""+Constants.USER_PASSWORD+"\" id=\"url\" value=\""+Constants.USER_PASSWORD+"\" onclick=\"this.value = ''\">\n";
        content += "                            <img id=\"url_field\" src=\"./images/user_interface/password.png\" alt=\"\">\n";
        content += "                        </div>\n";
        content += "                        <div id=\"field_inputright\"></div>\n";
        content += "                    </div>\n";
	content += "                    <div id=\"submit\">\n";
	content += "                        <input type=\"image\" src=\"./images/user_interface/signin.png\" id=\"submit2\" name=\""+Constants.SIGNIN.toLowerCase()+"\" value=\""+Constants.SIGNIN+"\">\n";
	content += "                    </div>\n";
        if (isLoginError)
            content += "                    <h3 style=\"color: red\">"+Constants.ERROR_USERNAME_PASSWORD+"</h3>\n";		
	content += "                </div>\n";
	content += "                <div id=\"wrapperbottom\"></div>\n";
	content += "            </div>\n";    
        content += "        </form>\n";      
        content += "    </body>\n";
        content += "</html>";
        printWriter.println(content);
    }
}
