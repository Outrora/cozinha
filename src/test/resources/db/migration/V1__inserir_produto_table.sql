CREATE TABLE produto (
     id INT AUTO_INCREMENT PRIMARY KEY,
        nome VARCHAR(255) NOT NULL,
        descricao VARCHAR(255) NOT NULL,
        preco DECIMAL(19, 2) NOT NULL,
        categoria VARCHAR(20) NOT NULL,
        CHECK (categoria IN ('lanche', 'acompanhamento', 'bebida', 'sobremesa'))
);

INSERT INTO produto (id, nome, descricao, preco, categoria)
VALUES
(1,'Produto A', 'Descrição do Produto A', 100.00, 'lanche'),
(2,'Produto B', 'Descrição do Produto B', 200.00, 'acompanhamento'),
(3,'Produto C', 'Descrição do Produto C', 300.00, 'bebida');