# PLAN — 004-graphql-exception-handler-modern-api-alignment

## Status
State: READY
issue: #4

## Objetivo
Eliminar a dependência explícita do caminho legado/deprecado `onException` em `GraphQLCustomizeExceptionHandler`, alinhando o handler ao fluxo moderno `handleException` sem regressão de comportamento observável.

## Escopo
- Incluído:
  - Ajustar `GraphQLCustomizeExceptionHandler` para privilegiar a API moderna (`handleException`) e remover supressão de deprecação local.
  - Atualizar/adicionar testes focados do handler GraphQL para garantir paridade funcional de erros (auth inválida + constraint violation).
  - Validar que os gates de build/lint/testes continuam verdes.
- Fora de escopo:
  - Modernização ampla da stack Spring/GraphQL DGS.
  - Refatoração de outros datafetchers/handlers GraphQL fora do arquivo alvo.
  - Alterações de contrato funcional REST/GraphQL além da paridade atual de erro.
- files_owned:
  - src/main/java/io/spring/graphql/exception/GraphQLCustomizeExceptionHandler.java
  - src/test/java/io/spring/graphql/exception/GraphQLCustomizeExceptionHandlerTest.java

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices
- Documento/bloco de origem: risco residual registrado em `docs/agent/completed/003-graphql-exception-handler-warning-hygiene/SLICE-REPORT.md`
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
  - Espera-se paridade no comportamento observável de erros GraphQL já existente.

## Critérios de aceite
- [ ] `./gradlew clean test` permanece PASS.
- [ ] `./gradlew spotlessCheck` permanece PASS.
- [ ] `./gradlew build -x test` permanece PASS.
- [ ] `GraphQLCustomizeExceptionHandler` não possui mais supressão de deprecação para `onException`.
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
  - depends_on: [003-graphql-exception-handler-warning-hygiene]

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
  Slice incremental de alinhamento de API em handler GraphQL já coberto por testes,
  com risco moderado por tocar fluxo executável de exceção, sem expansão para
  modernização estrutural da stack.
```

## Deterministic gates (a rodar antes de READY)
- build
- lint
- unit_tests
- integration_tests

## Condições de parada
- API atual da lib exigir manutenção de `onException` para compatibilidade em runtime sem alternativa segura no escopo da slice.
- Qualquer ajuste exigir alteração estrutural fora de `files_owned`.
- Quebra de paridade observável de erros sem causa objetiva resolvível localmente.

## Riscos e pendências
- Risco: tentativa de remover caminho legado pode quebrar integração com contrato interno da versão atual da stack GraphQL.
- Pendência: validação independente (Prompt 03) após implementação.
