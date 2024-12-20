package com.nespolino.qtech.exam.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TreeOperations<T> {

  public Tree<T> addChild(Tree<T> tree, String parentId, Tree<T> node) {
    Tree<T> parent = getNode(tree, parentId);
    parent.getChildren().add(node);
    return tree;
  }

  public Optional<Tree<T>> findNode(Tree<T> tree, String nodeId) {
    if (tree.getNodeId().equals(nodeId)) {
      return Optional.of(tree);
    }

    for (Tree<T> child : tree.getChildren()) {
      Optional<Tree<T>> parent = findNode(child, nodeId);
      if (parent.isPresent()) {
        return parent;
      }
    }
    return Optional.empty();
  }

  public Tree<T> deleteChild(Tree<T> tree, String parentId, String nodeId) {
    Tree<T> parent = getNode(tree, parentId);
    if (!containsId(parent, nodeId)) {
      throw new NodeNotFoundException(nodeId);
    }
    parent.getChildren().removeIf(child -> child.getNodeId().equals(nodeId));
    return tree;
  }

  public void moveNode(Tree<T> tree, String nodeId, String fromParentId, String toParentId) {
    Tree<T> fromParent = getNode(tree, fromParentId);
    Tree<T> toParent = getNode(tree, toParentId);
    Tree<T> node =
        fromParent.getChildren().stream()
            .filter(child -> child.getNodeId().equals(nodeId))
            .findFirst()
            .orElseThrow(() -> new NodeNotFoundException(nodeId));
    fromParent.getChildren().remove(node);
    toParent.getChildren().add(node);
  }

  private Tree<T> getNode(Tree<T> tree, String nodeId) {
    return findNode(tree, nodeId).orElseThrow(() -> new NodeNotFoundException(nodeId));
  }

  public boolean containsId(Tree<T> tree, String nodeId) {
    return findNode(tree, nodeId).isPresent();
  }

  public List<T> getDescendantsData(Tree<T> tree) {
    return getDescendantsData(tree, false);
  }

  public List<T> getDescendantsData(Tree<T> tree, boolean shouldIncludeSelf) {
    List<T> descentandsData = new ArrayList<>();
    if (shouldIncludeSelf) {
      descentandsData.add(tree.getData());
    }
    for (Tree<T> child : tree.getChildren()) {
      descentandsData.addAll(getDescendantsData(child, true));
    }
    return descentandsData;
  }
}
