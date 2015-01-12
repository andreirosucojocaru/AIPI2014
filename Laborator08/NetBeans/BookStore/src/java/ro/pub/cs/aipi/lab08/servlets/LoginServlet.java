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

import ro.pub.cs.aipi.lab08.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab08.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab08.general.Constants;

public class LoginServlet extends HttpServlet {

    final public static long serialVersionUID = 10001000L;

    private DataBaseWrapper dataBaseWrapper;
    private String userName, userPassword;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
    }

    @Override
    public void destroy() {
        dataBaseWrapper.releaseResources();
    }

    public boolean isLoginError(String userName, String userPassword) {
        return (userName != null && !userName.isEmpty() && userPassword != null && !userPassword.isEmpty() && getUserRole(userName, userPassword) == Constants.USER_NONE);
    }

    public int getUserRole(String userName, String userPassword) {
        int result = Constants.USER_NONE;
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add(Constants.USER_ROLE);
            ArrayList<ArrayList<String>> role = dataBaseWrapper.getTableContent(Constants.USERS_TABLE, attributes, Constants.USER_NAME + "=\'" + userName + "\' AND " + Constants.USER_PASSWORD + "=\'" + userPassword + "\'", null, null);
            if (role != null && !role.isEmpty() && role.get(0) != null && role.get(0).get(0) != null) {
                switch (role.get(0).get(0).toString()) {
                    case Constants.ADMINISTRATOR_ROLE:
                        return Constants.USER_ADMINISTRATOR;
                    case Constants.CLIENT_ROLE:
                        return Constants.USER_CLIENT;
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
        return result;
    }

    public String getUserDisplayName(String userName, String userPassword) {
        String result = new String();
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add("CONCAT(first_name, ' ', last_name)");
            ArrayList<ArrayList<String>> displayName = dataBaseWrapper.getTableContent(Constants.USERS_TABLE, attributes, Constants.USER_NAME + "=\'" + userName + "\' AND " + Constants.USER_PASSWORD + "=\'" + userPassword + "\'", null, null);
            if (displayName != null) {
                return displayName.get(0).get(0).toString();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> parameters = request.getParameterNames();
        boolean found = false;
        while (parameters.hasMoreElements()) {
            String parameter = (String) parameters.nextElement();
            if (parameter.equals(Constants.USER_NAME)) {
                found = true;
                userName = request.getParameter(parameter);
            }
            if (parameter.equals(Constants.USER_PASSWORD)) {
                found = true;
                userPassword = request.getParameter(parameter);
            }
        }
        if (!found) {
            userName = "";
            userPassword = "";
        }
        response.setContentType("text/html");
        RequestDispatcher requestDispatcher = null;
        if (getUserRole(userName, userPassword) != Constants.USER_NONE) {
            HttpSession session = request.getSession(true);
            session.setAttribute(Constants.IDENTIFIER, getUserDisplayName(userName, userPassword));
            switch (getUserRole(userName, userPassword)) {
                case Constants.USER_ADMINISTRATOR:
                    requestDispatcher = getServletContext().getRequestDispatcher("/AdministratorServlet");
                    break;
                case Constants.USER_CLIENT:
                    requestDispatcher = getServletContext().getRequestDispatcher("/ClientServlet");
                    break;
            }
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
                return;
            }
        }

        requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
        if (requestDispatcher != null) {
            request.setAttribute("error", isLoginError(userName, userPassword));
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
