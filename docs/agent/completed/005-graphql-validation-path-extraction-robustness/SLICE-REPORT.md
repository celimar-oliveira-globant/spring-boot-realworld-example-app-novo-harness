# SLICE-REPORT — 005-graphql-validation-path-extraction-robustness
- Data: 2026-06-24 · issue: #5 · commit: local/pendente

## O que mudou
A slice corrigiu a extração de campo de validação em `GraphQLCustomizeExceptionHandler.getParam(...)` para caminhos com dois segmentos.
Antes, um path como `input.email` podia resultar em chave vazia; agora retorna `email` corretamente.
Foi adicionado teste de regressão para validar explicitamente esse cenário sem alterar o comportamento já existente dos demais fluxos de erro.

## Evidência
- Critérios de aceite: PASS — ver `REVIEW.md`.
- Gates: PASS (`build`, `lint`, `unit_tests`, `integration_tests`) via:
  - `./gradlew clean test`
  - `./gradlew spotlessCheck`
  - `./gradlew build -x test`
- Regressão coberta: `should_keep_leaf_field_for_two_segment_property_path` em `GraphQLCustomizeExceptionHandlerTest`.
- Conformidade com o guia: conforme para `standard_path_B` com rastreabilidade por issue `#5`.
- Issue: `#5` fechada manualmente via `gh issue close` no closeout.
- QA (se houve): n/a (fluxo QA adicional não acionado).
- Integração: n/a nesta etapa (closeout da slice concluído; integração formal depende do Prompt 06 quando aplicável).

## Riscos residuais
- Baixo: nota de deprecação conhecida no handler permanece fora do escopo desta slice.

## Estado e próximo passo
- Estado/fase agora: `State: READY`, fase 3 (execução incremental por slices) em andamento.
- Próxima ação recomendada: definir a próxima slice e iniciar novo Prompt 01.
