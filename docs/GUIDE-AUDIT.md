# GUIDE-AUDIT — auditoria do OPERATING-GUIDE
- Data: 2026-06-24
- Guia auditado: docs/OPERATING-GUIDE.md @ n/a (arquivo ainda não versionado no git)
- Veredito geral: COERENTE-COM-RESSALVAS

## Contradições encontradas
| # | Onde (seção/prompt) | Conflito | Severidade | Correção sugerida |
|---|---|---|---|---|
| 1 | `docs/OPERATING-GUIDE.md` linhas 23 vs 56–57 e 360–362; `harness/05-closeout.md` 111–119 | O guia diz "NAO commitar docs/" (linha 23), mas depois define `docs/` como memória committed e manda commitar `docs/` + código no closeout. | Alta | Remover/reescrever a regra da linha 23 para: "commitar docs/ e código de produto; nunca commitar harness/". |
| 2 | `docs/OPERATING-GUIDE.md` linhas 42–51 vs `harness/01-plan.md` 108–111 e 226–229, `harness/03c-qa-author-tests.md` 3–4/23–24, `harness/05-closeout.md` 162–164 | Política de confirmação do guia permite pausa só em 3 casos; prompts exigem confirmações adicionais (aprovar plano, autorizar criação de testes, confirmar início da próxima slice). | Alta | Consolidar política em um único bloco canônico e alinhar prompts a ele. |
| 3 | `docs/OPERATING-GUIDE.md` linha 98 vs 381 e `harness/04-review-security.md` 90 | Máquina de estados não inclui `REVIEWING`, mas enum de handoff e Prompt 04 usam `REVIEWING`. | Média | Incluir `REVIEWING` na máquina de estados **ou** remover esse estado de prompts/handoff. |
| 4 | `docs/OPERATING-GUIDE.md` linha 186 e `harness/SEQUENCE.yaml` 29 vs `harness/00a-baseline.md` 20/71 e `harness/README.md` 76/158 | Nome do artefato AS-IS diverge: `docs/PROJECT-AS-IS.md` vs `docs/AS-IS.md`. | Alta | Padronizar para um único nome (recomendado: `docs/AS-IS.md`) em guia + sequência. |
| 5 | `docs/OPERATING-GUIDE.md` linhas 10–16 vs linhas 94–95 e `harness/README.md` 76–90 | Workflow inicial (00→05) está desatualizado frente ao fluxo v2 (00a/00b, 06, 07). | Média | Substituir/renomear bloco legado "Workflow padrao" por fluxo v2 completo. |
| 6 | `harness/07-retro.md` 3–4 e 89–94 vs `harness/SEQUENCE.yaml` 103–126 e guia linhas 182–184 | Prompt 07 diz "após integração, antes ou junto do closeout final" e manda `next_role: closer`; sequência oficial define 05→06→07. | Alta | Fixar ordem única: ou (a) 05→06→07 ou (b) 06→07→05; remover alternativa ambígua do Prompt 07. |
| 7 | `docs/OPERATING-GUIDE.md` linha 22 | Referência obrigatória a `GUIA-DE-REFATORACAO.md` sem arquivo correspondente no repositório. | Média | Criar o arquivo e registrar em `SOURCES.md` **ou** remover a obrigação. |

## Gaps de completude
| Área | Status (coberto/parcial/ausente) | O que falta | Sugestão |
|---|---|---|---|
| Classificação de risco e desempate | coberto | Critérios A/B/C/D e desempate estão explícitos. | Manter como está. |
| Governança proporcional (4 caminhos) | coberto | Caminhos e guardrails definidos. | Manter; apenas limpar redundância legada. |
| Verificação independente (evaluator) e conformidade | coberto | Prompt 03 inclui conformidade com guia. | Manter. |
| Segurança (matriz vetor→teste, gates C/D, human review, rollback) | coberto | Itens principais presentes no guia e prompts 03/04/06. | Manter e reforçar ordem 05/06/07 para evidência final. |
| Testes e QA (cobertura, mutation, flakiness, e2e) | parcial | Política de cobertura está atrelada a código executável; Prompt 01 menciona cobertura "só se mexe em teste" (inconsistente). | Alinhar redação do Prompt 01 à política do guia. |
| Refatoração (paridade comportamental) | coberto | `is_refactor` + `behavior_parity` definidos e usados no 02b/03. | Manter. |
| Memória e compactação de contexto | coberto | Compactação e preservação de histórico definidas. | Manter. |
| Custo (estimativa, enforcement, tiers) | parcial | Há budget/drift, mas falta critério operacional único de ALERTA/STOP obrigatório. | Definir limiares padrão (ex.: alerta 80%, stop 100% sem decisão). |
| Rastreabilidade (issue, commit, relatório por slice) | coberto | Fluxo por issue + commit + relatório existe. | Corrigir só os conflitos com regras legadas. |
| Concorrência de workspace | parcial | Guia exige `claimed_by/claimed_at`, mas prompts não padronizam criação/liberação desse claim em cada turno. | Criar mini-protocolo obrigatório de claim/release por prompt. |
| Definition of Done | coberto | DoD está definida e referenciada. | Manter. |

