# WorkWell - Backend Spring Boot

Sistema backend desenvolvido em Spring Boot para monitoramento de bem-estar corporativo e prevenção de burnout. Aplicação web completa com interface Thymeleaf, integração com Hugging Face para IA conversacional e mensageria assíncrona com RabbitMQ.

## Sobre o Projeto

WorkWell é uma aplicação Spring Boot que permite empresas monitorarem o bem-estar de seus colaboradores através de check-ins diários, métricas de saúde, alertas de burnout e suporte emocional via chatbot com IA.

## Tecnologias

- **Spring Boot 3.5.7**: Framework principal
- **Java 17**: Linguagem de programação
- **PostgreSQL**: Banco de dados relacional
- **Flyway**: Migrações de banco de dados
- **RabbitMQ**: Mensageria assíncrona
- **Hugging Face API**: Integração com modelos de IA conversacional
- **Spring Security**: Autenticação e autorização
- **Thymeleaf**: Template engine para interface web
- **WebFlux**: Cliente reativo para APIs externas

## Estrutura do Projeto

```
src/main/java/br/com/fiap/workwell/
├── config/              # Configurações (Security, RabbitMQ, WebClient)
├── controller/          # Controllers REST e MVC
├── dto/                # Data Transfer Objects
├── model/              # Entidades JPA
├── repository/         # Repositórios Spring Data JPA
└── service/            # Lógica de negócio
    ├── ChatService.java
    ├── DashboardService.java
    ├── HuggingFaceService.java
    └── MessageSenderService.java
```

## Funcionalidades Principais

### Autenticação e Autorização
- Login com Spring Security
- Controle de acesso baseado em roles (ADMIN, USER)
- Proteção de rotas e recursos

### Check-ins Diários
- Registro de nível de estresse, horas trabalhadas e sono
- Histórico completo de check-ins
- Cálculo de métricas de saúde

### Dashboard
- Estatísticas gerais da empresa
- Total de usuários e alertas ativos
- Saúde geral calculada a partir de métricas
- Casos críticos de burnout

### Chat com IA
- Integração com Hugging Face para análise conversacional
- Suporte a múltiplos modelos de IA
- Fallback automático quando modelos falham
- Análise de situações de bem-estar

### Mensageria Assíncrona
- Envio de mensagens para fila RabbitMQ
- Consumo assíncrono para análise de IA
- Processamento em background

## Configuração

### Variáveis de Ambiente

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/workwell
SPRING_DATASOURCE_USERNAME=workwell
SPRING_DATASOURCE_PASSWORD=workwell
SPRING_RABBITMQ_ADDRESSES=amqp://localhost:5672
SPRING_API_KEY=sua-chave-huggingface
```

### Banco de Dados

O projeto utiliza Flyway para gerenciar migrações. As migrations estão em `src/main/resources/db/migration/` e são executadas automaticamente na inicialização.

## Execução

### Desenvolvimento Local

```bash
./gradlew bootRun
```

### Docker Compose

```bash
docker-compose up
```

A aplicação estará disponível em `http://localhost:8080`

## Endpoints Principais

- `/login` - Página de login
- `/dashboard` - Dashboard principal
- `/checkins` - Gerenciamento de check-ins
- `/chat` - Interface de chat com IA
- `/usuarios` - Gerenciamento de usuários (Admin)
- `/alertas` - Alertas de burnout

## Integração com Hugging Face

O sistema integra com a API do Hugging Face para análise conversacional. Suporta múltiplos modelos:
- microsoft/DialoGPT-small
- facebook/blenderbot-400M-distill
- google/flan-t5-base
- E outros modelos compatíveis

## Mensageria

Utiliza RabbitMQ para processamento assíncrono de análises de IA. Mensagens são enviadas para a fila `ai.analysis` e processadas pelo `AiAnalysisConsumerService`.

## Segurança

- Autenticação via formulário Spring Security
- Senhas criptografadas com BCrypt
- Proteção CSRF habilitada
- Controle de acesso baseado em roles

## Desenvolvimento

Projeto desenvolvido para Global Solution 2025 - FIAP
