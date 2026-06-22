---
marp: true
theme: default
paginate: true
size: 16:9
---

# SOLID Principles in Java
### Writing object-oriented code that survives change

A 4-hour workshop — theory + AI-assisted refactoring practice

---

## Agenda (4 hours)

| Time          | Block                                    |
|---------------|-------------------------------------------|
| 10 min        | Welcome + AI assistant check              |
| 35 min        | OOP refresher                              |
| 75 min        | SOLID theory (this deck)                   |
| 10 min        | Break                                      |
| 90 min        | Hands-on refactoring kata                  |
| 20 min        | Review + wrap-up                           |

---

## Before we start

Open your IDE and confirm Continue is talking to the self-hosted model:

> *"In one sentence, what does the Single Responsibility Principle mean?"*

If you get an answer back — you're ready. If not, flag it now, not during the exercises.

---

# Why does design matter?

---

## The same bug, two codebases

In codebase A, fixing a bug means changing **one class**, in **one place**.

In codebase B, the same fix means tracing the logic through **six classes**, and you're
not sure what else depends on the one you're about to touch.

Same language. Same features. Wildly different cost of change.

**SOLID is about keeping you in codebase A.**

---

## Code smells: the symptoms we're hunting

- A class does five unrelated things
- Adding one new case means editing ten existing `if` branches
- A subclass breaks code that worked fine with its parent
- A class is forced to implement methods it doesn't need
- A class can't be tested without a real database/network call

Each of these maps to one SOLID principle. We'll see all five today.

---

# Quick OOP refresher

(classes, interfaces, polymorphism, coupling & cohesion)

---

## Classes & objects, in one slide

```java
class Car {
    private int speed;          // state

    void accelerate() {         // behavior
        speed += 10;
    }
}

Car myCar = new Car();          // object = instance of the class
myCar.accelerate();
```

A **class** is a blueprint. An **object** is one thing built from that blueprint.

---

## Interfaces: a contract, not an implementation

```java
interface PaymentMethod {
    void pay(double amount);
}

class CardPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Charged " + amount + " to card");
    }
}

class CashPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Received " + amount + " in cash");
    }
}
```

Code that only knows about `PaymentMethod` doesn't care *which* one it got.

---

## Polymorphism: same call, different behavior

```java
void checkout(PaymentMethod method, double amount) {
    method.pay(amount);   // works for ANY PaymentMethod
}

checkout(new CardPayment(), 50.0);
checkout(new CashPayment(), 50.0);
```

`checkout` never changes, no matter how many payment methods we add later.
This is the mechanism SOLID leans on, over and over.

---

## Coupling & cohesion — the vocabulary you need today

- **Cohesion**: how related the things inside one class are.
  High cohesion = a class does one job well.
- **Coupling**: how much one class knows about another's internals.
  Low coupling = you can change one class without breaking others.

**Goal: high cohesion, low coupling.**
Every SOLID principle is a different angle on getting there.

---

# What is SOLID?

---

## The acronym

| Letter | Principle                         |
|--------|-------------------------------------|
| S      | Single Responsibility Principle     |
| O      | Open/Closed Principle                |
| L      | Liskov Substitution Principle        |
| I      | Interface Segregation Principle      |
| D      | Dependency Inversion Principle       |

Coined/popularized by **Robert C. Martin ("Uncle Bob")**, early 2000s — five
ideas about where to put boundaries in object-oriented code.

---

# S — Single Responsibility Principle

---

## SRP — definition

> A class should have **one reason to change**.

Not "one method" — one *responsibility*, i.e. one stakeholder/concern whose
requirements would drive a change to this class.

If a class changes when pricing rules change **and** when the file format
changes **and** when the email provider changes — it has three reasons to
change, and three responsibilities.

---

## SRP — the smell

```java
class InvoiceProcessor {
    double calculateTotal(List<Item> items) { /* pricing logic */ }

    void printInvoice(List<Item> items) { /* formatting logic */ }

    void saveToFile(String text, String path) { /* file I/O */ }

    void emailInvoice(String address, String text) { /* email logic */ }
}
```

