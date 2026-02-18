def tree_from_traversals(preorder: list, inorder: list) -> dict:
    if len(preorder) != len(inorder):
        raise ValueError("traversals must have the same length")

    if set(preorder) != set(inorder):
        raise ValueError("traversals must have the same elements")

    if len(set(preorder)) != len(preorder):
        raise ValueError("traversals must contain unique items")

    return build_tree(preorder, inorder)

def build_tree(preorder: list, inorder: list) -> dict:
    if not inorder:
        return {}

    root = preorder.pop(0)
    root_index = inorder.index(root)
    left, right = inorder[:root_index], inorder[root_index + 1:]

    return {"v": root, 'l': build_tree(preorder, left), 'r': build_tree(preorder, right)}
