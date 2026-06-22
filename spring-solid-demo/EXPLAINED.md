# What Is All This? (zero-Java-knowledge edition)

This file assumes you've never touched Java before. It explains every kind of file in
this folder, what Maven and Spring actually are, and how a single HTTP request travels
through the whole project. Read `README.md` first for *what to run*; read this for *why
it's structured this way and what each piece is called*.

## The 30-second version

- **Java** is a programming language. Your `.java` files get translated into a format
  the computer can run.
- **Maven** is the tool that downloads the external code libraries this project needs,
  and knows how to compile/test/run everything.
- **Spring** (specifically **Spring Boot**) is a library that does a lot of the
  repetitive setup for you — like starting a web server — so you can write business
  logic instead of plumbing.
- **`pom.xml`** is the file that tells Maven which libraries to download and how to
  build the project.
- There is **no database** — everything is stored in a plain Java `HashMap` in RAM. Stop
  the app, and all the data disappears. That's intentional, see `README.md`.

## Java in one paragraph

Java code is written in `.java` files. Before it can run, it gets *compiled* into
`.class` files (a format called "bytecode"), which run on something called the **JVM**
(Java Virtual Machine) — that's what `java -version` printed when you checked your
setup. You don't need to know more than this for this project: Maven handles the
compiling for you, you'll never run `javac` by hand here.

## What is Maven, really?

Imagine every piece of reusable code anyone has ever written for Java — a way to talk
to a database, parse JSON, build a web server — sitting in one giant public catalog
called **Maven Central**. Maven is the tool that:

1. Reads a list of "things this project needs" from `pom.xml`,
2. downloads those things (and *their* dependencies, and so on) from Maven Central into
   a local folder on your machine (`~/.m2/repository`),
3. compiles your `.java` files using all of that,
4. and can run your tests or start your application.

If you've used `npm` (JavaScript) or `pip` (Python) before: Maven is that, for Java.
`pom.xml` is this project's equivalent of `package.json` or `requirements.txt`.

### Why `mvnw` instead of `mvn`

Normally you'd install Maven once on your machine and run `mvn ...` commands. This
project ships its own copy of that tool instead, called the **Maven Wrapper**:

- `mvnw` (Mac/Linux) and `mvnw.cmd` (Windows) are small scripts.
- `.mvn/wrapper/maven-wrapper.properties` tells those scripts exactly which Maven
  version to use.
- The first time you run `./mvnw <anything>`, the script downloads that exact Maven
  version into a hidden folder and uses it from then on.

Net effect: nobody has to install Maven globally, and everyone on the team uses the
identical Maven version. That's why every command in `README.md` starts with `./mvnw`,
never `mvn`.

## Reading `pom.xml`

This is the actual file in this folder, piece by piece:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.1.0</version>
</parent>
```
Every dependency below could specify its own version number, and getting a combination
that's actually compatible by hand is painful. This `<parent>` is a curated list of
versions that are known to work together for this Spring Boot release. Because we
inherit from it, the dependencies below don't need version numbers at all — they
inherit one automatically.

```xml
<groupId>com.example</groupId>
<artifactId>spring-solid-demo</artifactId>
<version>0.0.1-SNAPSHOT</version>
```
This project's own "name tag": who made it (`groupId`, by convention a reversed domain
name), what it's called (`artifactId`), and its version. Nothing downloads this part —
it's how *other* projects would refer to this one if they depended on it.

```xml
<properties>
    <java.version>17</java.version>
</properties>
```
"Compile this code so it's compatible with Java 17." (Your machine has a newer Java
installed — that's fine, newer Java runtimes can run code compiled for older versions.)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```
The actual libraries this project uses:
- `spring-boot-starter-webmvc` is a bundle ("starter") that pulls in *everything* needed
  to build a web API: an embedded web server, JSON conversion, the annotations like
  `@RestController` you'll see in the code. One line, dozens of real libraries behind
  it.
