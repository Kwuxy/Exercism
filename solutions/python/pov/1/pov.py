from json import dumps
from typing import List, Optional


class Tree:
    def __init__(self, label: str, children: List["Tree"]=None):
        self.parent: Optional["Tree"] = None
        self.label = label
        self.children = children if children is not None else []
        for child in self.children:
            child.parent = self

    def from_pov(self, from_node: str) -> Optional["Tree"]:
        root = self._get_node(from_node)
        if root is None:
            raise ValueError("Tree could not be reoriented")

        return root._set_parent_as_child()

    def _get_node(self, label) -> Optional["Tree"]:
        if self.label == label:
            return self

        for child in self.children:
            result = child._get_node(label)
            if result is not None:
                return result

        return None

    def _set_parent_as_child(self):
        if self.parent is None:
            return self

        self.parent.children.remove(self)
        self.children.append(self.parent)

        self.parent._set_parent_as_child()
        self.parent.parent = self
        self.parent = None
        return self

    def path_to(self, from_node: str, to_node: str) -> List[str]:
        def _go_to(node: "Tree") -> List[str]:
            if node.label == to_node:
                return [node.label]

            for child in node.children:
                path = _go_to(child)
                if path:
                    return [node.label, *path]

            return []

        root = self.from_pov(from_node)
        if root is None:
            raise ValueError("No path found")

        result = _go_to(root)
        if not result:
            raise ValueError("No path found")

        return result

    def __dict__(self):
        return {self.label: [c.__dict__() for c in sorted(self.children)]}

    def __str__(self, indent=None):
        return dumps(self.__dict__(), indent=indent)

    def __lt__(self, other):
        return self.label < other.label

    def __eq__(self, other):
        return self.__dict__() == other.__dict__()
