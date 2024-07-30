package com.demo.user.dao;

import com.demo.user.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private String jdbcURL="jdbc:mysql://localhost:3306/mydb";
    private String jdbcUserName="root";
    private String jdbcPassword="project";

    private static final String INSERT_USER_SQL="insert into users"+"(name,email,country) values"+"(?,?,?);";
    private static final String SELECT_USER_BY_ID="select id,name,email,country from users where id=?";
    private static final String SELCT_ALL_USERS="select * from users";

    private static final String DELETE_USER_SQL="delete from users where id=?";
    private static final String UPDATE_USERS_SQL="update users set name=?, email=?,country=? where id=?";


    public UserDAO(){

    }

    protected Connection getConnection(){
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(jdbcURL,jdbcUserName,jdbcPassword);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(Exception e ){
            e.printStackTrace();

        }
    return con;
    }

    public void insertUser(User user) throws SQLException{
        System.out.println(INSERT_USER_SQL);

try(Connection con=getConnection(); PreparedStatement pstmt=con.prepareStatement(INSERT_USER_SQL)){
    pstmt.setString(1,user.getName());
pstmt.setString(2,user.getEmail());
pstmt.setString(3,user.getCountry());
System.out.println(pstmt);
pstmt.executeUpdate();

}catch(Exception e){
    e.printStackTrace();
}
    }
public User selectUser(int id){
        User user= null;
//create a connection
        try(Connection con= getConnection();
            //create a statement
            PreparedStatement pstmt=con.prepareStatement(SELECT_USER_BY_ID)){
            pstmt.setInt(1,id);
            System.out.println(pstmt);
//execute query
           ResultSet rs=  pstmt.executeQuery();
           //process the rs
            while(rs.next()){
                String name=rs.getString("name");
                String email=rs.getString("email");
                String country=rs.getString("country");
                user= new User(id,name,email,country);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
}

public List<User> selectAllUsers(){
        List<User> users= new ArrayList<>();

        try(Connection con=getConnection();PreparedStatement pstmt=con.prepareStatement(SELCT_ALL_USERS)){
System.out.println(pstmt);
ResultSet rs=pstmt.executeQuery();

            while (rs.next()) {
            int id=rs.getInt("id");
            String name=rs.getString("name");
                String email=rs.getString("email");
                String country=rs.getString("country");

                users.add(new User(id,name,email,country));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
}

public boolean deleteUser(int id) throws SQLException{
        boolean rowDeleted = false;
        try(
                Connection con=getConnection();
                PreparedStatement pstmt=con.prepareStatement(DELETE_USER_SQL);
                ){
           pstmt.setInt(1,id);
           rowDeleted=pstmt.executeUpdate()>0;

        }catch(Exception e){
            e.printStackTrace();
        }
        return rowDeleted;
}

public boolean updateUser(User user) throws SQLException{
        boolean rowUpdated=false;
        try(Connection con=getConnection();
            PreparedStatement pstmt=con.prepareStatement(UPDATE_USERS_SQL);
        ){
            pstmt.setString(1,user.getName());
            pstmt.setString(2,user.getEmail());
            pstmt.setString(3,user.getCountry());
            pstmt.setInt(4,user.getId());

            rowUpdated=pstmt.executeUpdate()>0;

        }catch(Exception e){
            e.printStackTrace();
        }
        return rowUpdated;
}

private void printSQLException(SQLException ex){
        for (Throwable e : ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState: "+((SQLException)e ).getSQLState());
                System.err.println("Error code: "+((SQLException)e ).getErrorCode());
                System.err.println("message: "+e.getMessage());
                Throwable t= ex.getCause();
                while(t!=null){
                    System.out.println("Cause: "+t);
                    t=t.getCause();
                }
            }
        }
    }

}
