# PLAN — 003-graphql-exception-handler-warning-hygiene

## Status
State: READY
issue: #3

## Objetivo
Remover warnings de compilação (`deprecated`/`unchecked`) originados em `GraphQLCustomizeExceptionHandler`, preservando o comportamento observável de erros GraphQL.

## Escopo
- Incluído:
  - Ajustar typing/coleções e usos de API no `GraphQLCustomizeExceptionHandler` para eliminar warnings de compilação reportados no build.
  - Adicionar/ajustar testes focados no handler GraphQL quando necessário para preservar comportamento esperado.
  - Validar que os gates de build/lint/testes permanecem verdes.
- Fora de escopo:
  - Alteração de contratos funcionais de domínio (REST/GraphQL) além da paridade atual de erro.
  - Migração ampla de versões de Spring/GraphQL DGS/Gradle.
  - Refatoração estrutural de módulos GraphQL fora do handler alvo.
- files_owned:
  - src/main/java/io/spring/graphql/exception/GraphQLCustomizeExceptionHandler.java
  - src/test/java/io/spring/graphql/exception/GraphQLCustomizeExceptionHandlerTest.java
  - build.gradle

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices
- Documento/bloco de origem: risco residual registrado em `docs/agent/completed/002-spotless-implicit-dependencies/SLICE-REPORT.md`
- context_sources:
  - skills: []
  - design: null
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: n/a (tratamento de exceção e payload de erro sem PII real).
- Permissões / minimização / mascaramento: sem novos acessos/permissões.
- Auditoria / log (sem PII, sem secrets): sem introdução de novos logs sensíveis.
- Revisão humana: não obrigatória (categoria B).

## API, entidades e integrações
- Endpoints/contratos / entidades/tabelas / integrações externas / jobs/eventos:
  - Sem mudança de endpoint/tabela.
  - Impacto potencial apenas em serialização/formato de erro GraphQL já existente; deve manter paridade observável.

## Critérios de aceite
- [ ] `./gradlew clean test` permanece PASS.
- [ ] `./gradlew spotlessCheck` permanece PASS.
- [ ] `./gradlew build -x test` permanece PASS sem warnings `deprecated`/`unchecked` originados em `GraphQLCustomizeExceptionHandler`.
- [ ] Comportamento observável do tratamento de erro GraphQL (auth inválida e constraint violation) permanece compatível.
- [ ] Documentação e riscos atualizados.

## Operational path & risk
- risk_category: B   # gatilho: toca código executável de tratamento de exceção GraphQL
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
budget_max_usd: 7.50
rationale: |
  Slice de higiene técnica com impacto em código executável (handler GraphQL),
  exigindo verificação reproduzível de não-regressão e manutenção de gates verdes
  sem expansão para modernização ampla de stack.
```

## Deterministic gates (a rodar antes de READY)
- build
- lint
- unit_tests
- integration_tests

## Condições de parada
- Correção exigir troca estrutural de API GraphQL/DGS fora do `files_owned`.
- Persistência de warnings após duas abordagens locais sem causa objetiva.
- Necessidade de decisão arquitetural não prevista (ex.: mudança de estratégia global de error handling GraphQL).

## Riscos e pendências
- Risco: ajuste de typing/deprecated API pode alterar payload de erro se feito sem teste de paridade.
- Pendência: validação independente (Prompt 03) após implementação.
