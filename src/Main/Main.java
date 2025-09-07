package Main;

import config.config;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int opt, pass, choi, attem = 0;
        String repName, itemName, itemCategory, itemDesc, lastSeen, dateReported;
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
                System.out.print("Enter your name: ");
                repName = sc.nextLine();
                System.out.print("Enter your contact: ");
                int repContact = sc.nextInt();
                sc.nextLine();

                
                String insertRep = "INSERT INTO rep_tbl (rep_name, rep_contact) VALUES (?, ?)";
                int reporterId = conf.addRecordReturnId(insertRep, repName, repContact);

                System.out.print("Item Found/Lost name: ");
                itemName = sc.nextLine();
                System.out.print("Category (Found/Lost): ");
                itemCategory = sc.nextLine();
                System.out.print("Description: ");
                itemDesc = sc.nextLine();
                System.out.print("Last seen: ");
                lastSeen = sc.nextLine();

             
                String insertItem = "INSERT INTO item_tbl (item_name, item_category, item_description, last_seen) VALUES (?, ?, ?, ?)";
                int itemId = conf.addRecordReturnId(insertItem, itemName, itemCategory, itemDesc, lastSeen);

                System.out.print("Date reported: ");
                dateReported = sc.nextLine();

                
                String insertReport = "INSERT INTO report_tbl (reporter_id, item_id, date_reported) VALUES (?, ?, ?)";
                conf.addRecord(insertReport, reporterId, itemId, dateReported);

                System.out.println("Report added successfully!");

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
                                        String query = "SELECT r.report_id, rep.rep_name, rep.rep_contact, i.item_name, i.item_category, i.item_description, i.last_seen, r.date_reported " +
                                                "FROM report_tbl r " +
                                                "JOIN rep_tbl rep ON r.reporter_id = rep.rep_id " +
                                                "JOIN item_tbl i ON r.item_id = i.item_id";
                                        ResultSet rs = conf.getRecords(query);

                                        System.out.printf("%-5s %-15s %-12s %-15s %-12s %-20s %-15s %-12s\n",
                                                "ID", "Reporter", "Contact", "Item", "Category", "Description", "Last Seen", "Date");
                                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                                        while (rs.next()) {
                                            System.out.printf("%-5d %-15s %-12d %-15s %-12s %-20s %-15s %-12s\n",
                                                    rs.getInt("report_id"),
                                                    rs.getString("rep_name"),
                                                    rs.getInt("rep_contact"),
                                                    rs.getString("item_name"),
                                                    rs.getString("item_category"),
                                                    rs.getString("item_description"),
                                                    rs.getString("last_seen"),
                                                    rs.getString("date_reported"));
                                        }
                                        System.out.println("---------------------------------------------------------------------------------------------------------------");
                                    } catch (SQLException e) {
                                        System.out.println("Error fetching records: " + e.getMessage());
                                    }
                                    break;

                                case 2: // Update Reports
                                    System.out.print("Enter report ID to update: ");
                                    int updateId = sc.nextInt();
                                    sc.nextLine();

                                    System.out.print("Enter new item Found/Lost name: ");
                                    itemName = sc.nextLine();
                                    System.out.print("Enter new description: ");
                                    itemDesc = sc.nextLine();

                                    String updateItem = "UPDATE item_tbl SET item_name = ?, item_description = ? WHERE item_id = (SELECT item_id FROM report_tbl WHERE report_id = ?)";
                                    conf.updateRecord(updateItem, itemName, itemDesc, updateId);
                                    System.out.println("Report updated successfully!");
                                    break;

                                case 3: // Delete Reports
                                    System.out.print("Enter report ID to delete: ");
                                    int deleteId = sc.nextInt();
                                    sc.nextLine();
                                    String deleteReport = "DELETE FROM report_tbl WHERE report_id = ?";
                                    conf.deleteRecord(deleteReport, deleteId);
                                    System.out.println("Report deleted successfully!");
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
