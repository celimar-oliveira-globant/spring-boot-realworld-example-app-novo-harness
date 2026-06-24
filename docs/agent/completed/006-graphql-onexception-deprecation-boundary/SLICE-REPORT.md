# SLICE-REPORT — 006-graphql-onexception-deprecation-boundary
- Data: 2026-06-24 · issue: #6 · commit: local/pendente

## O que mudou
A slice removeu ruído residual de compilação no handler GraphQL sem alterar comportamento funcional.
Foi aplicada supressão de depreciação **localizada** no método legado `onException(...)`, mantendo o fluxo moderno de tratamento em `handleException(...)` e a lógica existente de erros intacta.

## Evidência
- Critérios de aceite: PASS — ver `REVIEW.md`.
- Gates: PASS (`build`, `lint`, `unit_tests`, `integration_tests`) via:
  - `./gradlew clean test`
  - `./gradlew spotlessCheck`
  - `./gradlew build -x test`
- Verificação do objetivo principal: scan textual do build retornou `DEPREC_NOT_FOUND` para `GraphQLCustomizeExceptionHandler.*deprecat`.
- Conformidade com o guia: conforme para `standard_path_B` com rastreabilidade por issue `#6`.
- Issue: `#6` fechada manualmente via `gh issue close` no closeout.
- QA (se houve): n/a.
- Integração: n/a nesta etapa (closeout da slice concluído; integração formal depende do Prompt 06 quando aplicável).

## Riscos residuais
- Baixo: a necessidade da supressão localizada permanece até upgrade da stack (`graphql-java`/DGS) que elimine o método legado obrigatório.

## Estado e próximo passo
- Estado/fase agora: `State: READY`, fase 3 (execução incremental por slices) em andamento.
- Próxima ação recomendada: definir a próxima slice e iniciar novo Prompt 01.
