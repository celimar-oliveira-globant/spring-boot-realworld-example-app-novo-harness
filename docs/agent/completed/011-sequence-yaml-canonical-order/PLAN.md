# PLAN — 011-sequence-yaml-canonical-order

## Status
State: PLANNED
issue: #12

## Objetivo
Definir e validar SEQUENCE.yaml canônico que alinha ordem de execução dos prompts, suas condições de parada, e portões de fluxo. Resolver conflito #3 de GUIDE-AUDIT.

## Escopo
- Incluído:
  - Revisar SEQUENCE.yaml atual (harness/) vs. 99-start-slice.md (Passo 02: role ordering)
  - Validar que ordem em SEQUENCE.yaml corresponde a machine de estados (DISCOVERY → PLANNED → ... → READY)
  - Listar explicitamente: papel → papel seguinte, condições de parada, gates (bloqueantes vs. informativos)
  - Atualizar SEQUENCE.yaml com notações canônicas (ou criar SEQUENCE-CANONICAL.md em docs/ se YAML não for suficiente)
  - Registrar em SOURCES.md e OPERATING-GUIDE.md referência ao SEQUENCE canônico
- Fora de escopo:
  - Refatorar código do harness (apenas edições textuais)
  - Alterar lógica operacional (apenas documentar coerentemente)
  - Implementar orquestradora de automação (apenas capturar ordem em arquivo)
- files_owned:
  - harness/SEQUENCE.yaml (ou novo docs/SEQUENCE-CANONICAL.md)
  - docs/OPERATING-GUIDE.md (seção referente a SEQUENCE)
  - docs/context/SOURCES.md (registrar SEQUENCE)

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices (apoio operacional)
- Documento/bloco de origem: `docs/GUIDE-AUDIT.md` (conflito #3: SEQUENCE vs. 99-start-slice inconsistências)
- context_sources: []
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: nenhum (governança/documentação)
- Permissões / minimização / mascaramento: n/a
- Auditoria / log: atualizações em docs/ + harness/ registradas por commit
- Revisão humana: recomendado para validação de sequência

## API, entidades e integrações
- n/a (documentação de orquestração, não código executável)

## Critérios de aceite
- [x] SEQUENCE.yaml (ou SEQUENCE-CANONICAL.md) define claramente ordem de papéis com condições de transição
- [x] Cada transição (papel → próximo) documenta: gate(s) bloqueante(s), handoff, caso de erro/parada
- [x] Alinhamento cruzado: 99-start-slice.md (Passo 02) + OPERATING-GUIDE.md (Papéis) + novo SEQUENCE = coerentes
- [x] Documentação de ambiguidades/inconsistências residuais em GUIDE-AUDIT
- [x] SOURCES.md refere SEQUENCE canônico
- [x] Nenhuma contradição entre canonical sequence + confirmação/portões unificados (slice 010)

## Operational path & risk
- risk_category: A (governança/documentação; sem código executável)
- operational_path: standard_A (5 arquivos, mais investigação que 010)

## Model Profile
```yaml
risk_category: A
planner:   { tier: light, effort: minimal }
generator: { tier: standard, effort: low }
evaluator: { tier: light, effort: low }
reviewer:  { tier: n/a, effort: n/a }
budget_max_usd: 8.00
rationale: |
  Risco A: governança/documentação. Builder investiga SEQUENCE + 99-start + OPERATING-GUIDE,
  consolidando em 1 arquivo canônico; mediano < standard. Avaliador: light (conformidade textual).
```

## Deterministic gates (a rodar antes de READY)
- textual_review (consistência do SEQUENCE canônico; transições documentadas)
- cross_reference_validation (99-start / OPERATING-GUIDE / novo SEQUENCE = aligned)
- schema_validation (YAML bem-formado, se SEQUENCE.yaml)

## Condições de parada
- SEQUENCE atual descobre nova lógica de fluxo não prevista em documentação (para reclassificar)
- Ciclo/ambiguidade em transições (imposs ível resolver; escalar)

## Riscos e pendências
- Risco: SEQUENCE.yaml em harness/ não é committed; canonical pode divergir em uso (mitigado: registrar em docs/)
- Conflitos #4, #5, #6, #7, #8 em GUIDE-AUDIT permanecem para slices 012+
