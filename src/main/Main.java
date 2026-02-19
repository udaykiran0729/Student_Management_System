package main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dao.StudentDAO;
import model.Student;
import org.bson.Document;
import service.AnalyticsService;
import util.MongoDBConnection;

import java.util.Scanner;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static StudentDAO dao = new StudentDAO();
    static AnalyticsService analytics = new AnalyticsService();

    public static void main(String[] args) {

        if (!login()) {
            System.out.println("❌ Invalid Login!");
            return;
        }

        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Performance Analytics");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> dao.viewStudents();
                case 3 -> {
                    System.out.print("Enter Roll No: ");
                    dao.deleteStudent(sc.nextInt());
                }
                case 4 -> {
                    System.out.print("Enter Roll No: ");
                    dao.searchStudent(sc.nextInt());
                }
                case 5 -> analytics.showAnalytics();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static boolean login() {

        System.out.print("Username: ");
        String username = sc.next();
        System.out.print("Password: ");
        String password = sc.next();

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> adminCollection = db.getCollection("admin");

        Document admin = adminCollection.find(
                and(eq("username", username), eq("password", password))
        ).first();

        return admin != null;
    }

    private static void addStudent() {

        System.out.print("Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Course: ");
        String course = sc.nextLine();

        System.out.print("Marks: ");
        double marks = sc.nextDouble();

        System.out.print("Attendance (%): ");
        double attendance = sc.nextDouble();

        if (attendance < 75) {
            System.out.println("⚠ Not Eligible for Exam!");
        }

        dao.addStudent(new Student(roll, name, email, course, marks, attendance));
    }
}
