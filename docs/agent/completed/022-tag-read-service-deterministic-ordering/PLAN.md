# PLAN — 022-tag-read-service-deterministic-ordering

- risk_category: A

State: PLANNED
issue: #22

Objetivo: Garantir ordem determinística de tags no nível de banco de dados. Adicionar `ORDER BY name ASC` ao SQL de TagReadService.

Escopo:
- Modificar TagReadService.xml: adicionar `order by name asc` ao SELECT
- Garantir que tags sejam retornadas em ordem alfabética no DB (não apenas aplicação)
- Manter compatibilidade com JPA (quando migrar)
- Validar testes passam

Critérios de aceite:
- [x] SQL com ORDER BY name asc
- [x] Tags sempre retornadas alfabeticamente (do DB)
- [x] Comportamento parity
- [x] Tests: PASS

Risk: A (SQL apenas, determinismo)
Operational path: trivial_path
Budget: budget_max_usd: 2.00
