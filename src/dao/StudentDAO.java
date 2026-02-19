package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Student;
import org.bson.Document;
import util.MongoDBConnection;

import static com.mongodb.client.model.Filters.eq;


public class StudentDAO {

    MongoDatabase database = MongoDBConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("students");

    public void addStudent(Student student) {

        Document doc = new Document("rollNo", student.getRollNo())
                .append("name", student.getName())
                .append("email", student.getEmail())
                .append("course", student.getCourse())
                .append("marks", student.getMarks())
                .append("attendance", student.getAttendance());

        collection.insertOne(doc);
        System.out.println("✅ Student Added Successfully!");
    }

    public void viewStudents() {

        for (Document doc : collection.find()) {
            System.out.println(doc.getInteger("rollNo") + " | " +
                    doc.getString("name") + " | " +
                    doc.getDouble("marks") + "% | Attendance: " +
                    doc.getDouble("attendance") + "%");
        }
    }

    public void deleteStudent(int rollNo) {
        collection.deleteOne(eq("rollNo", rollNo));
        System.out.println("🗑 Student Deleted!");
    }

    public void searchStudent(int rollNo) {

        Document doc = collection.find(eq("rollNo", rollNo)).first();

        if (doc != null) {
            System.out.println("Found: " + doc.getString("name"));
        } else {
            System.out.println("No student found!");
        }
    }
}
