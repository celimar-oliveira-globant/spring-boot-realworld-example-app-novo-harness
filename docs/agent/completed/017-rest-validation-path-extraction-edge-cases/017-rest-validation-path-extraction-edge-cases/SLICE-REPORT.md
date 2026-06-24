# SLICE-REPORT — 017-rest-validation-path-extraction-edge-cases

- Data: 2026-06-24 · issue: #17

## O que mudou

Refatoração de robustez em `CustomizeExceptionHandler.getParam()`. Melhorada manipulação de paths com poucos segmentos (1–2 elementos).

- **Arquivo:** `src/main/java/io/spring/api/exception/CustomizeExceptionHandler.java` (linhas 101–107)
- **Antes:** `splits`, lógica ambígua para 1–2 segmentos
- **Depois:** `segments`, edge case explícito: `if (segments.length <= 2) return segments[segments.length - 1]`
- **Comportamento:** Idêntico (behavior_parity validado via testes)

## Evidência

- Critérios de aceite: ✓ PASS
  - Refatoração preserva comportamento observável ✓
  - Unit tests cobrem 1, 2, 3+ segmentos ✓
  - Tests: `should_keep_leaf_field_for_two_segment_property_path`, `should_preserve_single_segment_property_path` ✓
- Gates: ✓ PASS
  - `./gradlew clean test`: BUILD SUCCESSFUL (11s)
  - `./gradlew spotlessCheck`: BUILD SUCCESSFUL (1s)
- Regressão: Nenhuma (validação em testes existentes)
- Conformidade: conforme para fast_path_A

## Riscos residuais

Nenhum (refatoração de baixo risco com cobertura de testes).

## Estado

Slice 017 completa. Produto: exception handling mais robusto.
