package com.recommendation;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.*;
import java.util.*;

/**
 * ============================================================
 *   AI-Based Recommendation System — Task 4
 *   Algorithm : User-Based Collaborative Filtering
 *   Library   : Apache Mahout
 *   Author    : Vishalini
 * ============================================================
 *
 * HOW IT WORKS:
 *   1. Load user-product rating data from a CSV file.
 *   2. Compute similarity between users using Pearson Correlation.
 *   3. Find the neighborhood of similar users for a target user.
 *   4. Recommend products that similar users liked but the
 *      target user has not yet interacted with.
 */
public class RecommendationEngine {

    // ── Product name lookup (loaded from products.csv) ──────────────
    private static final Map<Long, String> PRODUCT_NAMES = new HashMap<>();
    private static final Map<Long, String> PRODUCT_CATEGORIES = new HashMap<>();

    public static void main(String[] args) throws Exception {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║      AI-Based Product Recommendation System      ║");
        System.out.println("║         Powered by Apache Mahout — Task 4        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();

        // ── Step 1: Load product catalog ────────────────────────────
        loadProductCatalog("data/products.csv");

        // ── Step 2: Load ratings data model ─────────────────────────
        System.out.println("📂 Loading ratings data from data/ratings.csv...");
        File ratingsFile = new File("data/ratings.csv");
        DataModel model = new FileDataModel(ratingsFile);
        System.out.println("✅ Loaded " + model.getNumUsers() + " users and "
                + model.getNumItems() + " products.\n");

        // ── Step 3: Compute user similarity ─────────────────────────
        // Pearson Correlation measures how similarly two users rate items.
        // Score of 1.0 = perfectly similar, -1.0 = perfectly opposite.
        System.out.println("🔍 Computing user similarity (Pearson Correlation)...");
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // ── Step 4: Build user neighborhood ─────────────────────────
        // Threshold 0.1 means: include users with similarity score >= 0.1
        // (Lowered to ensure enough neighbors exist in our small dataset)
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

        // ── Step 5: Build the recommender ───────────────────────────
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // ── Step 6: Generate recommendations for all users ──────────
        System.out.println("🤖 Generating recommendations...\n");
        System.out.println("─".repeat(60));

        int numRecommendations = 3; // How many products to recommend per user

        for (long userId = 1; userId <= model.getNumUsers(); userId++) {
            generateAndPrintRecommendations(recommender, model, userId, numRecommendations);
        }

        System.out.println("─".repeat(60));
        System.out.println("\n✅ Recommendation engine completed successfully.");
        System.out.println("   Algorithm : User-Based Collaborative Filtering");
        System.out.println("   Similarity: Pearson Correlation");
        System.out.println("   Library   : Apache Mahout");
    }

    /**
     * Generates and prints recommendations for a single user.
     *
     * @param recommender        The Mahout recommender instance
     * @param model              The data model
     * @param userId             Target user ID
     * @param numRecommendations Number of items to recommend
     */
    private static void generateAndPrintRecommendations(
            UserBasedRecommender recommender,
            DataModel model,
            long userId,
            int numRecommendations) throws TasteException {

        System.out.printf("👤 User %d Recommendations:%n", userId);

        // Print what this user has already rated
        System.out.print("   Already rated : ");
        try {
        	long[] ratedItems = model.getItemIDsFromUser(userId).toArray();
        	List<String> ratedNames = new ArrayList<>();
            for (long itemId : ratedItems) {
                ratedNames.add(getProductName(itemId));
            }
            System.out.println(String.join(", ", ratedNames));
        } catch (Exception e) {
            System.out.println("(none)");
        }

        // Get recommendations
        List<RecommendedItem> recommendations = recommender.recommend(userId, numRecommendations);

        if (recommendations.isEmpty()) {
            System.out.println("   Recommended  : No recommendations available (user has rated all items)");
        } else {
            System.out.println("   Recommended  :");
            for (int i = 0; i < recommendations.size(); i++) {
                RecommendedItem item = recommendations.get(i);
                long productId = item.getItemID();
                float score = item.getValue();
                String name = getProductName(productId);
                String category = getProductCategory(productId);

                System.out.printf("      %d. %-28s [%s]  → Predicted Rating: %.2f%n",
                        i + 1, name, category, score);
            }
        }
        System.out.println();
    }

    /**
     * Loads product names and categories from products.csv into memory.
     */
    private static void loadProductCatalog(String filePath) throws IOException {
        System.out.println("📦 Loading product catalog from " + filePath + "...");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    long id = Long.parseLong(parts[0].trim());
                    PRODUCT_NAMES.put(id, parts[1].trim());
                    PRODUCT_CATEGORIES.put(id, parts[2].trim());
                }
            }
        }
        System.out.println("✅ Loaded " + PRODUCT_NAMES.size() + " products.\n");
    }

    private static String getProductName(long productId) {
        return PRODUCT_NAMES.getOrDefault(productId, "Product #" + productId);
    }

    private static String getProductCategory(long productId) {
        return PRODUCT_CATEGORIES.getOrDefault(productId, "Unknown");
    }
}
