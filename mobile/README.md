# App Mobile - Sistema de Pagamentos

Aplicativo web mobile desenvolvido em React + Vite para gerenciar pagamentos e consultar indicadores avançados do Oracle.

## 🚀 Como Executar

### Pré-requisitos
- Node.js (versão 16+)
- Backend da API rodando (veja README principal)

### Instalação e Execução
```bash
# Instalar dependências
npm install

# Executar em modo desenvolvimento
npm run dev
# App disponível em: http://localhost:5174/

# Build para produção
npm run build

# Preview da build
npm run preview
```

### Configuração da API
Por padrão, o app conecta em `http://localhost:8080`. Para alterar:

**macOS/Linux (zsh/bash):**
```bash
export VITE_API_BASE=http://localhost:8080
npm run dev
```

**Windows (PowerShell):**
```powershell
$env:VITE_API_BASE="http://localhost:8080"
npm run dev
```

## 📱 Funcionalidades

### 🔐 Login
- Usuário: `admin` / Senha: `admin123` (perfil completo)
- Usuário: `user` / Senha: `user123` (somente leitura)

### 💳 Gestão de Pagamentos
- **Listar** pagamentos com informações de cliente e cartão
- **Criar** novos pagamentos (requer login admin)
- **Editar** pagamentos existentes (requer login admin)  
- **Excluir** pagamentos (requer login admin)
- **Filtros** automáticos de cartões por cliente selecionado

### 📊 Indicadores Oracle (PL/SQL)
Acesse a aba **"Indicadores"** na parte inferior da tela para consultar:

#### 1. Ticket Médio por Cliente
- **Endpoint:** `GET /indicadores/ticket-medio/{clienteId}`
- **Função PL/SQL:** `FN_TICKET_MEDIO_CLIENTE`
- **Como usar:** Selecione um cliente e clique em "Ticket médio"
- **Resultado:** Valor médio dos pagamentos do cliente (ex: R$ 150,50)

#### 2. Descrição Formatada de Pagamento
- **Endpoint:** `GET /indicadores/descricao-pagamento/{pagamentoId}`
- **Função PL/SQL:** `FN_DESCRICAO_PAGAMENTO`
- **Como usar:** Selecione um pagamento e clique em "Descrição pagamento"
- **Resultado:** Frase formatada com detalhes do pagamento

#### 3. Sistema de Alertas
- **Endpoints:** 
  - `POST /indicadores/registrar-alertas?limite={valor}` (gerar)
  - `GET /indicadores/alertas` (listar)
- **Procedures PL/SQL:** `PRC_REGISTRAR_ALERTAS` + `PRC_LISTAR_ALERTAS`
- **Como usar:** 
  1. Digite um limite (ex: 200)
  2. Clique em "Registrar alertas" para gerar alertas de pagamentos acima do limite
  3. Clique em "Listar alertas" para visualizar alertas gerados
- **Resultado:** Lista de alertas com ID, valor e mensagem

#### 4. Relatório de Consumo por Cliente
- **Endpoint:** `GET /indicadores/relatorio-consumo/{clienteId}`
- **Procedure PL/SQL:** `PRC_RELATORIO_CONSUMO_CLIENTE`
- **Como usar:** Selecione um cliente e clique em "Relatório consumo"
- **Resultado:** Resumo mensal com quantidade e total de pagamentos

## 🎨 Interface

### Layout Responsivo
- **Header:** Título e botão de logout
- **Conteúdo Principal:** Lista de pagamentos + formulário lado a lado
- **Abas:** Indicadores em seção expansível na parte inferior

### Feedback Visual
- ✅ **Sucessos:** Mensagens verdes (ex: "Pagamento criado", "Ticket médio calculado!")
- ❌ **Erros:** Mensagens vermelhas (ex: "Erro ao salvar", "API offline")
- 🔄 **Loading:** Botões mostram "Enviando..." durante requisições
- 📡 **API Offline:** Banner vermelho com botão "Tentar novamente"

### Componentes Principais
- **LoginScreen:** Tela de autenticação
- **PaymentsScreen:** Tela principal com pagamentos e indicadores
- **Formulário dinâmico:** Criação/edição com validação
- **Lista interativa:** Pagamentos com ações (editar/excluir)

## 🔧 Tecnologias

- **React 18.2.0:** Biblioteca principal
- **Vite 4.2.0:** Bundler e dev server
- **CSS Moderno:** Variables, Flexbox, Grid
- **Fetch API:** Comunicação com backend
- **Local State:** useState + useEffect (sem Redux)

## 🌐 Endpoints da API

### Autenticação
```
GET /auth/validate
Authorization: Basic <base64(username:password)>
```

### Pagamentos
```
GET    /pagamentos           # Listar todos
POST   /pagamentos           # Criar novo
PUT    /pagamentos/{id}      # Atualizar
DELETE /pagamentos/{id}      # Excluir
```

### Clientes e Cartões
```
GET /clientes                # Listar clientes
GET /cartoes                 # Listar cartões
```

### Indicadores Oracle
```
GET  /indicadores/ticket-medio/{clienteId}
GET  /indicadores/descricao-pagamento/{pagamentoId}  
POST /indicadores/registrar-alertas?limite={valor}
GET  /indicadores/alertas
GET  /indicadores/relatorio-consumo/{clienteId}
```

## 🚨 Solução de Problemas

### API Offline
Se aparecer "API offline":
1. Verifique se o backend está rodando em `localhost:8080`
2. Confirme se o CORS está configurado no backend
3. Use o botão "Tentar novamente" após corrigir

### Indicadores não Funcionam
1. Certifique-se que o backend está usando Oracle (profile `prod`)
2. Verifique se as rotinas PL/SQL foram criadas no schema
3. Confirme se há dados de exemplo nas tabelas

### Erro de Login
- Verifique credenciais: `admin/admin123` ou `user/user123`
- Confirme se o endpoint `/auth/validate` está acessível

### Porta já em Uso
Se a porta 5173/5174 estiver ocupada:
```bash
# Vite escolherá automaticamente outra porta
npm run dev
# Ou especifique uma porta manualmente:
npx vite --port 3000
```

## 📁 Estrutura do Projeto

```
mobile/
├── package.json          # Dependências e scripts
├── vite.config.js        # Configuração do Vite
├── index.html           # HTML base
└── src/
    ├── main.jsx         # Entry point
    ├── App.jsx          # Componente principal
    └── styles.css       # Estilos globais
```


