TRUNCATE evaluation_scores, evaluation, enrollment, fellowship_weights, fellowship_candidates, fellowship, candidate RESTART IDENTITY CASCADE;

INSERT INTO candidate (email, ist_id, name) VALUES
('alice@example.com', 'IST001', 'Alice'),
('bob@example.com', 'IST002', 'Bob'),
('charlie@example.com', 'IST003', 'Charlie'),
('david@example.com', 'IST004', 'David'),
('eva@example.com', 'IST005', 'Eva'),
('frank@example.com', 'IST006', 'Frank'),
('grace@example.com', 'IST007', 'Grace'),
('henry@example.com', 'IST008', 'Henry'),
('irene@example.com', 'IST009', 'Irene'),
('jack@example.com', 'IST010', 'Jack'),
('kate@example.com', 'IST011', 'Kate'),
('leo@example.com', 'IST012', 'Leo'),
('mia@example.com', 'IST013', 'Mia'),
('nick@example.com', 'IST014', 'Nick'),
('olivia@example.com', 'IST015', 'Olivia'),
('paul@example.com', 'IST016', 'Paul'),
('quincy@example.com', 'IST017', 'Quincy'),
('rachel@example.com', 'IST018', 'Rachel'),
('steve@example.com', 'IST019', 'Steve'),
('tina@example.com', 'IST020', 'Tina');

INSERT INTO fellowship (end_date, monthly_value, start_date, name, description) VALUES
('2024-12-31', 1000.00, '2024-01-01', 'Fellowship A', 'Description for Fellowship A'),
('2024-12-31', 1200.00, '2024-01-01', 'Fellowship B', 'Description for Fellowship B'),
('2024-12-31', 1100.00, '2024-01-01', 'Fellowship C', 'Description for Fellowship C'),
('2024-12-31', 1300.00, '2024-01-01', 'Fellowship D', 'Description for Fellowship D'),
('2024-12-31', 1050.00, '2024-01-01', 'Fellowship E', 'Description for Fellowship E'),
('2024-12-31', 1150.00, '2024-01-01', 'Fellowship F', 'Description for Fellowship F'),
('2024-12-31', 1250.00, '2024-01-01', 'Fellowship G', 'Description for Fellowship G'),
('2024-12-31', 1350.00, '2024-01-01', 'Fellowship H', 'Description for Fellowship H'),
('2024-12-31', 1400.00, '2024-01-01', 'Fellowship I', 'Description for Fellowship I'),
('2024-12-31', 1500.00, '2024-01-01', 'Fellowship J', 'Description for Fellowship J'),
('2024-12-31', 1600.00, '2024-01-01', 'Fellowship K', 'Description for Fellowship K'),
('2024-12-31', 1700.00, '2024-01-01', 'Fellowship L', 'Description for Fellowship L'),
('2024-12-31', 1800.00, '2024-01-01', 'Fellowship M', 'Description for Fellowship M'),
('2024-12-31', 1900.00, '2024-01-01', 'Fellowship N', 'Description for Fellowship N'),
('2024-12-31', 2000.00, '2024-01-01', 'Fellowship O', 'Description for Fellowship O'),
('2024-12-31', 2100.00, '2024-01-01', 'Fellowship P', 'Description for Fellowship P'),
('2024-12-31', 2200.00, '2024-01-01', 'Fellowship Q', 'Description for Fellowship Q'),
('2024-12-31', 2300.00, '2024-01-01', 'Fellowship R', 'Description for Fellowship R'),
('2024-12-31', 2400.00, '2024-01-01', 'Fellowship S', 'Description for Fellowship S'),
('2024-12-31', 2500.00, '2024-01-01', 'Fellowship T', 'Description for Fellowship T');

INSERT INTO enrollment (candidate_id, fellowship_id, enrollment_date_time, motivation) VALUES
(1, 1, '2024-01-01 10:00:00', 'Motivation A1'),
(2, 1, '2024-01-01 10:00:00', 'Motivation A2'),
(3, 2, '2024-01-01 10:00:00', 'Motivation B1'),
(4, 2, '2024-01-01 10:00:00', 'Motivation B2'),
(5, 3, '2024-01-01 10:00:00', 'Motivation C1'),
(6, 3, '2024-01-01 10:00:00', 'Motivation C2'),
(7, 4, '2024-01-01 10:00:00', 'Motivation D1'),
(8, 4, '2024-01-01 10:00:00', 'Motivation D2'),
(9, 5, '2024-01-01 10:00:00', 'Motivation E1'),
(10, 5, '2024-01-01 10:00:00', 'Motivation E2'),
(11, 6, '2024-01-01 10:00:00', 'Motivation F1'),
(12, 6, '2024-01-01 10:00:00', 'Motivation F2'),
(13, 7, '2024-01-01 10:00:00', 'Motivation G1'),
(14, 7, '2024-01-01 10:00:00', 'Motivation G2'),
(15, 8, '2024-01-01 10:00:00', 'Motivation H1'),
(16, 8, '2024-01-01 10:00:00', 'Motivation H2'),
(17, 9, '2024-01-01 10:00:00', 'Motivation I1'),
(18, 9, '2024-01-01 10:00:00', 'Motivation I2'),
(19, 10, '2024-01-01 10:00:00', 'Motivation J1'),
(20, 10, '2024-01-01 10:00:00', 'Motivation J2'),
(1, 11, '2024-01-01 10:00:00', 'Motivation K1'),
(2, 11, '2024-01-01 10:00:00', 'Motivation K2'),
(3, 12, '2024-01-01 10:00:00', 'Motivation L1'),
(4, 12, '2024-01-01 10:00:00', 'Motivation L2'),
(5, 13, '2024-01-01 10:00:00', 'Motivation M1'),
(6, 13, '2024-01-01 10:00:00', 'Motivation M2'),
(7, 14, '2024-01-01 10:00:00', 'Motivation N1'),
(8, 14, '2024-01-01 10:00:00', 'Motivation N2'),
(9, 15, '2024-01-01 10:00:00', 'Motivation O1'),
(10, 15, '2024-01-01 10:00:00', 'Motivation O2'),
(11, 16, '2024-01-01 10:00:00', 'Motivation P1'),
(12, 16, '2024-01-01 10:00:00', 'Motivation P2'),
(13, 17, '2024-01-01 10:00:00', 'Motivation Q1'),
(14, 17, '2024-01-01 10:00:00', 'Motivation Q2'),
(15, 18, '2024-01-01 10:00:00', 'Motivation R1'),
(16, 18, '2024-01-01 10:00:00', 'Motivation R2'),
(17, 19, '2024-01-01 10:00:00', 'Motivation S1'),
(18, 19, '2024-01-01 10:00:00', 'Motivation S2'),
(19, 20, '2024-01-01 10:00:00', 'Motivation T1'),
(20, 20, '2024-01-01 10:00:00', 'Motivation T2');

