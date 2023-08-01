package com.shopping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Jdbc {
    static Connection con=null;
    static Statement st=null;

    static
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineshopping", "root", "Padmesh2002!!");
            st = con.createStatement();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}



//            ResultSet resultSet= st.executeQuery("select * from user");
//            int userId=2;
//            String tableName="user";
//            String adminUserName="admin";
//            String password="admin";
//            String newPassword="mana";
//            ResultSet rs=Jdbc.st.executeQuery("select * from user where user_id="+"'"+userId+"'"+" and user_name="+"'"+adminUserName+"'"+" and password="+"'"+newPassword+"'");
//            ResultSet rss=Jdbc.st.executeQuery("select * from user where user_id="+"'"+userId+"'"+" and user_name="+"'"+adminUserName+"'"+" and password="+"'"+password+"'");


//            ResultSet rs1=Jdbc.st.executeQuery("select * from user where user_id="+1);
//            int delete = Jdbc.st.executeUpdate("delete from user where user_id="+1);
//            ResultSet rs1=Jdbc.st.executeQuery("select * from inventory");
//
//            ResultSet rs2=Jdbc.st.executeQuery("select user_id from "+tableName);
//            if(rss.next())
//            {
//                System.out.println("asdfgbhjhgfd");
//            }
//            int customerUserid=1;
//            int productId=1;
//            ResultSet rs2=Jdbc.st.executeQuery("select * from cart where customer_id="+customerUserid);