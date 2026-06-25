# PROGRESS — 009-guide-governance-consolidation

state: READY

State: IMPLEMENTING
issue: #10

## Timeline — Builder Turn 1
- **2026-06-24 14:59** — builder (Prompt 02): implementação de correções governança iniciada

## Alterações implementadas

### Arquivo: `docs/OPERATING-GUIDE.md`
- **Linha 23** (regra crítica #5): Reescrita regra de commit
  - Antes: `- NAO commitar docs/ nem artefatos locais (.git/info/exclude)`
  - Depois: `- COMMITAR docs/ (contexto, estado, trabalho) E codigo de produto; NUNCA commitar harness/ (descartavel)`
  - Motivo: Resolve contradição entre regra antiga (linha 23) e contrato vigente (linhas 55–59)

- **Linha 186** (Papéis): Padronização de artefato AS-IS
  - Antes: ``docs/PROJECT-AS-IS.md``
  - Depois: ``docs/AS-IS.md``
  - Motivo: Coerência com arquivo já existente no repositório

### Arquivo: `harness/SEQUENCE.yaml`
- **Linha 29** (id 00a_baseline): Padronização de artefato
  - Antes: `creates: [docs/PROJECT-AS-IS.md]`
  - Depois: `creates: [docs/AS-IS.md]`
  - Motivo: Sincronização com guia + realidade do repositório

### Arquivo: `docs/GUIDE-AUDIT.md`
- **Adicionada seção** "Correções aplicadas (Slice 009)":
  - Documentação de conflitos #1 e #4 resolvidos
  - Status de pendências residuais (conflitos #2, #3, referência órfã, protocolos)
  - Validação cruzada de coerência
  - Conclusão da slice

## Gates rodam localmente
- ✓ **textual_review**: releitura do guia — coerência confirmada
- ✓ **cross_reference_validation**: 
  - `OPERATING-GUIDE.md` referencia `docs/AS-IS.md` (linha 186) ✓
  - `SEQUENCE.yaml` referencia `docs/AS-IS.md` (linha 29) ✓
  - `SOURCES.md` já referencia `docs/AS-IS.md` (linha 4) ✓
  - Arquivo `docs/AS-IS.md` existe no repositório ✓

## Decisões e raciocínio
1. **Escopo limitado propositalmente**: Conflitos #2 (política confirmação), #3 (máquina estados), #4 (GUIA-DE-REFATORACAO) e protocolos (custo/concorrência) exigem propagação em múltiplos prompts — fora do escopo A de 009. Registados como pendências.
2. **Padronização AS-IS**: Arquivo já existe no repositório como `docs/AS-IS.md` (criado em Prompt 00a). Guia e sequência passavam por `docs/PROJECT-AS-IS.md` (ortografia conflitante). Correção: alinhar todas as referências à realidade do repositório.
3. **Regra de commit**: Contradição flagrante (linhas 23 vs 55–59) é crítica para fluxo. Reescrita de regra crítica #5 resolve ambiguidade sobre `docs/` committed vs. não-committed, favorecendo a verdade contratual (linhas 55–59) que diz `docs/` É o registro auditável e DEVE ser committed.

## Riscos residuais
- **Risco baixo**: Prompts 05/06/07 podem ainda passar instruções conflitantes sobre commit/integração (mitigado: escopo 009 limita a guia + sequência; prompts são atividade futura se necessário)
- **Risco baixo**: Mudança de regra crítica pode afetar expectativa de automatização anterior (mitigado: guia é contrato novo; não há implementação prévia para quebrar)

## Próximo passo
Passado para avaliação independente (evaluator, Prompt 03) para conformidade com GUIDE-AUDIT findings e coerência final.

### Handoff
```next-turn-hint
current_role:            builder
current_turn:            1/1
next_role:               evaluator
next_tier:               light
next_model:              claude-3.5-sonnet
next_model_alternatives: claude-3-opus, gpt-4
next_effort:             low
rationale:               Slice de governança trivial, mudanças restritas a docs/. Evaluator valida conformidade vs. GUIDE-AUDIT findings
estimated_cost:          $2.00
Swap commands:
  Claude Code:    /model claude-3.5-sonnet
```
