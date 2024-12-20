package com.nespolino.qtech.exam.treedata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Tree<T> {
  private final String nodeId;
  private final T data;

  @JsonInclude(Include.NON_EMPTY)
  private final List<Tree<T>> children;
}