- `spring-boot-starter-webmvc-test` brings in the testing tools (JUnit, Mockito, MockMvc
  — explained further down). `<scope>test</scope>` means: only available while running
  tests, not included when the app actually runs.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
This is what makes `./mvnw spring-boot:run` work, and also what would let you build one
single runnable `.jar` file containing the app *and* every dependency it needs, with
`./mvnw package`.

## What is Spring (and Spring Boot)?

**Spring** is a Java framework built around one core idea: instead of your code creating
and wiring together every object it needs by hand, you describe *what kind of thing you
need*, and a Spring-managed "container" builds and hands it to you. This is called
**dependency injection** — you've already seen the concept explained from the Java side
in `../exercises/docs/05-dip.md`; Spring is the real-world tool that automates exactly
that pattern.

**Spring Boot** is Spring with sane defaults baked in. Plain Spring requires a lot of
manual configuration (which web server? which settings?). Spring Boot looks at which
dependencies you added in `pom.xml` and auto-configures the rest — add
`spring-boot-starter-webmvc`, and you automatically get a working embedded web server on
startup, with no separate configuration file required.

### How Spring finds and connects your classes

Open `src/main/java/com/example/taskmanager/SpringSolidDemoApplication.java`:

```java
@SpringBootApplication
public class SpringSolidDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSolidDemoApplication.class, args);
    }
}
```

`@SpringBootApplication` is the starting point. When the app launches, Spring scans
every class inside this package (`com.example.taskmanager`) and all its subpackages
(`domain`, `repository`, `service`, `web`...) looking for classes marked with one of
these labels (called **annotations**):

| Annotation       | Found in this project on...                          | Means              |
|-------------------|--------------------------------------------------------|---------------------|
| `@RestController` | `TaskController`                                        | "handles HTTP requests" |
| `@Service`         | `TaskServiceImpl`                                       | "business logic"   |
| `@Repository`      | `InMemoryTaskRepository`                                | "data access"       |
| `@Component`       | `LoggingTaskCompletionListener`, `CompletionStatsListener` | "generic managed object" |

For every class carrying one of these, Spring creates exactly one instance (called a
**bean**) and keeps it in memory for the app's lifetime.

Now open `TaskController.java`:

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    ...
```

`TaskController`'s constructor *asks for* a `TaskService` (an interface — see
`service/TaskService.java`). When Spring builds the `TaskController` bean, it looks
through all the beans it manages, finds the one bean that implements `TaskService`
(that's `TaskServiceImpl`, marked `@Service`), and passes it in automatically. You never
write `new TaskServiceImpl()` anywhere — Spring does it once, and hands the same
instance to whoever asks for a `TaskService`.

This same thing happens one layer down: `TaskServiceImpl`'s constructor asks for a
`TaskRepository` (an interface) and a `List<TaskCompletionListener>` — Spring finds the
one `@Repository` bean (`InMemoryTaskRepository`) and *every* `@Component` bean that
implements `TaskCompletionListener` (currently two: logging and stats), and injects
both. That's also how `OCP` is satisfied for free: add a third
`@Component implements TaskCompletionListener` class anywhere in the project, and it
shows up in that list automatically — nobody has to edit `TaskServiceImpl`.

## Following one HTTP request through the whole app

Say you run:
```bash
curl -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" \
  -d '{"title":"Write slides","priority":"HIGH"}'
