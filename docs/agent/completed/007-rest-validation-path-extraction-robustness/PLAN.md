# PLAN — 007-rest-validation-path-extraction-robustness

## Status
State: READY
issue: #7

## Objetivo
Corrigir a extração do nome de campo em erros de validação REST para evitar chaves vazias quando `propertyPath` possuir dois segmentos (ex.: `input.email`), mantendo paridade com a robustez já aplicada no handler GraphQL.

## Escopo
- Incluído:
  - Ajustar `getParam(...)` em `CustomizeExceptionHandler` para tratar corretamente caminhos com 1, 2 e 3+ segmentos.
  - Adicionar teste unitário focado cobrindo cenário de `propertyPath` com dois segmentos.
  - Validar gates de build/lint/testes.
- Fora de escopo:
  - Mudanças de contrato de erro REST além da correção da chave de campo.
  - Refatorações fora do handler REST e de seu teste dedicado.
- files_owned:
  - src/main/java/io/spring/api/exception/CustomizeExceptionHandler.java
  - src/test/java/io/spring/api/exception/CustomizeExceptionHandlerTest.java

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices
- Documento/bloco de origem: gap de paridade identificado após slice 005 (GraphQL)
- context_sources:
  - skills: []
  - design: null
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: n/a (payload de erro de validação sem PII real).
- Permissões / minimização / mascaramento: sem novos acessos/permissões.
- Auditoria / log (sem PII, sem secrets): sem introdução de logs sensíveis.
- Revisão humana: não obrigatória (categoria B).

## API, entidades e integrações
- Endpoints/contratos / entidades/tabelas / integrações externas / jobs/eventos:
  - Sem mudança de endpoint/tabela.
  - Ajuste localizado no campo de erro retornado em validação REST para paths com dois segmentos.

## Critérios de aceite
- [ ] `./gradlew clean test` permanece PASS.
- [ ] `./gradlew spotlessCheck` permanece PASS.
- [ ] `./gradlew build -x test` permanece PASS.
- [ ] Cenário com `propertyPath` de dois segmentos não gera chave vazia no erro REST.
- [ ] Comportamento existente para validações correntes permanece compatível.
- [ ] Documentação e riscos atualizados.

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
  - is_refactor: false
  - depends_on: [005-graphql-validation-path-extraction-robustness]

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
  Correção incremental pequena em lógica de parsing de erro REST,
  com impacto moderado por tocar código executável e necessidade de
  validação de paridade via teste focado.
```

## Deterministic gates (a rodar antes de READY)
- build
- lint
- unit_tests
- integration_tests

## Condições de parada
- Correção exigir alteração estrutural fora de `files_owned`.
- Evidência de que comportamento atual é contrato intencional e não defeito.
- Regressão no formato de erro REST sem solução local mínima.

## Riscos e pendências
- Risco: ajuste de parsing alterar formato de chave em cenários legados não cobertos.
- Pendência: avaliação independente (Prompt 03) após implementação.
