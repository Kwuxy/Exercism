class Reactor:
    def __init__(self):
        self._dirty = set()
        self._resolved = set()

    def mark_dirty(self, cell):
        self._dirty.add(cell)

    def _mark_resolved(self, cell):
        self._resolved.add(cell)

    def resolve(self):
        while len(self._dirty) - len(self._resolved) > 0:
            progressed = False
            for cell in list(self._dirty):
                if cell in self._resolved:
                    continue

                dirty_inputs = list(filter(lambda x: x in self._dirty and x not in self._resolved, cell.inputs))
                if len(dirty_inputs) > 0:
                    continue

                changed = cell.update_value()
                if changed:
                    cell.alert_listeners()
                    cell.notify_update()

                self._mark_resolved(cell)
                progressed = True

            if not progressed:
                raise Exception('Circular dependency detected')

        self._resolved.clear()
        self._dirty.clear()


class NumericCell:
    def __init__(self, initial_value):
        self._value = initial_value
        self._listeners = []

    def register(self, callback):
        self._listeners.append(callback)

    def alert_listeners(self):
        for callback in self._listeners:
            callback()

    @property
    def value(self):
        return self._value

    def _set_value(self, new_value):
        self._value = new_value

    @staticmethod
    def _get_value(other):
        if isinstance(other, NumericCell):
            return other._value

        if type(other) == int:
            return other

        raise TypeError('Can only add two numeric cells')

    def __add__(self, other):
        return self._value + self._get_value(other)

    def __radd__(self, other):
        return self._value + other

    def __sub__(self, other):
        return self._value - self._get_value(other)

    def __mul__(self, other):
        return self._value * self._get_value(other)

    def __lt__(self, other):
        return self._value < self._get_value(other)

    def __gt__(self, other):
        return self._value > self._get_value(other)


class InputCell(NumericCell):
    def __init__(self, initial_value: int) -> None:
        super().__init__(initial_value)

    @property
    def value(self):
        return self._value

    @value.setter
    def value(self, new_value):
        self._set_value(new_value)
        self.alert_listeners()
        reactor.resolve()


class ComputeCell(NumericCell):
    def __init__(self, inputs, compute_function):
        self._inputs = inputs
        self._compute_function = compute_function
        self._callbacks = []

        for el in self._inputs:
            el.register(self.input_changed)

        super().__init__(compute_function(inputs))
        self._latest_stable_value = self._value

    @property
    def inputs(self):
        return self._inputs

    def input_changed(self):
        reactor.mark_dirty(self)

    def update_value(self):
        old_value = self._value
        self._set_value(self._compute_function(self._inputs))
        return self._value != old_value

    def notify_update(self):
        if self._value == self._latest_stable_value:
            return

        self._latest_stable_value = self._value

        for callback in self._callbacks:
            callback(self._value)

    def add_callback(self, callback):
        if callback not in self._callbacks:
            self._callbacks.append(callback)

    def remove_callback(self, callback):
        if callback in self._callbacks:
            self._callbacks.remove(callback)


reactor = Reactor()
