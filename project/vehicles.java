package project;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class vehicles {
    private String registration_no;
    private String brand;
    private String model;
    private String colour;
    private int type_id;
    private Boolean is_available;
    private String type;
    
    //gettters
    public String getRegistration_no() {
        return registration_no;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColour() {
        return colour;
    }

    public int getType_id() {
        return type_id;
    }

    public Boolean getIs_available() {
        return is_available;
    }
    public String getType() {
        return type;
    }
    //setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }
    public void setType(String type) {
        this.type = type;
    }

    //Satrting of add_Car()
    public void add_Car(){
        Connection con;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project", "sssm1234");
            //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
            stmt = con.createStatement();

        } catch (Exception e) {
            System.out.println("\nOOPs!! cannot connect to database!!!! exiting...");
            System.exit(1);

        }

        Scanner sc = new Scanner(System.in);
        int choice;
        
        do{
        System.out.println("select Type Of car You Want to add:->");
        System.out.println("1.Sedan");
        System.out.println("2.SUV");
        System.out.println("3.Hatchback");
        System.out.println("0.Exit");
        choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            
            case 0:{
                return;
            }
            
            case 1: {
                setType("Sedan");
                setType_id(1);
                break;
            }
            case 2: {
                setType("SUV");
                setType_id(2);
                break;
            }
            case 3: {
                setType("Hatchback");
                setType_id(3);
                break;
            }
            default: {
                System.out.println("Enter a valid choice");
            }
            
        }
    }while(choice!=1 && choice!=2 && choice!=3);

        System.out.print("Enter Registration Number: ");
        setRegistration_no(sc.nextLine());
        System.out.print("Enter Brand of Car: ");
        setBrand(sc.nextLine());
        System.out.print("Enter Model of Car: ");
        setModel(sc.nextLine());
        System.out.print("Enter Colour: ");
        setColour(sc.nextLine());
        
        setIs_available(true);

        String sql;
        sql = "INSERT INTO vehicle " + "VALUES('" + registration_no + "','" + brand + "','" + model + "','" + colour
                + "'," + is_available + "," + type_id + ")";
        //System.out.println(sql);
        try{
            stmt.executeUpdate(sql);
            System.out.println("\nCar addedd successfully.....");
        }
        catch(Exception e){
            //System.out.println(e);
            System.out.println("\nSomething went wrong.....Can't able to update!!! exiting...");
            System.exit(1);
        }
        
        //sc.close();
    }
    //Ending of add_Car()


    //Starting of book_Ride()
    public void book_Ride(String email) throws Exception{
        HashMap<Integer,Double> map= new HashMap<>();
        Connection con;
        Statement stmt = null;
        String sql;
        ResultSet rs,rs2;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project", "sssm1234");
            //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        } catch (Exception e) {
            System.out.println("\nOOPs!! cannot connect to database!!!! exiting...");
            System.exit(1);

        }
        
        Scanner sc = new Scanner(System.in);
        System.out.println("BOOK RIDE");
        
        sql="select * from vehicle_type;";
        rs=stmt.executeQuery(sql);
        while(rs.next()){
            map.put(rs.getInt(1),rs.getDouble(2));
        }

        System.out.println("\n1.Sedan  Price per day is "+ map.get(1));
        System.out.println("\n2.SUV Price per day is "+ map.get(2));
        System.out.println("\n3.Hatchback Price per day is "+ map.get(3));
        System.out.println("\nNote: If the car is returned after end_date than 200 per day will be charged extra..");
        
        String want="YES";int i;int choice;
        do{
            System.out.print("\nEnter your choice: ");
            choice = Integer.parseInt(sc.nextLine());
        
            sql = "select * from vehicle where type_id='" + choice + "'and is_available=true ;";
            rs = stmt.executeQuery(sql);
            rs2 = rs;
        
            i = 1;
            while (rs.next())
            {
                System.out.print(i + ".");
                System.out.print("Registration_no: " + rs.getString(1));
                System.out.print("\tbrand: " + rs.getString(2));
                System.out.print("\tmodel: " + rs.getString(3));
                System.out.print("\tcolor: " + rs.getString(4));
                System.out.print("\n");
                i++;
            }
            if(i==1){
                System.out.print("\nNo car available in this category...\n"+ 
                "want to go for another category"+
                "\n'YES'or'yes' for Yes else 'NO' or 'no' : ");
                want=sc.nextLine();
            }
            else
                want="No";

        }while(want.equalsIgnoreCase("yes"));

        if(i==1 && want.equalsIgnoreCase("no")){
            return;
        }
        
        System.out.print("\nEnter the registration no of the car you want to select :");
        registration_no=sc.nextLine();

        System.out.print("\nEnter the start date in yyyy-MM-dd format: ");
        String startStr = sc.nextLine();
        
        System.out.print("\nEnter the end date in yyyy-MM-dd format: ");
	    String endStr = sc.nextLine();

        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date start = df.parse(startStr);
        java.util.Date end = df.parse(endStr);
        java.sql.Date sqlStart = new java.sql.Date(start.getTime());
        java.sql.Date sqlEnd = new java.sql.Date(end.getTime());

    	int diffInDays = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));

    	ResultSet types = stmt.executeQuery("select * from vehicle_type where type_id = " + choice + ";");
        
        double rate=0.0;
        while(types.next()){
            rate=types.getDouble(2);
        }
        if(diffInDays<0){
            System.out.println("End date should be ahead of start date!!!! Exiting....");
            System.exit(1);
        }
        else if(diffInDays==0){
            diffInDays=1;
        }
        	
	    Double amount = rate * diffInDays;
        String sql2;
        System.out.println("\nThe amount to be paid is "+ amount);
        
        sql2 = "INSERT INTO rental(email_id, registration_no, start_date, return_date, total_amount) " 
		+ "VALUES('" + email + "','" + registration_no + "','" + sqlStart  + "','" +  sqlEnd
                + "'," + amount + ");";
        sql= "update vehicle set is_available=false where registration_no='" + registration_no + "';" ;
        
        try{
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql);
            System.out.println("\nBooked successfully....");
            rs=stmt.executeQuery("select rental_id from rental order by rental_id desc limit 1;");
            System.out.println("\nYour booking details are below: ");
            while(rs.next()){
                System.out.println("\nRental id is : "+ rs.getInt(1));
            }
            System.out.println("\nEmail id from which ride is booked : " + email);
            System.out.println("\nRegistration number of the car booked : " + registration_no);
            System.out.println("\nDate of booking(start date) : " + start);
            System.out.println("\nDate of return car(expected) : " + end);
            System.out.println("\nNumber of days for which car is booked : " + diffInDays);
            System.out.println("\nAmount paid(without any penalty) : " + amount);

        }
        catch(Exception e){
            System.out.println("\nSomeThing went wrong...Can't book the ride!!!! exiting...");
            System.exit(1);
        }
        //sc.close();
        
    }
    //Ending of book_Ride()


    //Starting of delete_Car()
        public void delete_Car () throws Exception{
            Connection con;
            Statement stmt = null;
            Scanner sc=new Scanner(System.in);
            String sql;
            ResultSet rs=null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project", "sssm1234");
                //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
                stmt = con.createStatement();

            } 
            catch (Exception e) {
                System.out.println("\nOOPs!! cannot connect to database!!! exiting...");
                System.exit(1);
            }

            rs=stmt.executeQuery("select * from vehicle where is_available=true ;");
            System.out.println("\nCar available are:");
            while(rs.next()){
                System.out.print("\nRegistration_no: "+rs.getString(1));
                System.out.print("\tBrand: "+rs.getString(2));
                System.out.print("\tModel: "+rs.getString(3));
            }

            System.out.print("\n\nEnter the registration number of car to be deleted: ");
            setRegistration_no(sc.nextLine());

            sql= "update vehicle set is_available=false where registration_no='" + registration_no + "';" ;
            try{
                stmt.executeUpdate(sql);
                System.out.println("\nDeletion successfull...");
            }
            catch(Exception e){
                System.out.println("\nCan't excecute the updation Query!!!! exiting...");
                System.exit(1);
            }
            //sc.close();
        }
        //Ending of delete_Car()

}


    

