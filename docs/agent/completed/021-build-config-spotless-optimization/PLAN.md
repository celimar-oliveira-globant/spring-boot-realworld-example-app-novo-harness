# PLAN — 021-build-config-spotless-optimization

- risk_category: B

State: PLANNED
issue: #21

Objetivo: Otimizar configuração do Spotless em build.gradle. Restringir escopo de verificação apenas a `src/`.

Escopo:
- Alterar target de spotless: project.rootDir → src
- Remover exclusões desnecessárias (build/ patterns)
- Acelerar verificação de formatação
- Validar que spotlessCheck ainda funciona corretamente

Critérios de aceite:
- [x] Spotless target otimizado
- [x] spotlessCheck ainda PASS
- [x] Build mais rápido

Risk: A (config apenas)
Operational path: trivial_path
Budget: budget_max_usd: 3.00
