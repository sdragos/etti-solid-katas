# SOLID Principles in Java — 4-Hour Workshop

A 4-hour, hands-on course on the SOLID object-oriented design principles, taught in Java,
with an AI coding assistant used as a pairing partner during the practical exercises.

## Who this is for

Participants are assumed to know Java syntax, but their comfort with classes, interfaces,
and polymorphism is uncertain. The course opens with a short OOP refresher before going
into SOLID itself, but the principle coverage and exercises go beyond a bare-bones
introduction, since that's what was requested.

## Repo structure

```
README.md                  - this file
instructor-guide.md        - timing, facilitation notes, fallback explanations
setup/
  ai-assistant-setup.md    - how to connect Continue.dev to the self-hosted vLLM model
slides/
  solid-slides.md          - Marp theory deck (export to PDF/PPTX/HTML)
exercises/
  HOW-TO-RUN.md            - how to compile/run the Java exercises (no build tool needed)
  docs/                    - one instruction file per kata (task + AI prompts to try)
  src/solid/<principle>/starter/   - smelly code participants refactor
  src/solid/<principle>/solution/  - reference refactor (answer key)
```

The five principles each get a `starter` (the violation) and `solution` (reference fix)
package: `srp`, `ocp`, `lsp`, `isp`, `dip`.

## Prerequisites

- JDK 17+ installed (any IDE: VS Code or IntelliJ both work)
- Network access to the university vLLM server (see `setup/ai-assistant-setup.md`)
- The "Continue" extension installed in your IDE

## Agenda (4 hours / 240 minutes)

| Time          | Duration | Block                                                              |
|---------------|----------|---------------------------------------------------------------------|
| 00:00 – 00:10 | 10 min   | Welcome, agenda, AI assistant connectivity check                   |
| 00:10 – 00:45 | 35 min   | OOP refresher: classes, interfaces, coupling & cohesion             |
| 00:45 – 02:00 | 75 min   | SOLID theory deep dive (5 principles, ~15 min each, slides + demos) |
| 02:00 – 02:10 | 10 min   | Break                                                                |
| 02:10 – 03:40 | 90 min   | Hands-on refactoring kata (5 exercises, AI-assisted)                 |
| 03:40 – 04:00 | 20 min   | Review solutions together, Q&A, wrap-up                              |

See `instructor-guide.md` for minute-by-minute facilitation notes.

## Quick start

1. Open this folder in your IDE.
2. Follow `setup/ai-assistant-setup.md` to connect Continue to the self-hosted model.
3. Go through `slides/solid-slides.md` for the theory part.
4. Work through `exercises/docs/01-srp.md` … `05-dip.md` in order during the practical part.
