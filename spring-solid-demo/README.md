# spring-solid-demo

A minimal 3-tier Spring Boot app (no database — in-memory storage only) used as a live,
runnable demo of SOLID alongside the static refactoring katas in `../exercises/`.

New to Java/Maven/Spring? Read `EXPLAINED.md` first — it walks through what Maven and
Spring actually are, what every file here does, and traces one HTTP request through the
whole app, assuming no prior Java background.

## Run it

```bash
./mvnw spring-boot:run
```
(`mvnw.cmd` on Windows if not using Git Bash/WSL). No global Maven install needed — the
wrapper downloads Maven itself on first run. The app listens on `http://localhost:8080`.

## Try the API

```bash
curl -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" \
  -d '{"title":"Write slides","priority":"HIGH"}'

curl http://localhost:8080/api/tasks

curl -X POST http://localhost:8080/api/tasks/1/complete

curl -X POST http://localhost:8080/api/tasks/1/complete   # 409 - already completed

curl -X DELETE http://localhost:8080/api/tasks/1
```

## Run the tests

```bash
./mvnw test
```

16 tests: business-logic unit tests (mocked repository/listeners, no Spring context),
repository unit tests (plain Java, no Spring context), and `@WebMvcTest` controller
tests (mocked service, no business logic or storage). That layering is itself the SOLID
payoff — see below.

## The 3 tiers, and where each SOLID principle shows up

```
web/            (presentation)  -> HTTP only, no business rules
  TaskController, GlobalExceptionHandler, RequestLoggingFilter, dto/

service/        (business)      -> validation, domain rules, no HTTP, no storage details
  TaskService (interface) + TaskServiceImpl
  TaskCompletionListener (interface) + Logging/CompletionStats implementations

repository/     (data)          -> storage only, currently a ConcurrentHashMap
  TaskRepository (interface) + InMemoryTaskRepository

domain/         -> Task, Priority — the model shared across tiers
```

- **SRP**: each class above does exactly one of "talk HTTP", "enforce business rules",
  or "store data". None of them know about the others' internals.
- **OCP**: `TaskServiceImpl` depends on `List<TaskCompletionListener>`. Add a new
  `@Component implements TaskCompletionListener` (e.g. a notification sender) and Spring
  wires it in automatically — zero changes to `TaskServiceImpl`.
- **ISP**: `TaskRepository` and `TaskCompletionListener` each expose only the
  method(s) their callers actually need.
- **DIP**: every dependency between tiers is on an interface
  (`TaskController` → `TaskService`, `TaskServiceImpl` → `TaskRepository`), wired by
  Spring's constructor injection. This is the exact pattern from the DIP kata
  (`../exercises/docs/05-dip.md`) — Spring is just automating it.
- **LSP** isn't exercised here on purpose — there's no class hierarchy to substitute.
  That's the kata's job (`../exercises/docs/03-lsp.md`); this project is about the other
  four principles at an application-architecture scale.

The tests demonstrate the testability payoff directly: `TaskServiceImplTest` proves you
can verify all business rules with zero Spring context and zero real storage, purely
because `TaskServiceImpl` depends on abstractions instead of concrete classes.
