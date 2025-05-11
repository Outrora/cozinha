# src/test/resources/features/cadastrarPedido.feature
# language: pt
Funcionalidade: Cadastrar Produto

  Cenario: Cadastrar um produto com sucesso
    Dado o usuário preenche os dados do produto
    Quando o usuário envia o produto
    Então o sistema deve cadastrar o produto com sucesso
