package service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.MongoDBConnection;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;

import java.util.Arrays;

public class AnalyticsService {

    MongoDatabase database = MongoDBConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("students");

    public void showAnalytics() {

        // Class Average
        collection.aggregate(Arrays.asList(
                group(null, avg("averageMarks", "$marks"))
        )).forEach(doc ->
                System.out.println("📊 Class Average: " + doc.getDouble("averageMarks"))
        );

        // Top Performer
        Document top = collection.find()
                .sort(new Document("marks", -1))
                .first();

        if (top != null) {
            System.out.println("🏆 Top Performer: " +
                    top.getString("name") +
                    " (" + top.getDouble("marks") + "%)");
        }
    }
}
