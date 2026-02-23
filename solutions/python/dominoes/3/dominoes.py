"""
A module to check if a list of dominoes can form a valid chain.

This module provides functionality for determining whether a set of domino pairs
can be arranged in a continuous chain such that the end of one pair matches
the beginning of the next. It uses a graph-based approach to represent the
domino connections and perform the chaining.

Classes:
    Node: Represents a node in the graph with a value and connections.

Functions:
    can_chain: Determines whether the provided dominoes can form a valid chain.

Private functions:
    _build_chain: Recursively builds a chain starting from a given node.
    _build_graph: Constructs a graph representation of the dominoes.
    _get_node: Retrieves or creates a Node with a specific value.
"""

from dataclasses import dataclass, field


@dataclass
class Node:
    value: int
    children: list["Node"] = field(default_factory=list)

def can_chain(dominoes: list[tuple[int, int]]):
    """Determines whether the provided dominoes can form a valid chain."""
    if not dominoes:
        return []

    nodes = _build_graph(dominoes)
    res = []

    while nodes:
        index = next((idx + 1 for idx, tpl in enumerate(res) if tpl[1] == nodes[0].value), -1 if res else 0)
        if index == -1:
            return None

        res = res[:index] + _build_chain(nodes[0]) + res[index:]
        nodes = [element for element in nodes if element.children]

    if res[0][0] != res[-1][1]:
        return None

    return res

def _build_chain(current: Node) -> list[tuple[int, int]]:
    if not current.children:
        return []

    _next = current.children[0]
    current.children.remove(_next)
    _next.children.remove(current)
    return [(current.value, _next.value)] + _build_chain(_next)

def _build_graph(dominoes: list[tuple[int, int]]) -> list[Node]:
    res = []
    for domino in dominoes:
        left, right = _get_node(res, domino[0]), _get_node(res, domino[1])
        left.children.append(right)
        right.children.append(left)

    return res

def _get_node(nodes: list[Node], value: int) -> Node:
    value_nodes = [element for element in nodes if element.value == value]
    if value_nodes:
        return value_nodes[0]

    nodes.append(Node(value))
    return nodes[-1]
