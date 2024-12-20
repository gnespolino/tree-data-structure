package com.nespolino.qtech.exam.treedata;

import com.nespolino.qtech.exam.exception.NodeNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
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
            .filter(child -> StringUtils.equals(child.getNodeId(), nodeId))
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

  public List<Tree<T>> getDescendantsData(Tree<T> tree) {
    return getDescendantsData(tree, false);
  }

  public List<Tree<T>> getDescendantsData(Tree<T> tree, boolean shouldIncludeSelf) {
    List<Tree<T>> descendantsData = new ArrayList<>();
    if (shouldIncludeSelf) {
      descendantsData.add(tree);
    }
    for (Tree<T> child : tree.getChildren()) {
      descendantsData.addAll(getDescendantsData(child, true));
    }
    return descendantsData;
  }
}
