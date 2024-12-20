package com.nespolino.qtech.exam.string;

import com.nespolino.qtech.exam.controller.BaseTreeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("string-tree")
public class StringTreeController extends BaseTreeController<String> {

  public StringTreeController(InMemoryStringTreeService treeService) {
    super(treeService);
  }
}
