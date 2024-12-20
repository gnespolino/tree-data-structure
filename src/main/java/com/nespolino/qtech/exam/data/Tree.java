package com.nespolino.qtech.exam.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Tree<T> {
  private final String nodeId;
  private final T data;
  private final List<Tree<T>> children;

  public Tree<T> addChild(String parentId, Tree<T> node) {
    Tree<T> parent = getParentNode(parentId);
    parent.getChildren().add(node);
    return this;
  }

  public Optional<Tree<T>> findNode(String nodeId) {
    if (this.nodeId.equals(nodeId)) {
      return Optional.of(this);
    }

    for (Tree<T> child : children) {
      Optional<Tree<T>> parent = child.findNode(nodeId);
      if (parent.isPresent()) {
        return parent;
      }
    }
    return Optional.empty();
  }

  public Tree<T> deleteChild(String parentId, String nodeId) {
    Tree<T> parent = getParentNode(parentId);
    if (!parent.containsId(nodeId)) {
      throw new NodeNotFoundException(nodeId);
    }
    parent.getChildren().removeIf(child -> child.getNodeId().equals(nodeId));
    return this;
  }

  public void moveNode(String nodeId, String fromParentId, String toParentId) {
    Tree<T> fromParent = getParentNode(fromParentId);
    Tree<T> toParent = getParentNode(toParentId);
    Tree<T> node =
        fromParent.getChildren().stream()
            .filter(child -> child.getNodeId().equals(nodeId))
            .findFirst()
            .orElseThrow(() -> new NodeNotFoundException(nodeId));
    fromParent.getChildren().remove(node);
    toParent.getChildren().add(node);
  }

  private Tree<T> getParentNode(String parentId) {
    return findNode(parentId).orElseThrow(() -> new NodeNotFoundException(parentId));
  }

  public boolean containsId(String nodeId) {
    return findNode(nodeId).isPresent();
  }

  public List<T> getDescendantsData() {
    return getDescendantsData(false);
  }

  public List<T> getDescendantsData(boolean shouldIncludeSelf) {
    List<T> descentandsData = new ArrayList<>();
    if (shouldIncludeSelf) {
      descentandsData.add(data);
    }
    for (Tree<T> child : children) {
      descentandsData.addAll(child.getDescendantsData(true));
    }
    return descentandsData;
  }
}
