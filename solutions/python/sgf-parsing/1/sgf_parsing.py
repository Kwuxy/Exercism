import re


class SgfTree:
    def __init__(self, properties=None, children=None):
        self.properties = properties or {}
        self.children = children or []

    def __eq__(self, other):
        if not isinstance(other, SgfTree):
            return False
        for key, value in self.properties.items():
            if key not in other.properties:
                return False
            if other.properties[key] != value:
                return False
        for key in other.properties.keys():
            if key not in self.properties:
                return False
        if len(self.children) != len(other.children):
            return False
        for child, other_child in zip(self.children, other.children):
            if child != other_child:
                return False
        return True

    def __ne__(self, other):
        return not self == other

    def __repr__(self):
        return f"SgfTree({self.properties}, {self.children})"

RE_GROUP_PATTERN = r'\[((?:\\.|[^\]])*)\]'
RE_PROPERTY_PATTERN = f'([a-zA-Z]+)(({RE_GROUP_PATTERN})*)'
RE_NODE_PATTERN = f'(;({RE_PROPERTY_PATTERN})*)'

def parse(input_string):
    if len(input_string) < 2 or input_string[0] != '(' or input_string[-1] != ')':
        raise ValueError("tree missing")

    input_string = input_string[1:-1]
    input_string = input_string.replace('\t', ' ')

    if len(input_string) == 0:
        raise ValueError("tree with no nodes")

    nodes = []

    node_pattern = re.compile(RE_NODE_PATTERN)
    for node_match in node_pattern.findall(input_string):
        node = parse_node(node_match[0])
        nodes.append(node)

    root = nodes[0]
    root.children = nodes[1:]
    return root

def parse_node(node_string):
    tree = SgfTree()

    property_pattern = re.compile(RE_PROPERTY_PATTERN)
    for property_match in property_pattern.findall(node_string):
        key, values = parse_property(property_match[0], property_match[1])
        tree.properties[key] = values

    return tree

def parse_property(key, groups):
    if groups == '':
        raise ValueError("properties without delimiter")

    if key != key.upper():
        raise ValueError("property must be in uppercase")

    group_pattern = re.compile(RE_GROUP_PATTERN)
    values = group_pattern.findall(groups)
    values = list(map(clean_property_value, values))

    return key, values

def clean_property_value(value: str) -> str:
    res, i = '', 0
    while i < len(value):
        if value[i:i + 1] == '\\\\':
            res += value[i]
            i += 2
        elif value[i:i + 2] == '\\\n':
            i += 2
            continue
        elif value[i] == '\\':
            i += 1

        res += value[i]
        i += 1

    return res