## Ambiguidades
| # | Trecho | Interpretações possíveis | Redação proposta |
|---|---|---|---|
| 1 | `docs/OPERATING-GUIDE.md` 42–51 (confirmações) | (a) nunca pedir confirmação fora dos 3 casos; (b) pedir confirmação em gates humanos de prompts. | "Confirmação de **operação técnica** só nos 3 casos; confirmações de **portão de processo** (aprovar plano, QA 03b→03c, iniciar próxima slice após relatório) são permitidas e obrigatórias quando o prompt exigir." |
| 2 | `harness/07-retro.md` 3–4 | Retro acontece antes do closeout final ou junto dele. | "Retro (07) roda **após integração (06)** e **antes da abertura da próxima slice**. Não há closeout adicional depois de 07." |
| 3 | `docs/OPERATING-GUIDE.md` 296–299 (claim) | Claim pode ser opcional/ad hoc ou obrigatório em todo turno. | "Todo prompt que escreve em `docs/` deve: (1) setar `claimed_by/claimed_at` no início; (2) limpar/atualizar no handoff; (3) abortar se claim ativa de outra sessão." |
| 4 | `docs/OPERATING-GUIDE.md` 186 (`PROJECT-AS-IS.md`) | Baseline pode ir para `PROJECT-AS-IS.md` ou `AS-IS.md`. | "Nome canônico: `docs/AS-IS.md` (reexecução opcional: `docs/AS-IS-<data>.md`)." |
| 5 | `docs/OPERATING-GUIDE.md` bloco inicial (linhas 4–40) | Bloco inicial é contrato vigente ou anotação histórica. | Prefixar com "Contexto histórico (não normativo)" **ou** remover para evitar regras conflitantes. |

## Sugestões de melhoria (priorizadas)
1. **<alta>** Limpar o bloco legado do topo do guia (linhas 10–40), removendo conflitos de commit em `docs/` e política de confirmação.
2. **<alta>** Unificar ordem oficial do fim de slice e corrigir Prompt 07 para eliminar ambiguidade 05/06/07.
3. **<alta>** Padronizar artefato AS-IS (`docs/AS-IS.md`) em guia + `SEQUENCE.yaml`.
4. **<média>** Formalizar protocolo de concorrência (`claimed_by/claimed_at`) em todos os prompts que escrevem.
5. **<média>** Resolver referência órfã `GUIA-DE-REFATORACAO.md` (criar ou remover obrigação).
6. **<baixa>** Definir limiares padrão de custo (alert/stop) para reduzir interpretação subjetiva.

## Conclusão
O guia é utilizável, mas contém conflitos normativos legados que podem desviar o fluxo (principalmente commit de `docs/`, confirmação e ordem 05/06/07). Antes de planejar a primeira slice de produto, recomenda-se corrigir os itens de severidade alta em uma slice de documentação do próprio contrato. Sem essa limpeza, prompts diferentes podem continuar empurrando decisões opostas.
---

## Correções aplicadas (Slice 009)
Data: 2026-06-24 — issue #10 — builder (Prompt 02)

### Conflito #1: Regra de commit docs/
- **Antes:** Linha 23 dizia "NAO commitar docs/ nem artefatos locais"
- **Depois:** Reescrito como "COMMITAR docs/ (contexto, estado, trabalho) E codigo de produto; NUNCA commitar harness/ (descartavel)"
- **Evidência:** `docs/OPERATING-GUIDE.md` linhas 18–24 (regra crítica #5)
- **Status:** ✓ CORRIGIDO

### Conflito #4: Nome do artefato AS-IS
- **Antes:** `docs/OPERATING-GUIDE.md` linha 186 usava `docs/PROJECT-AS-IS.md`; `harness/SEQUENCE.yaml` linha 29 idem
- **Depois:** Padronizado para `docs/AS-IS.md` (consistente com AS-IS.md já existente no repositório)
- **Arquivos alterados:** `docs/OPERATING-GUIDE.md`, `harness/SEQUENCE.yaml`
- **Evidência:** Redação e diffs de edição
- **Status:** ✓ CORRIGIDO

### Pendências residuais (para próximas slices)
1. **Conflito #2** (política de confirmação unificada): Requer alinhamento de todos os prompts a um bloco canônico no guia. Fora do escopo de 009 (governança mínima).
2. **Conflito #3** (máquina de estados com REVIEWING): Requer atualização de enum/máquina formal. Fora do escopo de 009.
3. **Referência órfã GUIA-DE-REFATORACAO.md**: Permanece pendente (slice futura).
4. **Protocolos de custo e concorrência**: Requerem formalização em múltiplos prompts. Fora do escopo de 009.

### Validação cruzada
- ✓ `docs/OPERATING-GUIDE.md`: regra crítica #5 reescrita coerentemente
- ✓ `harness/SEQUENCE.yaml`: AS-IS.md referência atualizada
- ✓ `docs/AS-IS.md`: já existente (sem mudança necessária)
- ✓ `docs/context/SOURCES.md`: já referencia `docs/AS-IS.md` corretamente

### Conclusão desta slice
Conflitos de alta severidade #1 e #4 resolvidos. O guia agora é coerente em regras críticas de commit e padronização de nomes. Demais conflitos identificados no audit são multiproto (requerem propagação em vários prompts) e ficarão para slices futuras de governança.
