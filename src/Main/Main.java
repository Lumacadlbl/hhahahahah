
package Main;


import config.config;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        config conf = new config();
        
        int date, opt, con, pass, choi, attem = 0;
        String cat, nm, des, item, seen,  rep;
        
        System.out.println("1. Reporter");
        System.out.println("2. Admin");
        System.out.print("Enter Choice: ");
        opt = sc.nextInt();
        
        if (opt == 1) {
            
            System.out.println("____________________________\n");
            System.out.print("Enter name: ");
            nm = sc.next();
            System.out.print("Enter contact: ");
            con = sc.nextInt();
            System.out.print("Item lost: ");
            item = sc.next();
            System.out.print("Enter description: ");
            des = sc.next();
            System.out.print("Last seen: ");
            seen = sc.next();
            System.out.print("Category ((Lost/Found): ");
            cat = sc.next();
            System.out.print("Date reported: ");
            rep = sc.next();
           
                
            System.out.println("____________________________\n");
         
              String sql = "INSERT INTO lost_tbl (r_name, r_contact, r_item, r_descrip, r_loc, r_category, r_date ) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
                
              conf.addRecord(sql, nm, con, item, des, seen, cat, rep);
        }else{
            
            // Sir, Wala ko kahibaw unsaon pag butang sa Update, Delete ug View. Maong kani ra ako na buhat
            while (attem < 3){
            
            System.out.print("Enter password: ");
            pass = sc.nextInt();
            
               if (pass == 12345){
                   
                   System.out.println("Access Granted!\n");
               
                   System.out.println("____________________________\n");
                   System.out.println("1. Update reports");
                   System.out.println("2. Delete reports");
                   System.out.print("Enter Choice: ");
                   choi = sc.nextInt();
                   System.out.println("____________________________\n");
                  
                   break;
               }else{
                  attem++;
                   System.out.println("Wrong password! Attempts left: " +(3 - attem));
                  
                }
            
        }
            if (attem == 3){
                System.out.println("Too many failed attemps. System exiting...");
                System.exit(0);
            }
            
        }
    
    }
}