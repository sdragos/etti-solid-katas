# AI Assistant Setup — Continue.dev + Self-Hosted Qwen3-30B-A3B (vLLM)

This course uses a **self-hosted, free** AI assistant instead of a commercial cloud one:
a Qwen3-30B-A3B model served via vLLM on a university server (2× L40s, 48GB each), accessed
through the **Continue** extension (free, open-source) in VS Code or IntelliJ.

Why this combination:
- Continue speaks the OpenAI chat-completions API, and vLLM's server exposes exactly that —
  no adapter or paid relay needed.
- Everything stays on the university network: no external API key, no per-token billing,
  no student data leaving the building.
- Continue works the same way in VS Code and JetBrains IDEs, so students can use whichever
  they already have set up.

## 1. Server side (instructor, done once before the session)

Start vLLM's OpenAI-compatible server with both GPUs and a shared API key:

```bash
vllm serve Qwen/Qwen3-30B-A3B \
  --served-model-name qwen3-30b-a3b \
  --host 0.0.0.0 --port 8000 \
  --tensor-parallel-size 2 \
  --gpu-memory-utilization 0.90 \
  --max-model-len 32768 \
  --api-key COURSE-SHARED-KEY
```

Notes:
- `--tensor-parallel-size 2` splits the model across both L40s.
- Set a real `--api-key` even on the internal network — it's a free knob against accidental
  misuse, not just external attackers.
- vLLM's continuous batching handles many concurrent chat requests well; a 30B-A3B MoE model
  only activates ~3B params per token, so per-request latency stays low even under a
  classroom's worth of simultaneous requests. Still, do a load check (a few people chatting
  at once) before the session if you can.
- Confirm the server is reachable from the classroom network/VPN ahead of time — this is the
  most common failure point on the day.

## 2. Client side (each participant)

### Install
1. Install VS Code **or** IntelliJ (Community is fine).
2. Install the **Continue** extension/plugin from the respective marketplace.

### Configure the model
Continue (recent versions) reads `~/.continue/config.yaml`. Open it (Continue's IDE panel
also has an "Add model" UI that writes this for you) and add:

```yaml
models:
  - name: University Qwen3 (self-hosted)
    provider: openai
    model: qwen3-30b-a3b
    apiBase: http://<server-host-or-ip>:8000/v1
    apiKey: COURSE-SHARED-KEY
    roles:
      - chat
      - edit
      - apply
```

Replace `<server-host-or-ip>` with the actual address the instructor provides. If you're on
an older Continue version using `config.json`, the equivalent fields are `title`,
`provider: "openai"`, `model`, `apiBase`, `apiKey`.

### Test it
1. Reload the IDE window.
2. Open the Continue side panel, select "University Qwen3 (self-hosted)" as the active model.
3. Ask it something trivial: *"In one sentence, what does the Single Responsibility
   Principle mean?"* — if you get a sensible answer back, you're set.

## Using Continue during the exercises

Two modes you'll use in the practical part:
- **Chat**: ask questions, paste a class, ask "what SOLID principle does this violate and
  why?" or "suggest a refactor and explain the trade-off."
- **Edit/Apply**: select a block of code in the editor and ask Continue to transform it
  in place, then review the diff before accepting.

Treat the AI like a second pair of eyes, not an oracle: it can suggest an overengineered
fix or miss the actual smell. You're expected to read its suggestion, decide if it's right,
and be able to explain why — that's the skill being practiced, not just getting code that
compiles.

## Troubleshooting

| Symptom                         | Likely cause                                         |
|----------------------------------|-------------------------------------------------------|
| Connection refused / timeout     | Not on the campus network/VPN, or wrong host:port      |
| 401 Unauthorized                 | `apiKey` in your config doesn't match the server's     |
| Very slow first response         | Model/server cold start — normal, retry once           |
| Model gives nonsense / empty reply | Wrong `model` name — must match `--served-model-name` |

**Fallback if the server is unreachable during the session**: pair up with someone whose
connection works, or fall back to a free web chat assistant for that exercise only. The
exercises don't require the AI to succeed — they're solvable by hand, the assistant just
speeds up exploration.
