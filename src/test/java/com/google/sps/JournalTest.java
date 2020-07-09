// package com.google.sps;

// import java.util.ArrayList;
// import java.util.Arrays;
// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.junit.runners.JUnit4;
// import java.io.IOException;

// @RunWith(JUnit4.class)
// public final class JournalTest {
//     private static final String positiveText = "I am happy and great!";
//     private static final String negativeText = "I am feeling sad and depressed"; 

//     private Journal sentiment;

//     @Before
//     public void setUp() {
//         sentiment = new Journal();
//     }

//     @Test
//     public void theNegativeTest() throws IOException {
//         float score = sentiment.sentimentAnalysis(negativeText);
//         boolean actual = score < 0.0f;

//         Assert.assertEquals(true, actual);
//     }

//     @Test
//     public void thePositiveTest() throws IOException {
//         float score = sentiment.sentimentAnalysis(positiveText);
//         boolean actual = score >= 0.0f;

//         Assert.assertEquals(true, actual);
//     }

// } 