Four reasons to change, living in one class. Touch pricing, risk breaking
printing. Touch email, recompile the whole thing.

---

## SRP — refactored

```java
class InvoiceCalculator { double calculateTotal(List<Item> items) {...} }

class InvoicePrinter { String format(Invoice invoice) {...} }

class InvoiceFileRepository { void save(String path, String text) {...} }

class InvoiceMailer { void send(String address, String text) {...} }
```

Each class now has exactly one reason to change. Composing them is the
job of whoever calls them — not a concern of any single class.

---

## SRP — try it with your AI assistant

> *"This class has multiple responsibilities. Identify them, then suggest how
> to split it following the Single Responsibility Principle."*

Watch for: does it actually name *different stakeholders/reasons to change*,
or does it just mechanically split methods into smaller classes without a
real justification? You decide if the split makes sense.

---

# O — Open/Closed Principle

---

## OCP — definition

> Software entities should be **open for extension, but closed for
> modification**.

You should be able to add new behavior without editing code that already
works and is already tested.

---

## OCP — the smell

```java
class AreaCalculator {
    double totalArea(List<Shape> shapes) {
        double total = 0;
        for (Shape s : shapes) {
            if (s.type.equals("RECTANGLE")) total += s.width * s.height;
            else if (s.type.equals("CIRCLE")) total += Math.PI * s.radius * s.radius;
            else throw new IllegalArgumentException("Unknown shape: " + s.type);
        }
        return total;
    }
}
```

Adding a `Triangle` means editing this method — and re-testing everything
that already worked.

---

## OCP — refactored

```java
interface Shape { double area(); }

class Rectangle implements Shape {
    double width, height;
    public double area() { return width * height; }
}

class Circle implements Shape {
    double radius;
    public double area() { return Math.PI * radius * radius; }
}

class AreaCalculator {
    double totalArea(List<Shape> shapes) {
        return shapes.stream().mapToDouble(Shape::area).sum();
    }
}
```

A new `Triangle implements Shape` needs **zero** changes to `AreaCalculator`.

---

## OCP — try it with your AI assistant

> *"Adding a new shape to this calculator requires editing the calculator
> itself. Refactor it so new shapes can be added without modifying existing
> code."*

Then actually add a `Triangle` and check: did you have to touch
`AreaCalculator`? If yes, the refactor isn't done yet.

---

# L — Liskov Substitution Principle

---

## LSP — definition

> Objects of a subclass should be **substitutable** for objects of the
> superclass, without breaking the client's expectations.

If code works correctly with a `Bird`, it must keep working correctly when
you hand it *any* subclass of `Bird` — not just the ones you tested with.

---

## LSP — the smell

```java
abstract class Bird {
    abstract void fly();
}

class Sparrow extends Bird {
    void fly() { System.out.println("Sparrow flies away"); }
}

class Penguin extends Bird {
    void fly() { throw new UnsupportedOperationException("Penguins can't fly!"); }
}

void makeAllFly(List<Bird> birds) {
    for (Bird b : birds) b.fly();   // crashes the moment a Penguin is in the list
}
```

`Penguin` *is a* `Bird` biologically, but it's not substitutable as one here.

---

## LSP — refactored

```java
abstract class Bird { abstract void move(); }

interface Flyable { void fly(); }

class Sparrow extends Bird implements Flyable {
    void move() { System.out.println("Sparrow hops"); }
    public void fly() { System.out.println("Sparrow flies away"); }
}

class Penguin extends Bird {
    void move() { System.out.println("Penguin waddles and swims"); }
    // no fly() — and nothing forces it to have one
}

void makeAllFly(List<Flyable> flyers) {
    for (Flyable f : flyers) f.fly();   // only ever called on things that CAN fly
}
```

---

## LSP — try it with your AI assistant

> *"This subclass overrides a method by throwing an exception. Does this
> violate the Liskov Substitution Principle? Propose a fix."*

A correct answer should change the **hierarchy/abstraction**, not just wrap
the call in a try/catch at the caller. Catching the exception hides the
violation, it doesn't fix it.

---

# I — Interface Segregation Principle

---

