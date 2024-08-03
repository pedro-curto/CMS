-- Reset all tables
TRUNCATE evaluation_scores, evaluation_weights, evaluation, enrollment, fellowship_weights, fellowship_candidates, fellowship, candidate RESTART IDENTITY CASCADE;

-- Insert synthetic data into the candidate table
INSERT INTO candidate (email, ist_id, name) VALUES
                                                ('alice@example.com', 'IST001', 'Alice'),
                                                ('bob@example.com', 'IST002', 'Bob'),
                                                ('charlie@example.com', 'IST003', 'Charlie');

-- Insert synthetic data into the fellowship table
INSERT INTO fellowship (end_date, monthly_value, start_date, name, description) VALUES
                                                                                    ('2024-12-31', 1000.00, '2024-01-01', 'Fellowship A', 'Description for Fellowship A'),
                                                                                    ('2024-12-31', 1200.00, '2024-01-01', 'Fellowship B', 'Description for Fellowship B');

-- Insert synthetic data into the enrollment table
INSERT INTO enrollment (candidate_id, fellowship_id, enrollment_date_time, motivation) VALUES
                                                                                           (1, 1, '2024-01-01 10:00:00', 'Motivation A1'),
                                                                                           (2, 1, '2024-01-01 10:00:00', 'Motivation A2'),
                                                                                           (3, 1, '2024-01-01 10:00:00', 'Motivation A3'),
                                                                                           (1, 2, '2024-01-01 10:00:00', 'Motivation B1'),
                                                                                           (2, 2, '2024-01-01 10:00:00', 'Motivation B2'),
                                                                                           (3, 2, '2024-01-01 10:00:00', 'Motivation B3');

-- Insert synthetic data into the evaluation table
INSERT INTO evaluation (enrollment_id) VALUES
                                           (1),
                                           (2),
                                           (3),
                                           (4),
                                           (5),
                                           (6);

-- Insert synthetic data into the evaluation_scores table
INSERT INTO evaluation_scores (evaluation_id, scores, scores_key) VALUES
                                                                      (1, 85.5, 0),
                                                                      (1, 90.2, 1),
                                                                      (1, 88.3, 2),
                                                                      (2, 78.4, 0),
                                                                      (2, 82.6, 1),
                                                                      (2, 80.1, 2),
                                                                      (3, 92.5, 0),
                                                                      (3, 87.4, 1),
                                                                      (3, 91.0, 2),
                                                                      (4, 89.0, 0),
                                                                      (4, 88.2, 1),
                                                                      (4, 85.3, 2),
                                                                      (5, 76.3, 0),
                                                                      (5, 81.5, 1),
                                                                      (5, 79.4, 2),
                                                                      (6, 93.5, 0),
                                                                      (6, 89.6, 1),
                                                                      (6, 90.2, 2);

-- Insert synthetic data into the evaluation_weights table
INSERT INTO evaluation_weights (evaluation_id, weights, weights_key) VALUES
                                                                         (1, 0.4, 0),
                                                                         (1, 0.3, 1),
                                                                         (1, 0.3, 2),
                                                                         (2, 0.4, 0),
                                                                         (2, 0.3, 1),
                                                                         (2, 0.3, 2),
                                                                         (3, 0.4, 0),
                                                                         (3, 0.3, 1),
                                                                         (3, 0.3, 2),
                                                                         (4, 0.5, 0),
                                                                         (4, 0.3, 1),
                                                                         (4, 0.2, 2),
                                                                         (5, 0.5, 0),
                                                                         (5, 0.3, 1),
                                                                         (5, 0.2, 2),
                                                                         (6, 0.5, 0),
                                                                         (6, 0.3, 1),
                                                                         (6, 0.2, 2);

-- Insert synthetic data into the fellowship_weights table
INSERT INTO fellowship_weights (fellowship_id, weights, weights_key) VALUES
                                                                         (1, 0.4, 0),
                                                                         (1, 0.3, 1),
                                                                         (1, 0.3, 2),
                                                                         (2, 0.5, 0),
                                                                         (2, 0.3, 1),
                                                                         (2, 0.2, 2);

-- Insert synthetic data into the fellowship_candidates table
INSERT INTO fellowship_candidates (fellowships_id, candidates_id) VALUES
                                                                      (1, 1),
                                                                      (1, 2),
                                                                      (1, 3),
                                                                      (2, 1),
                                                                      (2, 2),
                                                                      (2, 3);
