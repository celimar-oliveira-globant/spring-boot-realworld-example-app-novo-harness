# Guia Operacional do Agente
# Guia de Operacao do Agente

---

## **Nota Histórica** *(linhas 4–40 consolidadas em SEQUENCE-CANONICAL.md)*

A versão anterior deste guia continha um bloco "Workflow padrao" (linhas 4–40) descrevendo o fluxo v1 (Prompts 00→05). Esse bloco foi movido para o arquivo histórico e substituído por referência canônica: **`docs/SEQUENCE-CANONICAL.md`**. O guia atual (seção normativa abaixo) descreve o fluxo v2 (Prompts 00a/00b, 01–07) com papéis, gates, e máquina de estados completa. Vide SEQUENCE-CANONICAL.md para diagrama completo e rastreabilidade.

---

## Politica unificada de confirmacao e portoes de processo (vale para todos os papeis)
**Confirmação de OPERAÇÃO TÉCNICA** — NUNCA solicite para low/medium risk:
- navegacao, leitura, ls, grep, find, mv/cp/mkdir, git * (qualquer), build/test, edição de arquivos

Execute-as direto e registre o resultado.

**Pausas (ask_user) — OPERAÇÕES/DECISÕES BLOQUEANTES** permitidas em TRÊS casos:
  1. **pending_decision arquitetural** — escolha de stack/framework/banco (exige usuário decidir)
  2. **red flag de segurança** em slice C/D (defesa: para validação humana antes de prosseguir)
  3. **aprovação humana obrigatória** (C/D antes do merge, ou compliance workflow específico)

**PORTÕES DE PROCESSO** — confirmações de fluxo (NOT bloqueantes a nível de operação, mas de decisão):
  - **Aprova plano (Prompt 01)**: planner propõe PLAN.md; usuário diz "ok" ou pede mudança antes de implementar
  - **Autoriza testes (Prompt 03b→03c)**: qa mede cobertura, para, aguarda confirmação antes de criar testes
  - **Continua próxima slice (Prompt 05 + 99)**: closeout apresenta SLICE-REPORT, aguarda 10s por intervenção; sem resposta, avança

Regra: **dentro de um turno, execute operações técnicas direto. Entre turnos/slices, pausas de processo são formalizadas no handoff como `next_role` e aguardam contexto novo (usuário lê, toma ação).**



Contrato de trabalho deste projeto. Todo agente lê este arquivo **antes de agir**.
Este arquivo vive em `docs/OPERATING-GUIDE.md` e **é committed** no repositório —
ele e o restante de `docs/` são o registro auditável do projeto. Os prompts do
framework vivem em `harness/` (pasta auxiliar, **não committed**); o agente os lê
para saber como operar, mas eles não fazem parte do produto.

## Layout de arquivos (importante)

```
<projeto>/
  docs/                      # COMMITTED — registro do projeto
    OPERATING-GUIDE.md       # este arquivo
    STATE.md                 # ponteiro de retomada + claim de concorrência
    context/
      CHARTER.md             # identidade, personas, objetivo, escopo, risco, stack, DoD
      SOURCES.md             # PRD/ADR/SKILL/DESIGN/roadmap registrados (autoridade + status)
      ROADMAP.md             # fases: objetivo, escopo, critérios, sequência
      LESSONS.md             # aprendizado acumulado, lido pelo planner
    agent/work/<slug>/             # slice em andamento
      PLAN.md  DESIGN-SKETCH.md  PROGRESS.md  REVIEW.md  FIX.md
    agent/completed/<slug>/        # slices concluídas (movidas no closeout)
  harness/                   # NÃO COMMITTED (.gitignore) — framework descartável
    00-onboard.md … 07-retro.md, 99-start-slice.md, SEQUENCE.yaml, README.md
```

Regras de caminho, sem exceção:
- Todo artefato de **contexto, estado e trabalho** é criado/atualizado em `docs/`.
- O agente **lê** os prompts de `harness/`, mas **nunca** escreve lá nem cria
  memória de projeto lá. `harness/` é removível sem perda de histórico.
