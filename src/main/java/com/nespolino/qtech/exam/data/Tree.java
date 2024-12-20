package com.nespolino.qtech.exam.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Tree<T> {
  private final String nodeId;
  private final T data;
  private final List<Tree<T>> children;
}
