package com.nespolino.qtech.exam.service;

import com.nespolino.qtech.exam.data.Tree;
import com.nespolino.qtech.exam.data.TreeFactory;
import com.nespolino.qtech.exam.data.TreeOperations;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class InMemoryMapTreeService extends TreeService<Map<String, Object>> {

  public InMemoryMapTreeService(
      InMemoryMapTreeRepository treeRepository,
      TreeOperations<Map<String, Object>> treeOperations) {
    super(treeRepository, treeOperations);
  }

  @Override
  Tree<Map<String, Object>> getDefaultTree() {
    return TreeFactory.createTree("ROOT", Map.of("name", "ROOT"));
  }
}
