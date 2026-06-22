# 02 — Open/Closed Principle

**Code**: `src/solid/ocp/starter/`
**Reference solution**: `src/solid/ocp/solution/`

## The smell

`AreaCalculator.totalArea` branches on a `type` string. Every new shape
means editing this method and re-testing everything that already worked.

## Your task

Refactor so that `AreaCalculator` never needs to change again when a new
shape is added. Then prove it: add a `Triangle` shape.

**Acceptance criteria**:
- Adding `Triangle` requires creating a new class only — zero changes to
  `AreaCalculator`.
- `Main` computes the total area including the new `Triangle`.

## AI prompts to try

- "Why does this design violate the Open/Closed Principle?"
- "Refactor this so new shapes can be added without modifying
  AreaCalculator."
- "Here's my Triangle class — does adding it require any change elsewhere?"

## Stretch goal

Add a shape with a different constructor shape (e.g. a `RegularPolygon`
taking side count + side length) and confirm it still requires no
calculator changes.
