package ro.pub.cs.aipi.lab07.graphicuserinterface;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab07.entities.Record;
import ro.pub.cs.aipi.lab07.general.Constants;
import ro.pub.cs.aipi.lab07.general.Utilities;

public class ClientGraphicUserInterface {
	
    final private static DataBaseWrapper dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
    
    public ClientGraphicUserInterface() { }
    
    public static String getBookAuthors(String book_id) {
        String result = new String();
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add("GROUP_CONCAT(CONCAT(w.first_name,' ',w.last_name))");
            ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent("writer w, author a",attributes,"a.book_id=\'"+book_id+"\' AND w.id=a.writer_id", null, null);
            if (tableContent == null || tableContent.get(0) == null || tableContent.get(0).get(0) == null)
                return "-";
            return tableContent.get(0).get(0);
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: "+sqlException.getMessage());
            if (Constants.DEBUG)
            	sqlException.printStackTrace();
        }
        return result;
    }
    
    public static String getBookDetails(String book_id) {
        String result = new String();
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add("b.title");
            attributes.add("ph.name");
            attributes.add("b.printing_year");
            ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent("book b, publishing_house ph",attributes,"b.id=\'"+book_id+"\' AND ph.id=b.publishing_house_id", null, null);
            if (tableContent == null || tableContent.get(0) == null || tableContent.get(0).get(0) == null)
                return "-";
            ArrayList<String> details = tableContent.get(0);
            return details.get(0)+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;("+details.get(1)+","+details.get(2)+")<br/>&nbsp;&nbsp;&nbsp;&nbsp;";
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: "+sqlException.getMessage());
            if (Constants.DEBUG)
            	sqlException.printStackTrace();
        }
        return result;
    }
        
    public static String getBookPrice(String book_id) {
        String result = new String();
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add(Constants.PRICE);
            ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent(Constants.BOOKS_TABLE,attributes,dataBaseWrapper.getTablePrimaryKey(Constants.BOOKS_TABLE)+"=\'"+book_id+"\'",null,null);
            if (tableContent == null || tableContent.get(0) == null || tableContent.get(0).get(0) == null)
                return "-";
            return tableContent.get(0).get(0);
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: "+sqlException.getMessage());
            if (Constants.DEBUG)
            	sqlException.printStackTrace();
        }
        return result;
    }
    
    public static ArrayList<String> getDistinctContent(String tableName, String attribute) {
        ArrayList<String> result = new ArrayList<>();
        result.add(Constants.ALL);
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add("DISTINCT("+attribute+")");
            ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent(tableName, attributes, null, null, null);
            tableContent.stream().forEach((tableRow) -> {
                result.add(tableRow.get(0));
            });
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: "+sqlException.getMessage());
            if (Constants.DEBUG)
            	sqlException.printStackTrace();
        }
        return result;
    }
    
    public static void displayClientGraphicUserInterface(String userDisplayName, String errorMessage, String currentTableName, String currentCollection, String currentDomain, ArrayList<Record> shoppingCart, PrintWriter printWriter) {
        String content = new String();
        content += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n";
        content += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
        content += "    <head>\n";
        content += "        <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n";
        content += "        <title>"+Constants.APPLICATION_NAME+"</title>\n";
        content += "        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/bookstore.css\" />\n"; 
        content += "        <link rel=\"icon\" type=\"image/x-icon\" href=\"./images/favicon.ico\" />\n";  
        content += "    </head>\n";       
        content += "    <body>\n";
        content += "        <h2 style=\"text-align: center\">"+Constants.APPLICATION_NAME.toUpperCase()+"</h2>\n";
        content += "        <form action=\"ClientServlet\" method=\"post\" name=\"formular\">\n";
        content += "            <p style=\"text-align:right\">\n";
        content += "                "+Constants.WELCOME_MESSAGE+userDisplayName+"\n";
        content += "                <br/>\n";
        content += "                <input type=\"image\" name=\""+Constants.SIGNOUT.toLowerCase()+"\" value=\""+Constants.SIGNOUT+"\" src=\"./images/user_interface/signout.png\" />\n";
        content += "                <br/>\n";
        content += "            </p>\n";
        content += "            <h2 style=\"text-align: center\">"+Constants.CLIENT_SERVLET_PAGE_NAME+"</h2>\n";
        content += "            <table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;\">\n";
        content += "                <tbody>\n";
        content += "                    <tr>\n";
        content += "                        <td style=\"width: 20%; text-align: left; vertical-align: top;\">\n";
        content += "                            <div id=\"wrapperrelative\">\n";
        content += "                                <div id=\"wrappertop\"></div>\n";
        content += "                                <div id=\"wrappermiddle\">\n";
        content += "                                    <table border=\"0\" cellpadding=\"4\" cellspacing=\"1\">\n";
        content += "                                        <tbody>\n";
        content += "                                            <tr>\n";
        content += "                                                <td>"+Constants.COLLECTION+"</td>\n";
        content += "                                                <td>\n";
        content += "                                                    <select name=\"selectedCollection\" onchange=\"document.formular.submit()\" style=\"width: 100%\">\n";
        for (String collection: getDistinctContent(Constants.COLLECTIONS_TABLE, "name"))
            if (collection.equals(currentCollection))
                content += "                                                        <option value=\""+collection+"\" selected=\"selected\">"+collection+"</option>\n";
            else
                content += "                                                        <option value=\""+collection+"\">"+collection+"</option>\n";
        content += "                                                    </select>\n";       
        content += "                                                </td>\n";
        content += "                                            </tr>\n";
        content += "                                            <tr><td colspan=\"2\">&nbsp;</td></tr>\n";
        content += "                                            <tr>\n";
        content += "                                                <td>"+Constants.DOMAIN+"</td>\n";
        content += "                                                <td>\n";
        content += "                                                    <select name=\"selectedDomain\" onchange=\"document.formular.submit()\" style=\"width: 100%\">\n";
        for (String domain: getDistinctContent(Constants.DOMAINS_TABLE,"name"))
            if (domain.equals(currentDomain))
                content += "                                                        <option value=\""+domain+"\" selected=\"selected\">"+domain+"</option>\n";
            else
                content += "                                                        <option value=\""+domain+"\">"+domain+"</option>\n";
        content += "                                                    </select>\n";
        content += "                                                </td>\n";
        content += "                                            </tr>\n";
        content += "                                        </tbody>\n";
        content += "                                    </table>\n";
        content += "                                </div>\n";
        content += "                                <div id=\"wrapperbottom\"></div>\n";
        content += "                            </div>\n";
        content += "                        </td>\n";
        content += "                        <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>\n";
        content += "                        <td style=\"width: 60%; text-align: center;\">\n";
        try {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                content += "                            "+errorMessage + "\n";
                content += "                            <br/>\n";
                content += "                            <br/>\n";
            }
            ArrayList<String> attributes = dataBaseWrapper.getTableColumns(currentTableName);
            String whereClause = new String();
            if (!currentCollection.equals(Constants.ALL)) {
                String primaryKeyAttribute = dataBaseWrapper.getTablePrimaryKey(Constants.COLLECTIONS_TABLE);
                whereClause += primaryKeyAttribute+" IN (SELECT "+primaryKeyAttribute+" FROM "+Constants.COLLECTIONS_TABLE+" WHERE name=\'"+currentCollection+"\')";
            }
            if (!currentDomain.equals(Constants.ALL)) {
                String primaryKeyAttribute = dataBaseWrapper.getTablePrimaryKey(Constants.DOMAINS_TABLE);
                whereClause += (whereClause.length()!=0?"AND ":"")+primaryKeyAttribute+" IN (SELECT "+primaryKeyAttribute+" FROM "+Constants.DOMAINS_TABLE+" WHERE name=\'"+currentDomain+"\')";
            }
            ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent(currentTableName, attributes, (whereClause.length()!=0?whereClause:null), null, null);
            int primayKeyIndex = 0;
            content += "                            <table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"margin: 0px auto;\">\n";
            content += "                                <tbody>\n";
            for (ArrayList<String> tableRow: tableContent) {
                String currentPrimaryKey = tableRow.get(primayKeyIndex);
                content += "                                    <tr>\n";
                content += "                                        <td>\n";
                content += "                                            <div id=\"wrappertop\"></div>\n";
                content += "                                            <div id=\"wrappermiddle\">\n";                
                content += "                                                <table border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\n";
                content += "                                                    <tbody>\n";
                content += "                                                        <tr>\n";
                content += "                                                            <td style=\"vertical-align: top;\"><img src=\"./images/book_covers/"+Utilities.removeSpaces(tableRow.get(1)).toLowerCase()+".jpg\"/></td>\n";
                content += "                                                            <td>&nbsp;</td>\n";
                content += "                                                            <td style=\"width: 100%; background: #ebebeb; text-align: left;\">\n";
                content += "                                                                "+Constants.AUTHORS+getBookAuthors(currentPrimaryKey)+"\n";
                content += "                                                                <br/>\n";
                int currentIndex = 0;
                for (String tableColumn: tableRow) {
                    if (currentIndex != 0) {
                        content += "                                                                "+attributes.get(currentIndex)+": "+tableColumn+"\n";
                        content += "                                                                <br/>\n";
                    }
                    currentIndex++;
                }
                content += "                                                            </td>\n";
                content += "                                                            <td>&nbsp;</td>\n";
                content += "                                                            <td style=\"vertical-align: middle;\">\n";
                content += "                                                                <table>\n";
                content += "                                                                    <tr>\n";
                content += "                                                                        <td>\n";
                content += "                                                                            <input type=\"text\" name=\""+Constants.COPIES.toLowerCase()+"_"+currentPrimaryKey+"\" size=\"3\"/>\n";
                content += "                                                                            <br/>\n";
                content += "                                                                            <input type=\"image\" name=\""+Constants.ADD_BUTTON_NAME.toLowerCase()+"_"+currentPrimaryKey+"\" value=\""+Constants.ADD_BUTTON_NAME+"\" src=\"./images/user_interface/add_to_shopping_cart.png\"/>\n";
                content += "                                                                        </td>\n";
                content += "                                                                    </tr>\n";
                content += "                                                                </table>\n";
                content += "                                                            </td>\n";
                content += "                                                        </tr>\n";
                content += "                                                    </tbody>\n";
                content += "                                                </table>\n";
                content += "                                            </div>\n";
                content += "                                            <div id=\"wrapperbottom\"></div>\n";           
                content += "                                        </td>\n";
                content += "                                    </tr>\n";
                content += "                                    <tr>\n";
                content += "                                        <td>&nbsp;</td>\n";
                content += "                                    </tr>\n";
            }
            content += "                                </tbody>\n";
            content += "                            </table>\n";
            content += "                        </td>\n";
            content += "                        <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>\n";
            content += "                        <td style=\"width: 20%; text-align: left; vertical-align: top\">\n";
            content += "                            <div id=\"wrappertop\"></div>\n";
            content += "                            <div id=\"wrappermiddle\">\n";
            content += "                                <table style=\"width: 100%\">\n";
            content += "                                    <tr>\n";
            content += "                                        <td style=\"text-align: center\">"+Constants.SHOPPING_CART+" ("+ shoppingCart.size() +") </td>\n";
            content += "                                    </tr>\n";
            if (!shoppingCart.isEmpty()) {
                // TO DO (exercitiul 8): obtine continut cos de cumparaturi
                double shoppingCartValue = 0.0;
                content += "                                    <tr>\n";
                content += "                                        <td>\n";
                content += "                                            <table border=\"0\" cellpadding=\"4\" cellspacing=\"1\" style=\"width: 100%; background: #ffffff;\">\n";
                content += "                                                <tbody>\n";
                for (Record shoppingCartContent: shoppingCart) {
                    double currentBookValue = Double.parseDouble(getBookPrice(shoppingCartContent.getAttribute()));
                    currentBookValue *= Integer.parseInt(shoppingCartContent.getValue());
                    content += "                                                    <tr style=\"background: #ebebeb;\">\n";
                    content += "                                                        <td>"+shoppingCartContent.getValue()+" x "+getBookDetails(shoppingCartContent.getAttribute())+"="+currentBookValue+"</td>\n";
                    content += "                                                    </tr>\n";
                    shoppingCartValue += currentBookValue;
                }
                content += "                                                    <tr style=\"background: #ebebeb;\"><td></td></tr>\n";
                content += "                                                    <tr style=\"background: #ebebeb;\"><td>"+Constants.ORDER_TOTAL+"<b>"+shoppingCartValue+"</b></td></tr>\n";
                content += "                                                </tbody>\n";
                content += "                                            </table>\n";
                content += "                                        </td>\n";
                content += "                                    </tr>\n";
                // TO DO (exercitiul 9): control pentru finalizare comanda
                content += "                                    <tr>\n";
                content += "                                        <td style=\"text-align: center\">\n";
                content += "                                            <input type=\"image\" name=\""+Utilities.removeSpaces(Constants.CANCEL_COMMAND.toLowerCase())+"\" value=\""+Constants.CANCEL_COMMAND+"\" src=\"./images/user_interface/remove_from_shopping_cart.png\" />\n";
                content += "                                            &nbsp;&nbsp;\n";
                content += "                                            <input type=\"image\" name=\""+Utilities.removeSpaces(Constants.COMPLETE_COMMAND.toLowerCase())+"\" value=\""+Constants.COMPLETE_COMMAND+"\" src=\"./images/user_interface/shopping_cart_accept.png\" />\n";
                content += "                                        </td>\n";
                content += "                                    </tr>\n";
            } else {
                content += "                                    <tr>\n";
                content += "                                        <td style=\"text-align: center;\">"+Constants.EMPTY_CART+"</td>\n";
                content += "                                    </tr>\n";
            } 
            content += "                                </table>\n";
            content += "                            </div>\n";
            content += "                            <div id=\"wrapperbottom\"></div>\n";
            content += "                        </td>\n";
            content += "                    </tr>\n";
            content += "                </tbody>\n";
            content += "            </table>\n";
        } catch (SQLException sqlException) {
            System.out.println ("An exception has occurred: "+sqlException.getMessage());
            if (Constants.DEBUG)
            	sqlException.printStackTrace();
        }       
        content += "        </form>\n";
        content += "    </body>\n";
        content += "</html>";  
        printWriter.println(content);
    }    
}
