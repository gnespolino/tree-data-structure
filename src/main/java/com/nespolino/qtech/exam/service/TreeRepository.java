package com.nespolino.qtech.exam.service;

import com.nespolino.qtech.exam.data.Tree;

interface TreeRepository<T> {

  Tree<T> getTreeData();

  void save(Tree<T> tTree);
}
