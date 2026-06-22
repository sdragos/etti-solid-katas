# Instructor Guide

Facilitation notes for the 4-hour SOLID workshop. Pairs with `slides/solid-slides.md`
and the `exercises/` kata. Audience background is uncertain — they may be shaky on
classes/interfaces — but they explicitly asked for more than a bare-bones intro, so the
plan below keeps the refresher short and spends the saved time on depth, not repetition.

## Before the session

- Confirm the vLLM server is up and reachable from the classroom network/VPN (see
  `setup/ai-assistant-setup.md`). Do this the day before, not 10 minutes before — network
  ACLs/VPN access are the most common failure point. Re-check connectivity again the
  morning of the session.
- Do a quick concurrency smoke test: a few people chatting with the model at once, to
  make sure vLLM's batching keeps latency reasonable for the room size.
- Pre-share the Continue `config.yaml` snippet (with the real host/IP and API key filled
  in) so people aren't hand-typing it during the 10-minute setup window.
- Have a fallback web-chat assistant in mind in case the self-hosted endpoint goes down
  mid-session — don't let one broken connection block someone's whole exercise block.

## 00:00–00:10 — Welcome + AI assistant check

Get everyone's Continue panel talking to the model before anything else. This is the
highest-risk dependency in the whole session — better to discover a broken setup now
than during the practical block at 02:10.

## 00:10–00:45 — OOP refresher (35 min)

Goal: get everyone to the same baseline vocabulary, fast. Don't teach Java syntax from
scratch — assume they can read a class. Focus on:
- classes/objects (1 slide, move on quickly if the room is comfortable)
- interfaces and polymorphism — spend real time here, this is the mechanism every SOLID
  principle leans on
- coupling & cohesion — this is the vocabulary they're missing most often; give it room

**Fallback framing if the room is lost**: "a class is a blueprint, an interface is a
socket shape — anything built to fit the socket plugs in, the wall doesn't care which
brand." Concrete analogies land better than definitions here.

**Check before moving on**: ask someone to explain, in their own words, why code that
depends on an interface instead of a concrete class is easier to change. If nobody can
answer, slow down — the rest of the session assumes this clicked.

## 00:45–02:00 — SOLID theory (75 min, ~15 min/principle)

Per principle: definition (2 min) → bad example (4 min, let them spot the smell before
you explain it) → refactor (5 min) → AI prompt demo (3 min, live in the IDE if time
allows) → questions (1 min buffer).

Common misconceptions to head off:
- **SRP** ≠ "one method per class." It's one *reason to change* — a class can have many
  small methods and still be cohesive.
- **OCP** doesn't mean "never edit a file again." It means new *cases* of existing
  behavior shouldn't require editing existing, working code.
- **LSP** violations are about behavior, not just type-checking. "It compiles" is not
  the same as "it's substitutable."
- **ISP** "small interfaces" can be taken too far — splitting `Workable` into a separate
  interface per method would be over-engineering. The split should track real
  capability boundaries (eat vs. sleep vs. work), not be maximally granular.
- **DIP** is not "use interfaces everywhere." It's specifically about high-level policy
  not depending on low-level detail. A `Rectangle implements Shape` extension (OCP) is
  not automatically a DIP example.

## 02:00–02:10 — Break

## 02:10–03:40 — Hands-on refactoring kata (90 min, ~18 min/exercise)

Point everyone at `exercises/HOW-TO-RUN.md` and `exercises/docs/01-srp.md` to start.
Exercises are ordered to match the theory block (SRP → OCP → LSP → ISP → DIP); faster
participants can work ahead, slower ones don't need to finish all five to get value.

Facilitation tips:
- Walk the room in the first 5 minutes specifically checking AI connectivity — this is
  where a broken setup actually bites.
- Encourage pairing if the room skews less experienced; the AI assistant is a third
  voice in the conversation, not a replacement for the other two.
- Push back on anyone pasting the AI's suggestion in unread. Ask "why is this the right
  fix" — if they can't answer, they haven't done the exercise yet, the AI has.
- The `solution/` packages are reference answers, not the *only* correct refactor. If a
  participant's structure differs but satisfies the acceptance criteria in the doc,
  that's a pass.

If someone finishes early, point them at the "Stretch goal" section of that exercise's
doc rather than letting them idle.

## 03:40–04:00 — Review + wrap-up (20 min)

Walk through one exercise together (LSP or DIP tend to generate the best discussion —
LSP because the "it compiles but it's wrong" gap is vivid, DIP because it connects
directly to testing and to frameworks like Spring). Close with the recap slide: SOLID as
a question ("what changes here, and where do I pay for that change?"), not a checklist.
