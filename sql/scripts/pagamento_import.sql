SET SERVEROUTPUT ON;

DECLARE
  TYPE list_t IS TABLE OF VARCHAR2(100);
  TYPE list_tv IS TABLE OF NUMBER(5,2);

  nomes                 list_t;
  sobrenomes            list_t;
  descricoes_pagamento  list_t;
  valores_pagamento     list_tv;

  v_nome_completo         VARCHAR2(200);
  v_email_base            VARCHAR2(200);
  v_email_final           VARCHAR2(200);
  v_id_cliente_pagamento  NUMBER;
  v_id_cartao_pagamento   NUMBER;
  v_descricao_selecionada VARCHAR2(200);
  v_valor_selecionado NUMBER(10,2);
  v_indice_pagamento NUMBER(1);

BEGIN
  nomes := list_t('Ana', 'Bruno', 'Carla', 'Daniel', 'Eduarda', 'Fábio', 'Gabriela', 'Henrique', 'Isabela', 'João', 'Larissa', 'Marcos');
  sobrenomes := list_t('Silva', 'Santos', 'Oliveira', 'Souza', 'Rodrigues', 'Ferreira', 'Alves', 'Pereira', 'Lima', 'Gomes', 'Costa', 'Ribeiro');
  descricoes_pagamento := list_t(
    'Plano MindMatch básico', 'Plano MindMatch Premium', 'Plano MindMatch básico família', 'Plano MindMatch premium família',
    'Plamno MindMatch corporativo'
  );
  valores_pagamento := list_tv(24.99, 44.99, 49.99, 119.99, 520.00);

  FOR i IN 1..10 LOOP
    v_nome_completo := nomes(TRUNC(DBMS_RANDOM.VALUE(1, nomes.COUNT + 1))) || ' ' || sobrenomes(TRUNC(DBMS_RANDOM.VALUE(1, sobrenomes.COUNT + 1)));

    v_email_base := LOWER(REPLACE(v_nome_completo, ' ', '.'));
    v_email_final := v_email_base || TRUNC(DBMS_RANDOM.VALUE(10, 999)) || '@emailaleatorio.com';

    INSERT INTO TB_CLIENTE (
      id_cliente,
      nome_cliente,
      email_cliente,
      telefone_cliente,
      valor_medio_pagamento
    ) VALUES (
      i,
      v_nome_completo,
      v_email_final,
      '(11) 9' || LPAD(TRUNC(DBMS_RANDOM.VALUE(80000000, 99999999)), 8, '0'),
      ROUND(DBMS_RANDOM.VALUE(150, 2500), 2)
    );
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('-> 10 registros de clientes realistas inseridos em TB_CLIENTE.');


  FOR i IN 1..20 LOOP
    INSERT INTO TB_CARTAO (
      id_cartao,
      id_cliente,
      numero_cartao,
      cvv_cartao,
      tipo_cartao,
      validade_cartao
    ) VALUES (
      i,
      MOD(i-1, 10) + 1,
      '4' || LPAD(TRUNC(DBMS_RANDOM.VALUE(10000000000000, 99999999999999)), 14, '0') || LPAD(i, 1, '0'),
      LPAD(TRUNC(DBMS_RANDOM.VALUE(1, 999)), 3, '0'),
      CASE TRUNC(DBMS_RANDOM.VALUE(1, 2)) WHEN 1 THEN 'CREDITO' WHEN 2 THEN 'DEBITO' END,
      ADD_MONTHS(TRUNC(SYSDATE), TRUNC(DBMS_RANDOM.VALUE(12, 60)))
    );
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('-> 20 registros de cartões realistas inseridos em TB_CARTAO.');


  FOR i IN 1..50 LOOP
    v_id_cartao_pagamento := TRUNC(DBMS_RANDOM.VALUE(1, 21));

    SELECT id_cliente INTO v_id_cliente_pagamento FROM TB_CARTAO WHERE id_cartao = v_id_cartao_pagamento;

    v_indice_pagamento := TRUNC(DBMS_RANDOM.VALUE(1, descricoes_pagamento.COUNT + 1));

    v_descricao_selecionada := descricoes_pagamento(v_indice_pagamento);
    v_valor_selecionado := valores_pagamento(v_indice_pagamento);

    INSERT INTO TB_PAGAMENTO (
      id_pagamento,
      id_cartao,
      id_cliente,
      valor_pagamento,
      descricao_pagamento,
      data_pagamento
    ) VALUES (
      i,
      v_id_cartao_pagamento,
      v_id_cliente_pagamento,
      ROUND(DBMS_RANDOM.VALUE(10, 850), 2),
      v_descricao_selecionada,
      TRUNC(SYSDATE - DBMS_RANDOM.VALUE(1, 365)) 
    );
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('-> 50 registros de pagamentos realistas inseridos em TB_PAGAMENTO.');

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('> Transações confirmadas (COMMIT).');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('> Ocorreu um erro. As transações foram desfeitas (ROLLBACK).');
    DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;
/
