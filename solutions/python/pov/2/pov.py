from json import dumps
from typing import List


class Tree:
    def __init__(self, label: str, children: List["Tree"]=None):
        self.label = label
        self.children = children if children is not None else []

    def from_pov(self, from_node: str) -> "Tree":
        path = self._get_path_to(from_node)
        if not path:
            raise ValueError("Tree could not be reoriented")

        for parent, child in zip(path[:-1], path[1:]):
            parent.children.remove(child)
            child.children.append(parent)

        return path[-1]

    def path_to(self, from_node: str, to_node: str) -> List[str]:
        root = self.from_pov(from_node)
        path = root._get_path_to(to_node)
        if not path:
            raise ValueError("No path found")

        return [node.label for node in path]

    def _get_path_to(self, label: str) -> List["Tree"]:
        if self.label == label:
            return [self]

        for child in self.children:
            result = child._get_path_to(label)
            if result:
                return [self, *result]

        return []

    def __dict__(self):
        return {self.label: [c.__dict__() for c in sorted(self.children)]}

    def __str__(self, indent=None):
        return dumps(self.__dict__(), indent=indent)

    def __lt__(self, other):
        return self.label < other.label

    def __eq__(self, other):
        return self.__dict__() == other.__dict__()
