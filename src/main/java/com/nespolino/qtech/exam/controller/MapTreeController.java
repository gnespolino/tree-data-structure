package com.nespolino.qtech.exam.controller;

import com.nespolino.qtech.exam.service.TreeService;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapTreeController extends BaseTreeController<Map<String, Object>> {

  public MapTreeController(TreeService<Map<String, Object>> treeService) {
    super(treeService);
  }
}
