package com.nespolino.qtech.exam.service;

import com.nespolino.qtech.exam.data.DuplicateIdException;
import com.nespolino.qtech.exam.data.Tree;
import com.nespolino.qtech.exam.data.TreeFactory;
import com.nespolino.qtech.exam.data.TreeOperations;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@RequiredArgsConstructor
public abstract class TreeService<T> {

  private final TreeRepository<T> treeRepository;
  private final TreeOperations<T> treeOperations;

  public Tree<T> getTreeData() {
    return Optional.ofNullable(treeRepository.getTreeData()).orElseGet(this::getDefaultTree);
  }

  abstract Tree<T> getDefaultTree();

  public String addNodeAndGetId(String parentId, String nodeId, T data) {
    Tree<T> treeData = getTreeData();
    if (treeOperations.containsId(treeData, nodeId)) {
      throw new DuplicateIdException(nodeId);
    }
    Pair<String, Tree<T>> nodeWithId = TreeFactory.createNode(nodeId, data);
    Tree<T> tTree = treeOperations.addChild(treeData, parentId, nodeWithId.getRight());
    treeRepository.save(tTree);
    return nodeWithId.getLeft();
  }

  public Tree<T> deleteNode(String parentId, String nodeId) {
    Tree<T> tTree = treeOperations.deleteChild(getTreeData(), parentId, nodeId);
    treeRepository.save(tTree);
    return tTree;
  }

  public Tree<T> moveNode(String nodeId, String fromParentId, String toParentId) {
    Tree<T> tTree = getTreeData();
    treeOperations.moveNode(tTree, nodeId, fromParentId, toParentId);
    treeRepository.save(tTree);
    return tTree;
  }

  public List<T> getDescendantsData(String nodeId) {
    return treeOperations
        .findNode(getTreeData(), nodeId)
        .map(treeOperations::getDescendantsData)
        .orElse(List.of());
  }
}
