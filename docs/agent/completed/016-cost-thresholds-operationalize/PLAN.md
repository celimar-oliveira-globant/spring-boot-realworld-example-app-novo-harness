# PLAN — 016-cost-thresholds-operationalize

- risk_category: A

State: PLANNED
issue: #16

Objetivo: Operacionalizar cost enforcement. Conflito #8 de GUIDE-AUDIT: guia menciona `budget_max_usd` e enforcement mas falta limiares concretos (ALERT/STOP).

Escopo: Adicionar seção "Operacionalização de custo: limiares padrão" em OPERATING-GUIDE.md:
- 80% de budget_max_usd → ALERT (log warning em PROGRESS.md, continua normalmente)
- 100% de budget_max_usd → STOP (halt workflow; exige decision from user)
- Exceção: user pode passar `force_continue=true` para sobrescrever STOP (com evidência em PROGRESS.md)

Critérios de aceite:
- [x] Seção "Operacionalização de custo" criada em OPERATING-GUIDE.md
- [x] Limiares 80% (ALERT) e 100% (STOP) documentados
- [x] Exceção force_continue registrada
- [x] GUIDE-AUDIT marca #8 resolvido

Risk: A (documentação)
