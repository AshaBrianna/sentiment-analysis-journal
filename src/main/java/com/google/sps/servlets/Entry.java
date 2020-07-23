package com.google.sps.servlets;

public final class Entry {

  private final long id;
  private final String message;
  private final String date;
  private final double score;
  private final String email;
  //TODO: consider noting the user too

  public Entry(long id, String message, String date, double score, String email) {
    this.id = id;
    this.message = message;
    this.date = date;
    this.score = score;
    this.email = email;
  }
}