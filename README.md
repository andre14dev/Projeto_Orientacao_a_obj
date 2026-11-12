# 📌 Apresentação Final POO

Projeto final da disciplina **C06 – Programação Orientada a Objetos** (NP2) — Inatel
Professor: **Christopher Lima**

---

## 📖 Sobre o Projeto

**Tema:** Sistema de Gestão de Provedor de Internet (ISP).

**Problema:** Provedores de internet de pequeno e médio porte costumam controlar clientes, planos e cobranças de forma manual, em planilhas. Cada tipo de plano cobra de um jeito: a fibra tem mensalidade fixa, e o plano móvel varia conforme o consumo de dados acima da franquia. Calcular tudo à mão, gerar as faturas de muitos clientes de uma vez e descobrir quem está com fatura vencida para suspender o serviço são tarefas lentas e sujeitas a erro, o que causa cobranças incorretas e atraso na suspensão de inadimplentes.

**Solução:** Uma aplicação em Java, executada pelo console, que organiza esse processo:

- **Cadastro de clientes**, cada um com um plano de Fibra ou Móvel, com validação dos dados (nome, CPF e endereço).
- **Cálculo automático da mensalidade:** cada plano calcula a sua. A fibra cobra o valor fixo, e o móvel soma a tarifa dos GB que passam da franquia.
- **Geração de faturas em paralelo**, com uma thread por cliente, o que agiliza o processamento quando há muitos clientes.
- **Persistência em arquivos CSV**, que guardam clientes e faturas entre as execuções, sem precisar de banco de dados.
- **Controle de inadimplência:** o sistema identifica quem tem fatura vencida e não paga, trata isso com uma exceção de negócio e suspende a conexão do cliente.

### 🎓 Relevância ao Curso

O projeto trabalha a Engenharia de Software, ao aplicar modelagem orientada a objetos (UML), herança, polimorfismo, interfaces e classes abstratas, tratamento de exceções, persistência em arquivos e programação concorrente. Também se liga à Telecomunicações, porque o domínio é o de uma operadora: planos de fibra e móvel, franquia de dados, cobrança de excedente e suspensão por inadimplência. É um projeto multidisciplinar, como o enunciado permite.

---

## 👥 Integrantes

| Nome | Matrícula | Curso | GitHub |
|------|-----------|-------|--------|
| André Luiz | 967 | GES | [@Andre](https://github.com/andre14dev) |
| [Nome 2] | [Matrícula] | [Curso] | [@usuario](https://github.com/usuario) |
| [Nome 3] | [Matrícula] | [Curso] | [@usuario](https://github.com/usuario) |
| [Nome 4] | [Matrícula] | [Curso] | [@usuario](https://github.com/usuario) |

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework / Bibliotecas:** Nenhuma
- **Banco de Dados / Arquivos:** Arquivos CSV

---

## 🚀 Como Executar

### Pré-requisitos

- [Requisito 1]
- [Requisito 2]

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/andre14dev/Projeto_Orientacao_a_obj.git

# 2. Entre na pasta do projeto
cd Projeto_Orientacao_a_obj

# 3. [Comando para instalar dependências]

# 4. [Comando para executar]
```

---

## 📁 Estrutura do Projeto

```
projeto_final/
├── src/
│   ├── modelos/
│   │   ├── ServicoTelecom.java        (abstrata)
│   │   ├── PlanoFibra.java
│   │   ├── PlanoMovel.java
│   │   ├── Cliente.java
│   │   └── Fatura.java
│   ├── interfaces/
│   │   └── Bloqueavel.java
│   ├── excecoes/
│   │   ├── DadosInvalidosException.java
│   │   └── ClienteInadimplenteException.java
│   ├── persistencia/
│   │   ├── ClienteRepositorio.java
│   │   └── FaturaRepositorio.java
│   ├── threads/
│   │   └── ProcessadorFaturas.java
│   └── principal/
│       └── Main.java
├── dados/
│   ├── clientes.csv                   (gerado ao executar)
│   └── faturas.csv                    (gerado ao executar)
├── docs/
│   └── uml/
│       └── diagrama.png               (e o .drawio editável)
├── .gitignore
└── README.md
```

---

## 🧩 Conceitos de POO Aplicados

| Conceito | Onde foi aplicado |
|----------|-------------------|
| **Classes** | [Classes principais, com atributos, métodos e construtores] |
| **Herança** | [Hierarquia de classes — ex.: `ClasseBase` → `ClasseFilha`] |
| **Polimorfismo** | [Onde e como o polimorfismo é usado] |
| **Interfaces** | [Interfaces criadas e quem as implementa] |
| **Classes Abstratas** | [Classes abstratas e quem as estende] |
| **Tratamento de Erros** | [Exceções tratadas / exceções personalizadas] |
| **Arquivos ou Banco de Dados** | [Como os dados são lidos/gravados] |
| **Threads** | [Onde e para que as threads são usadas] |

---

## 📐 Diagrama UML

O diagrama UML completo está disponível em [`docs/uml/`](docs/uml/).

![Diagrama UML](docs/uml/[diagrama].png)

---

## 🤖 Uso de Inteligência Artificial

**Links das conversas:**

**André Luiz**
[link da conversa]

**[Nome 2]**
[link da conversa]

**[Nome 3]**
[link da conversa]

**[Nome 4]**
[link da conversa]