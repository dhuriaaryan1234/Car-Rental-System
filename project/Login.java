package project;

import java.util.*;



public class Login {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    //Satrting of login_user()
    public Boolean login_user (Map<String,users>user) throws Exception{
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter username: ");
        username=sc.nextLine();
        System.out.print("Enter Password: ");
        password=sc.nextLine();
        

        //System.out.println(user.get(username).getPassword());

        if(user.get(username)!=null && (user.get(username).getPassword()).equals(password)){    
            System.out.println("\nLogging in.......");
            //sc.close();
            return true;
        }
        else{
            System.out.println("\nusername or password is incorrect");
            //sc.close();  
            return false;
        }
    }
    //Ending of login_user()
}
