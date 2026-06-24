# STATE

State: READY              # DISCOVERY | PLANNED | IMPLEMENTING | EVALUATING | FIXING | DOCUMENTING | READY
Current phase: 3 — Execução incremental por slices
Last completed phase: 3 — Execução incremental por slices (slice 015 finalizada)
Active work unit: none    # docs/agent/work/<slug> quando houver
Baseline AS-IS: 2026-06-24 — docs/AS-IS.md
Guide audit: 2026-06-24 — docs/GUIDE-AUDIT.md (conflitos #1, #2, #3 resolvidos em slices 009–015)

## Briefing — o que o próximo agente faz primeiro
1. Revisar `docs/agent/completed/015-concurrency-yaml-canonical-order/SLICE-REPORT.md` e `docs/context/ROADMAP.md`.
2. Definir a próxima slice (016: custo thresholds claimed_by legacy canônico) e acionar Prompt 01 (planner) com novo slug/issue.

## Não faça
- Não inventar requisitos de produto.
- Não escolher stack sem contexto.
- Não escrever código de produto antes de uma unidade de trabalho ser planejada.
- Não usar segredos/credenciais/dados sensíveis reais.
- Não pedir confirmações para operações triviais de bash e git.
