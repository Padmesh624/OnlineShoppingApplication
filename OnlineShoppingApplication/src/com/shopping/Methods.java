package com.shopping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Methods {
    static Scanner sc= new Scanner(System.in);
    static HashMap<String,String > hashMap= new HashMap<>();
    static HashMap<Integer,HashMap<String,String>> inventory = new HashMap<>();
    static HashMap<String ,HashMap<String,Integer>> cart = new HashMap<>();
    static HashMap<String ,HashMap<String,Integer>> history = new HashMap<>();

    static HashMap<String,Integer> wallet = new HashMap<>();

    public static boolean checkId (int id,String tableName)
    {
        ResultSet res;
        try {
            res = Jdbc.st.executeQuery("select * from "+tableName);
            while (res.next())
            {
                int userId=res.getInt(1);
                if(userId==id)
                {
                    return false;
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
    public static boolean validNumber(String number)
    {
        int count=0;
        for(int i=0;i<number.length();i++)
        {
            if((int)number.charAt(i)>=48 &&  (int)number.charAt(i)<=57)
            {
                count++;
            }
        }
        if(count==number.length())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static void purchase(int  customerUserId , String tableName) throws SQLException {
        System.out.println("Enter the product id to purchase : ");
        int productId = sc.nextInt();
        ResultSet rs1=Jdbc.st.executeQuery("select * from inventory where product_id="+productId);

        if(rs1.next())
        {
            System.out.print("Enter the number of items to purchase : ");
            int numberOfItems = sc.nextInt();
            String name=rs1.getString(2);
            int price=rs1.getInt(3);
            int availableItems=rs1.getInt(4);
            if(numberOfItems<=availableItems && tableName.equals("cart"))
            {
                int rs= Jdbc.st.executeUpdate("insert into cart values ("+customerUserId+","+productId+",'"+name+"',"+numberOfItems+","+(numberOfItems*price)+")");
                System.out.println("Item added to cart successfully");
                System.out.println("=========================================");
            }
            else if(numberOfItems<=availableItems && tableName.equals("history"))
            {
                int totalPrice = price * numberOfItems;
                ResultSet rs2=Jdbc.st.executeQuery("select * from wallet where user_id="+customerUserId);
                int walletAmount = 0;
                if(rs2.next())
                {
                    walletAmount=rs2.getInt(2);
                }
                if(walletAmount>=totalPrice)
                {
                    int rs= Jdbc.st.executeUpdate("insert into history values ("+customerUserId+","+productId+",'"+name+"',"+numberOfItems+","+totalPrice+")");
                    int update = Jdbc.st.executeUpdate("update inventory set stock="+"'"+(availableItems-numberOfItems)+"'"+" where product_id="+productId);
                    int secondUpdate = Jdbc.st.executeUpdate("update wallet set amount="+"'"+(walletAmount-totalPrice)+"'"+" where user_id="+customerUserId);
                    System.out.println();
                    System.out.println("Item purchased successfully");
                    System.out.println("Total price of the purchased product : "+totalPrice);
                    System.out.println("Your wallet balance is "+walletAmount);
                    System.out.println("Amount deducted from your wallet . Your current wallet amount is "+(walletAmount-totalPrice));
                }
                else
                {
                    int requiredAmount= totalPrice-walletAmount;
                    System.out.println("Total price of the product is "+totalPrice+". Your wallet has only "+walletAmount);
                    System.out.println("Your wallet amount is insufficient for your purchase.");
                    System.out.println("You need to pay extra including your wallet : "+requiredAmount);
                    System.out.print("Enter the amount : ");
                    while(true)
                    {
                        int amount=sc.nextInt();
                        if(amount>=requiredAmount)
                        {
                            System.out.println();
                            System.out.println("Item purchased successfully");
                            System.out.println("Total price of the purchased product : "+totalPrice);
                            if(amount==requiredAmount)
                            {
                                requiredAmount=0;
                                System.out.println("You entered exact amount. Your wallet amount is 0.");
                            }
                            else {
                                requiredAmount=amount-requiredAmount;
                                System.out.println("You entered more the required amount. So the remaining amount "+requiredAmount+" is added to your wallet.");
                                System.out.println("Your current wallet balance is : "+requiredAmount);
                            }
                            break;
                        }
                        else
                        {
                            System.out.print("Please enter required amount : ");
                        }
                    }
                    int rs= Jdbc.st.executeUpdate("insert into history values ("+customerUserId+","+productId+",'"+name+"',"+numberOfItems+","+totalPrice+")");
                    int update = Jdbc.st.executeUpdate("update inventory set stock="+"'"+(availableItems-numberOfItems)+"'"+" where product_id="+productId);
                    int secondUpdate = Jdbc.st.executeUpdate("update wallet set amount="+"'"+requiredAmount+"'"+" where user_id="+customerUserId);
                }

                System.out.println("==============================================================================");
            }
            else {
                System.out.println("Sorry, Stock is less than your requirement.");
                System.out.println("===========================================");
            }
        }
        else {
            System.out.println("Entered product id is not available");
            System.out.println("===================================");
        }
    }
    public static void addUser() throws SQLException {
        System.out.println("Enter new user id : ");
        int userid;
        String tablename="user";
        while(true)
        {
            userid = sc.nextInt();
            if(checkId(userid,tablename))
            {
                break;
            }
            else
            {
                System.out.println("This id is already present. Please enter another id");
            }
        }
        System.out.println("Enter username : ");
        String adminUserName= sc.next();
        System.out.println("Enter password : ");
        String password = sc.next();
        int rs= Jdbc.st.executeUpdate("insert into user values ("+userid+",'"+adminUserName+"','"+password+"')");
        System.out.println("Account created successfully");
        System.out.println("=============================");
    }
    //
    public static void removeManager() throws SQLException {
        System.out.println("Enter manager userId : ");
        int managerUserId= sc.nextInt();
        ResultSet rs1=Jdbc.st.executeQuery("select * from user where user_id="+managerUserId);
        if(rs1.next())
        {
            int delete = Jdbc.st.executeUpdate("delete from user where user_id="+managerUserId);
            System.out.println("Manager removed successfully");
            System.out.println("=======================");
        }
        else {
            System.out.println("Entered manager name is not available");
        }
    }

    public static void addInventory() throws SQLException {
        System.out.print("Enter the new product id : ");
        int productId;
        String tablename="inventory";
        while(true)
        {
            productId = sc.nextInt();
            if(checkId(productId,tablename))
            {
                break;
            }
            else
            {
                System.out.println("This id is already present. Please enter another id");
            }
        }
        System.out.print("Enter the product name : ");
        String name=sc.next();
        System.out.print("Enter the price : ");
        int price = sc.nextInt();
        System.out.print("Enter the quantity in stock : ");
        int stock= sc.nextInt();
        int rs= Jdbc.st.executeUpdate("insert into inventory values ("+productId+",'"+name+"',"+price+","+stock+")");
        System.out.println("Item added successfully");
        System.out.println("=======================");
    }
    public static void updateInventory() throws SQLException {
        System.out.println("***** To update stock in inventry *****");
        System.out.print("Enter the old product id : ");
        int productid= sc.nextInt();
        ResultSet rs1=Jdbc.st.executeQuery("select * from inventory where product_id="+productid);
        if(rs1.next())
        {
            int delete = Jdbc.st.executeUpdate("delete from inventory where product_id="+productid);
            addInventory();
        }
        else {
            System.out.println("Entered product id is not available");
            System.out.println("===================================");
        }
    }
    public static void viewInventory() throws SQLException {
        ResultSet rs1=Jdbc.st.executeQuery("select * from inventory");
        if(rs1!=null)
        {
            System.out.println("Product_Id       Product_name         Price           Stock");
            while(rs1.next())
            {
                System.out.println("    "+rs1.getInt(1)+"               "+rs1.getString(2)+"                "+rs1.getInt(3)+"              "+rs1.getInt(4));
            }
            System.out.println("==================================================================================");
        }
        else {
            System.out.println("There is no items in inventory.");
            System.out.println("===============================");
        }
    }

    public static void removeInventory() throws SQLException {
        System.out.print("Enter the product id : ");
        int productid= sc.nextInt();
        ResultSet rs1=Jdbc.st.executeQuery("select * from inventory where product_id="+productid);
        if(rs1.next())
        {
            int delete = Jdbc.st.executeUpdate("delete from inventory where product_id="+productid);
            System.out.println("Item removed successfully");
            System.out.println("=======================");
        }
        else {
            System.out.println("Entered product id is not available");
        }
    }


    public static void main (String args[])
    {

    }
}

