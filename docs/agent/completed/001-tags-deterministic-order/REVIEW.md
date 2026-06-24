# REVIEW — 001-tags-deterministic-order

## Veredito
Result: PASS

## Checagem de escopo
- Spec seguido: **sim**.
  - Mudança funcional da slice permanece no escopo planejado (`GET /tags` determinístico + testes).
  - Ajustes adicionais pós-FIX foram técnicos e mínimos para destravar gates (`gradle.properties` para compatibilidade Spotless/JDK17 + formatação em `DefaultJwtServiceTest`), conforme `FIX.md`, sem alterar comportamento de produto da slice.
- Mudanças fora de escopo:
  - Não houve mudança funcional fora de escopo.

## Conformidade com o OPERATING-GUIDE
| Item | Status | Evidência |
|---|---|---|
| Caminho operacional condiz com risco B (`standard_path_B`) | conforme | `PLAN.md` (`risk_category: B`, `operational_path: standard_path_B`) |
| Gates do caminho executados com evidência | conforme | `clean test`, `spotlessCheck`, `build -x test` PASS nesta avaliação |
| Sem secrets/dados sensíveis reais | conforme | Diff não introduz segredos/PII |
| Rastreabilidade por issue | conforme | `issue: #1` em `PLAN.md` e `PROGRESS.md` |
| `is_refactor`/`behavior_parity` aplicável | n/a | Slice não é refatoração |

## Critérios de aceite
| Critério | Resultado | Evidência |
|---|---|---|
| `GET /tags` retorna lista em ordem determinística lexical ascendente | PASS | `TagReadService.xml` com `select name from tags order by name asc`; teste `should_get_all_tags_sorted_ascending` |
| Testes automatizados cobrem ordenação determinística | PASS | `TagsQueryServiceTest` + `TagsApiTest` |
| Sem alteração de auth/schema/estrutura da resposta | PASS | Diff funcional restrito ao mapper de leitura e testes; contrato `{ "tags": [...] }` preservado |
| Documentação e riscos atualizados | PASS | `PROGRESS.md`, `STATE.md`, `REVIEW.md`, `FIX.md` presentes e atualizados |

## Deterministic gates
| Gate | Resultado | Evidência |
|---|---|---|
| build | PASS | `./gradlew build -x test` → `BUILD SUCCESSFUL` |
| unit_tests | PASS | `./gradlew clean test` → `BUILD SUCCESSFUL` |
| integration_tests | PASS | `./gradlew clean test` (suite do projeto incluindo API/infra) → `BUILD SUCCESSFUL` |
| lint | PASS | `./gradlew spotlessCheck` → `BUILD SUCCESSFUL` |

## Achados
| Severidade | Achado | Ação requerida |
|---|---|---|
| Baixa | `build -x test` ainda exibe warning de otimização Gradle/Spotless (implicit dependency), mas sem falha de gate | Tratar em slice de infra/build dedicada, fora desta slice funcional |

## Próximo passo
- Aprovar para closeout (Prompt 05).
