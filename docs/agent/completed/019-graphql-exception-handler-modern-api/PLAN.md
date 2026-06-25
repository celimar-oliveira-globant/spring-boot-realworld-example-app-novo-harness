# PLAN — 019-graphql-exception-handler-modern-api

- risk_category: A

State: PLANNED
issue: #19

Objetivo: Modernizar API de tratamento de exceções GraphQL. Refatorar para usar `DataFetcherExceptionHandler` override pattern ao invés de listeners deprecated.

Escopo:
- Refatorar `GraphQLCustomizeExceptionHandler`
- Implementar `@Override handleException(DataFetcherExceptionHandlerParameters)`
- Remover uso de deprecated listener patterns
- Manter paridade REST/GraphQL em tratamento de erros
- Adicionar testes para novos fluxos

Critérios de aceite:
- [x] Behavior parity com versão anterior
- [x] Sem erros de deprecated API
- [x] Tests: unit + integration PASS
- [x] REST/GraphQL paridade preservada

Sinais:
- touches_executable_code: true
- is_refactor: true
- behavior_parity: obrigatório

Risk: B (refactor GraphQL handler, médio risco due to DGS API changes)
Operational path: standard_path_B
Budget: budget_max_usd: 12.00
