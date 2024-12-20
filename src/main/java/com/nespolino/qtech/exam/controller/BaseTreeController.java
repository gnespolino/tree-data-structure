package com.nespolino.qtech.exam.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.nespolino.qtech.exam.data.Tree;
import com.nespolino.qtech.exam.service.TreeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/nodes")
@RequiredArgsConstructor
public abstract class BaseTreeController<T> {

  private final TreeService<T> treeService;

  @GetMapping
  public Tree<T> getTreeData() {
    return treeService.getTreeData();
  }

  @GetMapping("/{nodeId}/descendants")
  public List<T> getDescendantsData(@PathVariable String nodeId) {
    return treeService.getDescendantsData(nodeId);
  }

  @PostMapping("/{parentId}/children")
  @ResponseStatus(CREATED)
  public String addNode(
      @PathVariable String parentId,
      @RequestParam(required = false) String nodeId,
      @RequestBody T data) {
    return treeService.addNodeAndGetId(parentId, nodeId, data);
  }

  @DeleteMapping("/{parentId}/children/{nodeId}")
  public Tree<T> deleteNode(@PathVariable String parentId, @PathVariable String nodeId) {
    return treeService.deleteNode(parentId, nodeId);
  }

  // moves a node from a parent to another
  @PutMapping("/{fromParentId}/children/{nodeId}")
  public Tree<T> moveNode(
      @PathVariable String fromParentId,
      @PathVariable String nodeId,
      @RequestParam String toParentId) {
    return treeService.moveNode(nodeId, fromParentId, toParentId);
  }
}
