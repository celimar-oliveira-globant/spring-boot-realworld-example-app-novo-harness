# PLAN — 013-workflow-legacy-cleanup

## Status
State: PLANNED
issue: #14

## Objetivo
Remover bloco legado de workflow (linhas 4–40 de OPERATING-GUIDE.md) que descreve fluxo 00→05 obsoleto, conflitando com v2 (00a/00b, 06, 07). Resolver conflito #5 de GUIDE-AUDIT.

## Escopo
- Incluído:
  - Revisar linhas 4–40 (bloco "Workflow padrao")
  - Remover ou reescrever como "contexto histórico" (não normativo)
  - Manter seção normativa (40+ em diante, já coerente)
  - Validar alinhamento com docs/SEQUENCE-CANONICAL.md
  - Marcar conflito #5 resolvido em GUIDE-AUDIT.md
- Fora de escopo: refatorar código

## Critérios de aceite
- [x] Linhas 4–40 removidas ou marcadas como histórico (não normativo)
- [x] Seção normativa 40+ intacta e coerente
- [x] Alinhamento com SEQUENCE-CANONICAL.md (00a→00b→01→...→05→06→07)
- [x] GUIDE-AUDIT.md marca #5 resolvido

## Risk
risk_category: A (documentação)
operational_path: simple_path (edit + validate + mark resolved)
