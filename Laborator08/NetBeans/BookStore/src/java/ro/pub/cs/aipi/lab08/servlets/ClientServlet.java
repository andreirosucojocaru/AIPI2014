package ro.pub.cs.aipi.lab08.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ro.pub.cs.aipi.lab08.dataaccess.DataBaseException;
import ro.pub.cs.aipi.lab08.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab08.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab08.entities.Record;
import ro.pub.cs.aipi.lab08.general.Constants;
import ro.pub.cs.aipi.lab08.general.Utilities;

public class ClientServlet extends HttpServlet {

    final public static long serialVersionUID = 10021002L;

    private DataBaseWrapper dataBaseWrapper;

    private String selectedTable, selectedCollection, selectedDomain;
    private String userDisplayName;

    private ArrayList<Record> shoppingCart;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
        selectedTable = Constants.BOOKS_TABLE;
        selectedCollection = Constants.ALL;
        selectedDomain = Constants.ALL;
    }

    @Override
    public void destroy() {
        dataBaseWrapper.releaseResources();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session == null || session.getAttribute(Constants.IDENTIFIER) == null) {
            return;
        }
        userDisplayName = session.getAttribute(Constants.IDENTIFIER).toString();
        shoppingCart = (ArrayList<Record>) session.getAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()));
        if (shoppingCart == null) {
            shoppingCart = new ArrayList<>();
        }
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        Enumeration<String> parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameter = (String) parameters.nextElement();
            if (parameter.equals(Constants.SELECTED_COLLECTION)) {
                selectedCollection = request.getParameter(parameter);
            }
            if (parameter.equals(Constants.SELECTED_DOMAIN)) {
                selectedDomain = request.getParameter(parameter);
            }
            // TO DO (exercitiul 7): stabileste continut cos de cumparaturi
            if (parameter.contains(Constants.COPIES.toLowerCase()) && request.getParameter(parameter) != null && !request.getParameter(parameter).isEmpty()) {
                String book_id = parameter.substring(parameter.indexOf("_") + 1);
                String quantity = request.getParameter(parameter);
                ArrayList<String> attributes = new ArrayList<>();
                attributes.add(Constants.STOCK);
                int stock = 0;
                try {
                    stock = Integer.parseInt(dataBaseWrapper.getTableContent(selectedTable, attributes, dataBaseWrapper.getTablePrimaryKey(selectedTable) + "=\'" + book_id + "\'", null, null).get(0).get(0));
                } catch (SQLException sqlException) {
                    System.out.println("An exception has occurred: " + sqlException.getMessage());
                    if (Constants.DEBUG) {
                        sqlException.printStackTrace();
                    }
                }
                boolean found = false;
                for (Record shoppingCartContent : shoppingCart) {
                    if (shoppingCartContent.getAttribute().equals(book_id)) {
                        if (Integer.parseInt(quantity) != 0) {
                            if (Integer.parseInt(quantity) <= stock) {
                                shoppingCartContent.setValue(quantity);
                            } else {
                                errorMessage += "<span style=\"color: #ff0000;\">" + Constants.INVALID_COMMAND_ERROR1 + book_id + Constants.INVALID_COMMAND_ERROR2 + "</span>";
                            }
                        } else {
                            shoppingCart.remove(shoppingCartContent);
                        }
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (Integer.parseInt(quantity) <= stock) {
                        shoppingCart.add(new Record(book_id, quantity));
                    } else {
                        errorMessage += "<span style=\"color: #ff0000;\">" + Constants.INVALID_COMMAND_ERROR1 + book_id + Constants.INVALID_COMMAND_ERROR2 + "</span>";
                    }
                }
            }
            // TO DO (exercitiul 10): finalizare comanda -> continut cos de cumparaturi = {}, inregistrare comanda in baza de date, actualizare stocuri
            if (parameter.equals(Utilities.removeSpaces(Constants.CANCEL_COMMAND.toLowerCase()) + ".x")) {
                shoppingCart = new ArrayList<>();
            }
            if (parameter.equals(Utilities.removeSpaces(Constants.COMPLETE_COMMAND.toLowerCase()) + ".x")) {
                ArrayList<String> attributes;
                try {
                    ArrayList<String> insertValues = new ArrayList<>();
                    int invoice_id = dataBaseWrapper.getTablePrimaryKeyMaximumValue(Constants.INVOICE_TABLE) + 1;
                    insertValues.add(invoice_id + "");
                    insertValues.add(Utilities.generateInvoiceNumber());
                    insertValues.add("CURDATE()");
                    insertValues.add(Constants.STATE_ISSUED);
                    attributes = new ArrayList<>();
                    attributes.add(Constants.USER_PERSONAL_IDENTIFIER);
                    insertValues.add(dataBaseWrapper.getTableContent(Constants.USERS_TABLE, attributes, "CONCAT(first_name, ' ', last_name)=\'" + userDisplayName + "\'", null, null).get(0).get(0));
                    dataBaseWrapper.insertValuesIntoTable(Constants.INVOICE_TABLE, null, insertValues, false);
                    for (Record shoppingCartContent : shoppingCart) {
                        String book_id = shoppingCartContent.getAttribute();
                        String quantity = shoppingCartContent.getValue();
                        attributes = new ArrayList<>();
                        attributes.add(Constants.STOCK);
                        int stock = 0;
                        try {
                            stock = Integer.parseInt(dataBaseWrapper.getTableContent(selectedTable, attributes, dataBaseWrapper.getTablePrimaryKey(selectedTable) + "=\'" + book_id + "\'", null, null).get(0).get(0));
                        } catch (SQLException sqlException) {
                            System.out.println("An exception has occurred: " + sqlException.getMessage());
                            if (Constants.DEBUG) {
                                sqlException.printStackTrace();
                            }
                        }
                        if (Integer.parseInt(quantity) <= stock) {
                            ArrayList<String> updateAttributes = new ArrayList<>();
                            updateAttributes.add(Constants.STOCK);
                            ArrayList<String> updateValues = new ArrayList<>();
                            updateValues.add(stock - Integer.parseInt(quantity) + "");
                            dataBaseWrapper.updateRecordsIntoTable(selectedTable, updateAttributes, updateValues, dataBaseWrapper.getTablePrimaryKey(selectedTable) + "=\'" + book_id + "\'");
                            insertValues = new ArrayList<>();
                            insertValues.add(invoice_id + "");
                            insertValues.add(book_id);
                            insertValues.add(quantity);
                            dataBaseWrapper.insertValuesIntoTable(Constants.INVOICE_DETAIL_TABLE, null, insertValues, true);
                        } else {
                            errorMessage += "<span style=\"color: #ff0000;\">" + Constants.INVALID_COMMAND_ERROR1 + book_id + Constants.INVALID_COMMAND_ERROR2 + "</span><br/>";
                        }
                    }
                } catch (SQLException | DataBaseException exception) {
                    System.out.println("An exception has occurred: " + exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
                shoppingCart = new ArrayList<>();
            }
            session.setAttribute(Utilities.removeSpaces(Constants.SHOPPING_CART.toLowerCase()), shoppingCart);
            if (parameter.equals(Constants.SIGNOUT.toLowerCase() + ".x")) {
                Enumeration<String> requestParameters = request.getParameterNames();
                while (requestParameters.hasMoreElements()) {
                    request.removeAttribute(requestParameters.nextElement());
                }
                requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                }
                session.invalidate();
                selectedTable = Constants.BOOKS_TABLE;
                selectedCollection = Constants.ALL;
                selectedDomain = Constants.ALL;
                return;
            }
        }
        response.setContentType("text/html");
        requestDispatcher = getServletContext().getRequestDispatcher("/client.jsp");
        if (requestDispatcher != null) {
            request.setAttribute("userDisplayName", userDisplayName);
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("currentTableName", selectedTable);
            if (selectedCollection == null || selectedCollection.isEmpty()) {
                selectedCollection = Constants.ALL;
            }
            request.setAttribute("currentCollection", selectedCollection);
            if (selectedDomain == null || selectedDomain.isEmpty()) {
                selectedDomain = Constants.ALL;
            }
            request.setAttribute("currentDomain", selectedDomain);
            request.setAttribute("shoppingCart", shoppingCart);
            try {
                ArrayList<String> attributes = dataBaseWrapper.getTableColumns(selectedTable);
                request.setAttribute("attributes", attributes);
                String collectionPrimaryKey = dataBaseWrapper.getTablePrimaryKey(Constants.COLLECTIONS_TABLE);
                String primaryKey = dataBaseWrapper.getTablePrimaryKey(selectedTable);
                request.setAttribute("primaryKey", primaryKey);
                request.setAttribute("collectionPrimaryKey", collectionPrimaryKey);
                String domainPrimaryKey = dataBaseWrapper.getTablePrimaryKey(Constants.DOMAINS_TABLE);
                request.setAttribute("domainPrimaryKey", domainPrimaryKey);
            } catch (SQLException sqlException) {
                System.out.println("An exception has occurred: " + sqlException.getMessage());
                if (Constants.DEBUG) {
                    sqlException.printStackTrace();
                }
            }
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
