# 💳 Sistema de Pagamentos - MindMatch

Sistema completo para gerenciamento de pagamentos com indicadores avançados, desenvolvido em **Spring Boot + Oracle PL/SQL + React Mobile**.

## 🎯 Visão Geral

- **Backend:** API REST em Spring Boot com integração Oracle PL/SQL
- **Frontend Mobile:** App React/Vite responsivo para gestão e consulta
- **Dashboard:** Angular (projeto separado em `dashboard/`)
- **Banco:** Oracle (produção) + H2 (desenvolvimento)
- **Indicadores:** Functions e Procedures PL/SQL integradas via endpoints REST

## Novidades da Fase 6 (Oracle + PL/SQL)

- Script com Functions e Procedures Oracle: `sql/scripts/oracle_plsql_objects.sql`
  - Tabela auxiliar `TB_ALERTA` (persistência de alertas)
  - Function `FN_TICKET_MEDIO_CLIENTE(p_id_cliente)` → retorna ticket médio (NUMBER)
  - Function `FN_DESCRICAO_PAGAMENTO(p_id_pagamento)` → retorna descrição formatada (VARCHAR2)
  - Procedure `PRC_REGISTRAR_ALERTAS(p_limite IN, o_qtd OUT)` → gera alertas para pagamentos acima do limite
  - Procedure `PRC_RELATORIO_CONSUMO_CLIENTE(p_id_cliente IN, o_cursor OUT SYS_REFCURSOR)` → consumo mensal por cliente
  - Procedure `PRC_LISTAR_ALERTAS(o_cursor OUT SYS_REFCURSOR)` → lista alertas

### Como aplicar no Oracle

1. Conecte no Oracle com o usuário do seu schema (o mesmo de `ORACLE_USER`).
2. Execute os scripts de schema/dados (se necessário):
   - `sql/scripts/pagamento_create.sql`
   - `sql/scripts/pagamento_import.sql`
3. Execute o script das rotinas PL/SQL:
   - `sql/scripts/oracle_plsql_objects.sql`

### Integração Java (visual e direta)

Foram criados:
- Repositório `IndicadoresRepository` com `JdbcTemplate`/`SimpleJdbcCall` chamando as rotinas.
- Serviço `IndicadoresService` encapsula as chamadas.
- Controller `IndicadoresController` expõe endpoints REST:
  - `GET /indicadores/ticket-medio/{clienteId}` → chama `FN_TICKET_MEDIO_CLIENTE`
  - `GET /indicadores/descricao-pagamento/{pagamentoId}` → chama `FN_DESCRICAO_PAGAMENTO`
  - `POST /indicadores/registrar-alertas?limite=100.0` → chama `PRC_REGISTRAR_ALERTAS`
  - `GET /indicadores/relatorio-consumo/{clienteId}` → chama `PRC_RELATORIO_CONSUMO_CLIENTE` (REF CURSOR)
  - `GET /indicadores/alertas` → chama `PRC_LISTAR_ALERTAS` (REF CURSOR)

## 🌐 API Endpoints Completa

### Autenticação
```http
GET /auth/validate
Authorization: Basic <base64(username:password)>
```

### CRUD Principal
```http
GET    /pagamentos           # Listar todos os pagamentos
POST   /pagamentos           # Criar novo pagamento
PUT    /pagamentos/{id}      # Atualizar pagamento
DELETE /pagamentos/{id}      # Excluir pagamento

GET    /clientes             # Listar todos os clientes  
GET    /cartoes              # Listar todos os cartões
```

### Indicadores Oracle (PL/SQL)
```http
GET  /indicadores/ticket-medio/{clienteId}
     # → FN_TICKET_MEDIO_CLIENTE
     # Retorna: NUMBER (valor médio dos pagamentos)

GET  /indicadores/descricao-pagamento/{pagamentoId}
     # → FN_DESCRICAO_PAGAMENTO  
     # Retorna: STRING (descrição formatada)

POST /indicadores/registrar-alertas?limite={valor}
     # → PRC_REGISTRAR_ALERTAS
     # Retorna: NUMBER (quantidade de alertas gerados)

GET  /indicadores/alertas
     # → PRC_LISTAR_ALERTAS
     # Retorna: ARRAY (lista de alertas com ID, valor, data, mensagem)

GET  /indicadores/relatorio-consumo/{clienteId}
     # → PRC_RELATORIO_CONSUMO_CLIENTE
     # Retorna: ARRAY (resumo mensal: mês, quantidade, total)
```

