from typing import Optional


class Node:
    def __init__(self, value: int, parent: Optional["Node"], left: Optional[dict]=None, right: Optional[dict]=None):
        self.parent = parent
        self.value = value
        self.left = Node.from_dict(left, self) if left is not None else None
        self.right = Node.from_dict(right, self) if right is not None else None

    @staticmethod
    def from_dict(node_dict: dict, parent: Optional["Node"]=None):
        if node_dict is None:
            return None

        return Node(node_dict["value"], parent, node_dict.get("left"), node_dict.get("right"))

    def to_dict(self):
        return {
            "value": self.value,
            "left": self.left.to_dict() if self.left is not None else None,
            "right": self.right.to_dict() if self.right is not None else None
        }

    def __repr__(self):
        return f"<Node value={self.value}, left={self.left}, right={self.right}>"


class Zipper:
    def __init__(self, root: Optional[Node]=None):
        self.root = root
        self.current = root

    @staticmethod
    def from_tree(tree):
        return Zipper(root=Node.from_dict(tree))

    def value(self):
        return self.current.value

    def set_value(self, value):
        if self.current is None:
            return None

        self.current.value = value
        return self

    def left(self):
        if self.current.left is None:
            return None

        self.current = self.current.left
        return self

    def set_left(self, child: dict=None):
        if self.current is None:
            return None

        self.current.left = Node.from_dict(child, self.current)
        return self

    def right(self):
        if self.current.right is None:
            return None

        self.current = self.current.right
        return self

    def set_right(self, child: dict=None):
        if self.current is None:
            return None

        self.current.right = Node.from_dict(child, self.current)
        return self

    def up(self):
        if self.current.parent is None:
            return None

        self.current = self.current.parent
        return self

    def to_tree(self):
        return self.root.to_dict()
