# SEQUENCE — Canonical execution order of agente papéis (roles) and gates

**Autoridade:** consolidated from `harness/SEQUENCE.yaml` + `harness/99-start-slice.md` + `docs/OPERATING-GUIDE.md` (Passo 188)

**Versão:** 2 (slices 009–010 consolidation)

**Lido por:** human runner e orchestrator (99-start-slice.md em modo contínuo)

---

## Estrutura da sequência

Cada slice segue a máquina de estados: `DISCOVERY → PLANNED → IMPLEMENTING → EVALUATING → FIXING → DOCUMENTING → READY`

Papéis e suas transições:

### Fase 0 — Onboarding & Baseline (primeira ativação apenas)
| Papel | Prompt | Condição de entrada | Saída | Próximo papel |
|---|---|---|---|---|
| **onboard/resume** | 00-onboard.md | sempre (entrada) | STATE.md, OPERATING-GUIDE.md, contexto, .gitignore | surveyor (se projeto existente) |
| **surveyor** | 00a-baseline.md | projeto existente | AS-IS.md | guide_auditor |
| **guide_auditor** | 00b-guide-audit.md | sempre (após onboard) | GUIDE-AUDIT.md | planner (slice 001) |

### Fase 1–3 — Slice workflow (repetido para cada slice)

#### Bloco A — Planejamento
| Papel | Prompt | Condição de entrada | Saída | Próximo papel |
|---|---|---|---|---|
| **planner** | 01-plan.md | sempre (nova slice) | PLAN.md, issue #id (gh create) | designer (se ambiguidade) ou builder |
| **designer** (opcional) | 01b-design-sketch.md | ambiguidade de desenho (não stack/framework/banco) | DESIGN-SKETCH.md | builder |

**Portão (Prompt 01):** Plan approval — usuário confirma PLAN.md antes de implementação. Não bloqueante a nível de operação, mas de fluxo (formalizado no handoff). Se não aprovado, volta a planner.

#### Bloco B — Implementação
| Papel | Prompt | Condição de entrada | Saída | Próximo papel |
|---|---|---|---|---|
| **builder** | 02-implement.md | `is_refactor == false` | mudanças de código | evaluator |
| **refactorer** | 02b-refactor.md | `is_refactor == true` | refatoração (paridade comportamental obrigatória) | evaluator |

#### Bloco C — Avaliação independente
| Papel | Prompt | Condição de entrada | Saída | Próximo papel |
|---|---|---|---|---|
| **evaluator** | 03-evaluate.md | implementação/refatoração completa | REVIEW.md (PASS/FAIL) | qa (se testável) ou reviewer (se C/D) ou closer |
| **qa** (opcional) | 03b-qa-coverage.md | slice toca código testável relevante | QA-COVERAGE.md | test_author (se usuário confirma) |
| **test_author** (opcional) | 03c-qa-author-tests.md | usuário confirmou QA-COVERAGE.md | QA-TESTS.md, testes passando | e2e_tester (se jornada alto-valor) ou reviewer (se C/D) ou closer |
| **e2e_tester** (opcional) | 03d-e2e.md | slice entrega jornada ponta-a-ponta alto-valor | QA-E2E.md | reviewer (se C/D) ou closer |
| **reviewer** (opcional) | 04-review-security.md | risco C/D OU módulo compartilhado OU design-core | REVIEW-APPROVED (PASS/FAIL/CONDITIONAL) | closer |

**Portão (Prompt 03b→03c):** QA decision — qa mede cobertura e para antes de criar testes. Usuário revisa QA-COVERAGE.md e autoriza (ou revisa) antes de test_author criar testes. Documentado em OPERATING-GUIDE.md ("Autoriza testes").

#### Bloco D — Encerramento e integração
| Papel | Prompt | Condição de entrada | Saída | Próximo papel |
|---|---|---|---|---|
| **closer** | 05-closeout.md | PASS (03) + APPROVED (04 se C/D) | SLICE-REPORT.md, slice movida para completed/, commit | integrator |

