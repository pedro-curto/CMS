# CMS: Exercício Prático BL121/2024

O **DEI Management System** (ou _DMS_) é uma plataforma interna do Departamento de Engenharia Informática (DEI) que agrega vários subsistemas de gestão de informação que são utilizados diariamente pelos funcionários do DEI. 

De forma a facilitar a gestão dos candidatos a bolsas do DEI, é necessário o desenvolvimento de um subsistema simples para o efeito: o **CMS: Candidates Management System** (o seu exercício).

Este subsistema deve permitir a gestão de:

-   candidatos a bolsas do DEI, guardando, para cada um, pelo menos o seu nome, IST ID, email e outros campos que considere relevantes;
-   bolsas do DEI, guardando, para cada uma, pelo menos o ID, a data de início, a data de fim, valor mensal, candidatos e outros campos que considere relevantes;

Inicialmente, o **CMS** será utilizado apenas por funcionários dos Serviços do DEI, estando acessível apenas dentro da rede da Secretaria, não estando por isso previsto para o momento atual o desenvolvimento de autenticação e autorização.

## Implementação

O objetivo deste exercício é desenvolver o subsistema **CMS** usando Spring Boot[\[2\]] v2 (backend) e Vue.js[\[3\]] v3 (frontend).

O subsistema **CMS** deverá permitir, pelo menos:

-   Visualizar todos os candidatos numa tabela de consulta rápida, contendo pelo menos nome e IST ID;
-   Adicionar novos candidatos;
-   Atualizar e remover candidatos existentes;
-   Visualizar todas as bolsas em que um candidato está inscrito;
-   Visualizar todas as bolsas numa tabela de consulta rápida, contendo pelo menos ID, data de início e data de fim;
-   Visualizar uma bolsa em particular numa página dedicada, contendo pelo menos data de início, data de fim, valor mensal e candidatos inscritos;
-   Adicionar novas bolsas;
-   Atualizar e remover bolsas existentes;
-   Inscrever e desinscrever candidatos em bolsas existentes;
-   Dentro de uma bolsa, permitir avaliar candidatos, guardando, para cada um, a nota atribuída a cada categoria de avaliação (por exemplo, "Avaliação curricular", "Entrevista", "Exercício prático");
-   Dentro de uma bolsa, permitir mudar o peso de cada categoria de avaliação;
-   Dentro de uma bolsa, permitir visualizar a média final de cada candidato, calculada com base nas notas atribuídas e nos pesos das categorias de avaliação.
-   Dentro de uma bolsa, uma vez que as avaliações estejam concluídas, permitir selecionar o(s) candidato(s) vencedor(es) da bolsa.

É permitida (mas **não obrigatória**) a implementação de funcionalidades adicionais às acima, especialmente se considerar que algum aspeto melhoraria significativamente a _User Experience (UX)_ durante a utilização do sistema (por exemplo, uma homepage, um dashboard de estatísticas, etc.).

É-lhe fornecido **código base** que deve analisar e utilizar como ponto de partida, estando algumas das funcionalidades acima já parcial ou integralmente implementadas. Não é necessária a implementação de testes. Será penalizada qualquer falta de validação de input do utilizador.

Encontra-se, na backend do código base, um exemplo de como poderá ser a estrutura das entidades a serem utilizadas. Este exemplo (**Materials**) não é relevante para o exercício, mas poderá ser útil para perceber como estruturar camadas em Spring Boot.

Deve realizar o exercício de forma modular. Serão valorizadas qualidade e estética do código e da interface web apresentada.

_Note-se que a situação descrita neste enunciado foi simplificada e não retrata necessariamente a realidade. A sua solução será usada apenas para fins de avaliação._

## Submissão

É obrigatório e alvo de avaliação a utilização de um sistema de controlo de versões (especificamente, `git`[\[4\]]) durante o desenvolvimento do exercício. Deve submeter a sua solução no repositório privado que lhe foi atribuído para o efeito, na instância de _GitLab_ da RNL[\[5\]], e que inicialmente contém o código base, do qual deve partir. Não é necessária a utilização de nenhuma funcionalidade específica do _GitLab_ (e.g., _Issues_ e _Merge Requests_), apenas da ferramenta `git` em si. Assim, ser-nos-á possível validar a sua capacidade de utilização desta ferramenta, podendo até ser-lhe benéfico durante a componente de entrevista.

Deve incluir, junto da sua solução, um ficheiro `README` que descreva o procedimento para iniciar um servidor local de testes. Pode incluir nesse ficheiro `README` qualquer informação que considere relevante para a avaliação do seu trabalho, tal como alguma eventual justificação de decisões tomadas. Não submeta artefactos irrelevantes (e.g., pasta `node_modules`).

**Prazo máximo de entrega: domingo, dia 6 de Agosto de 2024, às 16h00 (hora de Lisboa).**

Não serão aceites quaisquer entregas após o prazo limite, por qualquer razão. Qualquer candidato que não entregue uma solução até ao prazo limite será automaticamente avaliado com a cotação de 0 (zero) valores na componente de seleção correspondente ao exercício prático.

**Boa sorte!**

## Recursos Potencialmente Úteis

-   https://vuetifyjs.com/en/
-   https://spring.io/guides
-   https://www.javatpoint.com/spring-boot-architecture

[\[2\]]: https://spring.io/projects/spring-boot
[\[3\]]: https://vuejs.org/
[\[4\]]: https://git-scm.com/
[\[5\]]: https://gitlab.rnl.tecnico.ulisboa.pt/