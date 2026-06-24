# FIX REQUEST — 001-tags-deterministic-order

## Motivo
A avaliação encontrou `CONCERNS`: os critérios de aceite funcionais estão atendidos, porém os gates obrigatórios `build` e `lint` falham por incompatibilidade de toolchain (`spotlessJava`/`google-java-format` com `IllegalAccessError`).

## Escopo permitido
- Permitido:
  - Ajustes de execução/toolchain para viabilizar `spotlessCheck` e `build` nesta branch/sessão.
  - Atualização de evidência em `PROGRESS.md` e revalidação dos gates.
- Não permitido:
  - Alterar comportamento funcional da slice `/tags`.
  - Expandir para mudanças arquiteturais não relacionadas.

## Correções exigidas
- Garantir execução de `lint` e `build` sem erro de `spotlessJava` (ex.: ambiente JDK compatível com plugin configurado).
- Reexecutar os gates bloqueados e registrar resultado literal:
  - `./gradlew spotlessCheck`
  - `./gradlew build -x test`
- Se a correção exigir mudança estrutural fora do escopo desta slice (ex.: alteração global de build/plugin), registrar bloqueio explícito e devolver para replanejamento da slice de infra.

## Evidência requerida
- Sensor/comando:
  - `./gradlew spotlessCheck`
  - `./gradlew build -x test`
- Resultado esperado:
  - Ambos **PASS** (sem `IllegalAccessError` em `spotlessJava`)
