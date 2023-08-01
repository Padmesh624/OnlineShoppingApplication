package com.shopping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineShopping {
    static Scanner sc= new Scanner(System.in);
    public static void main (String args[]) throws SQLException {
        Methods method= new Methods();
        while (true)
        {
            System.out.println("1.Create an admin user");
            System.out.println("2.Login with admin credential");
            System.out.println("3.Create a manager login");
            System.out.println("4.Login with manager credential");
            System.out.println("5.Create a customer login");
            System.out.println("6.Login with customer credential");
            int decision ;
            String value=sc.next();
            if(!method.validNumber(value))
            {
                System.out.println("Enter a valid number");
                System.out.println("====================");
                continue;
            }
            decision=Integer.valueOf(value);
            if(decision==1)
            {
                method.addUser();
            }
            else if(decision==2){
                System.out.println("Enter admin userid : ");
                int adminUserid= sc.nextInt();
                System.out.println("Enter admin username : ");
                String adminUserName= sc.next();
                System.out.println("Enter password : ");
                String password = sc.next();
                ResultSet rs=Jdbc.st.executeQuery("select * from user where user_id="+"'"+adminUserid+"'"+" and user_name="+"'"+adminUserName+"'"+" and password="+"'"+password+"'");
                String checkPassword=null;
                String username= null;
                int userid = -1;
                if(rs.next())
                {
                    userid=rs.getInt(1);
                    username=rs.getString(2);
                    checkPassword=rs.getString(3);
                }
                while (true)
                {
                    if((checkPassword!=null && checkPassword.equals(password))&&(username!=null && username.equals(adminUserName))&&(userid!=-1 && userid==adminUserid))
                    {
                        System.out.println("1.Change password");
                        System.out.println("2.Update profile");
                        System.out.println("3.Create Manager");
                        System.out.println("4.Update manager");
                        System.out.println("5.Remove manager");
                        System.out.println("6.Add inventory");
                        System.out.println("7.Update inventory");
                        System.out.println("8.Remove inventory");
                        System.out.println("9.View inventory");
                        System.out.println("10.Log out");
                        System.out.println("=====================");

                        int temp=0;
                        String input=sc.next();
                        if(!method.validNumber(input))
                        {
                            System.out.println("Enter a valid number");
                            continue;
                        }
                        temp=Integer.valueOf(input);
                        if(temp==1)
                        {
                            System.out.println("Enter password : ");
                            String newPassword = sc.next();
                            int rs1=Jdbc.st.executeUpdate("update user set user_name="+"'"+adminUserName+"'"+" , password="+"'"+newPassword+"'"+" where user_id="+adminUserid);
                            System.out.println("Admin password changed successfully");
                            System.out.println("====================================");
                        }
                        else if(temp==2)
                        {
                            System.out.println("*****Update profile section*****");
                            int delete = Jdbc.st.executeUpdate("delete from user where user_id="+adminUserid);
                            method.addUser();
                            System.out.println("Profile updated successfully");
                            System.out.println("=============================");
                        }
                        else if(temp==3)
                        {
                            method.addUser();
                        }
                        else if(temp==4)
                        {
                            System.out.println("*****Update profile section*****");
                            System.out.println("Enter manager userid : ");
                            int managerUserid= sc.nextInt();
                            ResultSet rs1=Jdbc.st.executeQuery("select * from user where user_id="+managerUserid);
                            if(rs1.next())
                            {
                                int delete = Jdbc.st.executeUpdate("delete from user where user_id="+managerUserid);
                                method.addUser();
                            }

                            else {
                                System.out.println("Entered manager details is not available");
                                System.out.println("=========================================");
                            }
                        }
                        else if(temp==5)
                        {
                            method.removeManager();
                        }
                        else if(temp==6)
                        {
                            method.addInventory();
                        }
                        else if(temp==7)
                        {
                            method.updateInventory();
                        }
                        else if(temp==8)
                        {
                            method.removeInventory();
                        }
                        else if(temp==9)
                        {
                            method.viewInventory();
                        }
                        else if(temp==10)
                        {
                            break;
                        }
                    }
                    else {
                        System.out.println("UserId or Username or password is wrong");
                        break;
                    }
                }

            }
            else if (decision == 3)
            {
                method.addUser();
            }
            else  if(decision==4)
            {
                ResultSet rs1=Jdbc.st.executeQuery("select * from user");
                if(rs1!=null)
                {

                    System.out.println("Enter manager userid : ");
                    int managerUserid= sc.nextInt();
                    System.out.println("Enter manager username : ");
                    String managerUserName= sc.next();
                    System.out.println("Enter password : ");
                    String password = sc.next();
                    ResultSet rs=Jdbc.st.executeQuery("select * from user where user_id="+"'"+managerUserid+"'"+" and user_name="+"'"+managerUserName+"'"+" and password="+"'"+password+"'");
                    String checkPassword=null;
                    String username= null;
                    int userid = -1;
                    if(rs.next())
                    {
                        userid=rs.getInt(1);
                        username=rs.getString(2);
                        checkPassword=rs.getString(3);
                    }
                    while (true)
                    {
                        if((checkPassword!=null && checkPassword.equals(password))&&(username!=null && username.equals(managerUserName))&&(userid!=-1 && userid==managerUserid))
                        {
                            System.out.println("1.Change password");
                            System.out.println("2.Update profile");
                            System.out.println("3.Add inventory");
                            System.out.println("4.Update inventory");
                            System.out.println("5.Remove inventory");
                            System.out.println("6.View inventory");
                            System.out.println("7.Log out");
                            int temp=0;
                            String input=sc.next();
                            if(!method.validNumber(input))
                            {
                                System.out.println("Enter a valid number");
                                continue;
                            }
                            temp=Integer.valueOf(input);
                            if(temp==1)
                            {
                                System.out.println("Enter new password : ");
                                String newPassword = sc.next();
                                int rs2=Jdbc.st.executeUpdate("update user set user_name="+"'"+managerUserName+"'"+" , password="+"'"+newPassword+"'"+" where user_id="+managerUserid);
                                System.out.println("Manager password changed successfully");
                                System.out.println("====================================");
                            }
                            else if(temp==2)
                            {
                                System.out.println("*****Update profile section*****");
                                int delete = Jdbc.st.executeUpdate("delete from user where user_id="+managerUserid);
                                method.addUser();
                                System.out.println("Profile updated successfully");
                                System.out.println("=============================");
                            }
                            else if(temp==3)
                            {
                                method.addInventory();
                            }
                            else if(temp==4)
                            {
                                method.updateInventory();
                            }
                            else if(temp==5)
                            {
                                method.removeInventory();
                            }
                            else if(temp==6)
                            {
                                method.viewInventory();
                            }
                            else if(temp==7)
                            {
                                break;
                            }
                        }
                        else {
                            System.out.println("Username or password is incorrect.");
                            System.out.println("==================================");
                        }
                    }
                }
                else {
                    System.out.println("No manager is registered. Create an manager account");
                    System.out.println("====================================================");
                }
            }
            else if(decision==5)
            {
                method.addUser();
            }
            else if(decision==6)
            {
                ResultSet rs1=Jdbc.st.executeQuery("select * from user");
                if(rs1!=null)
                {

                    System.out.println("Enter customer userid : ");
                    int customerUserid= sc.nextInt();
                    System.out.println("Enter customer username : ");
                    String customerUserName= sc.next();
                    System.out.println("Enter password : ");
                    String password = sc.next();
                    ResultSet rs=Jdbc.st.executeQuery("select * from user where user_id="+"'"+customerUserid+"'"+" and user_name="+"'"+customerUserName+"'"+" and password="+"'"+password+"'");
                    String checkPassword=null;
                    String username= null;
                    int userid = -1;
                    if(rs.next())
                    {
                        userid=rs.getInt(1);
                        username=rs.getString(2);
                        checkPassword=rs.getString(3);
                    }
                    if((checkPassword!=null && checkPassword.equals(password))&&(username!=null && username.equals(customerUserName))&&(userid!=-1 && userid==customerUserid))
                    {
                        while(true)
                        {
                            System.out.println("1.Change password");
                            System.out.println("2.Update profile");
                            System.out.println("3.View products");
                            System.out.println("4.Add to cart");
                            System.out.println("5.Remove an item from cart");
                            System.out.println("6.View cart");
                            System.out.println("7.Purchase");
                            System.out.println("8.View purchase history");
                            System.out.println("9.Add money to wallet");
                            System.out.println("10.View wallet balance");
                            System.out.println("11.Logout");
                            int temp=0;
                            String input=sc.next();
                            if(!method.validNumber(input))
                            {
                                System.out.println("Enter a valid number");
                                System.out.println("============================");
                                continue;
                            }
                            temp=Integer.valueOf(input);
                            if(temp==1)
                            {
                                System.out.println("Enter new password : ");
                                String newPassword = sc.next();
                                int rs2=Jdbc.st.executeUpdate("update user set user_name="+"'"+customerUserName+"'"+" , password="+"'"+newPassword+"'"+" where user_id="+customerUserid);
                                System.out.println("Customer password changed successfully");
                                System.out.println("======================================");
                            }
                            else if(temp==2)
                            {
                                System.out.println("*****Update profile section*****");
                                int delete = Jdbc.st.executeUpdate("delete from user where user_id="+customerUserid);
                                method.addUser();
                                System.out.println("Profile updated successfully");
                                System.out.println("============================");
                            }
                            else if(temp==3)
                            {
                                method.viewInventory();
                            }
                            else if(temp==4)
                            {
                                String type="cart";
                                method.purchase(customerUserid,type);
                            }
                            else if(temp==5)
                            {
                                System.out.print("Enter the product id to remove from cart : ");
                                int productId= sc.nextInt();
                                ResultSet rs2=Jdbc.st.executeQuery("select * from cart where customer_id="+customerUserid+" and product_id="+productId);
                                if(rs2.next())
                                {
                                    int delete = Jdbc.st.executeUpdate("delete from cart where customer_id="+customerUserid+" and product_id="+productId);
                                    System.out.println("Item removed successfully.");
                                    System.out.println("==========================");
                                }
                                else
                                {
                                    System.out.println("Entered product id is not available in your cart.");
                                    System.out.println("=================================================");
                                }
                            }
                            else if(temp==6)
                            {
                                ResultSet rs2=Jdbc.st.executeQuery("select * from cart where customer_id="+customerUserid);
                                if(rs2.next())
                                {
                                    System.out.println("Product_Id      Product_name       Number_Of_Items     Price");
                                    System.out.println("    "+rs2.getInt(2)+"             "+rs2.getString(3)+"                 "+rs2.getInt(4)+"                "+rs2.getInt(5));
                                    while(rs2.next())
                                    {
                                        System.out.println("    "+rs2.getInt(2)+"             "+rs2.getString(3)+"                 "+rs2.getInt(4)+"                "+rs2.getInt(5));
                                    }
                                    System.out.println("======================================================================");
                                }
                                else {
                                    System.out.println("There is no items in your cart.");
                                    System.out.println("===============================");
                                }
                            }
                            else if (temp==7)
                            {
                                String type= "history";
                                method.purchase(customerUserid,type);
                            }
                            else if(temp==8)
                            {
                                System.out.println("****** Your purchase history ******");
                                ResultSet rs2=Jdbc.st.executeQuery("select * from history where customer_id="+customerUserid);
                                if(rs2.next())
                                {
                                    System.out.println("Product_Id      Product_name       Number_Of_Items     Price");
                                    System.out.println("    "+rs2.getInt(2)+"             "+rs2.getString(3)+"                 "+rs2.getInt(4)+"                "+rs2.getInt(5));
                                    while(rs2.next())
                                    {
                                        System.out.println("    "+rs2.getInt(2)+"             "+rs2.getString(3)+"                 "+rs2.getInt(4)+"                "+rs2.getInt(5));
                                    }
                                    System.out.println("================================================================================");
                                }
                                else {
                                    System.out.println("There is no purchase history.");
                                    System.out.println("=============================");
                                }
                            }
                            else if(temp==9)
                            {
                                System.out.print("Enter the money to add in wallet : ");
                                int amount = sc.nextInt();
                                ResultSet rs2=Jdbc.st.executeQuery("select * from wallet where user_id="+customerUserid);
                                if(rs2.next())
                                {
                                    int existingAmount=rs2.getInt(2);
                                    int rs3= Jdbc.st.executeUpdate("insert into wallet values ("+customerUserid+","+(existingAmount+amount)+")");
                                    System.out.println("Your wallet amount : "+(amount+existingAmount));
                                }
                                else
                                {
                                    int rs3= Jdbc.st.executeUpdate("insert into wallet values ("+customerUserid+","+amount+")");
                                    System.out.println("Your wallet amount : "+amount);
                                }
                                System.out.println("Amount added successfully");
                                System.out.println("======================================");
                            }
                            else if(temp==10)
                            {
                                ResultSet rs2=Jdbc.st.executeQuery("select * from wallet where user_id="+customerUserid);
                                int balance=0;
                                if(rs2.next())
                                {
                                    balance=rs2.getInt(2);
                                }
                                System.out.println("Your wallet balance : "+balance);
                                System.out.println("==================================");
                            }
                            else if(temp==11)
                            {
                                break;
                            }
                        }
                    }
                    else {
                        System.out.println("Username or password is incorrect");
                    }
                }
                else {
                    System.out.println("No customer is registered. Create an customer account");
                }
            }
        }


    }
}

