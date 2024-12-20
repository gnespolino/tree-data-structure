package com.nespolino.qtech.exam.service;

import com.nespolino.qtech.exam.treedata.Tree;

public interface TreeRepository<T> {

  Tree<T> getTreeData();

  void save(Tree<T> tTree);
}
