/* Group-35 Car Rental System
	19UCS177 - Saurav Somani
    19UCS150 - Aryan Dhuria
	19UCS154 - Shivesh Kaundinaya
	19UCS146 - Shikhar Nigam
*/


import java.sql.*;
import java.util.*;
import project.*;


public class Main {

    public static void main(String[] args) throws Exception{
        Map <String,users> user=new HashMap<>();
        Scanner sc= new Scanner(System.in);
        Login log=new Login();
        Rental r=new Rental();
        String continuing = "NO";

        Connection con;
        Statement stmt=null;
        ResultSet rs;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con=DriverManager.getConnection("jdbc:mysql://freedb.tech:3306/freedbtech_car_rental","freedbtech_ap_project","sssm1234");
            //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","sskvss43");
            stmt=con.createStatement();
            rs=stmt.executeQuery("select * from users");
            while(rs.next()){
                users u=new users();
                u.setEmail_id(rs.getString(1));
                u.setFirst_name(rs.getString(2));
                u.setLast_name(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setContact_no(rs.getString(5));
                u.setAddress(rs.getString(6));
                u.setIs_admin(rs.getBoolean(7));
                user.put(u.getEmail_id(),u);
            }

        }
        catch(Exception e)
        { 
            System.out.println("OOPs!! cannot connect to database!!!! exiting...");
            System.exit(1);
        }
        users u=new users();

        //Starting Page

        System.out.println("\n\n\t\tWelcome to safar-the cab service\t\t");
        System.out.print("1.Login\n2.SignUp\n3.Forgot password\nSelect your choice: ");
        int choice = Integer.parseInt(sc.nextLine());
        switch(choice){
            case 1:{    if(log.login_user(user)==false)
                            System.exit(1);
                        u=user.get(log.getUsername());
                        break;
                    }
            
            case 2:{    u.add_User();
                        user.put(u.getEmail_id(),u);
                        log.setUsername(u.getEmail_id());
                        break;
                    }

            case 3:{    String username,password;
                        System.out.print("\nEnter registered email_id: ");
                        username=sc.nextLine();
                        if(user.get(username)!=null){
                            System.out.print("Enter new password: ");
                            password=sc.nextLine();
                            stmt.executeUpdate("update users set password = '" + password +"' where email_id = '" + username + "' ;");
                            System.out.println("\nPassword changed successfully......\n\nLogin Again..\n");
                            user.get(username).setPassword(password);
                            if(log.login_user(user)==false)
                                System.exit(1);
                            u=user.get(log.getUsername());
                            break;
                        }
                        else{
                            System.out.println("Username doesnot exist!!!! exiting");
                            System.exit(1);
                        }

                    }

                default:{   System.out.println("Enter a valid choice!!! exiting.....");
                            System.exit(1);
        
                        }
                    }

        r.setEmail_id(log.getUsername());


        System.out.println("\nWelcome "+ user.get(log.getUsername()).getFirst_name() + " "+ user.get(log.getUsername()).getLast_name());
        
        //Next page

        System.out.println("\n\n\t\tServices & Facilities\t\t");
    
        loop: do{
            vehicles v=new vehicles();
            System.out.print("\n0.Exit\n1.Book a ride\n2.Rental history\n3.Edit profile\n4.return a car\n");

            if(user.get(log.getUsername()).getIs_admin())
                System.out.print("5.Add Car\n6.Remove a Car\n");
                
            System.out.print("\nSelect your choice: ");
            if(sc.hasNextInt())
                choice = Integer.parseInt(sc.nextLine());
            else
                choice= Integer.parseInt(sc.nextLine());

            switch(choice){
            
                case 0:{    break loop;

                        }
            
                case 1:{    v.book_Ride(log.getUsername());
                            break;
                        }

                case 2: {   r.Rental_history();
                            break;
                        }

                case 3: {   u.edit_Profile();
                            break;
                        }
                    
                case 4: {   r.return_Car();
                            break;
                        }

                case 5:{    if(!user.get(log.getUsername()).getIs_admin()){
                                System.out.println("Enter a valid choice!!!");
                                break;
                            }
                            v.add_Car();
                            break;
                        }

                case 6:{    if(!user.get(log.getUsername()).getIs_admin()){
                                System.out.println("Enter a valid choice!!!");
                                break;
                            }
                            v.delete_Car();
                            break;

                        }

                default :{  System.out.println("Enter a valid choice!!!");
                            break;
                        }
            }
        
            System.out.print("\n\nDo you want to continue to services and facilities(Yes or No): ");
            continuing=sc.nextLine();
            
        }while(continuing.equalsIgnoreCase("yes"));
        
        System.out.println("\n\nThank You for being with us...\nHave a good day ahead\n");
        
        sc.close();

    }
}
