CREATE TABLE PEDIDO (
    id VARCHAR(200) PRIMARY KEY,
    codigocliente INTEGER NOT NULL,
    datacriacao TIMESTAMP NOT NULL,
    estadopedido VARCHAR(20) NOT NULL,
    CONSTRAINT check_estado CHECK (estadopedido IN ('FINALIZADO', ' CANCELADO', 'EM_PREPARACAO', 'PAGAMENTO_APROVADO', 'PAGAMENTO_RECUSADO','PEDIDO_CADASTRADO'))
);

CREATE TABLE pedido_produto (
    pedido_id VARCHAR(200) NOT NULL,
    produto_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES PEDIDO(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);


INSERT INTO pedido (id, codigocliente, datacriacao, estadopedido)
VALUES ('1', 1, CURRENT_TIMESTAMP, 'PEDIDO_CADASTRADO');

INSERT INTO pedido (id, codigocliente, datacriacao, estadopedido)
VALUES ('2', 1, CURRENT_TIMESTAMP, 'PAGAMENTO_APROVADO');

INSERT INTO pedido (id, codigocliente, datacriacao, estadopedido)
VALUES ('3', 1, CURRENT_TIMESTAMP, 'FINALIZADO');

INSERT INTO pedido_produto (pedido_id, produto_id, quantidade)
VALUES ('1', 1, 2);

INSERT INTO pedido_produto (pedido_id, produto_id, quantidade)
VALUES ('1', 2, 2);