## ISP — definition

> Don't force a class to implement methods it doesn't use.
> Prefer several small, specific interfaces over one large, general one.

---

## ISP — the smell

```java
interface Worker {
    void work();
    void eat();
    void sleep();
}

class RobotWorker implements Worker {
    public void work() { System.out.println("Welding..."); }
    public void eat() { throw new UnsupportedOperationException("Robots don't eat"); }
    public void sleep() { throw new UnsupportedOperationException("Robots don't sleep"); }
}
```

`RobotWorker` is forced into a contract that doesn't fit it.

---

## ISP — refactored

```java
interface Workable { void work(); }
interface Eatable { void eat(); }
interface Sleepable { void sleep(); }

class HumanWorker implements Workable, Eatable, Sleepable { /* all three, for real */ }

class RobotWorker implements Workable { /* only what actually applies */ }
```

Code that only needs `work()` can depend on `Workable` alone — and never
has to know `RobotWorker` even exists as a distinct concept.

---

## ISP — try it with your AI assistant

> *"This interface forces implementers to provide methods that don't apply
> to all of them. Split it following the Interface Segregation Principle."*

Push back if it just renames methods instead of actually separating
unrelated capabilities into different interfaces.

---

# D — Dependency Inversion Principle

---

## DIP — definition

> High-level code should depend on **abstractions**, not on low-level
> concrete implementations. Details should depend on abstractions too —
> never the other way around.

---

## DIP — the smell

```java
class MySqlOrderRepository {
    void save(String order) { System.out.println("Saving to MySQL: " + order); }
}

class OrderService {
    private MySqlOrderRepository repository = new MySqlOrderRepository();

    void placeOrder(String order) {
        // business logic...
        repository.save(order);
    }
}
```

`OrderService` (high-level business logic) is welded to one concrete
database technology. You can't test it without a real MySQL instance.

---

## DIP — refactored

```java
interface OrderRepository { void save(String order); }

class MySqlOrderRepository implements OrderRepository { /* real DB */ }

class InMemoryOrderRepository implements OrderRepository { /* for tests */ }

class OrderService {
    private final OrderRepository repository;

    OrderService(OrderRepository repository) {     // constructor injection
        this.repository = repository;
    }

    void placeOrder(String order) {
        repository.save(order);
    }
}
```

Swap the repository freely. `OrderService` never changes.

---

## DIP — try it with your AI assistant

> *"This high-level class instantiates a concrete low-level dependency
> directly. Refactor it to depend on an abstraction, injected via the
> constructor."*

This is the principle behind dependency-injection frameworks like Spring —
the framework just automates the wiring you're about to do by hand.

---

# SOLID at a glance

| Principle | One-liner                                | Smell to watch for                        |
|-----------|--------------------------------------------|---------------------------------------------|
| S         | One reason to change                       | A class doing unrelated things              |
| O         | Extend without modifying                   | Long `if`/`switch` chains on type            |
| L         | Subtypes must be truly substitutable        | Override that throws/disables behavior       |
| I         | No forced unused methods                    | Interface methods implementers fake/ignore   |
| D         | Depend on abstractions, not concretions     | `new ConcreteThing()` inside business logic  |

---

# Practical part: refactoring kata

---

## How the exercises work

1. Each of the 5 exercises has a `starter` package with one SOLID violation.
2. Run its `Main`, see the current (working but smelly) behavior.
3. Refactor in place — use Continue as a pairing partner: ask it to name the
   smell, propose a fix, or transform selected code.
4. Re-run `Main` — behavior must still work, and the acceptance criteria in
   `docs/0X-*.md` must hold.
5. Compare against the `solution` package only after you've tried it yourself.

**Use the AI critically**: it can suggest something that compiles but is
overengineered, or miss the actual problem. You decide, and you should be
able to explain *why* your final version is correct — that's the actual
skill.

---

# Thank you — questions?

Recap:
- SOLID isn't a checklist, it's a way of asking "what will change here, and
  am I paying for that change in one place or in ten?"
- Your AI assistant is fast at proposing refactors — it is not the judge of
  whether they're *right*. That's still you.
