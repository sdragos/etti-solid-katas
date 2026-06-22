# 04 — Interface Segregation Principle

**Code**: `src/solid/isp/starter/`
**Reference solution**: `src/solid/isp/solution/`

## The smell

`Worker` forces every implementer to provide `work()`, `eat()`, and
`sleep()`. `RobotWorker` doesn't eat or sleep, so it fakes those methods by
throwing.

## Your task

Split `Worker` into smaller interfaces so a class only implements the
capabilities it actually has.

**Acceptance criteria**:
- `RobotWorker` no longer has an `eat()` or `sleep()` method at all (not
  even one that throws).
- Code that only needs `work()` can depend on a `Workable`-shaped interface
  without knowing `RobotWorker` exists.

## AI prompts to try

- "Why does this interface violate the Interface Segregation Principle?"
- "Split this interface so implementers only declare the capabilities they
  actually have."
- "Is eat()/sleep() naturally one capability or two separate ones? Justify
  your split."

## Stretch goal

Add a `VendingMachineWorker` that only works and never eats/sleeps, and a
`Manager` that works, eats, sleeps, *and* approves budgets — without
forcing any class into a method it doesn't need.
