olá recrutador, o código presente nesse repositório é apenas uma parte do projeto.

esse projeto é uma api restful para um sistema para restaurantes que fornece comunicação entre o aplicativo mobile e a interface web, esses estão em outros repositórios.
essa api é destinada às aplicações do estabelecimento e ainda está em desenvolvimento, então é possível encontrar algum tipo de código não tão otimizado como deveria; 
posteriormente serão desenvolvidas as aplicações e apis para o consumidor e entregadores.

atualmente, os recursos funcionais são: 
- Cadastro, edição e listagem de usuários -> é possível cadastrar um usuário caso ainda não tenha uma conta, nesse caso deve estar atrelado ao primeiro cadastro do estabelecimento 
- Cadastro e listagem de categorias
- Cadastro e listagem de produtos (pizza)
- Autenticação via token
- Login,
- Recuperação de senha via e-mail (esqueci senha),
- Alteração de senha pelo método de informar a senha anterior (quando logado) e pelo método de usuário administrador sem necessidade de informar a senha anterior (alteração arbitrária)
- Armazenamento de imagens do banco de dados direto no disco, isso é feito toda vez que uma imagem é requisitada e não é encontrada no disco, nesse caso ela é baixada do banco e copiada para o repositório local, assim torna-se mais rápida a listagem de imagens
- Utilização de cache nos endpoins mais utilizados

principais recursos que serão implementados posteriormente:

  - Criação de testes automatizados com o JUnit
  - Recebimentos de pagamentos dos produtos, certamente será utilizada a api do mercado pago, ou similar
  - Listagem de pedidos realizados por clientes
  - Listagem de cardápio
  - Cadastro de produtos padrão (que não sejam pizzas)
  - Cadastro e listagem de entregadores fixos
  - Controle de acesso de usuários, por nível/hierarquia
  - Inclusão de notas e comentários dadas pelos clientes aos produtos, garçons, entregadores e ao próprio estabelecimento
  - Listagem de produtos mais vendidos
  - Listagem de estoque, para produtos que se aplicam o controle de estoque
  - Listagem de vendas realizadas em um período específico
  - Migração de usuários que já possuem contas atreladas a outro estabelecimento, esses poderão ser transferidos para o novo estabelecimento,
    a nota de atendimento que possui e os comentários se manterão, porém os comentários serão separados por uma secção denominada "comentários antigos"
  - Listagem de quantidade de mesas disponíveis
  - Geração de QR Code para referenciar as mesas do local, posteriormente serão utilizados pelos clientes
