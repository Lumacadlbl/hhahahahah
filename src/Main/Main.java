package Main;

import config.config;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int opt, con, pass, choi, attem = 0;
        String cat, nm, des, item, seen, rep;
        boolean run = true;

        while (run) {
            System.out.println("\n===== Lost and Found System =====");
            System.out.println("1. Reporter");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");
            opt = sc.nextInt();
            sc.nextLine();

            if (opt == 1) { 
                System.out.println("____________________________\n");
                System.out.print("Enter name: ");
                nm = sc.nextLine();
                System.out.print("Enter contact: ");
                con = sc.nextInt();
                sc.nextLine();
                System.out.print("Item lost: ");
                item = sc.nextLine();
                System.out.print("Enter description: ");
                des = sc.nextLine();
                System.out.print("Last seen: ");
                seen = sc.nextLine();
                System.out.print("Category (Lost/Found): ");
                cat = sc.nextLine();
                System.out.print("Date reported: ");
                rep = sc.nextLine();

                String sql = "INSERT INTO lost_tbl (r_name, r_contact, r_item, r_descrip, r_loc, r_category, r_date) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
                conf.addRecord(sql, nm, con, item, des, seen, cat, rep);

            } else if (opt == 2) { 
                attem = 0;
                while (attem < 3) {
                    System.out.print("Enter password: ");
                    pass = sc.nextInt();
                    sc.nextLine();

                    if (pass == 12345) {
                        System.out.println("Access Granted!\n");

                        boolean adminMenu = true;
                        while (adminMenu) {
                        System.out.println("____________________________\n");
                        System.out.println("1. View Reports");
                        System.out.println("2. Update Reports");
                        System.out.println("3. Delete Reports");
                        System.out.println("4. Logout");
                        System.out.print("Enter Choice: ");
                        choi = sc.nextInt();
                        sc.nextLine();
                        System.out.println("____________________________\n");

                        switch (choi) {
                        case 1: 
                             try {
                                String selectQuery = "SELECT * FROM lost_tbl";
                                ResultSet rs = conf.getRecords(selectQuery);

                                        
                                System.out.printf("%-5s %-15s %-12s %-15s %-20s %-15s %-10s %-12s\n",
                                "ID", "Name", "Contact", "Item", "Description", "Location", "Category", "Date");
                                System.out.println("---------------------------------------------------------------------------------------------------------------");

                                        
                                while (rs.next()) {
                                System.out.printf("%-5d %-15s %-12d %-15s %-20s %-15s %-10s %-12s\n",
                                 rs.getInt("r_id"),
                                 rs.getString("r_name"),
                                 rs.getInt("r_contact"),
                                 rs.getString("r_item"),
                                 rs.getString("r_descrip"),
                                 rs.getString("r_loc"),
                                 rs.getString("r_category"),
                                 rs.getString("r_date"));
                                  }
                                 System.out.println("---------------------------------------------------------------------------------------------------------------");

                                    } catch (SQLException e) {
                                        System.out.println("Error fetching records: " + e.getMessage());
                                    }
                                    break;

                                case 2: 
                                    try {
                                        
                                        String selectQuery = "SELECT * FROM lost_tbl";
                                        ResultSet rs = conf.getRecords(selectQuery);
                                        System.out.printf("%-5s %-15s %-12s %-15s %-20s %-15s %-10s %-12s\n",
                                                "ID", "Name", "Contact", "Item", "Description", "Location", "Category", "Date");
                                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                                        while (rs.next()) {
                                            System.out.printf("%-5d %-15s %-12d %-15s %-20s %-15s %-10s %-12s\n",
                                                    rs.getInt("r_id"),
                                                    rs.getString("r_name"),
                                                    rs.getInt("r_contact"),
                                                    rs.getString("r_item"),
                                                    rs.getString("r_descrip"),
                                                    rs.getString("r_loc"),
                                                    rs.getString("r_category"),
                                                    rs.getString("r_date"));
                                        }
                                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                                    } catch (SQLException e) {
                                        System.out.println("Error fetching records: " + e.getMessage());
                                    }

                                    // Now ask which to update
                                    System.out.print("Enter ID to update: ");
                                    int updateId = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Enter New Item: ");
                                    item = sc.nextLine();
                                    System.out.print("Enter New Description: ");
                                    des = sc.nextLine();

                                    String updateQuery = "UPDATE lost_tbl SET r_item = ?, r_descrip = ? WHERE r_id = ?";
                                    conf.updateRecord(updateQuery, item, des, updateId);
                                    break;

                                case 3: // Delete Reports
                                    try {
                                        // Show records first
                                        String selectQuery = "SELECT * FROM lost_tbl";
                                        ResultSet rs = conf.getRecords(selectQuery);
                                        System.out.printf("%-5s %-15s %-12s %-15s %-20s %-15s %-10s %-12s\n",
                                                "ID", "Name", "Contact", "Item", "Description", "Location", "Category", "Date");
                                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                                        while (rs.next()) {
                                            System.out.printf("%-5d %-15s %-12d %-15s %-20s %-15s %-10s %-12s\n",
                                                    rs.getInt("r_id"),
                                                    rs.getString("r_name"),
                                                    rs.getInt("r_contact"),
                                                    rs.getString("r_item"),
                                                    rs.getString("r_descrip"),
                                                    rs.getString("r_loc"),
                                                    rs.getString("r_category"),
                                                    rs.getString("r_date"));
                                        }
                                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                                    } catch (SQLException e) {
                                        System.out.println("Error fetching records: " + e.getMessage());
                                    }

                                    
                                    System.out.print("Enter ID to delete: ");
                                    int deleteId = sc.nextInt();
                                    sc.nextLine();
                                    String deleteQuery = "DELETE FROM lost_tbl WHERE r_id = ?";
                                    conf.deleteRecord(deleteQuery, deleteId);
                                    break;

                                case 4: 
                                    adminMenu = false;
                                    System.out.println("Logged out of Admin.");
                                    break;

                                default:
                                    System.out.println("Invalid option.");
                            }
                        }
                        break;
                    } else {
                        attem++;
                        System.out.println("Wrong password! Attempts left: " + (3 - attem));
                    }
                }

                if (attem == 3) {
                    System.out.println("Too many failed attempts. Returning to main menu...");
                }
            } else if (opt == 3) {
                run = false;
                System.out.println("Exiting system...");
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }
}
