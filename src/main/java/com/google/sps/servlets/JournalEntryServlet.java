package com.google.sps.servlets;

import com.google.gson.Gson;
// import com.google.sps.Scoring;
import com.google.sps.Entry;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/journal")
public final class JournalEntryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query("Entry").addSort("timestamp", SortDirection.DESCENDING);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        List<Entry> entries = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            String message = (String) entity.getProperty("message");
            long timestamp = (long) entity.getProperty("timestamp");
            double score = (double) entity.getProperty("score");
            Entry entryToAdd = new Entry(id, message, timestamp, score);
            entries.add(entryToAdd);
        }
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(entries));
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {        
        // Scoring sentiment = new Scoring();        
        String message = request.getParameter("message");
        long timestamp = System.currentTimeMillis();
        double score = Entry.sentimentAnalysis(message);
        System.out.println(score);
        Entity entryEntity = new Entity("Entry");
        entryEntity.setProperty("message", message);
        entryEntity.setProperty("timestamp", timestamp);
        entryEntity.setProperty("score", score);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(entryEntity);
        response.sendRedirect("/index.html"); //do we want to redirect home after entry is submitted?

    }
}