- O agente **não commita** `harness/`. Garanta que `harness/` está no
  `.gitignore` (o Prompt 00 cria essa entrada na primeira ativação).

## Invocação manual de prompts (sem colar conteúdo)
- Após o `00-onboard.md`, o usuário pode chamar o próximo turno por referência:
  `harness/<arquivo>.md`, "Prompt 01", "rodar 03", ou equivalente.
- Nesses casos, o agente **não** deve pedir para colar o prompt inteiro; deve
  abrir o arquivo local correspondente em `harness/` e executar o turno.

## Ciclo de trabalho
Understand → Audit → Plan → (Design) → Implement/Refactor → Verify → Fix →
Document → Integrate → Retro → Report

## Máquina de estados de uma unidade de trabalho
DISCOVERY → PLANNED → IMPLEMENTING → EVALUATING → FIXING → DOCUMENTING → READY

## Princípios inegociáveis
1. Não inventar contexto de produto, stack, entidades, APIs ou fases.
2. Nunca usar segredos, credenciais ou dados sensíveis reais em código, testes,
   exemplos ou docs.
3. Manter o trabalho pequeno, auditável e reversível.
4. Toda mudança de comportamento tem critério de aceite + verificação.
5. Todo fluxo sensível identifica dados, permissões, auditabilidade e
   necessidade de revisão humana ANTES da implementação.
6. Atualizar a documentação quando comportamento, arquitetura, API, postura de
   segurança ou processo mudarem.
7. Nunca fabricar APIs, padrões ou comportamentos. Incerteza > invenção.
8. Refatoração preserva o comportamento observável. Mistura de refatoração com
   mudança de comportamento no mesmo diff é proibida — separe em duas slices.
9. "Passou local" não é "passou integrado". Integração é verificada (Prompt 06).

## Knowledge Verification Chain (ordem obrigatória ao decidir algo técnico)
1. Codebase — código, convenções e padrões já em uso no projeto.
2. Docs & context inputs — README, docs/, ADRs, PRDs, e os context inputs
   registrados (DESIGN.md / SKILL.md), quando presentes. Quando presentes, são
   autoritativos: siga-os e não os contrarie.
3. Resolvedor de biblioteca (Context7 MCP ou equivalente) — resolver o ID da
   lib e consultar a API/padrões atuais.
4. Busca na web — docs oficiais, fontes confiáveis.
5. Sinalizar como incerto — "não tenho certeza sobre X; aqui está meu raciocínio,
   mas verifique". Nunca pular para o passo 5 se 1–4 estão disponíveis.

**Evidência da cadeia (obrigatório).** Para decisões sobre **API externa / lib /
contrato**, registre a fonte em `PROGRESS.md`: o nível da cadeia usado e o
identificador (versão da doc, ID do Context7, URL). "Verifiquei" sem citação
**não** é evidência — o evaluator trata decisão sobre API externa sem fonte como
achado de severidade média (PARTIAL no critério correspondente).

## Categorias de risco (fronteira de confiança — classifique de D para A; o primeiro match vence)
- D — Crítico: primitivas de cripto, KDF, assinatura, RNG de segurança, dados
  regulados (LGPD/GDPR/HIPAA/PCI) em runtime, protocolos de segurança próprios.
- C — Alto: auth/authz, sessões/tokens/JWT/OAuth, secrets, path traversal,
  upload/download com input, shell/subprocess com input, SSRF, CORS/CSP,
  webhook signatures, rate-limiting de endpoint público. Remover um check de
  segurança também é C.
- B — Médio: muda estado ou comportamento visível ao usuário sem cruzar
  fronteira de auth/secrets — escrita em path fixo validado, API interna
  autenticada, migração não-destrutiva, paginação/filtro.
- A — Baixo: não cruza fronteira de confiança — refatoração interna, docs,
  config local de path fixo, parsing de formato público, geração estática.

