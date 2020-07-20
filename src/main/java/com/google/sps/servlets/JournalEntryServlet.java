package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.servlets.Entry;
import com.google.sps.Journal;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
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
    UserService userService = UserServiceFactory.getUserService();
    String nickname = getUserNickname(userService.getCurrentUser().getUserId());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query(nickname).addSort("timestamp", SortDirection.DESCENDING);
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

        if (userService.isUserLoggedIn()) {
            Journal sentiment = new Journal();

            if (nickname == null) {
                response.sendRedirect("/nickname");
                return;
            }
        
            // Get the input from the form.
            String message = request.getParameter("message");
            if (nickname == null) {
            response.sendRedirect("/nickname");
            return;
            }
            long timestamp = System.currentTimeMillis();
            double score = sentiment.sentimentAnalysis(message);

            Entity entryEntity = new Entity(nickname);
            entryEntity.setProperty("message", message);
            entryEntity.setProperty("timestamp", timestamp);
            entryEntity.setProperty("score", score);
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            datastore.put(entryEntity);
            response.sendRedirect("home.html"); 
        }
        else{
            response.sendRedirect("index.html");
        }
        
    }

    private String getUserNickname(String id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query =
            new Query("UserInfo")
                .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
        PreparedQuery results = datastore.prepare(query);
        Entity entity = results.asSingleEntity();
        if (entity == null) {
        return "";
        }
        String nickname = (String) entity.getProperty("nickname");
        return nickname;
    }
}