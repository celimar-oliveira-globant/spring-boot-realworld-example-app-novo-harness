# PLAN — 001-tags-deterministic-order

## Status
State: READY
issue: #1

## Objetivo
Garantir ordem determinística da lista retornada por `GET /tags`, sem alterar autenticação nem schema de banco.

## Escopo
- Incluído:
  - Tornar determinística a ordenação dos tags no fluxo de leitura usado por `GET /tags`.
  - Atualizar/adicionar testes para validar a ordenação determinística da resposta.
  - Preservar paridade do comportamento no caminho compartilhado (REST/GraphQL) ao usar o mesmo query service.
- Fora de escopo:
  - Mudança de autenticação/autorização.
  - Mudança de schema/migração de banco.
  - Alterações em endpoints além do comportamento de ordenação de tags.
- files_owned:
  - src/main/resources/mapper/TagReadService.xml
  - src/test/java/io/spring/application/tag/TagsQueryServiceTest.java
  - src/test/java/io/spring/api/TagsApiTest.java

## Origem e fase
- Fase (ROADMAP): 2 — Planejamento da primeira slice de produto
- Documento/bloco de origem: intenção do usuário (chat)
- context_sources:
  - skills: []
  - design: null
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: nomes de tags (dados públicos de domínio).
- Permissões / minimização / mascaramento: sem mudança de permissão; endpoint já público de leitura.
- Auditoria / log (sem PII, sem secrets): sem novos logs previstos.
- Revisão humana: não obrigatória (categoria B).

## API, entidades e integrações
- Endpoint: `GET /tags` (REST)
- Caminho compartilhado impactado: `TagsQueryService` (pode refletir também em GraphQL `Query.Tags`)
- Entidades/tabelas: leitura da tabela `tags`
- Integrações externas/jobs/eventos: n/a

## Critérios de aceite
- [ ] `GET /tags` retorna lista `tags` em ordem determinística estável (ordem lexical ascendente) para os mesmos dados persistidos.
- [ ] Testes automatizados cobrem o comportamento de ordenação determinística no fluxo afetado.
- [ ] Não há alteração de auth, schema de banco ou contrato estrutural da resposta (mantém `{ "tags": [...] }`).
- [ ] Documentação e riscos atualizados.

## Operational path & risk
- risk_category: B   # gatilho: altera comportamento observável de resposta em endpoint existente (leitura DB)
- operational_path: standard_path_B
- sinais:
  - touches_executable_code: true
  - touches_build_deploy: false
  - touches_secrets_config: false
  - touches_auth: false
  - touches_database: true
  - touches_external_integration: false
  - touches_money_movement: false
  - touches_crypto_primitive: false
  - touches_regulated_data: false
  - is_refactor: false
  - depends_on: []

## Model Profile
```yaml
risk_category: B
planner:   { tier: standard, effort: low }
generator: { tier: standard, effort: medium }
evaluator: { tier: standard, effort: medium }
reviewer:  { tier: light, effort: low }
cross_family_evaluator: false
budget_max_usd: 8.00
rationale: |
  Slice pequena e localizada, com mudança comportamental observável em leitura de endpoint.
  Requer governança B (plano + avaliação), sem superfície C/D.
```

## Deterministic gates (a rodar antes de READY)
- build
- unit_tests
- integration_tests
- lint

## Condições de parada
- Ordem esperada de tags ficar ambígua por collation/locale do banco e impactar previsibilidade cross-ambiente.
- Evidência de dependência externa do consumidor na ordem antiga sem documentação explícita.
- Difusão de impacto fora de `files_owned`.
- Falha recorrente de gate sem causa clara após duas tentativas de correção.

## Riscos e pendências
- Risco: consumidores podem depender implicitamente da ordem atual não determinística.
- Pendência: aprovação do plano para iniciar implementação (Prompt 02).
