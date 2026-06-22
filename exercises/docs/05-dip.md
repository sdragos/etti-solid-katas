# 05 — Dependency Inversion Principle

**Code**: `src/solid/dip/starter/`
**Reference solution**: `src/solid/dip/solution/`

## The smell

`OrderService` creates its own `MySqlOrderRepository` with `new` inside the
class. You can't test `OrderService` without a real MySQL database, and you
can't swap storage technology without editing `OrderService`.

## Your task

Introduce an abstraction and inject the dependency through the constructor,
so `OrderService` depends on the abstraction, not the concrete class.

**Acceptance criteria**:
- `OrderService` contains no `new MySqlOrderRepository()` (or any concrete
  repository) anywhere in its body.
- You can construct `OrderService` with a different `OrderRepository`
  implementation (e.g. an in-memory one for tests) with no change to
  `OrderService`'s code.

## AI prompts to try

- "Why is this a Dependency Inversion Principle violation, not just a
  testability nitpick?"
- "Refactor this to inject the dependency via the constructor, depending on
  an interface."
- "This is the same pattern frameworks like Spring automate — explain how
  constructor injection here relates to what a DI framework does."

## Stretch goal

Add a third `OrderRepository` implementation (e.g. one that writes to a
file) and switch `Main` to use it by changing only one line.