**Portão (Prompt 05 + 99):** Auto-continue — closer apresenta SLICE-REPORT.md e abre janela de 10s. Se intervenção humana, respeita; se silêncio, auto-avança para próxima etapa. Documentado em OPERATING-GUIDE.md ("Continua próxima slice").

| Papel | Prompt | Condição de entrada | Saída | Próximo papel |
|---|---|---|---|---|
| **integrator** | 06-integrate.md | slice READY + human_review concedida (se C/D) | integração verificada (merge, rollback, regressão) | doc_gardener |
| **doc_gardener** | 07-retro.md | slice integrada | LESSONS.md atualizado | planner (próxima slice) |

**Auto-loop:** após 07, volta para 01 (próxima slice) se houver slices pendentes; se última slice ou fase concluída, encerra ou passa para Prompt 00 (próxima fase).

---

## Condições de parada e re-entrada

### Parada em avaliação (FIX)
- **Avaliador detecta FAIL (03):** cria FIX.md, volta a 02/02b (builder/refactorer para corrigir)
- **Revisor detecta FAIL (04):** cria FIX.md, volta a 02/02b (ou 04 se revisão iterativa)
- Máximo: 2 rounds de FIX antes de escalar (regra de projeto: configurar em OPERATING-GUIDE)

### Parada em integração (BLOCKING)
- **Integrador detecta falha (06 FAIL):** bloqueia merge; cria FIX.md; volta a 02/02b
- **Dependência não integrada:** 06 bloqueia até deps completarem

### Parada em decisão bloqueante (pending_decision)
- **Planner identifica decisão arquitetural:** não avança a builder; emite bloco handoff com `next_role: planner` e pergunta ao usuário
- **Casos:** escolha de stack, framework, banco, ou decision digna de ADR
- Regra: nunca invente; sempre pergunte; opções de CHARTER/PRD

---

## Confirmação — Portões vs. Operações técnicas

Veja `docs/OPERATING-GUIDE.md` ("Politica unificada de confirmacao e portoes de processo") para definição completa.

**Sumário:**
- **Operações técnicas:** navegação, leitura, mv/cp/mkdir, git, build/test, edição → execute direto, não pergunte
- **Pausas (ask_user) — 3 casos bloqueantes:** pending_decision, red flag C/D, human approval C/D
- **Portões de processo — 3 casos de fluxo:** plan approval (01), qa gate (03b→03c), auto-continue (05)

---

## Handoff entre turnos

Cada turno encerra com bloco `### Handoff` (em `PROGRESS.md` da slice ou resposta visível):

```markdown
### Handoff
- state: <DISCOVERY|PLANNED|IMPLEMENTING|EVALUATING|FIXING|DOCUMENTING|READY>
- this_turn: <papel — o que foi feito>
- evidence: <comandos/testes/arquivos que comprovam>
- open_risks: <riscos que SEGUEM com o trabalho>
- pending_decision: <decisão que o usuário precisa tomar | none>
- next_role: <próximo papel em SEQUENCE>
- next_action: <uma linha: o que o próximo agente faz>
- suggested_model: <tier light|standard|deep + effort> — <motivo>
```

Orquestrador (99-start-slice.md) lê `next_role` e avança automaticamente ou para por `pending_decision`.

---

## Versão e histórico

- **v1:** SEQUENCE.yaml + 99-start-slice.md iniciais (não totalmente alinhados)
- **v2 (slices 009–010):** Consolidação unificada:
  - Slice 009: resolveu conflitos #1, #2 (docs/ commit, AS-IS naming)
  - Slice 010: consolidou confirmação + portões + bloco canônico
  - Slice 011: **este documento** — consolida ordem de papéis (SEQUENCE)
  - Conflitos #4, #5, #6, #7, #8 pendentes (slices 012+)

---

## Notas

- **Harness descartável:** SEQUENCE.yaml em harness/ é referência; canonical vive em docs/ (este arquivo)
- **OPERATING-GUIDE.md é autoridade:** este documento sumariza e refere OPERATING-GUIDE (seção Papéis, Máquina de estados, Portões)
- **Próxima manutenção:** sempre que um novo papel for adicionado ou ordem for alterada, atualizar este documento E OPERATING-GUIDE.md simultaneamente
