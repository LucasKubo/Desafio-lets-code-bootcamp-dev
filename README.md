# Desafio-lets-code-bootcamp-dev

## Pré-requisitos
- Java 8 ou superior instalado
- PostgreSQL 14 ou superior instalado
- Postman instalado

## Iniciando...
Serão executados 2 projetos, o LetsCodeChallengeAuth representa uma API REST de gerenciamento de acesso e identidade, a ideia é que esse projeto esteja exposto na web protegendo a aplicação com a regra de negócio que é o LetsCodeChallengeMovies, essa aplicação deve ficar em uma rede privada onde apenas a aplicação de segurança terá acesso por meio de requisições.

### Como iniciar:
- `git clone https://github.com/LucasKubo/Desafio-lets-code-bootcamp-dev`
- Importe os projetos como maven em sua IDE
- Crie o banco de dados no PostgreSQL com o nome `letscodemoviedb`
- Altere as informações de configuração do banco de dados nos arquivos `Desafio-lets-code-bootcamp-dev\LetsCodeChallengeAuth\src\main\resources\application.properties` e `Desafio-lets-code-bootcamp-dev\LetsCodeChallengeMovies\src\main\resources\application.properties`
- Conecte no banco de dados e execute os scripts do arquivo `Desafio-lets-code-bootcamp-dev\LetsCodeChallengeAuth\src\main\resources\scripts\v1.sql`
- Execute as duas APIs

## Executando teste via Postman
Acesse o postman e importe o arquivo `Desafio-lets-code-bootcamp-dev\CodeChallenge-LetsCode.postman_collection.json`, nela estarão disponíveis todas requisições.
Também é possível acessar a documentação das requisições por meio do link: https://documenter.getpostman.com/view/21638616/UzJETKia# Desafio-lets-code-bootcamp-dev