package com.nespolino.qtech.exam.service;

import com.nespolino.qtech.exam.treedata.Tree;

public class InMemoryTreeRepository<T> implements TreeRepository<T> {

  private Tree<T> defaultTree;

  @Override
  public Tree<T> getTreeData() {
    return defaultTree;
  }

  @Override
  public void save(Tree<T> tree) {
    defaultTree = tree;
  }
}
