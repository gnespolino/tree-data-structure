package com.nespolino.qtech.exam.treedata;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeFactory {

  public static <T> Tree<T> createTree(String treeName, T rootData) {
    return new Tree<>(treeName, rootData, new ArrayList<>());
  }

  public static <T> Pair<String, Tree<T>> createNode(String nodeId, T data) {
    String id = Optional.ofNullable(nodeId).orElseGet(() -> UUID.randomUUID().toString());
    return Pair.of(id, createTree(id, data));
  }
}
