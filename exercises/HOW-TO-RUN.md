# How to Run the Exercises

No build tool is required — just a JDK (17+). Open the `exercises/` folder as a plain
Java source root in your IDE and use its Run button on whichever `Main.java` you want to
try (IntelliJ and VS Code's Java extension both run a class with a `main` method directly,
no project config needed).

If you'd rather use the command line:

### Compile everything
```bash
# from the exercises/ folder, bash/macOS/Linux
mkdir -p out
javac -d out $(find src -name "*.java")
```
```powershell
# PowerShell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName)
```

### Run a specific exercise
```bash
java -cp out solid.srp.starter.Main
java -cp out solid.srp.solution.Main
```
(swap `srp` for `ocp`, `lsp`, `isp`, `dip`, and `starter`/`solution` as needed)

## Workflow per kata

1. Read `docs/0X-<principle>.md` for the task description.
2. Open `src/solid/<principle>/starter/` and run its `Main` to see the current (smelly)
   behavior.
3. Refactor the starter code in place — use your AI assistant as a pairing partner.
4. Re-run `Main` to confirm behavior is preserved and the acceptance criteria in the doc
   are met.
5. Only after attempting it yourself, compare against `src/solid/<principle>/solution/`.
