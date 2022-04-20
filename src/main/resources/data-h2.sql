INSERT INTO service_type (id, name_hr, name_de, name_it, name_en, is_deleted) VALUES (1, 'Bike HR', ' Bike DE', 'Bike IT', 'Bike EN',  false);
INSERT INTO service_type (id, name_hr, name_de, name_it, name_en, is_deleted) VALUES (2, 'Eletric Bike HR', 'Eletric Bike DE', 'Eletric Bike IT', 'Eletric Bike EN', false);

INSERT INTO service (id, name, is_enabled, is_deleted, service_type_id) VALUES (1, 'Bike #1', true, false, 1);
INSERT INTO service (id, name, is_enabled, is_deleted, service_type_id) VALUES (2, 'Bike #2', true, false, 1);
INSERT INTO service (id, name, is_enabled, is_deleted, service_type_id) VALUES (3, 'Bike #3', true, false, 1);
INSERT INTO service (id, name, is_enabled, is_deleted, service_type_id) VALUES (4, 'Bike #4', true, false, 1);
INSERT INTO service (id, name, is_enabled, is_deleted, service_type_id) VALUES (5, 'Electric Bike #1', true, false, 2);
INSERT INTO service (id, name, is_enabled, is_deleted, service_type_id) VALUES (6, 'Electric Bike #2', true, false, 2);
INSERT INTO service (id, name, is_enabled, is_deleted, service_type_id) VALUES (7, 'Bike #5', true, false, 1);

INSERT INTO room (id, name, number, uuid, is_deleted) VALUES (1, 'Hotel Room A', '1', '11111', false);
INSERT INTO room (id, name, number, uuid, is_deleted) VALUES (2, 'Hotel Room B', '2', '22222', false);
INSERT INTO room (id, name, number, uuid, is_deleted) VALUES (3, 'Hotel Room C', '3', '33333', true);


--password is: password
INSERT INTO account (id, username, password, role) VALUES (1, 'username', '$2a$12$MVRuLHk44X2SO9EE/gWt8OB4lrosi2KPt8ThNRsHZTxQDFcwooMvG', 'ADMIN');
INSERT INTO account (id, username, password, role) VALUES (2, 'username1', '$2a$12$MVRuLHk44X2SO9EE/gWt8OB4lrosi2KPt8ThNRsHZTxQDFcwooMvG', 'RECEPTIONIST');

INSERT INTO "order" (id,amount,created_at,start_at,end_at,room_id) VALUES (1, 2, '2022-02-27 12:00:00', '2022-02-27 12:00:00', '2022-02-27 13:00:00',1);
INSERT INTO "order" (id,amount,created_at,start_at,end_at,room_id) VALUES (2, 2, '2022-02-27 12:00:00',' 2022-02-27 12:00:00', '2022-02-27 16:00:00',1);
INSERT INTO "order" (id,amount,created_at,start_at,end_at,room_id) VALUES (3, 2, '2022-02-27 12:00:00', '2022-02-27 17:00:00', '2022-02-27 18:00:00',1);

INSERT INTO order_service (order_id,service_id) VALUES (1, 1);
INSERT INTO order_service (order_id,service_id) VALUES (2, 2);
INSERT INTO order_service (order_id,service_id) VALUES (3, 3);

INSERT INTO menu (id, is_deleted, name_de, name_en, name_hr, name_it) VALUES (1, false, 'nameDe', 'nameEn', 'nameHr', 'nameIt');

INSERT INTO menu_item (id, description_de, description_en, description_hr, description_it, is_deleted, name_de, name_en, name_hr, name_it, normative, price, menu_id)  VALUES
(1, '1descDe', '1descEn', '1descHr', '1descIt', false, '1nameDe', '1nameEn', '1nameHr', '1nameIt', '1normative', 22.0, 1);
INSERT INTO menu_item (id, description_de, description_en, description_hr, description_it, is_deleted, name_de, name_en, name_hr, name_it, normative, price, menu_id)  VALUES
(2, '2descDe', '2descEn', '2descHr', '2descIt', false, '2nameDe', '2nameEn', '2nameHr', '2nameIt', '2normative', 49.0, 1);
