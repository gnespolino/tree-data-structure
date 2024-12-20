package com.nespolino.qtech.exam.data;

public class NodeNotFoundException extends IllegalArgumentException {

  public NodeNotFoundException(String parentId) {
    super("Node with id " + parentId + " not found");
  }
}