```

1. The embedded web server (started automatically because of
   `spring-boot-starter-webmvc`, listening on port 8080) receives the raw HTTP request.
2. Spring looks at the URL (`/api/tasks`) and method (`POST`), and matches it to
   `TaskController.createTask(...)` because of `@RequestMapping("/api/tasks")` on the
   class and `@PostMapping` on the method.
3. Spring reads the JSON body and automatically converts it into a `CreateTaskRequest`
   Java object (that's what `@RequestBody` does) — you never parse JSON by hand.
4. `TaskController.createTask` calls `taskService.createTask(title, priority)`. Because
   of dependency injection, `taskService` here is really the one `TaskServiceImpl`
   instance Spring built.
5. `TaskServiceImpl.createTask` validates the title isn't blank, builds a `Task` object,
   and calls `taskRepository.save(task)`.
6. `InMemoryTaskRepository.save` puts that `Task` into a `ConcurrentHashMap` living in
   the JVM's memory and gives it an ID. This is the entire "database" — a Java
   collection, gone the moment the process stops.
7. The saved `Task` travels back up through the service to the controller, which wraps
   it in a `TaskResponse` DTO (see "What's a DTO?" below) and returns it.
8. Spring converts that `TaskResponse` object back into JSON automatically, and the web
   server sends it back as the HTTP response body you see in your terminal.

Every arrow in that chain (`Controller -> Service -> Repository`) crosses through an
*interface*, not a concrete class — that's the DIP principle from the course, visible
as actual running code instead of just a diagram.

## The folder structure

```
src/main/java/com/example/taskmanager/...    <- the actual application code
src/test/java/com/example/taskmanager/...    <- tests, mirrors the same package layout
src/main/resources/application.properties    <- app configuration (currently just a name)
```

A Java **package** like `com.example.taskmanager.service` is just a namespace, and it
maps directly onto a folder path: `src/main/java/com/example/taskmanager/service/`.
Every `.java` file in that folder starts with `package com.example.taskmanager.service;`
on line 1. This is how Java avoids two unrelated libraries both having a class called
`Task` and colliding — the *full* name is `com.example.taskmanager.domain.Task`.

## What's a DTO?

"DTO" = Data Transfer Object. `web/dto/TaskResponse.java` and
`web/dto/CreateTaskRequest.java` are plain classes whose only job is to define *exactly*
what JSON shape goes over HTTP, separate from `domain/Task.java` (the internal model).
Why bother with two classes that look similar? So that if the internal `Task` model
changes shape later, the public API contract doesn't have to change with it, and
vice versa — another flavor of the "low coupling" idea from the slides.

## What's going on in `src/test`

- **JUnit 5** is the testing framework: it's what makes `@Test`-annotated methods
  actually run when you call `./mvnw test`, and provides ways to assert "this should
  equal that."
- **Mockito** lets you create a fake stand-in for a real class — a **mock**. In
  `TaskServiceImplTest`, `@Mock private TaskRepository taskRepository;` creates a fake
  repository that never touches real storage; you tell it exactly what to return
  (`when(taskRepository.findById(1L)).thenReturn(...)`) and check it was called
  correctly (`verify(...)`). This is only possible because `TaskServiceImpl` depends on
  the `TaskRepository` *interface* — you can't easily fake a concrete class like that.
- **`@WebMvcTest`** (used in `TaskControllerTest`) is a Spring Boot helper that starts
  *only* the web layer for a test — real HTTP request parsing, real JSON conversion,
  real routing — while swapping in a mock `TaskService` (`@MockitoBean`) instead of the
  real business logic. It's deliberately a smaller, faster version of starting the
  whole application just to test one controller.

## Glossary

| Term | Meaning |
|------|---------|
| JVM | Java Virtual Machine — the program that actually runs compiled Java code |
| Bytecode | The compiled `.class` format the JVM understands; produced from your `.java` files |
| Dependency | An external library your project uses, declared in `pom.xml` |
| Maven Central | The public catalog Maven downloads dependencies from |
| Artifact | A single published library (a `.jar` file) identified by groupId+artifactId+version |
| Bean | An object that Spring created and manages for you, instead of you writing `new` |
| Annotation | A `@Something` label on a class/method that tools like Spring read to change behavior |
| Dependency injection | Asking for what you need (via a constructor) instead of constructing it yourself |
| Embedded server | A web server (here, started by Spring Boot) that runs *inside* your application process, not as a separate program you install |
| Endpoint | A specific URL + HTTP method your app responds to, e.g. `POST /api/tasks` |
| DTO | Data Transfer Object — a class whose only job is defining a request/response shape |
| Mock | A fake stand-in object used in tests, so you can test logic in isolation |
