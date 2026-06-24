# PLAN — 012-gitignore-canonical-rules

## Status
State: PLANNED
issue: #13

## Objetivo
Validar e documentar regras canônicas de .gitignore: harness/ (descartável, ignorado), docs/ (histórico, committed). Resolver conflito #4 de GUIDE-AUDIT.

## Escopo
- Incluído:
  - Revisar .gitignore atual (linha 29: /harness/)
  - Validar alinhamento com OPERATING-GUIDE.md ("Layout de arquivos")
  - Documentar regras canônicas em .gitignore-CANONICAL.md (ou comentário detalhado)
  - Atualizar GUIDE-AUDIT.md registrando conflito #4 resolvido
- Fora de escopo:
  - Alterar estrutura do repositório
  - Refatorar código (docs/ apenas)
- files_owned:
  - .gitignore
  - docs/.gitignore-CANONICAL.md (novo, referência)
  - docs/GUIDE-AUDIT.md (marcar #4 resolvido)

## Origem e fase
- Fase: 3 — Execução incremental
- Documento: GUIDE-AUDIT.md (conflito #4)
- affects_design_system_core: false

## Critérios de aceite
- [x] .gitignore valida regra: /harness/ sempre ignorado
- [x] Documentação clara: docs/ committed, harness/ gitignored, por quê
- [x] Alinhamento com OPERATING-GUIDE.md Layout
- [x] GUIDE-AUDIT.md marca #4 resolvido

## Operational path & risk
- risk_category: A (config + documentação)
- operational_path: trivial_path (3 itens: confirmar .gitignore + documentar + marcar resolução)

## Model Profile
```yaml
risk_category: A
planner:   { tier: light, effort: minimal }
generator: { tier: light, effort: minimal }
evaluator: { tier: light, effort: minimal }
budget_max_usd: 4.00
```

## Deterministic gates
- grep_validation (.gitignore: /harness/ presente e ativo)
- cross_reference_validation (.gitignore ↔ OPERATING-GUIDE Layout)

## Riscos e pendências
- Nenhum; slice validação pura
- Conflitos #5,#6,#7,#8 pendentes (slices 013+)
