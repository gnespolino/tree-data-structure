package com.nespolino.qtech.exam.map;

import com.nespolino.qtech.exam.service.TreeService;
import com.nespolino.qtech.exam.treedata.Tree;
import com.nespolino.qtech.exam.treedata.TreeFactory;
import com.nespolino.qtech.exam.treedata.TreeOperations;
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
  public Tree<Map<String, Object>> getDefaultTree() {
    return TreeFactory.createTree("ROOT", Map.of("name", "ROOT"));
  }
}
