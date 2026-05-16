# recommendation-system-task4

*COMPANY *: CODTECH IT SOLUTIONS

*NAME *: VISHALINI B

*INTERN ID *: CTIS8696

*DOMAIN *: Java Programming

*DURATION *: 4 WEEEKS

*MENTOR *: NEELA SANTOSH

# 🤖 AI-Based Recommendation System — Java

A Java-based product recommendation engine that suggests products to users
based on their preferences using **Collaborative Filtering** powered by
the Apache Mahout machine learning library. The system analyses user rating
patterns, identifies similar users, and predicts products they are likely
to enjoy — similar to how Amazon and Netflix recommend content.

---

## 📌 Overview

This project is built as part of **Task 4 — AI-Based Recommendation System**
using the Java programming language and Apache Mahout. The application loads
user-product rating data from a CSV file, computes similarity between users
using Pearson Correlation, identifies a neighborhood of similar users, and
recommends products that similar users liked but the target user has not yet
rated. A predicted rating score is displayed alongside each recommendation.

Two approaches are implemented: **User-Based Collaborative Filtering** as the
main engine, and **Item-Based Collaborative Filtering** as a bonus
implementation using Tanimoto Coefficient Similarity. Both approaches
represent industry-standard recommendation techniques used in real-world systems.

---

## 👩‍💻 Role

**Role: Java Developer — AI & Machine Learning**

Responsibilities undertaken in this project:

- Designed and implemented a User-Based Collaborative Filtering engine using Apache Mahout
- Loaded and processed user-product rating data from CSV using `FileDataModel`
- Computed user similarity using `PearsonCorrelationSimilarity`
- Built a threshold-based user neighborhood using `ThresholdUserNeighborhood`
- Generated ranked product recommendations with predicted rating scores
- Implemented a bonus Item-Based CF engine using `TanimotoCoefficientSimilarity`
- Loaded product catalog from CSV and mapped product IDs to names and categories
- Wrote clean, modular, and well-commented Java code with structured output

---

## 🛠️ Platform & Tools Used

| Tool | Details |
|---|---|
| IDE | Eclipse IDE |
| Language | Java |
| JDK Version | JDK 11+ |
| Build Tool | Apache Maven 3.6+ |
| ML Library | Apache Mahout 0.13.0 |
| Algorithm | Collaborative Filtering |
| Similarity Metrics | Pearson Correlation, Tanimoto Coefficient |
| Data Format | CSV |

---

## ⚙️ Features

- ✅ User-Based Collaborative Filtering using Pearson Correlation Similarity
- ✅ Item-Based Collaborative Filtering using Tanimoto Coefficient (Bonus)
- ✅ Loads ratings and product catalog from CSV files
- ✅ Displays products already rated by each user
- ✅ Recommends top 3 unrated products with predicted rating scores
- ✅ Shows product name and category alongside each recommendation
- ✅ Handles users who have rated all products gracefully
- ✅ Clean, structured console output with clear formatting

---

## 📂 Project Structure

```
recommendation-system/
│
├── data/
│   ├── ratings.csv                       ← 50 user-product ratings (sample data)
│   └── products.csv                      ← 8 products across 3 categories
│
├── src/
│   └── main/
│       └── java/
│           └── com/recommendation/
│               ├── RecommendationEngine.java    ← MAIN: User-Based CF
│               └── ItemBasedRecommender.java    ← BONUS: Item-Based CF
│
├── pom.xml                               ← Maven build + Mahout dependencies
└── README.md
```

---

## 🚀 How to Run

Follow these steps to run the project in **Eclipse IDE**:

1. Open **Eclipse IDE**
2. Go to **File → Import → Maven → Existing Maven Projects**
3. Click **Browse** → select the `recommendation-system` folder
4. Click **Finish** and wait for Maven to download Mahout automatically
5. Once build is complete, open `RecommendationEngine.java`
6. Right-click inside the file → **Run As → Java Application**
7. Output appears in the **Console** tab at the bottom

**To run via terminal:**
```bash
mvn clean package -q
java -cp target/recommendation-system-1.0-SNAPSHOT.jar com.recommendation.RecommendationEngine
```

**To run the bonus Item-Based recommender:**
```bash
java -cp target/recommendation-system-1.0-SNAPSHOT.jar com.recommendation.ItemBasedRecommender
```

---

## 📊 Sample Data

### Products Catalog — `products.csv`

