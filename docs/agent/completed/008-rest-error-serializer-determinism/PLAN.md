# PLAN — 008-rest-error-serializer-determinism

## Status
State: READY
issue: #8

## Objetivo
Tornar a serialização de erros REST determinística e segura em `ErrorResourceSerializer`, preservando ordem de campos e evitando supressão silenciosa de exceções de IO.

## Escopo
- Incluído:
  - Substituir `HashMap` por mapa com ordem estável de inserção no `ErrorResourceSerializer`.
  - Remover `printStackTrace()` e escrever itens com fluxo que propague `IOException` naturalmente.
  - Adicionar teste unitário para validar ordem de campos e agrupamento de mensagens.
  - Validar gates de build/lint/testes.
- Fora de escopo:
  - Mudanças de contrato de erro REST além de determinismo/segurança de serialização.
  - Refatorações em handlers REST/GraphQL.
- files_owned:
  - src/main/java/io/spring/api/exception/ErrorResourceSerializer.java
  - src/test/java/io/spring/api/exception/ErrorResourceSerializerTest.java

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices
- Documento/bloco de origem: oportunidade de robustez observada no serializer REST
- context_sources:
  - skills: []
  - design: null
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: n/a.
- Permissões / minimização / mascaramento: sem novos acessos.
- Auditoria / log (sem PII, sem secrets): elimina `printStackTrace()` em caminho de serialização.
- Revisão humana: não obrigatória (categoria B).

## API, entidades e integrações
- Endpoints/contratos / entidades/tabelas / integrações externas / jobs/eventos:
  - Sem mudança de endpoint/tabela.
  - Mantém payload funcionalmente equivalente com ordem determinística de chaves.

## Critérios de aceite
- [ ] `./gradlew clean test` permanece PASS.
- [ ] `./gradlew spotlessCheck` permanece PASS.
- [ ] `./gradlew build -x test` permanece PASS.
- [ ] Serialização de `ErrorResource` preserva ordem de primeira ocorrência dos campos.
- [ ] Não há `printStackTrace()` no serializer.
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
  - is_refactor: true
  - behavior_parity_required: true
  - depends_on: [007-rest-validation-path-extraction-robustness]

## Model Profile
```yaml
risk_category: B
planner:   { tier: standard, effort: low }
generator: { tier: standard, effort: low }
evaluator: { tier: standard, effort: medium }
reviewer:  { tier: light, effort: low }
cross_family_evaluator: false
budget_max_usd: 6.50
rationale: |
  Refino pequeno e localizado no serializer REST para melhorar determinismo
  e robustez de erro, exigindo validação de paridade por teste unitário.
```

## Deterministic gates (a rodar antes de READY)
- build
- lint
- unit_tests
- integration_tests

## Condições de parada
- Mudança implicar alteração contratual significativa no payload.
- Testes indicarem dependência explícita da ordem não determinística atual.

## Riscos e pendências
- Risco: consumidores dependerem acidentalmente de ordem atual não definida.
- Pendência: avaliação independente (Prompt 03) após implementação.
