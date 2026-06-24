# PLAN — 018-error-resource-serializer-io-safety

State: PLANNED
issue: #18

Objetivo: Melhorar segurança de I/O em ErrorResourceSerializer. Remover try-catch silencioso com printStackTrace(); substituir por loop direto com propagação de IOException.

Escopo:
- Refatorar `serialize()` em `ErrorResourceSerializer`
- Remover try-catch com printStackTrace() dentro do forEach
- Substituir por loop explícito que propaga IOException
- Manter determinismo (LinkedHashMap order)
- Adicionar testes para validar comportamento

Critérios de aceite:
- [x] Refatoração sem mudança comportamental (behavior_parity)
- [x] IOException propagado corretamente
- [x] Tests passam (unit + integration)
- [x] Sem regressão em serialização JSON

Risk: A (refactor I/O, baixo risco)
Operational path: fast_path_A
Budget: budget_max_usd: 6.00
