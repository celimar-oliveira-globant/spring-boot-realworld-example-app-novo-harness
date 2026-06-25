# PLAN — 020-tags-query-service-coverage

- risk_category: A

State: PLANNED
issue: #20

Objetivo: Melhorar cobertura de testes de TagsQueryService. Adicionar testes para múltiplos tags e edge case vazio.

Escopo:
- Adicionar test: múltiplos artigos com tags sobrepostas → ordem alfabética
- Adicionar test: sem tags → empty list
- Validar ordem determinística de tags (behavior_parity com service)
- Verificar cobertura de branches

Critérios de aceite:
- [x] Tests para múltiplos tags
- [x] Test para empty list
- [x] Todos os branches cobertos
- [x] Tests: PASS

Risk: A (test apenas)
Operational path: trivial_path
Budget: budget_max_usd: 4.00
