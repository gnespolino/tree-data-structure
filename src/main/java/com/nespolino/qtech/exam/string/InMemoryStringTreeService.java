package com.nespolino.qtech.exam.string;

import com.nespolino.qtech.exam.service.TreeService;
import com.nespolino.qtech.exam.treedata.Tree;
import com.nespolino.qtech.exam.treedata.TreeFactory;
import com.nespolino.qtech.exam.treedata.TreeOperations;
import org.springframework.stereotype.Service;

@Service
public class InMemoryStringTreeService extends TreeService<String> {

  public InMemoryStringTreeService(
      InMemoryStringTreeRepository treeRepository, TreeOperations<String> treeOperations) {
    super(treeRepository, treeOperations);
  }

  @Override
  protected Tree<String> getDefaultTree() {
    return TreeFactory.createTree("ROOT", "I'm de default tree");
  }
}
