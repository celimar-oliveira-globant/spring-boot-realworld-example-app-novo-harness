# PROGRESS — 006-graphql-onexception-deprecation-boundary (compactado)

State: READY
issue: #6

## Resumo da slice
- O que mudou: supressão de depreciação foi aplicada **apenas** no shim legado `onException(...)` de `GraphQLCustomizeExceptionHandler`, com comentário de contexto técnico.
- Evidência-chave: gates verdes (`./gradlew clean test`, `./gradlew spotlessCheck`, `./gradlew build -x test`) e ausência da nota de depreciação do handler (`DEPREC_NOT_FOUND`).
- Decisões que perduram: manter a lógica moderna em `handleException(...)` e limitar a supressão ao boundary exigido por `graphql-java 17.x`.
- Riscos residuais: baixo — remoção futura da supressão sem upgrade de stack pode reintroduzir ruído.
- Rastreabilidade de entrega: issue `#6` fechada no closeout.

## Handoff final
### Handoff
- state: READY
- this_turn: closer — slice "006-graphql-onexception-deprecation-boundary" concluída, documentada e relatada
- evidence: docs/agent/completed/006-graphql-onexception-deprecation-boundary/SLICE-REPORT.md (+ REVIEW.md, PROGRESS.full.md)
- open_risks: baixo — dependência do contrato legado de `graphql-java 17.x` para o shim `onException`
- pending_decision: confirmar início da próxima slice
- next_role: planner
- next_action: definir próxima slice e iniciar Prompt 01 com novo slug/issue
- suggested_model: standard + low — próximo planejamento incremental

> Histórico completo (timeline, comandos, decisões intermediárias) em `PROGRESS.full.md`, na mesma pasta.
