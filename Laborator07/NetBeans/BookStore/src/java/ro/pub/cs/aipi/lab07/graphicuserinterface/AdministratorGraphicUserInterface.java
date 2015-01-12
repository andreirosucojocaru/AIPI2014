package ro.pub.cs.aipi.lab07.graphicuserinterface;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab07.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab07.general.Constants;

public class AdministratorGraphicUserInterface {
	
    final private static DataBaseWrapper dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
    
    public AdministratorGraphicUserInterface() { }
    
    public static void displayAdministratorGraphicUserInterface(String userDisplayName, ArrayList<String> dataBaseStructure, String currentTableName, String primaryKeyValueForEditableRecord, PrintWriter printWriter) {
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
        content += "        <form action=\"AdministratorServlet\" method=\"post\"  name=\"formular\">\n";
        content += "            <p style=\"text-align: right\">\n";
        content += "                "+Constants.WELCOME_MESSAGE+userDisplayName+"\n";
        content += "                <br/>\n";
        content += "                <input type=\"image\" name=\""+Constants.SIGNOUT.toLowerCase()+"\" value=\""+Constants.SIGNOUT+"\" src=\"./images/user_interface/signout.png\"/>\n";
        content += "                <br/>\n";
        content += "            </p>\n";
        content += "            <h2>"+Constants.ADMINISTRATOR_SERVLET_PAGE_NAME+"</h2>\n"; 
        content += "            <p style=\"margin-left: auto; margin-right:auto\">\n";
        content += "                <select name=\"selectedTable\" onchange=\"document.formular.submit()\">\n";
        for (String tableName: dataBaseStructure)
            if (tableName.equals(currentTableName))
                content += "                    <option value=\""+tableName+"\" SELECTED>"+tableName+"</option>\n";
            else
                content += "                    <option value=\""+tableName+"\">"+tableName+"</option>\n";
        content += "                </select>\n";
        content += "            </p>\n";      
        try {
            content += "            <table style=\"background: #ffffff; margin: 0px auto;\" border=\"0\" cellpadding=\"4\" cellspacing=\"1\">\n";
            content += "                <tbody>\n"; 
            ArrayList<String> attributes = dataBaseWrapper.getTableColumns(currentTableName);
            int primayKeyIndex = 0;
            content += "                    <tr>\n";
            content = attributes.stream().map((attribute) -> "                        <th>"+attribute+"</th>\n").reduce(content, String::concat);
            content += "                        <th colspan=\"2\">"+Constants.OPERATION_TABLE_HEADER+"</th>\n";
            content += "                    </tr>\n";
            content += "                    <tr style=\"background: #ebebeb\">\n";
            for (String attribute: attributes)
                if (attribute.equals(dataBaseWrapper.getTablePrimaryKey(currentTableName)) && !currentTableName.equals(Constants.USERS_TABLE))
                    content += "                        <td><input type=\"text\" name=\""+attribute+"_"+Constants.ADD_BUTTON_NAME.toLowerCase()+"\" value=\""+(dataBaseWrapper.getTablePrimaryKeyMaximumValue(currentTableName)+1)+"\" disabled /></td>\n";
                else
                    content += "                        <td><input type=\"text\" name=\""+attribute+"_"+Constants.ADD_BUTTON_NAME.toLowerCase()+"\" /></td>\n";
            content += "                        <td style=\"text-align: center;\" colspan=\"2\"><input type=\"image\" name=\""+Constants.ADD_BUTTON_NAME.toLowerCase()+"\" value=\""+Constants.ADD_BUTTON_NAME+"\" src=\"./images/user_interface/insert.png\" /></td>\n";
            content += "                    </tr>\n";             
            ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent(currentTableName,attributes,null,null,null);
            for (ArrayList<String> tableRow: tableContent) {
                content += "                    <tr style=\"background:#ebebeb\">\n";
                String currentPrimaryKey = tableRow.get(primayKeyIndex);
                boolean isEditableRecord = (primaryKeyValueForEditableRecord != null && primaryKeyValueForEditableRecord.equals(currentPrimaryKey));
                int currentIndex = 0;
                for (String tableColumn: tableRow) {                    
                    if (isEditableRecord)                            
                        content += "                        <td><input type=\"text\" name=\""+attributes.get(currentIndex)+"_"+currentPrimaryKey+"\" value=\""+tableColumn+"\" "+((attributes.get(currentIndex).equals(dataBaseWrapper.getTablePrimaryKey(currentTableName)) && !currentTableName.equals(Constants.USERS_TABLE))?"disabled":"")+" /></td>\n";
                    else
                        content += "                        <td>"+tableColumn+"</td>\n";
                    currentIndex++;                    
                }
                content += "                        <td><input type=\"image\" name=\""+Constants.UPDATE_BUTTON_NAME.toLowerCase()+(isEditableRecord?"2":"1")+"_"+currentPrimaryKey+"\" value=\""+Constants.UPDATE_BUTTON_NAME+"\" src=\"./images/user_interface/update.png\" /></td>\n";
                content += "                        <td><input type=\"image\" name=\""+Constants.DELETE_BUTTON_NAME.toLowerCase()+"_"+currentPrimaryKey+"\" value=\""+Constants.DELETE_BUTTON_NAME+"\" src=\"./images/user_interface/delete.png\" /></td>\n";
                content += "                    </tr>\n";
            }           
            content += "                </tbody>\n";
            content += "            </table>\n";
        } catch (SQLException sqlException) {
            System.out.println ("An exception has occurred: "+sqlException);
            if (Constants.DEBUG)
            	sqlException.printStackTrace();
        }
        content += "        </form>\n";
        content += "    </body>\n";
        content += "</html>\n";        
        printWriter.println(content);
    }    
}
