package com.nespolino.qtech.exam.service;

import com.nespolino.qtech.exam.data.DuplicateIdException;
import com.nespolino.qtech.exam.data.Tree;
import com.nespolino.qtech.exam.data.TreeFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@RequiredArgsConstructor
public abstract class TreeService<T> {

  private final TreeRepository<T> treeRepository;

  public Tree<T> getTreeData() {
    return Optional.ofNullable(treeRepository.getTreeData()).orElseGet(this::getDefaultTree);
  }

  abstract Tree<T> getDefaultTree();

  public String addNodeAndGetId(String parentId, String nodeId, T data) {
    Tree<T> treeData = getTreeData();
    if (treeData.containsId(nodeId)) {
      throw new DuplicateIdException(nodeId);
    }
    Pair<String, Tree<T>> nodeWithId = TreeFactory.createNode(nodeId, data);
    Tree<T> tTree = treeData.addChild(parentId, nodeWithId.getRight());
    treeRepository.save(tTree);
    return nodeWithId.getLeft();
  }

  public Tree<T> deleteNode(String parentId, String nodeId) {
    Tree<T> tTree = getTreeData().deleteChild(parentId, nodeId);
    treeRepository.save(tTree);
    return tTree;
  }

  public Tree<T> moveNode(String nodeId, String fromParentId, String toParentId) {
    Tree<T> tTree = getTreeData();
    tTree.moveNode(nodeId, fromParentId, toParentId);
    treeRepository.save(tTree);
    return tTree;
  }

  public List<T> getDescendantsData(String nodeId) {
    return getTreeData().findNode(nodeId).map(Tree::getDescendantsData).orElse(List.of());
  }
}
