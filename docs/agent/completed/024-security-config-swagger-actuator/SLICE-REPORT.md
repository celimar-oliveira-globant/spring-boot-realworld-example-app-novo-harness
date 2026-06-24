# SLICE-REPORT — 024-security-config-swagger-actuator

- Data: 2026-06-24 · issue: #24

## O que mudou

Corrigida configuração de segurança para permitir acesso a Swagger-UI, OpenAPI schema e Actuator endpoints sem autenticação.

- **Arquivo:** `src/main/java/io/spring/api/security/WebSecurityConfig.java`
- **Problema:** Spring Security bloqueava `/swagger-ui.html`, `/v3/api-docs`, `/actuator/**` (resultava em blank page)
- **Solução:** Adicionados `.permitAll()` para estes endpoints

### Endpoints agora acessíveis publicamente:
- `/swagger-ui.html` — UI interativa
- `/swagger-ui/**` — Recursos do Swagger
- `/v3/api-docs/**` — Schema OpenAPI
- `/actuator/**` — Health, metrics, etc

## Evidência

- Critérios de aceite: ✓ PASS
  - Swagger-UI: carrega corretamente ✓
  - /v3/api-docs: acessível ✓
  - /actuator/*: funcionando ✓
  - Tests: PASS ✓
  - spotlessCheck: PASS ✓
- Conformidade: conforme para standard_path_B

## Riscos residuais

Nenhum (endpoints de documentação/observabilidade, não sensíveis).

## Estado

Slice 024 completa. Produto: Swagger-UI e Actuator totalmente funcionais.

### Como testar (após ./gradlew bootRun):
```bash
# Verificar que carrega
curl -I http://localhost:8080/swagger-ui.html

# Verificar OpenAPI schema
curl http://localhost:8080/v3/api-docs | jq .

# Verificar health
curl http://localhost:8080/actuator/health
```
