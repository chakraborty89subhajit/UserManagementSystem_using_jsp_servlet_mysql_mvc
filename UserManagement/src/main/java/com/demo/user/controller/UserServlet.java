package com.demo.user.controller;

import com.demo.user.dao.UserDAO;
import com.demo.user.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet ("/")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;


    public void init(){
        userDAO=new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        String action =request.getServletPath();

        try{
            switch(action){
                case "/new":
                    showNewForm(request,response);
                    break;

                case "/insert":
                    insertUser(request,response);
                    break;

                case "/delete":
                    deleteUser(request,response);
                    break;

                case "/edit":
                    showEditForm(request,response);
                    break;

                case "/update":
                    updateUser(request,response);
                    break;

                default:
                    listUser(request,response);
            }

        }catch(Exception e){
throw new ServletException(e);
        }
    }

    private void listUser(HttpServletRequest request,HttpServletResponse response)
            throws SQLException,IOException,ServletException{
        List<User> listUser=userDAO.selectAllUsers();
        request.setAttribute("listUser",listUser);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/views/allUsersList.jsp");
        dispatcher.forward(request,response);
    }


    private void showNewForm(HttpServletRequest request,HttpServletResponse response)
            throws SQLException,IOException,ServletException{

        RequestDispatcher dispatcher=request.getRequestDispatcher("views/addNewUser.jsp");
        dispatcher.forward(request,response);
    }

    private void showEditForm(HttpServletRequest request,HttpServletResponse response)
            throws SQLException,IOException,ServletException{
        int id= Integer.parseInt(request.getParameter("id"));
        User existingUser=userDAO.selectUser(id);


        RequestDispatcher dispatcher=request.getRequestDispatcher("views/userEditForm.jsp");
        request.setAttribute("user",existingUser);
        dispatcher.forward(request,response);
    }
private void insertUser(HttpServletRequest request,HttpServletResponse response)
    throws SQLException,IOException{
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String country=request.getParameter("country");

        User newUser=new User(name,email,country);

        userDAO.insertUser(newUser);

        response.sendRedirect("list");

}

private void updateUser(HttpServletRequest request,HttpServletResponse response) throws SQLException,IOException{
   int id= Integer.parseInt(request.getParameter("id"));
    String name=request.getParameter("name");
    String email=request.getParameter("email");
    String country=request.getParameter("country");

    User book= new User(id,name,email,country);
    userDAO.updateUser(book);
    response.sendRedirect("list");
}

private void deleteUser(HttpServletRequest request,HttpServletResponse response) throws SQLException,IOException{
        int id=Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect("list");
}

}
