# 01 — Single Responsibility Principle

**Code**: `src/solid/srp/starter/`
**Reference solution**: `src/solid/srp/solution/` (don't peek yet!)

## The smell

`InvoiceProcessor` calculates totals, formats text, writes files, and sends
emails — four unrelated reasons for this class to change.

## Your task

Refactor `InvoiceProcessor` into separate classes, each with a single
responsibility. Keep `Main` producing the same visible output (console
text, file written under `output/`, "emailing" message printed).

**Acceptance criteria**:
- No single class both calculates a total and performs file or "email" I/O.
- Changing the print format should require touching only one class.
- Changing how invoices are persisted (e.g. swapping file storage for
  something else) should require touching only one class.

## AI prompts to try

- "List the distinct responsibilities in this class and explain why each
  one is a separate reason to change."
- "Refactor this class so each responsibility lives in its own class,
  following the Single Responsibility Principle."
- "I split this into N classes — did I miss a responsibility, or split
  something that didn't need splitting?"

## Stretch goal

Add a second persistence option (e.g. an in-memory list instead of a file)
without touching the calculation or formatting classes at all.
