package com.google.sps;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Sentiment;
import java.io.IOException;

public class Journal {

    public double sentimentAnalysis(String message) throws IOException {
        LanguageServiceClient language = LanguageServiceClient.create();
        Document doc = Document.newBuilder().setContent(message).setType(Document.Type.PLAIN_TEXT).build();
        AnalyzeSentimentResponse response = language.analyzeSentiment(doc);
        Sentiment sentiment = response.getDocumentSentiment();
        double score = 0;
        if (sentiment == null) {
            score = 0;
        } else {
            score = sentiment.getScore();
        }

        return score;
    }

}