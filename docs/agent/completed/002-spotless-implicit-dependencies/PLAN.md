# PLAN — 002-spotless-implicit-dependencies

## Status
State: READY
issue: #2

## Objetivo
Eliminar os warnings de implicit dependency relacionados ao `spotlessJava` durante o build Gradle, preservando comportamento funcional da aplicação.

## Escopo
- Incluído:
  - Ajustar configuração do Spotless/Gradle para evitar dependências implícitas no `build -x test`.
  - Manter compatibilidade atual de execução de `spotlessCheck` em JDK 17.
  - Validar que os gates de build/lint/testes seguem verdes após o ajuste.
- Fora de escopo:
  - Mudança de funcionalidade de domínio (REST/GraphQL).
  - Migração de versão de Spring Boot/Gradle/plugin fora do necessário para remover os warnings.
  - Reestruturação ampla do pipeline CI.
- files_owned:
  - build.gradle
  - gradle.properties
  - README.md

## Origem e fase
- Fase (ROADMAP): 3 — Execução incremental por slices
- Documento/bloco de origem: risco residual da slice `001-tags-deterministic-order` (SLICE-REPORT)
- context_sources:
  - skills: []
  - design: null
- affects_design_system_core: false

## Dados, segurança e compliance
- Dados envolvidos / sensibilidade: n/a (apenas configuração de build local/CI).
- Permissões / minimização / mascaramento: sem novos acessos/permissões.
- Auditoria / log (sem PII, sem secrets): sem novos logs; manter sem exposição de secrets.
- Revisão humana: não obrigatória (categoria B).

## API, entidades e integrações
- Endpoints/contratos / entidades/tabelas / integrações externas / jobs/eventos:
  - Sem alteração de endpoint/contrato/tabela.
  - Impacto em comportamento do pipeline Gradle (`spotlessJava`, `spotlessCheck`, `build`).

## Critérios de aceite
- [ ] `./gradlew build -x test` não exibe warnings de implicit dependency relacionados ao `spotlessJava`.
- [ ] `./gradlew spotlessCheck` permanece PASS.
- [ ] `./gradlew clean test` permanece PASS.
- [ ] Não há mudança funcional em endpoints/contratos de produto.
- [ ] Documentação e riscos atualizados.

## Operational path & risk
- risk_category: B   # gatilho: toca configuração de build/deploy (comportamento visível de pipeline), sem auth/secrets
- operational_path: standard_path_B
- sinais:
  - touches_executable_code: false
  - touches_build_deploy: true
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
  Slice de infraestrutura de build com impacto em governança de gates e sem
  superfície C/D. Exige validação reproduzível de comandos Gradle e controle
  de escopo para não virar modernização ampla.
```

## Deterministic gates (a rodar antes de READY)
- build
- lint
- unit_tests
- integration_tests

## Condições de parada
- Correção exigir upgrade estrutural de Gradle/plugin com impacto além de `files_owned`.
- Persistência de warning após duas abordagens locais sem evidência clara da causa.
- Necessidade de decisão arquitetural (ex.: trocar formatter/plugin) não prevista no escopo.

## Riscos e pendências
- Risco: tentativa de remover warning pode introduzir acoplamento indevido entre tasks.
- Pendência: validação independente (Prompt 03) após implementação.
