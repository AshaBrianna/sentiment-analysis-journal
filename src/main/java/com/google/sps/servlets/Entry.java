package com.google.sps.servlets;

public final class Entry {

  private final long id;
  private final String message;
  private final long timestamp;
  private final double score;
  //TODO: consider noting the user too

  public Entry(long id, String message, long timestamp, double score) {
    this.id = id;
    this.message = message;
    this.timestamp = timestamp;
    this.score = score;
  }
}