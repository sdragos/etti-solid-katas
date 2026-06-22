# 03 — Liskov Substitution Principle

**Code**: `src/solid/lsp/starter/`
**Reference solution**: `src/solid/lsp/solution/`

## The smell

`Penguin extends Bird` but overrides `fly()` to throw. Any code written
against `Bird` (like `BirdWatcher.makeAllFly`) can crash on a perfectly
valid `Bird` subtype.

## Your task

Redesign the hierarchy so substituting any `Bird` subtype is always safe —
without deleting the idea that penguins are birds, and without just
wrapping the call in a try/catch.

**Acceptance criteria**:
- `BirdWatcher` (or its replacement) never throws when given any `Bird`,
  for any method it's allowed to call on a `Bird`.
- Flying-specific behavior is only ever invoked on things that can actually
  fly — enforced by the type system, not by a runtime check.

## AI prompts to try

- "Does this overridden method violate the Liskov Substitution Principle?
  Why?"
- "Redesign this hierarchy so a Penguin can never be asked to fly."
- "I added a Flyable interface — review whether this actually fixes the
  substitutability problem or just hides it."

## Stretch goal

Add an `Ostrich` (can't fly, but runs fast) and a `Duck` (flies and swims)
without breaking either `move()` or `fly()` for any existing class.
