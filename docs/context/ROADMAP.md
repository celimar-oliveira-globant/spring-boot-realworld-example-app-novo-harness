# ROADMAP

> Roadmap inicial inferido das fontes técnicas disponíveis. Ordem e escopo de negócio pendem confirmação adicional.

## Fase 1 — Baseline e governança operacional
- **Objetivo:** estabelecer visão AS-IS e validar coerência do contrato operacional.
- **Escopo:** executar baseline de código/documentação e auditoria do guia (prompts 00a/00b).
- **Fora de escopo:** alterações de comportamento no produto.
- **Critérios de aceite:** artefatos de baseline/auditoria registrados em `docs/` e próximos riscos priorizados.

## Fase 2 — Planejamento da primeira slice de produto
- **Objetivo:** definir uma unidade de trabalho pequena, auditável e reversível.
- **Escopo:** Prompt 01 com classificação de risco, critérios de aceite, gates e dependências.
- **Fora de escopo:** implementação sem plano aprovado.
- **Critérios de aceite:** `PLAN.md` criado em `docs/agent/work/<slug>/` com caminho operacional claro.

## Fase 3 — Execução incremental por slices
- **Objetivo:** evoluir o produto sem violar arquitetura/contratos.
- **Escopo:** ciclo 02/02b → 03 → 04 (condicional) → 05 → 06 → 07.
- **Fora de escopo:** mudanças estruturais sem decisão registrada.
- **Critérios de aceite:** cada slice concluída em `docs/agent/completed/<slug>/` com evidências.

## Status de execução atual
- Fase 1: concluída (baseline + guide audit em `docs/AS-IS.md` e `docs/GUIDE-AUDIT.md`).
- Fase 2: concluída (planejamento da primeira slice finalizado).
- Fase 3: em andamento.
  - Slice concluída: `001-tags-deterministic-order` em `docs/agent/completed/001-tags-deterministic-order/`.
  - Slice concluída: `002-spotless-implicit-dependencies` em `docs/agent/completed/002-spotless-implicit-dependencies/`.
  - Slice concluída: `003-graphql-exception-handler-warning-hygiene` em `docs/agent/completed/003-graphql-exception-handler-warning-hygiene/`.
  - Slice concluída: `004-graphql-exception-handler-modern-api-alignment` em `docs/agent/completed/004-graphql-exception-handler-modern-api-alignment/`.
  - Slice concluída: `005-graphql-validation-path-extraction-robustness` em `docs/agent/completed/005-graphql-validation-path-extraction-robustness/`.
  - Slice concluída: `006-graphql-onexception-deprecation-boundary` em `docs/agent/completed/006-graphql-onexception-deprecation-boundary/`.
  - Slice concluída: `007-rest-validation-path-extraction-robustness` em `docs/agent/completed/007-rest-validation-path-extraction-robustness/`.
  - Slice concluída: `008-rest-error-serializer-determinism` em `docs/agent/completed/008-rest-error-serializer-determinism/`.

## Sequência sugerida (inicial)
1. Baseline AS-IS + Guide Audit (se ainda não executados)
2. Primeira slice de baixo risco para validar o fluxo ponta a ponta
3. Slices de maior impacto/risco com governança proporcional (B/C/D)

## Dependências e restrições conhecidas
- Preservar paridade REST/GraphQL.
- Preservar DDD + CQRS.
- Tratar auth/JWT e fronteiras sensíveis com caminho estrito quando aplicável.
