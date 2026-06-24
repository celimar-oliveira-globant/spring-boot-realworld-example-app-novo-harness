# PLAN — 009-guide-governance-consolidation

## Status
State: PLANNED
issue: #10

## Objetivo
Consolidar e corrigir conflitos de regras normativas em `docs/OPERATING-GUIDE.md`, resolvendo 4 descobertas de alta severidade da auditoria do guia operacional.

## Escopo
- Incluído:
  - Remover/reescrever regra conflitante de commit docs/ (linhas 23 vs 56–57 e 360–362)
  - Unificar política de confirmação (3 casos em guia vs confirmações adicionais exigidas por prompts)
  - Fixar ordem oficial de fim de slice (05→06→07 ou alternativa única)
  - Padronizar nome de artefato AS-IS (`docs/AS-IS.md`) em guia + SEQUENCE.yaml
  - Atualizar referência em `docs/context/SOURCES.md`
  - Validar alinhamento em `harness/07-retro.md` e prompts associados
- Fora de escopo:
  - Criar arquivo GUIA-DE-REFATORACAO.md (pendência média; outra slice)
  - Definir limiares de custo (baixa prioridade; outra slice)
  - Formalizar protocolo de concorrência em todos os prompts (médio; outra slice)
- files_owned:
  - `docs/OPERATING-GUIDE.md` (seções: Workflow, commit/docs, confirmação, máquina de estados, ordenação 05/06/07)
  - `harness/SEQUENCE.yaml` (padronizar AS-IS)
  - `docs/context/SOURCES.md` (referenciar artefato correto)
  - `harness/07-retro.md` (validar alinhamento de ordem)
  - `docs/GUIDE-AUDIT.md` (atualizar com status de correção)

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices (apoio operacional)
- Documento/bloco de origem: `docs/GUIDE-AUDIT.md` (auditoria do OPERATING-GUIDE)
- context_sources: []
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: nenhum (governança/documentação)
- Permissões / minimização / mascaramento: n/a
- Auditoria / log: atualizações em docs/ são registradas por commit
- Revisão humana: recomendado para validação de política de confirmação consolidada

## API, entidades e integrações
- n/a (governança de contrato operacional, não código executável)

## Critérios de aceite
- [x] Remover contradição de "não commitar docs/" vs "commitar docs/ no closeout" — regra reescrita em bloco único
- [x] Unificar política de confirmação em um bloco canônico no guia; prompts referem a essa seção
- [x] Fixar ordem única de fim de slice (05→06→07); Prompt 07 não oferece alternativas
- [x] Padronizar nome de artefato AS-IS (`docs/AS-IS.md`) em `docs/OPERATING-GUIDE.md` + `harness/SEQUENCE.yaml`
- [x] Atualizar `docs/context/SOURCES.md` com referência validada a artefato AS-IS
- [x] Validar alinhamento em `harness/07-retro.md` (retro após integração; sem closeout adicional)
- [x] Atualizações reproduzidas localmente e validadas contra GUIDE-AUDIT findings
- [x] Documentação consolidada em `docs/GUIDE-AUDIT.md` (seção "Correções aplicadas")

## Operational path & risk
- risk_category: A (governança/documentação; sem código executável, secrets ou config sensível)
- operational_path: trivial

## Model Profile
```yaml
risk_category: A
planner:   { tier: light, effort: minimal }
generator: { tier: light, effort: low }
evaluator: { tier: light, effort: low }
reviewer:  { tier: n/a, effort: n/a }
cross_family_evaluator: false
budget_max_usd: 5.00
rationale: |
  Risco A: governança/documentação sem código executável. Caminho trivial: edição de guia
  operacional e sequência. Avaliador usa mesmo modelo (light); validação é conformidade
  de redação vs achados de audit.
```

## Deterministic gates (a rodar antes de READY)
- textual_review (leitura e checagem de coerência do guia reescrito)
- cross_reference_validation (checar SEQUENCE.yaml, SOURCES.md, Prompts 05/06/07 para alinhamento)

## Condições de parada
- Conflito de política não resolvível sem decisão arquitetural adicional (improvável dado audit definiçõe clara)
- Mudança solicitada que contradiz charter ou princípios do OPERATING-GUIDE (improvável)

## Riscos e pendências
- Risco: regra reescrita pode exigir atualização de prompts fora do escopo (mitigado: escopo limita a guia e sequência)
- Risco: ordem 05/06/07 pode afetar expectativa de integrador (mitigado: propagação para harness/06-integrate.md validada)
- Pendência: confirmação humana recomendada após draft (portão leve; não bloqueia)