Regra: classifique pela MAIOR categoria. Na dúvida entre duas, escolha a maior.
**Risco é o que cruza a fronteira de um atacante — não número de linhas.**
Refatoração **herda a categoria da fronteira que toca**: refatorar um validador
de JWT é C mesmo que sejam 3 linhas; um refactor de 500 linhas de CSS é A.

## Sinais de escopo (o planner avalia e registra no PLAN)
Binários: `touches_executable_code`, `touches_build_deploy`,
`touches_secrets_config`, `touches_auth`, `touches_database`,
`touches_external_integration`, `touches_money_movement`,
`touches_crypto_primitive`, `touches_regulated_data`.

Adicionais:
- `is_refactor: <true|false>` — melhora estrutura **sem** mudar comportamento
  observável. Se `true`, a implementação usa o **Prompt 02b** e o PLAN ganha o
  gate `behavior_parity` (obrigatório) + um critério de aceite de não-regressão.
- `depends_on: [<slug>, ...]` — slices de que esta depende; o integrator (06)
  bloqueia se uma dependência não estiver integrada.
- `is_feature_plus_refactor` — **proibido**: divida em duas slices.

## Caminhos operacionais (governança proporcional ao risco)
- trivial_path — docs/typo/comentário/formatação, sem tocar código executável:
  declaração de 3 itens em PROGRESS.md (descrição + risk_category: A +
  atestação de que não toca código/config sensível).
- fast_path_A — Categoria A, mudança pequena: PLAN.md mínimo + PROGRESS.md +
  Model Profile mínimo.
- standard_path_B — Categoria B: Pre-Flight Checklist completo + PLAN.md +
  Model Profile + PROGRESS.md + REVIEW.md (avaliação).
- strict_path_C_D — Categoria C/D: tudo do B MAIS security_reviewer +
  >= 4 deterministic gates VÁLIDOS + matriz vetor→teste + human_review: required
  + ADR quando arquitetural (D exige especialista). LLM nunca é a autoridade
  final em D.

Guardrails: qualquer sinal de C/D força strict_path. Se o diff sair do escopo do
caminho declarado, aborte e reclassifique. Na dúvida, escale para o caminho mais
alto.

## Papéis (um papel por turno, com handoff entre turnos)

**Ordem canônica e completa:** Veja `docs/SEQUENCE-CANONICAL.md` para tabelas detalhadas, condições de entrada, saídas e gates de cada papel.

Resumo:
planner → (designer) → builder | refactorer → evaluator →
(reviewer/security_reviewer) → (qa → test_author) → (e2e_tester) →
closer → integrator → doc_gardener.
Papéis entre parênteses são condicionais. Antes do primeiro planner, em projeto
existente, o **surveyor** (Prompt 00a) gera o baseline AS-IS (`docs/AS-IS.md`) —
somente leitura. O **guide_auditor** (Prompt 00b) audita a coerência do próprio
OPERATING-GUIDE e emite `docs/GUIDE-AUDIT.md` antes do trabalho começar.

Sobre QA (qa, test_author, e2e_tester): são **condicionais** e complementam — não
substituem — o gate `coverage_check`. Slices simples seguem só com o gate. Quando
cobertura/qualidade de teste merecem atenção, o **qa** (03b) mede e **para antes
de criar testes** (portão humano); após confirmação, o **test_author** (03c) cria,
executa e revalida; o **e2e_tester** (03d) cobre jornadas ponta-a-ponta de alto
valor. Meta de cobertura é **flexível e orientada a risco** — o sinal de qualidade
é o `mutation_test`, não a porcentagem.

## Extensões futuras — agentes "quando a dor aparecer" (NÃO implementados)
O harness é deliberadamente enxuto: cada papel novo é peso permanente, então só se
adiciona um quando a necessidade for concreta e recorrente. Estes agentes estão
**previstos mas não implementados** — este guia é o lugar onde a decisão de
adicioná-los deve ser registrada. Adicione um apenas quando o problema que ele
resolve já estiver doendo:

- **dependency_auditor** — periódico (não por slice): dependências desatualizadas,
  CVEs, licenças. Dispare quando o projeto passar a ter superfície de supply chain
  relevante ou exigência de compliance. (O baseline 00a só transcreve versões; não
  julga — este agente julgaria.)
- **observability_checker** — pós-deploy: confere se a slice se comportou como
  previsto em runtime (logs, métricas, erro rate). Dispare quando houver ambiente
  observável e o custo de regressão em produção justificar fechar esse loop.
- **dead_code_gardener** — periódico: código morto, slices abandonadas em `agent/work/`,
  TODOs antigos, cobertura que degradou. Dispare quando a dívida acumulada começar
  a atrapalhar.
- **load_benchmarker** — para slices em caminho quente: baseline de latência/
  throughput com comparação (análogo ao `behavior_parity`, mas para performance).
  Dispare quando houver requisito de performance explícito.
- **a11y_i18n_reviewer** — frontend: acessibilidade e internacionalização além do
  que o reviewer de design cobre. Dispare quando o produto tiver requisito formal
  de a11y ou múltiplos idiomas.

Regra para adicionar: o agente vira um Prompt numerado próprio, lê este guia,
emite `### Handoff`, e entra no mapa do orquestrador (`99-start-slice.md`) e no
`SEQUENCE.yaml`. Periódicos (deps, dead-code) rodam **fora** do ciclo por slice,
sob demanda ou agendados — não pesam em cada slice.

## Deterministic gates (verificações reproduzíveis, sem julgamento de LLM)
Válidos: build, unit_tests, integration_tests, contract_tests, lint, typecheck,
coverage_check, secret_scan, exfil_scan, migration_dry_run, schema_validation,
sast, dep_scan, policy_check, negative_tests, config_review, audit_log_check,
fuzz, constant_time_check, behavior_parity, mutation_test, flakiness_check.

NÃO contam (não são verificações reproduzíveis): "LLM revisou", "parece correto",
"sem erro visual", "review informal", "modelo confiante", "auto-check do agente".
Em C/D são exigidos **>= 4 gates VÁLIDOS**.

Gates específicos:
- `behavior_parity` — refatoração: a saída observável antes (`before`) deve ser
  idêntica à de depois (`after`). Obrigatório quando `is_refactor: true`.
- `mutation_test` — C/D (ou amostral em B sensível): se a suíte não falha quando
  se injeta mutação no código, os testes são decorativos. Defina mutation score
  mínimo no PLAN para módulos sensíveis. "Testes passam" não prova que testam.
- `flakiness_check` — roda a suíte N vezes (ex.: 5); teste não-determinístico
  **bloqueia** em vez de aprovar. Gate que passa 80% das vezes é gate vermelho.

## Matriz vetor → teste (rastreabilidade — obrigatória em C/D)
"`>= 4 gates`" mede quantidade, não cobertura. Em C/D, o PLAN contém uma matriz
ligando cada vetor de ameaça a um teste específico:

```markdown
## Threat matrix (C/D)
| Vetor de ameaça | Negative test que o cobre | Gate |
|---|---|---|
| path traversal (../) | test_rejects_parent_path | negative_tests |
| token expirado/forjado | test_rejects_expired_jwt | negative_tests |
```

O evaluator (03) e o security_reviewer (04) verificam que **todo vetor tem teste
que roda e passa**. Vetor sem teste = FAIL. Quatro gates que não cobrem os vetores
que importam **não** satisfazem o C/D.

## Política de cobertura (dispara ao adicionar código, não ao mexer em teste)
Se a slice adiciona/altera código executável → defina cobertura mínima por módulo
afetado (+10pp para auth/secrets/pagamentos/compliance) e rode `coverage_check`.
`N/A` só quando a slice não toca código executável.

## Rollback de primeira classe (C/D com estado persistente)
"Reversível" é asserção, não verificação. Slices C/D que tocam migração de banco,
config de produção ou estado persistente exigem no PLAN:

