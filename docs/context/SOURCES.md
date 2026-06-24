# SOURCES

| Caminho | Tipo (prd|adr|skill|design|roadmap|outro) | Autoridade (authoritative|reference) | Status (in-repo|referenced) | Escopo / gatilho |
|---|---|---|---|---|
| README.md | roadmap | reference | in-repo | Contexto do produto, arquitetura, módulos e execução local |
| build.gradle | outro | reference | in-repo | Stack técnica e dependências de build/runtime |
| harness/OPERATING-GUIDE.md | outro | authoritative | in-repo | Contrato operacional do agente (fonte copiada verbatim para docs/OPERATING-GUIDE.md) |
| harness/SEQUENCE.yaml | roadmap | reference | in-repo | Ordem de execução dos prompts e gates de fluxo |
| harness/README.md | outro | reference | in-repo | Guia de uso do framework agêntico |
| harness/00-onboard.md | outro | reference | in-repo | Prompt de onboarding/retomada |
| harness/00a-baseline.md | outro | reference | in-repo | Prompt de baseline AS-IS |
| harness/00b-guide-audit.md | outro | reference | in-repo | Prompt de auditoria do guia operacional |
| harness/01-plan.md | outro | reference | in-repo | Prompt de planejamento e triagem |
| harness/01b-design-sketch.md | outro | reference | in-repo | Prompt de desenho de solução (quando aplicável) |
| harness/02-implement.md | outro | reference | in-repo | Prompt de implementação |
| harness/02b-refactor.md | outro | reference | in-repo | Prompt de refatoração com paridade comportamental |
| harness/03-evaluate.md | outro | reference | in-repo | Prompt de avaliação independente |
| harness/03b-qa-coverage.md | outro | reference | in-repo | Prompt de medição de cobertura QA |
| harness/03c-qa-author-tests.md | outro | reference | in-repo | Prompt de autoria de testes QA |
| harness/03d-e2e.md | outro | reference | in-repo | Prompt de testes E2E |
| harness/04-review-security.md | outro | reference | in-repo | Prompt de revisão (segurança/design) |
| harness/05-closeout.md | outro | reference | in-repo | Prompt de encerramento da slice |
| harness/06-integrate.md | outro | reference | in-repo | Prompt de integração pós-closeout |
| harness/07-retro.md | outro | reference | in-repo | Prompt de retrospectiva e lições |
| harness/99-start-slice.md | outro | reference | in-repo | Orquestrador de um turno por vez |
| harness/install.sh | outro | reference | in-repo | Bootstrap de instalação do harness |
| docs/agent/completed/001-tags-deterministic-order/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da primeira slice concluída |
| docs/agent/completed/002-spotless-implicit-dependencies/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da segunda slice concluída (governança de build/Spotless) |
| docs/agent/completed/003-graphql-exception-handler-warning-hygiene/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da terceira slice concluída (higiene de warnings no handler GraphQL) |
| docs/agent/completed/004-graphql-exception-handler-modern-api-alignment/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da quarta slice concluída (alinhamento do handler GraphQL à API moderna com shim de compatibilidade) |
| docs/agent/completed/005-graphql-validation-path-extraction-robustness/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da quinta slice concluída (robustez na extração de campo em erro de validação GraphQL) |
| docs/agent/completed/006-graphql-onexception-deprecation-boundary/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da sexta slice concluída (supressão localizada de depreciação inevitável no boundary `onException`) |
| docs/agent/completed/007-rest-validation-path-extraction-robustness/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da sétima slice concluída (robustez na extração de campo em erro de validação REST) |
| docs/agent/completed/008-rest-error-serializer-determinism/SLICE-REPORT.md | outro | reference | in-repo | Relatório final consolidado da oitava slice concluída (determinismo e robustez no serializer de erros REST) |

## Notas
- Não foram encontrados documentos formais do tipo **PRD**, **ADR** ou **DESIGN** no escopo fornecido.
- Se houver fontes externas (ex.: PRD corporativo, ADRs fora do repositório, DESIGN system oficial), registrar como `referenced` sem mover arquivos.
