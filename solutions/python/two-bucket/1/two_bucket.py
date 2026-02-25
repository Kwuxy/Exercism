from dataclasses import dataclass
from queue import Queue
from typing import Generator


@dataclass(frozen=True)
class State:
    one: int
    two: int


def measure(bucket_one: int, bucket_two: int, goal: int, start_bucket: str) -> tuple[int, str, int]:
    if start_bucket == 'two':
        bucket_one, bucket_two = bucket_two, bucket_one

    initial_state = State(bucket_one, 0)
    visited = set()
    states = Queue()
    states.put((initial_state, 1))

    while not states.empty():
        state, actions = states.get()
        if state in visited:
            continue

        if state == State(0, bucket_two):
            continue

        if state.one == goal or state.two == goal:
            if start_bucket == 'two':
                state = State(state.two, state.one)
            return actions, "one" if state.one == goal else "two", state.two if state.one == goal else state.one

        visited.add(state)
        for neighbor in neighbors(state, bucket_one, bucket_two):
            states.put((neighbor, actions + 1))

    raise ValueError('No solution found')


def neighbors(state: State, bucket_one: int, bucket_two: int) -> Generator[State, None, None]:
    yield from fill_buckets(state, bucket_one, bucket_two)
    yield from empty_buckets(state)
    yield from pour_buckets(state, bucket_one, bucket_two)

def fill_buckets(state: State, bucket_one: int, bucket_two: int) -> Generator[State, None, None]:
    if state.one != bucket_one:
        yield State(bucket_one, state.two)

    if state.two != bucket_two:
        yield State(state.one, bucket_two)

def empty_buckets(state: State) -> Generator[State, None, None]:
    if state.one != 0:
        yield State(0, state.two)

    if state.two != 0:
        yield State(state.one, 0)

def pour_buckets(state: State, bucket_one: int, bucket_two: int) -> Generator[State, None, None]:
    if state.one != 0 and state.two != bucket_two:
        new_bucket_two = min(bucket_two, state.one + state.two)
        new_bucket_one = state.one - (new_bucket_two - state.two)
        yield State(new_bucket_one, new_bucket_two)

    if state.two != 0 and state.one != bucket_one:
        new_bucket_one = min(bucket_one, state.one + state.two)
        new_bucket_two = state.two - (new_bucket_one - state.one)
        yield State(new_bucket_one, new_bucket_two)