```markdown
## Rollback plan (C/D com estado)
- Como reverter:
- Gate que comprova: migration_dry_run (aplica E reverte sem perda)
- Janela/risco de reversão:
```

O integrator (06) **testa** o rollback. Sem rollback testado, C/D não integra.

## Tiers de modelo (mapeie aos seus modelos reais) + effort
- light — rápido/barato: edições mecânicas, formatação, buscas simples.
- standard — equilibrado: maior parte de planejamento, geração e avaliação.
- deep — raciocínio de fronteira: planejar/revisar código sensível, segurança,
  design difícil.
effort: minimal | low | medium | high | xhigh | max.
Para slices fact-sensitive (regulação, recall de API, fatos públicos), o
evaluator/reviewer deve ser de FAMÍLIA de modelo diferente do generator.

## Custo (enforcement)
`budget_max_usd` sem enforcement vira documentação. O closeout (05) **sempre**
registra `custo_real` vs. `budget_max_usd`. Estouro > 50% exige a nota `## Drift`
em PROGRESS.md. *"Mais agentes" nunca resolve estouro — disciplina de tier resolve.*

## Concorrência de workspace
`docs/STATE.md` é ponteiro único; dois agentes na mesma slice = corrida de escrita.
Antes de iniciar um turno, o agente **reivindica** a slice em `STATE.md`:
`Active work unit`, `claimed_by: <agente/sessão>`, `claimed_at: <timestamp>`. Se
já houver claim ativa de outra sessão na mesma slice, **pare** e reporte em vez de
escrever por cima. Liberação acontece no handoff.

## Aprendizado entre slices
O doc_gardener (07) destila lições em `docs/context/LESSONS.md`. O planner (01) lê
esse arquivo no início e cita qualquer `planner_hint` aplicável ao classificar a
próxima slice. Lição cara e recorrente vira **regra** (edita este guia ou um
SKILL.md), não só dica.

## Memória e compactação de contexto
A memória do projeto é **externalizada em arquivos** (`docs/`), não na janela de
contexto: `STATE.md` (ponteiro de retomada), `PROGRESS.md` por slice, `LESSONS.md`
(aprendizado), e os blocos `### Handoff`. Isso sobrevive entre sessões, pessoas e
modelos, e é auditável — uma retomada relê o disco, não depende de contexto vivo.

Para o contexto relido a cada turno não inchar:
- **Compactação ao fim da slice (closeout, Passo 3b).** O `PROGRESS.md` é resumido
  para uma forma curta (resumo + último handoff); o histórico bruto é preservado
  em `PROGRESS.full.md`, que acompanha a slice para `agent/completed/`. **Nada é
  perdido** — só sai do caminho de leitura cotidiano. A compactação ocorre apenas
  no encerramento; durante a slice a timeline cresce normalmente.
- **Histórico por slice.** Cada slice mantém o próprio histórico; não se mistura
  histórico entre slices.
- **Leitura mínima.** Em retomada, prefira ler o último `### Handoff` e o resumo
  do `PROGRESS.md`, não a timeline inteira. O `PROGRESS.full.md` só é aberto quando
  se investiga uma decisão específica daquela slice.

## Definition of Done
Concluído só quando: critérios de aceite satisfeitos, verificações aplicáveis
executadas, evidência registrada, documentação atualizada quando necessário,
riscos residuais explícitos e — quando aplicável — slice **integrada** (06).

## Relatório por slice e avanço contínuo controlado
Toda slice termina com um **relatório final** consolidado em
`docs/agent/completed/<slug>/SLICE-REPORT.md` (o que mudou, evidência, conformidade,
QA, integração, riscos, próximo passo). O closeout (05) **apresenta o caminho do
relatório e abre uma janela de 10 segundos para intervenção humana**. Se houver
interação humana, siga a orientação recebida. Se **não** houver interação no prazo,
o ciclo engata automaticamente a próxima etapa/slice. O fluxo só para por erro,
bloqueio, `done` ou guarda de concorrência.

## Git e GitHub Issues (rastreabilidade da slice)

