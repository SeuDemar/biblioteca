 Curso: ADS/ESW 
 
VISÃO  
“Ser referência na preparação de profissionais aptos a atuarem no 
mundo dos negócios, atendendo às dinâmicas da sociedade.” 
MISSÃO  
“Produzir e difundir o conhecimento, através do ensino presencial e 
à distância, buscando resultados sustentáveis e contribuindo para o 
desenvolvimento social.” 
 
SEMINARIO: 
 
Sistema de Biblioteca com Spring 
Objetivo: 
Desenvolver um sistema de gerenciamento de biblioteca utilizando Spring, com foco no gerenciamento de livros, usuários e empréstimos, aplicando regras 
de locação, controle de devoluções e cálculo de multas por atraso. O sistema será implementado via API que gerencia essas operações, utilizando java 
SpringBoot. 
Estrutura do Projeto: 
1. Entidades Principais 
o Usuário: Representa a pessoa que pode realizar o empréstimo de livros. 
Atributos sugeridos: 
▪ ID do usuário (Long ou numérico) 
▪ Nome 
▪ E-mail 
▪ Data de cadastro 
▪ Quantidade de livros atualmente emprestados 
o Livro: Representa os livros disponíveis na biblioteca. 
Atributos sugeridos: 
▪ ID do livro (Long ou numérico) 
▪ Título 
▪ Autor 
▪ Editora 
▪ Ano de publicação 
▪ Disponibilidade (booleano: disponível ou emprestado) 
o Empréstimo: Controla a relação entre o usuário e os livros emprestados, além de gerenciar datas e possíveis atrasos. 
Atributos sugeridos: 
▪ ID do empréstimo (UUID ou numérico) 
▪ ID do usuário (chave estrangeira) 
▪ ID do livro (chave estrangeira) 
▪ Data do empréstimo 
▪ Data de devolução (prevista e realizada) 
▪ Status (ativo, concluído, em atraso) 
▪ Multa aplicada (caso haja atraso) 
2. Regras de Locação 
o Quantidade de livros: Cada usuário pode emprestar no máximo 5 livros simultaneamente. 
o Duração do empréstimo: O prazo padrão para devolução é de 14 dias a partir da data de empréstimo. 
o Multa por atraso: Se o livro não for devolvido dentro do prazo, será aplicada uma multa de R$ 1,00 por dia de atraso. 
o Controle de disponibilidade: Um livro só pode ser emprestado se estiver disponível (não emprestado no momento). 
o Renovação: O usuário pode solicitar a renovação de um empréstimo caso o livro não esteja reservado por outro usuário. 
3. Regras de Negócio 
o Um usuário não pode realizar novos empréstimos se já tiver atingido o limite de 5 livros ou se houver pendências de devolução. 
o Ao devolver um livro, o sistema deverá calcular automaticamente a multa com base na quantidade de dias de atraso, se houver. 
o O sistema deve permitir a consulta de histórico de empréstimos, tanto por usuário quanto por livro. 
Funcionalidades da API 
• Usuários: 
o Criar, atualizar e remover usuários. 
o Consultar lista de usuários e detalhes individuais. 
• Livros: 
o Cadastrar novos livros, atualizar detalhes ou remover livros. 
o Consultar lista de livros disponíveis ou emprestados. 
• Empréstimos: 
o Realizar e registrar empréstimos de livros. 
o Registrar devoluções e calcular multas por atraso. 
o Consultar histórico de empréstimos (por usuário ou livro). 
  

 
src/ 
└── main/ 
    └── java/com/projeto/biblioteca 
        ├── application/ 
        │   ├── service/ 
        │   │   ├── UsuarioService.java 
        │   │   ├── LivroService.java 
        │   │   └── EmprestimoService.java 
        │    
        │ 
        ├── domain/ 
        │   ├── dto/ 
        │   │   ├── UsuarioDTO.java 
        │   │   ├── LivroDTO.java 
        │   │   └── EmprestimoDTO.java 
        │   ├── entity/ 
        │   │   ├── Usuario.java 
        │   │   ├── Livro.java 
        │   │   └── Emprestimo.java 
        │   ├── repositoy/ 
        │   │   ├── IUsuarioRepository.java 
        │   │   ├── ILivroRepository.java 
        │   │   └── IEmprestimoRepository.java 
        │   └── service/ 
        │       ├── IUsuarioServiceInterface.java 
        │       ├── ILivroServiceInterface.java 
        │       └── IEmprestimoServiceInterface.java 
        │ 
        ├── infrastructure/ 
        │   ├── persistence/ 
        │   │   └── DatabaseConfig.java 
        │   ├── config/ 
        │   │   └── SwaggerConfig.java 
        │ 
        ├── presentation/ 
        │   ├── controller/ 
        │   │   ├── UsuarioController.java 
        │   │   ├── LivroController.java 
        │   │   └── EmprestimoController.java 
        │   └── model/ 
        │       └── ResponseModel.java 
        │ 
        └── shared/ 
            └── exception/ 
                ├── CustomException.java 
                └── ExceptionHandler.java 
Considerações Finais 
O projeto deve garantir a integridade dos dados, como a manutenção de estados consistentes dos livros e empréstimos. A implementação de segurança 
também será essencial para controlar o acesso a funcionalidades sensíveis (como remoção de registros e modificações de dados). 
• Banco de dados: Postgres. 
• Documentação da API: Swagger. 
• Script do banco de dados: Disponibilizado na pasta do seminário banco.txt
