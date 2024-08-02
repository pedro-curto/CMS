-- Insert candidates
INSERT INTO candidate (id, email, ist_id, name) VALUES
(1, 'alice@example.com', 'IST12345', 'Alice Johnson'),
(2, 'bob@example.com', 'IST67890', 'Bob Smith'),
(3, 'carol@example.com', 'IST11223', 'Carol White');

-- Insert fellowships
INSERT INTO fellowship (id, end_date, monthly_value, start_date, name, description) VALUES
(1, '2024-12-31', 1500.00, '2024-01-01', 'Research Fellowship', 'A fellowship for research purposes.'),
(2, '2024-06-30', 2000.00, '2024-01-01', 'Teaching Fellowship', 'A fellowship for teaching purposes.');

-- Insert enrollments
INSERT INTO enrollment (id, candidate_id, fellowship_id, enrollment_date_time, motivation) VALUES
(1, 1, 1, '2024-01-05T10:00:00', 'I am interested in research and have the necessary skills.'),
(2, 2, 2, '2024-02-10T14:30:00', 'I want to enhance my teaching skills and contribute to education.'),
(3, 3, 1, '2024-03-15T09:15:00', 'I have a strong background in research and a passion for discovery.');

-- Ensure data is inserted correctly
SELECT * FROM candidate;
SELECT * FROM fellowship;
SELECT * FROM enrollment;