INSERT INTO evaluation (enrollment_id) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14),
(15),
(16),
(17),
(18),
(19),
(20);

INSERT INTO evaluation_scores (scores, scores_key, evaluation_id) VALUES
(15.5, 0, 1),
(18.2, 1, 1),
(12.3, 2, 1),
(10.4, 0, 2),
(16.6, 1, 2),
(14.1, 2, 2),
(19.5, 0, 3),
(17.4, 1, 3),
(20.0, 2, 3),
(13.0, 0, 4),
(18.2, 1, 4),
(15.3, 2, 4),
(11.3, 0, 5),
(16.5, 1, 5),
(14.4, 2, 5),
(19.5, 0, 6),
(17.6, 1, 6),
(20.0, 2, 6),
(14.5, 0, 7),
(16.2, 1, 7),
(18.1, 2, 7),
(10.4, 0, 8),
(13.6, 1, 8),
(15.1, 2, 8),
(19.5, 0, 9),
(18.4, 1, 9),
(20.0, 2, 9),
(13.0, 0, 10),
(17.2, 1, 10),
(15.3, 2, 10),
(11.3, 0, 11),
(16.5, 1, 11),
(14.4, 2, 11),
(19.5, 0, 12),
(17.6, 1, 12),
(20.0, 2, 12),
(14.5, 0, 13),
(16.2, 1, 13),
(18.1, 2, 13),
(10.4, 0, 14),
(13.6, 1, 14),
(15.1, 2, 14),
(19.5, 0, 15),
(18.4, 1, 15),
(20.0, 2, 15),
(13.0, 0, 16),
(17.2, 1, 16),
(15.3, 2, 16),
(11.3, 0, 17),
(16.5, 1, 17),
(14.4, 2, 17),
(19.5, 0, 18),
(17.6, 1, 18),
(20.0, 2, 18),
(14.5, 0, 19),
(16.2, 1, 19),
(18.1, 2, 19),
(10.4, 0, 20),
(13.6, 1, 20),
(15.1, 2, 20);

INSERT INTO fellowship_weights (fellowship_id, weights, weights_key) VALUES
(1, 0.4, 0),
(1, 0.3, 1),
(1, 0.3, 2),
(2, 0.5, 0),
(2, 0.3, 1),
(2, 0.2, 2),
(3, 0.4, 0),
(3, 0.3, 1),
(3, 0.3, 2),
(4, 0.5, 0),
(4, 0.3, 1),
(4, 0.2, 2),
(5, 0.4, 0),
(5, 0.3, 1),
(5, 0.3, 2),
(6, 0.5, 0),
(6, 0.3, 1),
(6, 0.2, 2),
(7, 0.4, 0),
(7, 0.3, 1),
(7, 0.3, 2),
(8, 0.5, 0),
(8, 0.3, 1),
(8, 0.2, 2),
(9, 0.4, 0),
(9, 0.3, 1),
(9, 0.3, 2),
(10, 0.5, 0),
(10, 0.3, 1),
(10, 0.2, 2);

INSERT INTO fellowship_candidates (fellowship_id, candidates_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 3),
(6, 3),
(7, 4),
(8, 4),
(9, 5),
(10, 5),
(11, 6),
(12, 6),
(13, 7),
(14, 7),
(15, 8),
(16, 8),
(17, 9),
(18, 9),
(19, 10),
(20, 10),
(1, 11),
(2, 11),
(3, 12),
(4, 12),
(5, 13),
(6, 13),
(7, 14),
(8, 14),
(9, 15),
(10, 15),
(11, 16),
(12, 16),
(13, 17),
(14, 17),
(15, 18),
(16, 18),
(17, 19),
(18, 19),
(19, 20),
(20, 20);