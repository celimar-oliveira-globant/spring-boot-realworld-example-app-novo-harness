# SLICE-REPORT — 008-rest-error-serializer-determinism
- Data: 2026-06-24 · issue: #8 · commit: local/pendente

## O que mudou
A slice reforçou a robustez do serializer de erros REST (`ErrorResourceSerializer`).
A agregação de erros por campo passou de `HashMap` para `LinkedHashMap`, tornando a ordem de campos determinística por primeira ocorrência.
Também foi removido o caminho de `printStackTrace()` durante escrita de mensagens, mantendo tratamento de `IOException` no fluxo padrão do serializer.

## Evidência
- Critérios de aceite: PASS — ver `REVIEW.md`.
- Gates: PASS (`build`, `lint`, `unit_tests`, `integration_tests`) via:
  - `./gradlew clean test`
  - `./gradlew spotlessCheck`
  - `./gradlew build -x test`
- Regressão coberta: `ErrorResourceSerializerTest#should_preserve_first_field_occurrence_order_when_serializing_errors`.
- Verificação de higiene: scan textual reportou `STACKTRACE_NOT_FOUND`.
- Conformidade com o guia: conforme para `standard_path_B` com rastreabilidade por issue `#8`.
- Issue: `#8` fechada manualmente via `gh issue close` no closeout.
- QA (se houve): n/a.
- Integração: n/a nesta etapa (closeout da slice concluído; integração formal depende do Prompt 06 quando aplicável).

## Riscos residuais
- Baixo: consumidores que dependiam de ordem não definida podem observar ordem estável de campos.

## Estado e próximo passo
- Estado/fase agora: `State: READY`, fase 3 (execução incremental por slices) em andamento.
- Próxima ação recomendada: definir a próxima slice e iniciar novo Prompt 01.
