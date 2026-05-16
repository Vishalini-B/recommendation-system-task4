# Task 4 — AI-Based Recommendation System
### Using Java + Apache Mahout | Collaborative Filtering

---

## 📌 Overview

This project implements an **AI-based product recommendation engine** using:
- **Language**: Java 11
- **Library**: Apache Mahout 0.13.0
- **Algorithm**: User-Based & Item-Based Collaborative Filtering
- **Sample Data**: 10 users × 8 products with ratings (1.0–5.0)

---

## 🧠 How It Works

### What is Collaborative Filtering?
Collaborative Filtering recommends items based on the behavior of **similar users** — it does not need to know anything about the product itself, only user ratings.

```
User A rated: Headphones(5), Keyboard(4), Book(2)
User B rated: Headphones(5), Keyboard(5), Book(2)  ← similar to A
User B also rated: Coffee Maker(4)

→ Recommend Coffee Maker to User A
```

### Two Approaches Implemented

| Approach | File | Algorithm | Similarity Metric |
|---|---|---|---|
| User-Based CF | `RecommendationEngine.java` | Find similar users → recommend what they liked | Pearson Correlation |
| Item-Based CF | `ItemBasedRecommender.java` | Find similar items → recommend similar products | Tanimoto Coefficient |

---

## 📁 Project Structure

```
recommendation-system/
│
├── data/
│   ├── ratings.csv       ← User-product ratings (sample data)
│   └── products.csv      ← Product catalog (names + categories)
│
├── src/main/java/com/recommendation/
│   ├── RecommendationEngine.java   ← MAIN: User-Based CF
│   └── ItemBasedRecommender.java   ← BONUS: Item-Based CF
│
├── pom.xml               ← Maven build + Mahout dependency
└── README.md
```

---

## 📊 Sample Data

**ratings.csv** — 50 user-product rating records:
```
userID, productID, rating
1, 101, 5.0    ← User 1 gave Wireless Headphones a 5-star rating
1, 102, 3.0    ← User 1 gave Running Shoes a 3-star rating
...
```

**products.csv** — 8 products across 3 categories:
```
101 — Wireless Headphones   [Electronics]
102 — Running Shoes         [Sports]
103 — Python Programming Book [Books]
104 — Coffee Maker          [Kitchen]
105 — Yoga Mat              [Sports]
106 — Mechanical Keyboard   [Electronics]
107 — The Alchemist Novel   [Books]
108 — Air Fryer             [Kitchen]
```

---

## ⚙️ Setup & Run

### Prerequisites
- Java 11 or higher → `java -version`
- Maven 3.6+ → `mvn -version`

### Step 1: Clone / Download the project
```bash
cd recommendation-system
```

### Step 2: Build the project
```bash
mvn clean package -q
```
This downloads Mahout automatically and creates a fat JAR.

### Step 3: Run User-Based Recommender (Main)
```bash
java -cp target/recommendation-system-1.0-SNAPSHOT.jar \
     com.recommendation.RecommendationEngine
```

### Step 4: Run Item-Based Recommender (Bonus)
```bash
java -cp target/recommendation-system-1.0-SNAPSHOT.jar \
     com.recommendation.ItemBasedRecommender
```

---

## 📤 Sample Output

```
╔══════════════════════════════════════════════════╗
║      AI-Based Product Recommendation System      ║
║         Powered by Apache Mahout — Task 4        ║
╚══════════════════════════════════════════════════╝

📂 Loading ratings data from data/ratings.csv...
✅ Loaded 10 users and 8 products.

🔍 Computing user similarity (Pearson Correlation)...
🤖 Generating recommendations...

────────────────────────────────────────────────────────────
👤 User 1 Recommendations:
   Already rated : Wireless Headphones, Running Shoes, Python Book, Coffee Maker, Yoga Mat
   Recommended  :
      1. Mechanical Keyboard         [Electronics]  → Predicted Rating: 4.12
      2. The Alchemist Novel         [Books]        → Predicted Rating: 3.85
      3. Air Fryer                   [Kitchen]      → Predicted Rating: 3.40

👤 User 2 Recommendations:
   Already rated : Wireless Headphones, Running Shoes, Python Book, Coffee Maker, Air Fryer
   Recommended  :
      1. Yoga Mat                    [Sports]       → Predicted Rating: 4.30
      2. Mechanical Keyboard         [Electronics]  → Predicted Rating: 4.05
      3. The Alchemist Novel         [Books]        → Predicted Rating: 3.75
...
```

---

## 🔬 Key Concepts

### Pearson Correlation Similarity
Measures how two users rate items relative to their own average.
- Score = **1.0** → Users rate everything identically
- Score = **0.0** → No correlation
- Score = **-1.0** → Users always disagree

### Threshold Neighborhood
`ThresholdUserNeighborhood(0.1, ...)` includes all users with similarity ≥ 0.1 as neighbors. A higher threshold means fewer but more similar neighbors.

### Predicted Rating Formula
Mahout estimates the rating User A would give Item X by taking a weighted average of similar users' ratings for Item X, weighted by their similarity to User A.

---

## 🚀 Possible Enhancements

1. **Matrix Factorization (SVD)** — More accurate than CF for large datasets
2. **Content-Based Filtering** — Recommend by product features, not just ratings  
3. **Hybrid System** — Combine CF + Content-Based
4. **Real Database** — Replace CSV with MySQL/PostgreSQL
5. **REST API** — Wrap in Spring Boot to serve recommendations via HTTP
6. **Gemini AI Layer** — Add natural language explanations for recommendations

---

*Task 4 — AI-Based Recommendation System | Apache Mahout | Collaborative Filtering*
