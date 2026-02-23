from dataclasses import dataclass, field


@dataclass
class Node:
    value: int
    children: list["Node"] = field(default_factory=list)

def can_chain(dominoes: list[tuple[int, int]]):
    if not dominoes:
        return []

    nodes = _build_graph(dominoes)
    res = []

    while nodes:
        index = next((i + 1 for i, tpl in enumerate(res) if tpl[1] == nodes[0].value), -1 if res else 0)
        if index == -1:
            return None

        res = res[:index] + _build_chain(nodes[0]) + res[index:]
        nodes = list(filter(lambda el: el.children, nodes))

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
    n = list(filter(lambda el: el.value == value, nodes))
    if n:
        return n[0]

    nodes.append(Node(value))
    return nodes[-1]