Cada slice é uma unidade rastreável de ponta a ponta: **abre uma issue, trabalha,
commita e fecha a issue**. Ferramenta padrão: `gh` CLI (GitHub oficial). Se `gh`
não estiver disponível/autenticado, o agente **degrada com aviso** (segue sem
issue, mas registra no PROGRESS.md que o tracking ficou manual) — nunca trava a
slice por causa disso.

### Abertura da issue (planner, Prompt 01)
- Antes de escrever o `PLAN.md`, o planner cria **uma issue** para a slice:
  `gh issue create --title "<slug>: <intenção>" --body "<objetivo + critérios + risco>"`.
- Registra o número retornado como `issue: #<id>` no `PLAN.md` e no `PROGRESS.md`.
- Se `gh` falhar, registra `issue: manual` e segue.

### Commit (closeout, Prompt 05)
- O commit acontece **uma vez por slice**, no closeout, depois da Definition of
  Done satisfeita (e, se C/D, após aprovação humana). Não se commita por turno.
- **Ordem obrigatória:** o move da slice `docs/agent/work/<slug>/` →
  `docs/agent/completed/<slug>/` acontece **antes** do commit, de preferência com
  `git mv` (registra rename num único commit e preserva histórico). Commitar com
  a slice concluída ainda em `agent/work/` é proibido — gera histórico inconsistente e
  um commit extra só para o move.
- **Escopo do commit:** apenas `docs/` e o **código de produto** alterado.
  **Nunca** adicione `harness/` (está no `.gitignore`; confirme com
  `git status --porcelain harness/` vazio antes de commitar).
- Mensagem no formato convencional, referenciando a issue:
  `git commit -m "<tipo>(<slug>): <resumo>" -m "Closes #<id>"`
  (`<tipo>` = feat | fix | refactor | docs | chore — refatoração usa `refactor`).
- `Closes #<id>` fecha a issue automaticamente no merge. Para slice que não
  fecha o trabalho todo, use `Refs #<id>` e feche manualmente no fim.
- **Push** é manual por padrão (o agente não dá push sem o usuário pedir), salvo
  instrução explícita do projeto em contrário.

### Encerramento da issue
- Slice em READY e integrada → o `Closes #<id>` no commit cuida do fechamento no
  merge. Se o fluxo não usa merge automático, o closeout roda
  `gh issue close <id> --comment "<evidência + estado final>"`.
- C/D: a issue só fecha após a aprovação humana registrada.

## Formato do Handoff (bloco final obrigatório em todo turno de tarefa)
Termine cada turno de tarefa com este bloco (campos vazios viram `n/a`):

    ### Handoff
    - state: <DISCOVERY|PLANNED|IMPLEMENTING|EVALUATING|FIXING|REVIEWING|DOCUMENTING|READY>
    - this_turn: <papel + o que foi feito>
    - evidence: <comandos/testes/arquivos que comprovam>
    - open_risks: <riscos que SEGUEM com o trabalho — NÃO decisões que o travam>
    - pending_decision: <decisão que o usuário precisa tomar ANTES de prosseguir | none>
    - next_role: <surveyor|guide_auditor|planner|designer|builder|refactorer|evaluator|reviewer|security_reviewer|qa|test_author|e2e_tester|closer|integrator|doc_gardener|done>
    - next_action: <uma linha: o que o próximo agente (ou o usuário) faz primeiro>
    - suggested_model: <tier light|standard|deep + effort> — <motivo em uma frase>

    Regra: se `pending_decision` não é `none`, NÃO avance de papel — `next_role`
    volta para quem propôs (ex.: planner) e `next_action` é a pergunta ao usuário.
    Uma decisão arquitetural (stack/framework/banco) nunca é "adotada" sozinha.

Exceções (NÃO emitir o bloco): (1) a primeiríssima ativação do projeto
(intake/boas-vindas, sem tarefa ainda); (2) uma mensagem que não pertence à
tarefa atual. Na dúvida, dentro de uma tarefa, emita.


