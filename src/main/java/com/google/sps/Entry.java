package com.google.sps;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Sentiment;
import java.io.IOException;

public class Entry {

    private long id;
    private String message;
    private long timestamp;
    private double score;
    //TODO: consider noting the user too

    public Entry(long id, String message, long timestamp, double score) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.score = score;
    }

    public static double sentimentAnalysis(String aMessage) throws IOException {
        LanguageServiceClient language = LanguageServiceClient.create();
        Document doc = Document.newBuilder().setContent(aMessage).setType(Document.Type.PLAIN_TEXT).build();
        AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
        Sentiment sentiment = response.getDocumentSentiment();
        float score = 0;
        if (sentiment == null) {
            score = 0;
        } else {
            score = sentiment.getScore();
        }
        double doubleScore = (double)score;
        return doubleScore;
    }

}