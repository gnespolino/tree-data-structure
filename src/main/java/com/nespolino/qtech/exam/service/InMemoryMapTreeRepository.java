package com.nespolino.qtech.exam.service;

import com.nespolino.qtech.exam.data.Tree;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryMapTreeRepository implements TreeRepository<Map<String, Object>> {

  private Tree<Map<String, Object>> defaultTree;

  @Override
  public Tree<Map<String, Object>> getTreeData() {
    return defaultTree;
  }

  @Override
  public void save(Tree<Map<String, Object>> tree) {
    defaultTree = tree;
  }
}
