package com.recommendation;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.*;
import java.util.*;

/**
 * ============================================================
 *   Item-Based Collaborative Filtering — Task 4 (Bonus)
 *   Algorithm : Item-Based Collaborative Filtering
 *   Library   : Apache Mahout
 * ============================================================
 *
 * DIFFERENCE FROM USER-BASED:
 *   Instead of finding similar USERS, this approach finds
 *   similar ITEMS. If a user liked Product A, and Product A
 *   is similar to Product B, recommend Product B.
 *
 *   Item-based is generally more scalable for large catalogs
 *   (Amazon uses this approach).
 */
public class ItemBasedRecommender {

    private static final Map<Long, String> PRODUCT_NAMES = new HashMap<>();

    public static void main(String[] args) throws Exception {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║    Item-Based Recommendation System (Bonus)      ║");
        System.out.println("║      Tanimoto Coefficient Similarity             ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // Load product names
        loadProductNames("data/products.csv");

        // Load data model
        DataModel model = new FileDataModel(new File("data/ratings.csv"));
        System.out.println("✅ Data loaded: " + model.getNumUsers()
                + " users, " + model.getNumItems() + " products.\n");

        // Tanimoto Coefficient works well for binary/sparse data
        ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

        System.out.println("🔍 Item-Based Recommendations:\n");
        System.out.println("─".repeat(55));

        for (long userId = 1; userId <= model.getNumUsers(); userId++) {
            List<RecommendedItem> recs = recommender.recommend(userId, 3);
            System.out.printf("👤 User %d → ", userId);
            if (recs.isEmpty()) {
                System.out.println("No new recommendations.");
            } else {
                List<String> names = new ArrayList<>();
                for (RecommendedItem item : recs) {
                    names.add(getProductName(item.getItemID())
                            + String.format(" (%.2f)", item.getValue()));
                }
                System.out.println(String.join(" | ", names));
            }
        }

        System.out.println("─".repeat(55));
        System.out.println("\n✅ Item-based recommender finished.");
    }

    private static void loadProductNames(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line; boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] p = line.split(",");
                if (p.length >= 2) PRODUCT_NAMES.put(Long.parseLong(p[0].trim()), p[1].trim());
            }
        }
    }

    private static String getProductName(long id) {
        return PRODUCT_NAMES.getOrDefault(id, "Product #" + id);
    }
}
