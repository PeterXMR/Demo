USE demodb;

DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    uuid            VARCHAR(250) NOT NULL,
    created_at      DATE         NOT NULL,
    updated_at      DATE DEFAULT NULL,
    amount          DECIMAL      NOT NULL,
    supplier_name   VARCHAR(250) NOT NULL,
    supplier_id     INTEGER      NOT NULL,
    customer_name   VARCHAR(250) NOT NULL,
    customer_id     INTEGER      NOT NULL,
    issuing_date    DATE         NOT NULL,
    due_date        DATE         NOT NULL,
    fulfilment_date DATE         NOT NULL,
    payment_form    VARCHAR(250) NOT NULL
);

INSERT INTO invoices (id, uuid, created_at, updated_at, amount, supplier_name, supplier_id, customer_name, customer_id,
                     issuing_date, due_date, fulfilment_date, payment_form)
VALUES (1, uuid(), '2023-10-11', null, 666, 'satoshi_labs', '1', 'Alice', 1000, '2023-10-11', '2023-11-11', '2033-11-11', 'cash'),
       (2, uuid(), '2023-10-11', null, 666.6, 'general_bytes', '2', 'Bob', 1000, '2023-10-11', '2023-11-11', '2023-11-11', 'bank_transfer'),
       (3, uuid(), '2023-10-11', null, 666666, 'vexl', '2', 'Carol', 1000, '2023-10-11', '2023-11-11', '2023-11-11', 'card');