# PROGRESS — 005-graphql-validation-path-extraction-robustness

State: EVALUATING
issue: #5

## Timeline
| Data/hora | Papel | State | Evidência |
|---|---|---|---|
| 2026-06-24 | planner | PLANNED | Issue #5 criada e `PLAN.md` publicado para robustez na extração de campo de validação GraphQL |
| 2026-06-24 | builder | EVALUATING | `getParam(...)` ajustado para paths com 1/2/3+ segmentos; teste focado adicionado para path `input.email`; gates executados com PASS |
| 2026-06-24 | evaluator | EVALUATING | Avaliação independente concluída: critérios de aceite PASS; gates PASS; `REVIEW.md` emitido |

## Decisões
- Corrigir parsing de path curto retornando o último segmento quando `segments.length <= 2`.
- Cobrir regressão funcional via validação aninhada (`NestedValidationInput`) para reproduzir path de dois segmentos.
- Preservar comportamento existente de auth inválida e validações já cobertas.

## Arquivos alterados
- src/main/java/io/spring/graphql/exception/GraphQLCustomizeExceptionHandler.java
- src/test/java/io/spring/graphql/exception/GraphQLCustomizeExceptionHandlerTest.java
- docs/agent/work/005-graphql-validation-path-extraction-robustness/PROGRESS.md
- docs/agent/work/005-graphql-validation-path-extraction-robustness/REVIEW.md

## Comandos e resultados
| Comando | Resultado |
|---|---|
| `gh issue create ...` | issue criada: #5 |
| `./gradlew clean test` | PASS |
| `./gradlew spotlessCheck` | PASS |
| `./gradlew build -x test` | PASS |
| scan textual (`GraphQLCustomizeExceptionHandler.*unchecked`) na saída do build | `UNCK_NOT_FOUND` |

## Riscos residuais / pendências
- Baixo: nota de deprecação da API legada do handler pode continuar aparecendo no `clean test` (fora do objetivo desta slice).
- Operacional: há mudanças locais pré-existentes fora desta slice no working tree; no closeout, manter staging estritamente restrito aos arquivos da slice.
- Próximo passo: executar Prompt 05 (closeout) para compactar artefatos, mover `work -> completed`, atualizar report e encerrar issue #5.
