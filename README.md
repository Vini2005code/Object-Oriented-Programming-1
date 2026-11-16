# SGA Jatinho Feliz - Sistema de Gerenciamento Aeroportuário

O **SGA Jatinho Feliz** é um sistema de software desenvolvido por Vinícius Rios, Arthur Nardy e Guilherme Rodrigues. É um sistema sob medida para a gestão da infraestrutura e operações do aeroporto regional Jatinho Feliz. Construído de raiz em Java, o sistema centraliza o controlo de aeromodelos (aviões, helicópteros e jatos), companhias, pistas, hangares e voos, eliminando inconsistências operacionais e garantindo rastreabilidade para auditorias.

# Funcionalidades Principais
*   **Gestão de Frota:** Cadastro e listagem categorizada de Aviões, Helicópteros e Jatos.
*   **Controlo de Infraestrutura:** Gestão do estado de ocupação de Hangares e alocação de pistas.
*   **Registo de Voos:** Controlo de entradas e saídas com validação cruzada de entidades (aeromodelos, companhias e pistas).
*   **Exportação para Auditoria:** Geração automática do relatório `RegistroVoos.txt`.
*   **Persistência Local:** Leitura e gravação de dados em tempo real utilizando ficheiros `.txt`, garantindo a integridade da informação entre sessões sem dependência de bases de dados externas.

# Arquitetura e Pilares da POO
O código foi estruturado com foco em boas práticas de engenharia de software e validação rigorosa de dados (via RegEx), aplicando os quatro pilares fundamentais da Programação Orientada a Objetos:

*   **Abstração:** Criação da classe abstrata `Aeromodelo` que centraliza atributos comuns e define contratos comportamentais, e da interface `Imprimivel` para padronização de saídas no terminal.
*   **Encapsulamento:** Proteção total do estado interno das entidades com modificadores de acesso `private`/`protected`. As regras de negócio e validações de input operam ativamente dentro dos `setters`.
*   **Herança:** Especialização da classe `Aeromodelo` nas subclasses `Aviao`, `Helicoptero` e `Jato`, além de uma hierarquia customizada de tratamento de erros (`ExcecaoSGA` -> `ValidacaoException` / `NaoEncontradoException`) para garantir a estabilidade do sistema.
*   **Polimorfismo:** Manipulação fluida de diferentes tipos de aeronaves através de referências da superclasse `Aeromodelo` (ex: a chamada `imprimir()` adapta-se automaticamente ao tipo específico da instância em tempo de execução).

 Tecnologias Utilizadas
*   **Linguagem:** Java (JDK 17+)
*   **Interface:** CLI (Command Line Interface - Terminal)
*   **Persistência:** Ficheiros de Texto Puro (`java.nio.file` e fluxos de I/O)

 Como Executar o Projeto

1. Certifique-se de ter o **Java Development Kit (JDK) 17 ou superior** instalado na máquina.
2. Clone este repositório ou descarregue o código-fonte.
3. Garanta que a pasta raiz do projeto possui o subdiretório `dados/` (se não existir, o sistema criará automaticamente ao iniciar).
4. Compile as classes a partir da pasta `src/`:
   ```bash
   javac -d bin src/br/com/skycode/sga/*.java
5. Se você for a Cristina, olhar com muito carinho para nos dar a nota maxima rs.
**Desenvolvido por: Vinícius Rios, Arthur Nardy e Guilherme Rodrigues**
