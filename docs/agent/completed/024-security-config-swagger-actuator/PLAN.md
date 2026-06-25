# PLAN — 024-security-config-swagger-actuator

- risk_category: B

State: PLANNED
issue: #24

Objetivo: Permitir acesso a Swagger-UI, OpenAPI schema e Actuator endpoints sem autenticação.

Escopo:
- Adicionar `/swagger-ui.html`, `/swagger-ui/**`, `/v3/api-docs/**` à lista de permitAll
- Adicionar `/actuator/**` à lista de permitAll (observabilidade pública)
- Manter segurança em endpoints sensíveis (/articles/feed, etc)
- Validar que Swagger-UI carrega corretamente

Critérios de aceite:
- [x] Swagger-UI carrega (não blank page)
- [x] /v3/api-docs acessível
- [x] /actuator/* acessível
- [x] REST API ainda protegida onde necessário
- [x] Tests: PASS

Risk: A (security config)
Operational path: standard_path_B
Budget: budget_max_usd: 5.00
