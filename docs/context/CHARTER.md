# CHARTER

## Identidade do projeto
- **Nome:** Spring Boot RealWorld Example App (Conduit backend)
- **Objetivo (inferido):** disponibilizar uma implementação de referência do backend RealWorld com CRUD, autenticação e suporte simultâneo a REST e GraphQL.

## Usuários / personas
- **[PENDING INPUT]** Personas de negócio não estão explícitas nas fontes analisadas.
- Persona técnica inferida: desenvolvedores que usam o repositório como referência/benchmark de arquitetura e API.

## Escopo funcional principal (inferido)
- APIs HTTP (REST) para fluxos centrais do domínio RealWorld.
- API GraphQL em paridade funcional com REST.
- Autenticação/autorização com JWT.
- Persistência relacional (SQLite) com mapeamento por MyBatis.

## Fora de escopo conhecido
- **[PENDING INPUT]** Não há documento de produto com escopo formal de versão (MVP) nas fontes atuais.

## Riscos e controles exigidos
- Preservar arquitetura declarada: **DDD + CQRS**.
- Preservar coexistência e paridade de contrato **REST + GraphQL**.
- Tratar alterações em auth/JWT como risco elevado (categoria C no guia operacional).
- Não usar dados sensíveis reais em código, testes ou documentação.

## Stack e restrições técnicas
- Java 11
- Spring Boot 2.6.3
- MyBatis
- SQLite
- GraphQL com Netflix DGS
- Build com Gradle

## Definition of Done (inicial, operacional)
- Slice planejada com critérios de aceite explícitos.
- Implementação/refatoração dentro do escopo aprovado.
- Verificações reproduzíveis (gates) executadas e registradas.
- Avaliação independente concluída.
- Documentação de trabalho atualizada em `docs/agent/work/<slug>/`.
- Encerramento e integração conforme fluxo do `OPERATING-GUIDE.md`.
