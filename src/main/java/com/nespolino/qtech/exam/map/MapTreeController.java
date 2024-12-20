package com.nespolino.qtech.exam.map;

import com.nespolino.qtech.exam.controller.BaseTreeController;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("map-tree")
public class MapTreeController extends BaseTreeController<Map<String, Object>> {

  public MapTreeController(InMemoryMapTreeService treeService) {
    super(treeService);
  }
}
