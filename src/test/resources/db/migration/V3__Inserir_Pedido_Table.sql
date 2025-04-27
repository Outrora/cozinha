CREATE TABLE PEDIDO (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    codigocliente INTEGER NOT NULL,
    datacriacao TIMESTAMP NOT NULL,
    estadopedido VARCHAR(20) NOT NULL,
    CONSTRAINT check_estado CHECK (estadopedido IN ('PENDENTE', 'EM_PREPARACAO', 'PRONTO', 'RETIRADO', 'CANCELADO'))
);

CREATE TABLE pedido_produto (
    pedido_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES PEDIDO(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);


INSERT INTO pedido (id, codigocliente, datacriacao, estadopedido)
VALUES (1, 1, CURRENT_TIMESTAMP, 'PENDENTE');

INSERT INTO pedido (id, codigocliente, datacriacao, estadopedido)
VALUES (2, 1, CURRENT_TIMESTAMP, 'PENDENTE');

INSERT INTO pedido (id, codigocliente, datacriacao, estadopedido)
VALUES (3, 1, CURRENT_TIMESTAMP, 'PRONTO');

INSERT INTO pedido_produto (pedido_id, produto_id, quantidade)
VALUES (1, 1, 2);

INSERT INTO pedido_produto (pedido_id, produto_id, quantidade)
VALUES (1, 2, 2);

ALTER TABLE PEDIDO ALTER COLUMN id RESTART WITH 4;