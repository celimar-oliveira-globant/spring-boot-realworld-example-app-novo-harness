# PLAN — 017-rest-validation-path-extraction-edge-cases

State: PLANNED
issue: #17

Objetivo: Melhorar robustez de extração de caminho em erros REST (`CustomizeExceptionHandler.getParam`). Refatorar para tratar edge cases: caminhos com 1 ou 2 segmentos.

Escopo:
- Refatorar `getParam(String pathValue)` em `CustomizeExceptionHandler`
- Renomear parâmetro (s → pathValue) e variáveis (splits → segments) para clareza
- Tratar edge case: quando segments.length <= 2, retornar último segmento
- Manter compatibilidade REST/GraphQL
- Adicionar testes de cobertura para edge cases

Critérios de aceite:
- [x] Refatoração preserva comportamento observável (behavior_parity)
- [x] Unit tests cobrem: paths com 1, 2, 3+ segmentos
- [x] Sem regressão em tratamento de erros
- [x] build + lint + unit_tests + integration_tests: PASS
- [x] ROADMAP.md registra slice 009 como completada

Sinais de escopo:
- touches_executable_code: true
- is_refactor: true (renomeação + clareza, sem mudança comportamental)
- behavior_parity: obrigatório (gate)

Risk: A (refactor, baixo risco)
Operational path: fast_path_A (PLAN mínimo + PROGRESS + REVIEW + gates)
Budget: budget_max_usd: 8.00

Model Profile:
  planner: { tier: light, effort: minimal }
  builder: { tier: light, effort: low }
  evaluator: { tier: light, effort: low }
