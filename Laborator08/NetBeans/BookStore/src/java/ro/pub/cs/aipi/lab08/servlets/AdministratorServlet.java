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

public class AdministratorServlet extends HttpServlet {

    final public static long serialVersionUID = 10011001L;

    private DataBaseWrapper dataBaseWrapper;
    private ArrayList<String> dataBaseStructure;
    private String selectedTable;
    private String userDisplayName;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
            dataBaseStructure = dataBaseWrapper.getTableNames();
            selectedTable = dataBaseStructure.get(0);
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        dataBaseWrapper.releaseResources();
    }

    public ArrayList<String> getAttributes(String tableName, ArrayList<Record> records) {
        ArrayList<String> columns = null;
        try {
            columns = dataBaseWrapper.getTableColumns(tableName);
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
        if (columns == null) {
            return null;
        }
        ArrayList<String> result = new ArrayList<>();
        for (Record record : records) {
            for (String column : columns) {
                if (column.equals(record.getAttribute())) {
                    result.add(record.getAttribute());
                }
            }
        }
        return result;
    }

    public ArrayList<String> getValues(String tableName, ArrayList<Record> records) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> attributes = getAttributes(tableName, records);
        attributes.stream().forEach((attribute) -> {
            records.stream().filter((record) -> (attribute.equals(record.getAttribute()))).map((record) -> record.getValue()).map((value) -> {
                if (value == null || value.isEmpty()) {
                    value = Constants.INVALID_VALUE;
                }
                return value;
            }).forEach((value) -> {
                result.add(value);
            });
        });
        return result;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Record> insertRecords = new ArrayList<>();
        ArrayList<Record> updateRecords = new ArrayList<>();
        ArrayList<Record> deleteRecords = new ArrayList<>();
        ArrayList<Record> genericRecords = new ArrayList<>();
        Enumeration<String> parameters = request.getParameterNames();
        int operation = Constants.OPERATION_NONE;
        String primaryKeyAttribute = new String();
        try {
            primaryKeyAttribute = dataBaseWrapper.getTablePrimaryKey(selectedTable);
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
        String primaryKeyValue = new String();

        while (parameters.hasMoreElements()) {
            String parameter = (String) parameters.nextElement();
            if (parameter.equals(Constants.SIGNOUT.toLowerCase() + ".x")) {
                operation = Constants.OPERATION_LOGOUT;
            }
            if (parameter.equals(Constants.ADD_BUTTON_NAME.toLowerCase() + ".x")) {
                operation = Constants.OPERATION_INSERT;
            } else if (parameter.contains(Constants.UPDATE_BUTTON_NAME.toLowerCase() + "1_") && parameter.contains(".x")) {
                operation = Constants.OPERATION_UPDATE_PHASE1;
                primaryKeyValue = parameter.substring(parameter.indexOf("_") + 1, parameter.indexOf(".x"));
            } else if (parameter.contains(Constants.UPDATE_BUTTON_NAME.toLowerCase() + "2_") && parameter.contains(".x")) {
                operation = Constants.OPERATION_UPDATE_PHASE2;
                primaryKeyValue = parameter.substring(parameter.indexOf("_") + 1, parameter.indexOf(".x"));
            } else if (parameter.contains(Constants.DELETE_BUTTON_NAME.toLowerCase()) && parameter.contains(".x")) {
                operation = Constants.OPERATION_DELETE;
                primaryKeyValue = parameter.substring(parameter.indexOf("_") + 1, parameter.indexOf(".x"));
                deleteRecords.add(new Record(primaryKeyAttribute, primaryKeyValue));
            } else {
                genericRecords.add(new Record(parameter, request.getParameter(parameter)));
            }
            if (parameter.equals(Constants.SELECTED_TABLE)) {
                selectedTable = request.getParameter(parameter);
            }
        }
        RequestDispatcher requestDispatcher = null;
        response.setContentType("text/html");
        switch (operation) {
            case Constants.OPERATION_INSERT:
                genericRecords.stream().forEach((record) -> {
                    String attribute = record.getAttribute();
                    String value = record.getValue();
                    if (attribute.endsWith("_" + Constants.ADD_BUTTON_NAME.toLowerCase())) {
                        insertRecords.add(new Record(attribute.substring(0, attribute.lastIndexOf("_")), value));
                    }
                });
                try {
                    dataBaseWrapper.insertValuesIntoTable(selectedTable, getAttributes(selectedTable, insertRecords), getValues(selectedTable, insertRecords), false);
                } catch (SQLException | DataBaseException exception) {
                    System.out.println("An exception has occurred: " + exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
                break;
            case Constants.OPERATION_UPDATE_PHASE2:
                String whereClause = new String();
                for (Record record : genericRecords) {
                    String attribute = record.getAttribute();
                    String value = record.getValue();
                    if (attribute.endsWith("_" + primaryKeyValue)) {
                        if (attribute.startsWith(primaryKeyAttribute)) {
                            whereClause += primaryKeyAttribute + "=\'" + primaryKeyValue + "\'";
                        } else {
                            updateRecords.add(new Record(attribute.substring(0, attribute.lastIndexOf("_")), value));
                        }
                    }
                }
                try {
                    if (whereClause.isEmpty()) {
                        whereClause += primaryKeyAttribute + "=\'" + primaryKeyValue + "\'";
                    }
                    dataBaseWrapper.updateRecordsIntoTable(selectedTable, getAttributes(selectedTable, updateRecords), getValues(selectedTable, updateRecords), whereClause);
                } catch (SQLException | DataBaseException exception) {
                    System.out.println("An exception has occurred: " + exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
                break;
            case Constants.OPERATION_DELETE:
                try {
                    dataBaseWrapper.deleteRecordsFromTable(selectedTable, getAttributes(selectedTable, deleteRecords), getValues(selectedTable, deleteRecords), null);
                } catch (SQLException | DataBaseException exception) {
                    System.out.println("An exception has occurred: " + exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
                break;
            case Constants.OPERATION_LOGOUT:
                Enumeration<String> requestParameters = request.getParameterNames();
                while (requestParameters.hasMoreElements()) {
                    request.removeAttribute(requestParameters.nextElement().toString());
                }
                requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                if (requestDispatcher != null) {
                    requestDispatcher.forward(request, response);
                    return;
                }
                break;
        }

        HttpSession session = request.getSession(true);
        if (session == null || session.getAttribute(Constants.IDENTIFIER) == null) {
            return;
        }
        userDisplayName = session.getAttribute(Constants.IDENTIFIER).toString();
        if (operation == Constants.OPERATION_UPDATE_PHASE1) {
            request.setAttribute("primaryKeyValue", primaryKeyValue);
        }
        requestDispatcher = getServletContext().getRequestDispatcher("/administrator.jsp");
        if (requestDispatcher != null) {
            request.setAttribute("userDisplayName", userDisplayName);
            request.setAttribute("dataBaseStructure", dataBaseStructure);
            request.setAttribute("currentTableName", selectedTable);
            try {
                ArrayList<String> attributes = dataBaseWrapper.getTableColumns(selectedTable);
                request.setAttribute("attributes", attributes);
                String primaryKey = dataBaseWrapper.getTablePrimaryKey(selectedTable);
                request.setAttribute("primaryKey", primaryKey);
                if (dataBaseWrapper.isAutoGeneratedPrimaryKey(selectedTable)) {
                    int primaryKeyMaxValue = dataBaseWrapper.getTablePrimaryKeyMaximumValue(selectedTable) + 1;
                    request.setAttribute("primaryKeyMaxValue", primaryKeyMaxValue);
                }
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
