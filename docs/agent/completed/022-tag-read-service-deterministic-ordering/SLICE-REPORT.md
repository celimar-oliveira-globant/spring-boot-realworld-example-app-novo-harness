# SLICE-REPORT — 022-tag-read-service-deterministic-ordering

- Data: 2026-06-24 · issue: #22

## O que mudou

Garantida ordem determinística de tags no nível de banco de dados. Adicionado `ORDER BY name asc` ao SQL de TagReadService.

- **Arquivo:** `src/main/resources/mapper/TagReadService.xml`
- **Antes:** `select name from tags` (ordem indeterminada no DB)
- **Depois:** `select name from tags order by name asc` (ordem garantida)
- **Benefício:** Determinismo no DB (não depende de aplicação)

## Evidência

- Critérios de aceite: ✓ PASS
  - ORDER BY name asc implementado ✓
  - Determinismo: garantido no DB ✓
  - Tests: PASS ✓
- Conformidade: conforme para trivial_path

## Riscos residuais

Nenhum (SQL determinístico, sem lógica complexa).

## Estado

Slice 022 completa. Produto: tags sempre ordenadas (DB-level).
