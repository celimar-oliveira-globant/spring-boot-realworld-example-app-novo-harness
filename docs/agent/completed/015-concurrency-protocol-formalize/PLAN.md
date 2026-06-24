# PLAN — 015-concurrency-protocol-formalize

State: PLANNED
issue: #15

Objetivo: Formalizar protocolo de concorrência (claimed_by/claimed_at) em OPERATING-GUIDE.md. Conflito #7 de GUIDE-AUDIT: guia exige `claimed_by/claimed_at` em STATE.md mas prompts não padronizam criação/liberação.

Escopo: Adicionar seção "Protocolo de concorrência mini-protocolo" em OPERATING-GUIDE.md com 3 regras obrigatórias:
1. Planner (início de slice): setar claimed_by=<agente/sessão>, claimed_at=<timestamp> em STATE.md (antes de agir)
2. Cada papel/turno: verificar se claim ativa de outra sessão; se sim, abortar e reportar
3. Closer (fim): limpar/atualizar claim no handoff (liberação)

Critérios de aceite:
- [x] Seção "Concorrência de workspace — mini-protocolo obrigatório" criada em OPERATING-GUIDE.md
- [x] 3 regras obrigatórias documentadas
- [x] Referência em SOURCES.md (OPERATING-GUIDE v2)
- [x] GUIDE-AUDIT marca #7 resolvido
- [x] Sem alteração de código; docs apenas

Risk: A (documentação)