| Product ID | Product Name | Category |
|---|---|---|
| 101 | Wireless Headphones | Electronics |
| 102 | Running Shoes | Sports |
| 103 | Python Programming Book | Books |
| 104 | Coffee Maker | Kitchen |
| 105 | Yoga Mat | Sports |
| 106 | Mechanical Keyboard | Electronics |
| 107 | The Alchemist Novel | Books |
| 108 | Air Fryer | Kitchen |

### Ratings — `ratings.csv`
- **10 users** × **8 products** = **50 rating records**
- Rating scale: **1.0 (lowest) → 5.0 (highest)**
- Format: `userID,productID,rating` (no header row — required by Mahout)

---

## 🔍 How It Works

### 📂 loadProductCatalog()
Reads `products.csv` line by line using `BufferedReader`. Parses each row
into product ID, name, and category, and stores them in `HashMap` collections
for fast lookup during recommendation display. Called once at startup before
any recommendation logic runs.

### 🔗 FileDataModel (Mahout)
Mahout's built-in `FileDataModel` reads `ratings.csv` and builds an in-memory
data model of all user-product-rating relationships. This model is the input
to all similarity and recommendation computations.

### 📐 PearsonCorrelationSimilarity (Mahout)
Computes a similarity score between every pair of users based on how
similarly they rate products. A score of 1.0 means identical taste,
0.0 means no correlation, and -1.0 means completely opposite preferences.

### 👥 ThresholdUserNeighborhood (Mahout)
Builds a neighborhood of similar users for each target user by including
all users whose similarity score is at or above the threshold of 0.1.
Only users in this neighborhood contribute to recommendation predictions.

### 🎯 GenericUserBasedRecommender (Mahout)
Takes the data model, neighborhood, and similarity metric as inputs.
For each target user, identifies unrated products, predicts ratings using
a weighted average of neighbors' ratings, sorts by predicted score,
and returns the top N recommendations.

### 🖥️ generateAndPrintRecommendations()
Custom method that fetches rated items for a user, maps product IDs to
names and categories using the preloaded catalog, prints all recommendations
with predicted scores in a clean formatted layout, and handles the edge
case where a user has rated all available products.

---

## 🛡️ Error Handling

| Error | Cause | Handling |
|---|---|---|
| `NumberFormatException` | Header row in ratings CSV | Remove header — Mahout reads raw numbers only |
| `TasteException` | No neighbors found for a user | Returns empty list — displayed as no recommendations |
| `FastIDSet.toArray()` mismatch | Mahout API difference from standard Java | Use `.toArray()` without arguments |
| User rated all items | No unrated products left | Displays "No recommendations available" message |
| Missing product in catalog | Product ID not in products.csv | Returns "Product #ID" as fallback name |
| `IOException` on CSV load | File not found or wrong path | Caught and rethrown with descriptive message |

---

## 🧠 Key Concepts

| Concept | Explanation |
|---|---|
| **Collaborative Filtering** | Recommends items based on behavior of similar users — no product knowledge needed |
| **Pearson Correlation** | Measures how similarly two users rate items. Score 1.0 = identical, -1.0 = opposite |
| **Tanimoto Coefficient** | Measures overlap between item sets. Works well for sparse and binary data |
| **Threshold Neighborhood** | Includes all users with similarity ≥ 0.1 as neighbors for prediction |
| **Predicted Rating** | Weighted average of neighbors' ratings, weighted by their similarity score |
| **User-Based CF** | Finds similar users → recommends what they liked (used by early Netflix) |
| **Item-Based CF** | Finds similar items → recommends related products (used by Amazon) |

---

## 📚 Concepts Used

| Concept | Class / Method Used |
|---|---|
| Data Loading | `FileDataModel`, `BufferedReader`, `FileReader` |
| User Similarity | `PearsonCorrelationSimilarity` |
| Item Similarity | `TanimotoCoefficientSimilarity` |
| Neighborhood Building | `ThresholdUserNeighborhood` |
| Recommendation Engine | `GenericUserBasedRecommender`, `GenericItemBasedRecommender` |
| Product Lookup | `HashMap<Long, String>` |
| Build & Dependencies | `pom.xml`, Apache Maven |

---

## 📤 Output

---

## 👩‍🎓 Author

**Vishalini B**
B.Tech — Information Technology | Third Year
Panimalar Engineering College, Chennai
