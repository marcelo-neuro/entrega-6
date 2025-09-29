--DROP
--DROP TABLE tb_pagamento CASCADE CONSTRAINTS;
--DROP TABLE tb_cartao    CASCADE CONSTRAINTS;
--DROP TABLE tb_cliente   CASCADE CONSTRAINTS;

--CREATE
CREATE TABLE tb_cliente (
    id_cliente              NUMBER(10)      NOT NULL,
    nome_cliente            VARCHAR2(100)   NOT NULL,
    email_cliente           VARCHAR2(100)   NOT NULL UNIQUE,
    telefone_cliente        VARCHAR2(20)    NOT NULL,
    valor_medio_pagamento   NUMBER(10,2)    NOT NULL,
    
    PRIMARY KEY(id_cliente),
    
    CONSTRAINT chk_valor_medio_pagamento_positivo CHECK (valor_medio_pagamento > 0)
);

CREATE TABLE tb_cartao (
    id_cartao       NUMBER(10)      NOT NULL,
    id_cliente      NUMBER(10)      NOT NULL,
    numero_cartao   VARCHAR(20)     NOT NULL    UNIQUE,
    cvv_cartao      CHAR(3)         NOT NULL,
    tipo_cartao     VARCHAR(20)     NOT NULL,
    validade_cartao DATE            NOT NULL,
    
    PRIMARY KEY(id_cartao),
    FOREIGN KEY(id_cliente) REFERENCES tb_cliente(id_cliente),
    
    CONSTRAINT chk_enum_tipo_cartao CHECK (tipo_cartao IN('DEBITO', 'CREDITO'))
);

CREATE TABLE tb_pagamento (
    id_pagamento NUMBER(10) NOT NULL,
    id_cliente NUMBER(10) NOT NULL,
    id_cartao NUMBER(10) NOT NULL,
    valor_pagamento NUMBER(10,2) NOT NULL,
    descricao_pagamento VARCHAR2(200) NOT NULL,
    data_pagamento DATE NOT NULL,
    
    PRIMARY KEY(id_pagamento),
    FOREIGN KEY(id_cliente) REFERENCES tb_cliente(id_cliente),
    FOREIGN KEY(id_cartao)  REFERENCES tb_cartao(id_cartao),
    
    CONSTRAINT chk_valor_pagamento_positivo CHECK (valor_pagamento > 0)
);