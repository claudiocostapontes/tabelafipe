Entregáveis:

- Arquivo Docker-compose.yml configurado para levantar todo o ambiente;

version: '3.8'

services:
  # Banco de Dados PostgreSQL
  postgres-db:
    image: postgres:14-alpine
    container_name: fipe-postgres
    restart: unless-stopped # ADICIONADO: Reinicia o contêiner se ele falhar
    environment:
      # ALTERADO: Usando variáveis do arquivo .env
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - fipe-network
    # ADICIONADO: Healthcheck para garantir que o banco esteja pronto
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${POSTGRES_USER} -d ${POSTGRES_DB}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s

  # Fila de Mensagens RabbitMQ
  rabbitmq:
    image: rabbitmq:3.9-management-alpine
    container_name: fipe-rabbitmq
    restart: unless-stopped # ADICIONADO
    ports:
      - "5672:5672"
      - "15672:15672"
    networks:
      - fipe-network
    # ADICIONADO: Healthcheck para garantir que a fila esteja pronta
    healthcheck:
      test: ["CMD", "rabbitmq-diagnostics", "check_running", "-q"]
      interval: 10s
      timeout: 5s
      retries: 10
      start_period: 10s

  # Cache em Memória Redis
  redis-cache:
    image: redis:7-alpine
    container_name: fipe-redis
    restart: unless-stopped # ADICIONADO
    ports:
      - "6379:6379"
    networks:
      - fipe-network
    # ADICIONADO: Healthcheck para o Redis
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 5s

  # API-1: Gateway e Orquestrador
  api-1-gateway:
    build:
      context: ./api-1
      dockerfile: Dockerfile
    container_name: api-1-gateway
    restart: unless-stopped # ADICIONADO
    # ALTERADO: Agora espera os healthchecks passarem
    depends_on:
      postgres-db:
        condition: service_healthy
      rabbitmq:
        condition: service_healthy
      redis-cache:
        condition: service_healthy
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/${POSTGRES_DB}
      - SPRING_DATASOURCE_USERNAME=${POSTGRES_USER}
      - SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}
      - SPRING_RABBITMQ_HOST=rabbitmq
      - SPRING_REDIS_HOST=redis-cache
    ports:
      - "8080:8080"
    networks:
      - fipe-network

  # API-2: Processador Assíncrono
  api-2-worker:
    build:
      context: ./api-2
      dockerfile: Dockerfile
    container_name: api-2-worker
    restart: unless-stopped # ADICIONADO
    # ALTERADO: Agora espera os healthchecks passarem
    depends_on:
      postgres-db:
        condition: service_healthy
      rabbitmq:
        condition: service_healthy
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/${POSTGRES_DB}
      - SPRING_DATASOURCE_USERNAME=${POSTGRES_USER}
      - SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}
      - SPRING_RABBITMQ_HOST=rabbitmq
    networks:
      - fipe-network

volumes:
  postgres_data:

networks:
  fipe-network:
    driver: bridge

- Instruções claras para a execução do ambiente e dos testes;
<img width="613" height="269" alt="image" src="https://github.com/user-attachments/assets/5f860710-8e27-41c9-a08b-8a8a44c64cec" />
na imagem só executar o play da api1 e api2

- Documentação breve dos endpoints, incluindo exemplos de requisição e resposta.

  1. Listar Veículos:
      
Endpoint: GET /api/v1/vehicles
Descrição: Retorna uma lista paginada de todos os veículos cadastrados no banco de dados.
Parâmetros de Requisição (Query):
page (opcional, número): O número da página que deseja visualizar (começando em 0). Exemplo: 0
size (opcional, número): A quantidade de veículos por página. Exemplo: 20
sort (opcional, string): Critério de ordenação. Exemplo: brand,asc

Exemplo de Requisição:
GET http://localhost:8080/api/v1/vehicles?page=0&size=5&sort=brand,asc

Exemplo de Resposta (200 OK):
{
  "content": [
{
"id": 1,
"fipeCode": "004001-1",
"brand": "VW - VolksWagen",
"model": "Gol 1.0 Plus 8v 2p",
"modelYear": 2001,
"fuel": "Gasolina",
"price": "R$ 6.834,00",
"referenceMonth": "agosto de 2025"
}
],
"pageable": {
"pageNumber": 0,
"pageSize": 5,
},
"totalPages": 150,
"totalElements": 750,
}

2. Buscar Veículo por Código FIPE:
Endpoint: GET /api/v1/vehicles/fipe/{fipeCode}
Descrição: Busca e retorna um único veículo correspondente ao código FIPE informado.
Parâmetros de Requisição (Path):
fipeCode (obrigatório, string): Código FIPE do veículo a ser buscado.
Exemplo de Requisição:
GET http://localhost:8080/api/v1/vehicles/fipe/004001-1

Exemplo de Resposta (200 OK):

{
  "id": 1,
  "fipeCode": "004001-1",
  "brand": "VW - VolksWagen",
  "model": "Gol 1.0 Plus 8v 2p",
  "modelYear": 2001,
  "fuel": "Gasolina",
  "price": "R$ 6.834,00",
  "referenceMonth": "agosto de 2025"
}





