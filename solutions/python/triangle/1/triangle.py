def equilateral(sides):
    if not isTriangle(sides):
        return False

    return sides[0] == sides[1] and sides[1] == sides[2]


def isosceles(sides):
    if not isTriangle(sides):
        return False

    return sides[0] == sides[1] or sides[1] == sides[2] or sides[0] == sides[2]


def scalene(sides):
    return isTriangle(sides) and not isosceles(sides)


def isTriangle(sides):
    if len(sides) != 3:
        return False

    if 0 in sides:
        return False

    integrity_checks = [
        (sides[0] + sides[1], sides[2]),
        (sides[0] + sides[2], sides[1]),
        (sides[1] + sides[2], sides[0]),
    ]

    for (sum, third_side) in integrity_checks:
        if sum < third_side:
            return False

    return True