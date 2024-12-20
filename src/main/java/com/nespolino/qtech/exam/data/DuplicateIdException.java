package com.nespolino.qtech.exam.data;

public class DuplicateIdException extends IllegalArgumentException {

  public DuplicateIdException(String nodeId) {
    super("Node with id " + nodeId + " already exists");
  }
}
