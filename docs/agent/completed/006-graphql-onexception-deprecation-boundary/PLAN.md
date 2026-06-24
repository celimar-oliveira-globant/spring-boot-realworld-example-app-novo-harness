# PLAN — 006-graphql-onexception-deprecation-boundary

## Status
State: READY
issue: #6

## Objetivo
Eliminar o ruído de compilação por depreciação inevitável no handler GraphQL aplicando supressão mínima e localizada no shim legado `onException`, sem alterar comportamento funcional.

## Escopo
- Incluído:
  - Adicionar supressão de depreciação **somente** no método `onException(...)` de `GraphQLCustomizeExceptionHandler`.
  - Manter a rota moderna em `handleException(...)` inalterada.
  - Executar gates de build/lint/testes e registrar evidências.
- Fora de escopo:
  - Upgrades de versão (`graphql-java`, DGS, Spring Boot).
  - Refatorações funcionais no mapeamento de erros.
- files_owned:
  - src/main/java/io/spring/graphql/exception/GraphQLCustomizeExceptionHandler.java

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices
- Documento/bloco de origem: warning residual identificado no closeout da slice 005
- context_sources:
  - skills: []
  - design: null
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: n/a.
- Permissões / minimização / mascaramento: sem novos acessos.
- Auditoria / log (sem PII, sem secrets): sem inclusão de dados sensíveis.
- Revisão humana: não obrigatória (categoria B).

## API, entidades e integrações
- Endpoints/contratos / entidades/tabelas / integrações externas / jobs/eventos:
  - Sem mudança de contrato externo; alteração apenas de higiene de compilação.

## Critérios de aceite
- [ ] `./gradlew clean test` PASS sem nota de depreciação para `GraphQLCustomizeExceptionHandler`.
- [ ] `./gradlew spotlessCheck` PASS.
- [ ] `./gradlew build -x test` PASS.
- [ ] Comportamento existente do handler preservado.
- [ ] Documentação da slice atualizada.

## Operational path & risk
- risk_category: B
- operational_path: standard_path_B
- sinais:
  - touches_executable_code: true
  - touches_build_deploy: false
  - touches_secrets_config: false
  - touches_auth: false
  - touches_database: false
  - touches_external_integration: false
  - touches_money_movement: false
  - touches_crypto_primitive: false
  - touches_regulated_data: false
  - is_refactor: true
  - behavior_parity_required: true
  - depends_on: [005-graphql-validation-path-extraction-robustness]

## Model Profile
```yaml
risk_category: B
planner:   { tier: standard, effort: low }
generator: { tier: standard, effort: low }
evaluator: { tier: standard, effort: medium }
reviewer:  { tier: light, effort: low }
cross_family_evaluator: false
budget_max_usd: 6.00
rationale: |
  Ajuste mínimo em código executável para remover warning residual de compilação,
  com exigência de paridade comportamental e validação por gates determinísticos.
```

## Deterministic gates (a rodar antes de READY)
- build
- lint
- unit_tests
- integration_tests

## Condições de parada
- Se a supressão localizada não eliminar a nota residual.
- Se houver necessidade de alteração funcional para eliminar o warning.

## Riscos e pendências
- Risco: o warning não ser exclusivamente do método shim.
- Pendência: validação independente (Prompt 03) após implementação.
