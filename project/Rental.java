package project;

import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Rental {
    int rental_id;
    String email_id;
    String registration_no;
    java.util.Date start_date;
    java.util.Date return_date;
    double total_amount;

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    //Starting of Rental_History()
    public void Rental_history() throws Exception{
        Connection con;
        Statement stmt = null;
        ResultSet rs=null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project", "sssm1234");
            //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
            stmt = con.createStatement();

        } catch (Exception e) {
            System.out.println("\nOOPs!! cannot connect to database!!!! exiting...");
            System.exit(1);
        }

        String sql = "select * from rental where email_id='"+email_id+"';";
        
        try {
            rs= stmt.executeQuery(sql);
            System.out.println("\nThe details are as follows: ");
        } catch (Exception e) {
            System.out.println("\nSomething went wrong....Can't fectch the details!!! exiting...");
            System.exit(1);
        }
        
        while(rs.next()){
            System.out.print("\nRegistration_no: "+rs.getString(3));
            System.out.print("\tStart_date: "+rs.getDate(4));
            System.out.print("\tReturn Date: "+rs.getDate(5));
            System.out.print("\tAmount: "+rs.getDouble(6));
        }
        
    }
    //Ending of Rental history


    //Starting of return_Car()
    public void return_Car() throws Exception{
            Connection con;
            Statement stmt = null;
            Scanner sc=new Scanner(System.in);
            String sql;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project", "sssm1234");
                //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
                stmt = con.createStatement();

            } 
            catch (Exception e) {
            System.out.println("OOPs!! cannot connect to database!!!!! exiting..");
            System.exit(1);

            }

            System.out.print("\nEnter the rental_id: ");
            rental_id = Integer.parseInt(sc.nextLine());
            System.out.print("\nEnter Today's date(real date of return) in yyyy-MM-dd format: ");
            String date1=sc.nextLine();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date start = df.parse(date1);
            java.sql.Date sqldate = null; 
            ResultSet types = stmt.executeQuery("select * from rental where rental_id = "+rental_id+";");

            double amt=0.0,y=0.0;
            while(types.next()){
                sqldate=types.getDate(5);
                amt = types.getDouble(6);
                registration_no=types.getString(3);
            }
            
            java.util.Date utildate= new java.util.Date(sqldate.getTime());
            int diffInDays = (int) ((start.getTime()-utildate.getTime()) / (1000 * 60 * 60 * 24));

            int x=0;
            if(diffInDays>0){
                types= stmt.executeQuery("select * from vehicle where registration_no = '"+ registration_no +"';");
                while(types.next())
                    x=types.getInt(6);
                
                types = stmt.executeQuery("select * from vehicle_type where type_id ="+ x +";");
                
                while(types.next())
                    y=types.getDouble(2);
                amt = amt + diffInDays*(y+200);
            }
            System.out.println("\nYour due date was: "+ utildate);
            System.out.println("\nNumber of days exceeded from due date are: " + diffInDays);
            System.out.println("\nAdditional amount to be paid: "+diffInDays*(y+200));
            java.sql.Date sqlStart = new java.sql.Date(start.getTime());
            
            sql= "update vehicle set is_available=true where registration_no='" + registration_no + "';" ;
            String sql2="update rental set total_amount = "+ amt + ",return_date = '"+ sqlStart+"'" + "where rental_id = "+ rental_id + ";";
            
            try{
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql2);
                System.out.println("\nReturned successfully...");
            }
            catch(Exception e){
                System.out.println("\nCan't excecute the updation Query!!!! exiting..");
                System.exit(1);
            }
            //sc.close();

        }
        //Ending of return_Car()

}

