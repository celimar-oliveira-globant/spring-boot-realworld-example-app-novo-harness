# AS-IS — fotografia do projeto
- Data: 2026-06-24
- Gerado por: surveyor (Prompt 00a) — somente leitura, SEM build/execução
- Commit de referência: ee17e31
- Repositório: git@github.com:celimar-oliveira-globant/spring-boot-realworld-example-app-novo-harness.git
- Confirmação de modo: **turno read-only; nenhuma execução de build/teste/instalação**

## Resumo (3–5 linhas)
Projeto backend RealWorld (Conduit) em Spring Boot + MyBatis, com suporte REST e GraphQL no mesmo código-base.
A stack declarada está centrada em Java 11/Gradle 7.4/Spring Boot 2.6.3, com dependências de segurança (JWT), migração (Flyway) e SQLite.
Há suíte de testes presente em `src/test/java` e CI no GitHub Actions, mas este baseline **não executou** nada (somente inspeção de arquivos).
A maturidade aparente é de código funcional com estrutura organizada; os commits mais recentes nos módulos de código estão datados de 2022-03-22.
Principais lacunas: sem validação de execução, sem medição de cobertura real e sem avaliação de CVEs/atualização de dependências.

## Stack e linguagens
| Linguagem | Arquivos (aprox.) |
|---|---:|
| Java | 116 |
| GraphQL | 1 |
| SQL | 1 |

- Manifestos de build presentes: `build.gradle`, `gradle/wrapper/gradle-wrapper.properties`
- Runtime/toolchain exigido (declarado): Java `11` (source/target), Gradle Wrapper `7.4`
- Plugins de build declarados: Spring Boot `2.6.3`, Dependency Management `1.0.11.RELEASE`, DGS Codegen `5.0.6`, Spotless `6.2.1`
- Outros manifestos comuns (`package.json`, `pyproject.toml`, `go.mod`, `Cargo.toml`, `pom.xml`): **não encontrado**
- Dockerfile/docker-compose na raiz: **não encontrado**
- CI: `.github/workflows/gradle.yml`

## Dependências declaradas (transcritas, sem julgamento)
| Pacote | Versão declarada | Origem (manifesto/lock) |
|---|---|---|
| org.springframework.boot:spring-boot-starter-web | managed-by-bom/unspecified | build.gradle |
| org.springframework.boot:spring-boot-starter-validation | managed-by-bom/unspecified | build.gradle |
| org.springframework.boot:spring-boot-starter-hateoas | managed-by-bom/unspecified | build.gradle |
| org.springframework.boot:spring-boot-starter-security | managed-by-bom/unspecified | build.gradle |
| org.mybatis.spring.boot:mybatis-spring-boot-starter | 2.2.2 | build.gradle |
| com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter | 4.9.21 | build.gradle |
| org.flywaydb:flyway-core | managed-by-bom/unspecified | build.gradle |
| io.jsonwebtoken:jjwt-api | 0.11.2 | build.gradle |
| io.jsonwebtoken:jjwt-impl | 0.11.2 | build.gradle |
| io.jsonwebtoken:jjwt-jackson | 0.11.2 | build.gradle |
| joda-time:joda-time | 2.10.13 | build.gradle |
| org.xerial:sqlite-jdbc | 3.36.0.3 | build.gradle |
| org.projectlombok:lombok | managed-by-bom/unspecified | build.gradle |
| io.rest-assured:rest-assured | 4.5.1 | build.gradle |
| io.rest-assured:json-path | 4.5.1 | build.gradle |
| io.rest-assured:xml-path | 4.5.1 | build.gradle |
| io.rest-assured:spring-mock-mvc | 4.5.1 | build.gradle |
| org.springframework.security:spring-security-test | managed-by-bom/unspecified | build.gradle |
| org.springframework.boot:spring-boot-starter-test | managed-by-bom/unspecified | build.gradle |
| org.mybatis.spring.boot:mybatis-spring-boot-starter-test | 2.2.2 | build.gradle |

> Atualização/CVE/licença: **NÃO avaliados** neste baseline.

## Módulos
| Módulo | Arquivos-fonte (aprox.) | Último commit | Observação |
|---|---:|---|---|
| src/main/java | 93 | 2022-03-22 (9d96868) | Código de aplicação principal |
| src/test/java | 23 | 2022-03-22 (9d96868) | Testes Java presentes |
| src/main/resources | 15 | 2022-03-22 (6af3a31) | Configuração, SQL e schema GraphQL |

## Testes (presença/tipo — NÃO executados)
| Camada | Arquivos | Observação |
|---|---:|---|
| unit | 6 | Heurística por caminho/nome (`core/`, `application/`, parte de `service`) |
| integration | 16 | Heurística por caminho/nome (`api/`, `infrastructure/`, `Transaction`) |
| e2e | 0 | Não encontrado |
| indeterminado | 1 | Ex.: arquivo utilitário de suporte (`TestHelper`) |

- Config de cobertura: **não encontrado** (cobertura real: não medida)

## Scripts e entrypoints
| Nome | Origem | (não executado) |
|---|---|---|
| gradlew | wrapper | não executado |
| gradlew.bat | wrapper | não executado |
| test | build.gradle task | não executado |
| clean | build.gradle task | não executado |
| generateJava | build.gradle task | não executado |
| bootRun | README/build (declarado) | não executado |
| bootBuildImage | README/build (declarado) | não executado |
| spotlessJavaApply | README/build (declarado) | não executado |

## Qualidade declarada
- Lint/format/typecheck: Spotless configurado em `build.gradle` (Google Java Format)
- Hooks: `.pre-commit-config.yaml` **não encontrado**
- CI: `.github/workflows/gradle.yml` (setup Java 11 + `./gradlew clean test`)

## Dívida e sinais de risco
- TODO/FIXME/HACK: `0` ocorrências na varredura de `src/`, `build.gradle` e `README.md`
- Possíveis secrets em texto (heurística por padrão de chave/valor): `23` ocorrências em `14` arquivos (**valores não transcritos**)
  - Arquivos com sinal: `src/main/resources/application.properties`, `src/main/resources/mapper/UserMapper.xml`, `src/main/resources/schema/schema.graphqls`, classes de JWT/token em `src/main/java/...`, e alguns testes relacionados
- Arquivos grandes (>1MB): nenhum sinal relevante na varredura
- Código possivelmente morto / arquivos gerados (heurística): presença de diretórios gerados locais como `bin/generated-sources/`, `bin/generated-test-sources/`, `.gradle/buildOutputCleanup/`

## Documentação existente
- `README.md`: presente
- `LICENSE`: presente
- `docs/`: presente (`STATE.md`, `OPERATING-GUIDE.md`, `context/CHARTER.md`, `context/SOURCES.md`, `context/ROADMAP.md`, `context/LESSONS.md`)
- ADRs (`ADR*.md` / pasta `adr/`): não encontrado
- `CHANGELOG.md`: não encontrado

## Confiança e lacunas (o que este baseline NÃO verificou)
- Compila/roda: não verificado (sem build).
- Testes passam / cobertura real: não verificado.
- Vulnerabilidades/atualização de dependências: não avaliado.
- Presença de testes ≠ corretude do módulo.
