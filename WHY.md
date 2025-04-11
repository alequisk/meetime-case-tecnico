# Desenvolvimento da API de Integração com HubSpot

## Stack Tecnológica Utilizada

Conforme solicitado no teste técnico, a proposta consiste na criação de uma API em Java com integração ao HubSpot. Para isso, optei por utilizar a stack que já aplico rotineiramente em projetos de produção, priorizando robustez, manutenibilidade e facilidade de integração contínua:

- **Spring Framework**  
  Principal framework do ecossistema Java para desenvolvimento de aplicações web e APIs REST. Utilizado para estruturar o projeto com foco em separação de responsabilidades e escalabilidade.

- **Docker**  
  Containerização da aplicação via Docker, garantindo portabilidade do ambiente de execução, isolamento de dependências e compatibilidade entre ambientes de desenvolvimento e produção. Ideal para orquestração posterior em ambientes Kubernetes.

- **RestTemplate**  
  Cliente HTTP nativo do Spring utilizado para comunicação com a API do HubSpot, permitindo chamadas síncronas e tratamento direto das respostas REST.

- **Bucket4j**  
  Biblioteca de controle de taxa (rate limiting) utilizada para limitar requisições ao endpoint de criação de contatos, prevenindo sobrecarga da API e garantindo aderência às políticas de uso do HubSpot.

---

## Arquitetura do Projeto

A estrutura segue a convenção do Spring com separação clara entre *Controllers*, *Services* e *DTOs*. Os *Controllers* atuam como camada de entrada, recebendo requisições e delegando o processamento para os *Services*, responsáveis pela lógica de negócio e integração com serviços externos (HubSpot).

---

## Principais Classes e Responsabilidades

Dado o escopo definido (autenticação via OAuth, criação de contatos e recebimento de eventos via Webhook), o projeto foi organizado em três serviços principais:

### AuthService

Responsável pelo gerenciamento do fluxo OAuth 2.0 com o HubSpot.

- **`generateAuthorizationUrl()`**  
  Gera dinamicamente a URL de autorização, conforme o padrão do HubSpot, incluindo parâmetros como `client_id`, `redirect_uri` e escopos.

- **`exchangeCodeForToken(code)`**  
  Realiza a troca do código de autorização por um `access_token` e `refresh_token`. Utiliza `RestTemplate` com envio `application/x-www-form-urlencoded`, conforme a especificação OAuth para `grant_type=authorization_code`.

- **`executeRefreshToken(refreshToken)`**  
  Atualiza o token de acesso utilizando o `refresh_token` previamente obtido.

### ContactService

Responsável por interagir com os endpoints de contatos do HubSpot.

- **`createContact(contact, token)`**  
  Realiza a criação de contatos utilizando o `access_token`. O método inclui validação de resposta HTTP e aplicação de controle de taxa via Bucket4j, garantindo que o limite de requisições não seja excedido por instância da aplicação.

### WebhookService

Encapsula o processamento de eventos recebidos via Webhook do HubSpot.

- **`processWebhookEvents(event)`**  
  Processa os eventos recebidos, atualmente registrando em log. Estruturado para ser facilmente extensível a integrações com serviços de notificação como Discord, Slack ou Telegram.

---

## Tratamento de exceções

Para melhor tratar as exceções lançadas pelas camadas de _Service_ foram criados Exceptions customizadas para cada erro já conhecido dentro da API.
- `ConflictDataException`: Quando o email do contato já existe.
- `InvalidTokenException`: Token passado pelo usuário expirado / inválido ou ausente.
- `RateLimitReachException`: Quando a aplicação atingir o máximo de requisições por minuto.
- `UnauthorizedException`: Quando o não tem permissão para fazer aquela ação.
- `InternalApiError`: Quando ocorre uma exceção não mapeada no sistema. 


Além disso, todas essas exceções lançadas de maneira independente no código são capturadas pela classe `ApiExceptionHandler` que faz o retorno padronizado de mensagens para o usuário.


## Melhorias Futuras

Para aumentar a resiliência, segurança e auditabilidade da aplicação, as seguintes melhorias são recomendadas:

1. **Rate Limiting por IP**  
   Implementar controle de taxa com base no IP de origem, protegendo endpoints críticos contra ataques de negação de serviço (DDoS) e abusos automatizados.

2. **Validação de Webhook via Checksum**  
   No endpoint `/contacts`, [validar o cabeçalho de assinatura](https://developers.hubspot.com/docs/guides/apps/authentication/validating-requests) (`X-HubSpot-Signature`) para garantir a integridade e autenticidade das notificações recebidas.

3. **Persistência de Eventos de Webhook**  
   Armazenar os eventos recebidos em banco de dados para fins de auditoria, rastreamento de erros e análise histórica de criação de contatos.

4. **Suporte a Novos Eventos**  
   Estender o sistema para lidar com outros tipos de eventos do HubSpot, como atualizações e deleções de contatos, oferecendo uma cobertura mais completa das interações do CRM.

5. **Criação de testes**
    Criando testes será possível validar a aplicação e testar integração a novas features desenvolvidas posteriormente de maneira automática.