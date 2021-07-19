package project;

import java.util.*;
import java.sql.*;

public class users {

    private String email_id;
    private String first_name;
    private String last_name;
    private String password;
    private String contact_no;
    private String address;
    private Boolean is_admin;

    // Getter functions
    public String getAddress() {
        return address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    // setter function
    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    //starting of add_User()
    public void add_User(){
        Connection con;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project", "sssm1234");
            //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
            stmt = con.createStatement();

        } catch (Exception e) {
            System.out.println("\nOOPs!! cannot connect to database!!! exiting...");
            System.exit(1);
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter email_id (It will be username): ");
        setEmail_id(sc.nextLine());
        System.out.print("Enter first name: ");
        setFirst_name(sc.nextLine());
        System.out.print("Enter last name: ");
        setLast_name(sc.nextLine());
        System.out.print("Enter Password: ");
        setPassword(sc.nextLine());
        System.out.print("Enter Contact number: ");
        setContact_no(sc.nextLine());
        System.out.print("Enter Address: ");
        setAddress(sc.nextLine());
        setIs_admin(false);

        String sql;
        sql = "INSERT INTO users " + "VALUES('" + email_id + "','" + first_name + "','" + last_name + "','" + password
                + "','" + contact_no + "','" + address + "',false)";

        try {
            stmt.executeUpdate(sql);
            System.out.println("\nAdded & logged in Successfully");
        } 
        catch (Exception e) {
            System.out.println("Something went wrong....Can't add new user" 
            +"reasons might be a user with same email id exists"
            +"or some database connection problem!!! exiting...");
            System.exit(1);
        }
        
        //sc.close();
    }
    //Ending of add_User()


    //Starting of edit_profile
    public void edit_Profile(){
        Connection con;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project", "sssm1234");
            //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
            stmt = con.createStatement();

        } catch (Exception e) {
            System.out.println("OOPs!! cannot connect to database!!!! exiting....");
            System.exit(1);
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first name: ");
        setFirst_name(sc.nextLine());
        System.out.print("Enter last name: ");
        setLast_name(sc.nextLine());
        System.out.print("Enter Password: ");
        setPassword(sc.nextLine());
        System.out.print("Enter Contact number: ");
        setContact_no(sc.nextLine());
        System.out.print("Enter Address: ");
        setAddress(sc.nextLine());

        
        String sql = "update users set first_name = '" + first_name + "',last_name = '" + last_name + 
        "', password = '"+ password + "', contact_no = '" + contact_no + "',address = '"+address+"'"+
        "where email_id = '" + email_id + "';";
        try{
            stmt.executeUpdate(sql);
            System.out.println("\nProfile updated successfully");
        }
        catch(Exception e){
            System.out.println("Something went wrong....Profile can't be updated!!!! exiting...");
            System.exit(1);
        }
        
        //sc.close();
    }
     //Ending of edit_Profile()   
    
}
