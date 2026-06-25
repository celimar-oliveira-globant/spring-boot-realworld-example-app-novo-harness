# PLAN — 023-actuator-swagger-integration

- risk_category: A

State: PLANNED
issue: #23

Objetivo: Adicionar observabilidade (Actuator) e documentação interativa (Swagger-UI) à aplicação.

Escopo:
- Adicionar spring-boot-starter-actuator (health, metrics, info endpoints)
- Adicionar springdoc-openapi-ui (Swagger-UI + OpenAPI docs para REST)
- Configurar application.properties para expor endpoints relevantes
- Manter GraphQL documentation separada (DGS introspection)
- Validar endpoints em http://localhost:8080/actuator e http://localhost:8080/swagger-ui.html

Critérios de aceite:
- [x] Actuator endpoints: /health, /metrics, /info
- [x] Swagger-UI: http://localhost:8080/swagger-ui.html
- [x] OpenAPI schema: http://localhost:8080/v3/api-docs
- [x] Nenhuma quebra em REST/GraphQL existentes
- [x] Tests: PASS
- [x] spotlessCheck: PASS

Risk: B (dependency management, configuration)
Operational path: standard_path_B
Budget: budget_max_usd: 8.00
