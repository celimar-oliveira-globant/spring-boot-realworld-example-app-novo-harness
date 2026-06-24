# PLAN — 010-governance-confirmations-protocol

## Status
State: PLANNED
issue: #11

## Objetivo
Consolidar e unificar política de confirmação (ask_user) em torno de um bloco canônico no OPERATING-GUIDE, alinhando prompts 01, 03b, 05, 07 (e harness/*) a regras consistentes.

## Escopo
- Incluído:
  - Definir bloco canônico em `docs/OPERATING-GUIDE.md` que unifica 3 casos de pausa (pending_decision, red_flag_C_D, human_review_approval) com portões de processo (plan_approval, qa_03b→03c_gate, next_slice_auto_continue_policy)
  - Atualizar `harness/01-plan.md` (Passo 3: Pre-Flight Checklist) com referência clara ao bloco canônico
  - Atualizar `harness/03b-qa-coverage.md` (QA gate) com referência ao portão humano de 03b→03c
  - Atualizar `harness/05-closeout.md` (relatório + janela) com alinhamento a auto-continue
  - Atualizar `harness/07-retro.md` (ordem e handoff)
  - Validar alinhamento cruzado em SOURCES.md se houver conflitos residuais
- Fora de escopo:
  - Refatorar código do harness (apenas edições textuais/estruturais mínimas)
  - Alterar comportamento de fluxo (apenas documentar existente de forma coerente)
- files_owned:
  - `docs/OPERATING-GUIDE.md` (novo bloco "Confirmação e portões de processo")
  - `harness/01-plan.md` (referência ao bloco)
  - `harness/03b-qa-coverage.md` (QA gate + portão 03b→03c)
  - `harness/05-closeout.md` (janela de continuação automática)
  - `harness/07-retro.md` (alinhamento de ordem 05→06→07)

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices (apoio operacional)
- Documento/bloco de origem: `docs/GUIDE-AUDIT.md` (achado #2: unificar confirmação)
- context_sources: []
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: nenhum (governança/documentação)
- Permissões / minimização / mascaramento: n/a
- Auditoria / log: atualizações em docs/ + harness/ registradas por commit
- Revisão humana: recomendado para validação de política

## API, entidades e integrações
- n/a (governança de contrato operacional, não código executável)

## Critérios de aceite
- [x] Bloco canônico em OPERATING-GUIDE.md unifica 3 + 3 casos (decisão, red flag, approval + plan, qa_gate, auto-continue)
- [x] Prompt 01 referencia bloco e aplica política (plan approval como portão, não bloqueio)
- [x] Prompt 03b referencia portão de 03c (QA mede, para, aguarda confirmação)
- [x] Prompt 05 alinha relatório + janela + auto-continue a política uniforme
- [x] Prompt 07 alinha ordem (05→06→07 única; sem alternativas)
- [x] Cross-reference validation: sem contradições entre guia + prompts
- [x] Documentação de lições registrada (LESSONS.md pode cite planner_hint)

## Operational path & risk
- risk_category: A (governança/documentação; sem código executável, secrets ou config sensível)
- operational_path: trivial → standard_A (edições em 5 arquivos; mais complexo que 009, mas ainda A)

## Model Profile
```yaml
risk_category: A
planner:   { tier: light, effort: minimal }
generator: { tier: standard, effort: low }
evaluator: { tier: light, effort: low }
reviewer:  { tier: n/a, effort: n/a }
cross_family_evaluator: false
budget_max_usd: 8.00
rationale: |
  Risco A: governança/documentação. Builder edita 5 arquivos harness+guia; mais envergadura que 009,
  logo tier: standard. Avaliador: light (conformidade textual vs. GUIDE-AUDIT achado #2).
```

## Deterministic gates (a rodar antes de READY)
- textual_review (coerência do bloco canônico + referências em prompts)
- cross_reference_validation (checar que 01, 03b, 05, 07 não conflitam com novo bloco)
- grep_validation (verificar que "confirmação" / "ask_user" referem ao bloco novo, sem duplicação)

## Condições de parada
- Conflito irreconciliável entre 3 + 3 casos (improvável: audit define claramente)
- Prompt descobre nova política não contemplada (para reclassificar)

## Riscos e pendências
- Risco: edições em 5 arquivos → janela maior para inconsistência (mitigado: validação cruzada textual obrigatória antes de READY)
- Risco: prompts já em "produção" (harness/) podem ter inferências implícitas de política (mitigado: slice documenta explicitamente; integrador valida)
- Pendência: conflitos #3, #5, #6, #7, #8 permanecem para slices 011+