### Exemplos de Teste (curl)
```bash
# Ticket médio do cliente 1
curl http://localhost:8080/indicadores/ticket-medio/1

# Descrição formatada do pagamento 10  
curl http://localhost:8080/indicadores/descricao-pagamento/10

# Registrar alertas para pagamentos > 200
curl -X POST "http://localhost:8080/indicadores/registrar-alertas?limite=200"

# Relatório de consumo do cliente 1 (por mês)
curl http://localhost:8080/indicadores/relatorio-consumo/1

# Listar alertas persistidos
curl http://localhost:8080/indicadores/alertas
```

### Observações de Segurança e CORS
- Em desenvolvimento, CORS liberado em `Pagamentos`, `Clientes`, `Cartoes`, `Indicadores`.
- Endpoints de PL/SQL estão públicos em dev; restrinja em produção conforme necessário.

## Como Executar (geral)

### Backend (Oracle)
```
ORACLE_USER=<usuario> ORACLE_PASSWORD=<senha> SPRING_PROFILES_ACTIVE=prod ./mvnw spring-boot:run
```

### Frontend Mobile (React/Vite)
Veja documentação completa em: **[mobile/README.md](mobile/README.md)**

Execução rápida:
```bash
cd mobile
npm install
npm run dev  # http://localhost:5174/
```

**Credenciais de teste:**
- Admin: `admin` / `admin123` (criar/editar/excluir)
- User: `user` / `user123` (somente leitura)

**Indicadores Oracle:** Acesse a aba "Indicadores" na parte inferior da tela principal.

## 📁 Estrutura do Projeto

```
entrega-6/
├── README.md                    # Documentação principal
├── pom.xml                      # Dependências Maven (Spring Boot)
├── src/main/java/               # Código Java (API REST)
│   └── com/mindmatch/pagamento/
│       ├── controller/          # Controllers REST
│       ├── service/             # Lógica de negócio  
│       ├── repositories/        # Acesso a dados
│       └── models/              # Entidades JPA
├── src/main/resources/          # Configurações e dados
│   ├── application*.properties  # Perfis (test=H2, prod=Oracle)
│   └── import.sql              # Dados iniciais
├── mobile/                      # App React/Vite
│   ├── README.md               # Documentação do mobile
│   ├── package.json            # Dependências npm
│   └── src/                    # Código React
├── dashboard/                   # Dashboard Angular (separado)
│   └── ...                     # Projeto Angular independente
└── sql/                        # Scripts de banco
    ├── DER/                    # Diagramas ER
    └── scripts/                # DDL, DML e PL/SQL
        ├── pagamento_create.sql     # Schema
        ├── pagamento_import.sql     # Dados exemplo
        └── oracle_plsql_objects.sql # Functions/Procedures
```

## 🗄️ Banco de Dados

### DER e Scripts
- **DER:** Diagramas em `sql/DER/`
- **DDL:** Schema em `sql/scripts/pagamento_create.sql`
- **DML:** Dados de exemplo em `sql/scripts/pagamento_import.sql`  
- **PL/SQL:** Rotinas Oracle em `sql/scripts/oracle_plsql_objects.sql`

### Perfis de Banco
- **Test Profile:** H2 em memória (desenvolvimento rápido)
- **Prod Profile:** Oracle (produção com PL/SQL)

## 🎯 Valor Agregado

✅ **Interoperabilidade completa:** Mobile (React) ⇄ API (Spring) ⇄ Oracle (PL/SQL)

✅ **Cálculos no banco:** Indicadores processados em PL/SQL (performance)

✅ **Interface intuitiva:** App mobile responsivo com feedback visual

✅ **Separação de responsabilidades:** Frontend, Backend, Database bem definidos

✅ **Flexibilidade:** Funciona com H2 (dev) e Oracle (prod)



