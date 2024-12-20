package com.nespolino.qtech.exam.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nespolino.qtech.exam.exception.DuplicateIdException;
import com.nespolino.qtech.exam.map.InMemoryMapTreeRepository;
import com.nespolino.qtech.exam.map.InMemoryMapTreeService;
import com.nespolino.qtech.exam.treedata.Tree;
import com.nespolino.qtech.exam.treedata.TreeOperations;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryMapTreeServiceTest {

  InMemoryMapTreeService inMemoryMapTreeService;

  @BeforeEach
  public void setUp() {
    inMemoryMapTreeService =
        new InMemoryMapTreeService(new InMemoryMapTreeRepository(), new TreeOperations<>());
  }

  @Test
  void testAddNodeAndGetId() {
    String nodeId =
        inMemoryMapTreeService.addNodeAndGetId("ROOT", "CHILD_1", Collections.emptyMap());
    assertThat(nodeId).isEqualTo("CHILD_1");
  }

  @Test
  void testAddNodeAndGetIdWhenParentDoesNotExist() {
    assertThatThrownBy(
            () ->
                inMemoryMapTreeService.addNodeAndGetId(
                    "NON_EXISTENT_PARENT", "CHILD_1", Collections.emptyMap()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testAddNodeAndGetIdWhenNodeAlreadyExists() {
    inMemoryMapTreeService.addNodeAndGetId("ROOT", "CHILD_1", Collections.emptyMap());
    assertThatThrownBy(
            () -> inMemoryMapTreeService.addNodeAndGetId("ROOT", "CHILD_1", Collections.emptyMap()))
        .isInstanceOf(DuplicateIdException.class);
  }

  @Test
  void testDeleteNode() {
    inMemoryMapTreeService.addNodeAndGetId("ROOT", "CHILD_1", Collections.emptyMap());
    Tree<Map<String, Object>> tree = inMemoryMapTreeService.deleteNode("ROOT", "CHILD_1");
    assertTrue(tree.getChildren().isEmpty());
  }

  @Test
  void testDeleteNodeWhenNodeDoesNotExist() {
    assertThatThrownBy(() -> inMemoryMapTreeService.deleteNode("ROOT", "CHILD_1"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testMoveNode() {
    inMemoryMapTreeService.addNodeAndGetId("ROOT", "CHILD_1", Collections.emptyMap());
    inMemoryMapTreeService.addNodeAndGetId("ROOT", "CHILD_2", Collections.emptyMap());
    inMemoryMapTreeService.moveNode("CHILD_1", "ROOT", "CHILD_2");
    Tree<Map<String, Object>> tree = inMemoryMapTreeService.getTreeData();
    assertThat(tree.getChildren().getFirst().getNodeId()).isEqualTo("CHILD_2");
    assertThat(tree.getChildren().getFirst().getChildren().getFirst().getNodeId()).isEqualTo("CHILD_1");
  }

  @Test
  void testGetDescendatsData() {
    inMemoryMapTreeService.addNodeAndGetId("ROOT", "B", Map.of("name", "B"));
    inMemoryMapTreeService.addNodeAndGetId("ROOT", "C", Map.of("name", "C"));
    inMemoryMapTreeService.addNodeAndGetId("ROOT", "D", Map.of("name", "D"));

    inMemoryMapTreeService.addNodeAndGetId("B", "E", Map.of("name", "E"));
    inMemoryMapTreeService.addNodeAndGetId("B", "H", Map.of("name", "H"));
    inMemoryMapTreeService.addNodeAndGetId("B", "F", Map.of("name", "F"));
    inMemoryMapTreeService.addNodeAndGetId("B", "G", Map.of("name", "G"));
    inMemoryMapTreeService.addNodeAndGetId("D", "I", Map.of("name", "I"));
    inMemoryMapTreeService.addNodeAndGetId("D", "L", Map.of("name", "L"));
    inMemoryMapTreeService.addNodeAndGetId("D", "M", Map.of("name", "M"));
    inMemoryMapTreeService.addNodeAndGetId("I", "J", Map.of("name", "J"));
    inMemoryMapTreeService.addNodeAndGetId("M", "N", Map.of("name", "N"));
    inMemoryMapTreeService.addNodeAndGetId("J", "K", Map.of("name", "K"));

    Set<String> rootDescendants = asSetOfString("ROOT");

    Set<String> expected =
        IntStream.rangeClosed('B', 'N')
            .mapToObj(i -> String.valueOf((char) i))
            .collect(Collectors.toSet());

    assertEquals(expected, rootDescendants);

    Set<String> descendantsOfD = asSetOfString("D");

    Set<String> expectedFromD =
        IntStream.rangeClosed('I', 'N')
            .mapToObj(i -> String.valueOf((char) i))
            .collect(Collectors.toSet());

    assertEquals(expectedFromD, descendantsOfD);

    Set<String> descendantsOfC = asSetOfString("C");

    Set<String> expectedFromC = Collections.emptySet();

    assertEquals(expectedFromC, descendantsOfC);
  }

  private Set<String> asSetOfString(String nodeId) {
    return inMemoryMapTreeService.getDescendantsData(nodeId).stream()
        .map(Tree::getNodeId)
        .collect(Collectors.toSet());
  }
}
