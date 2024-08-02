-- Clear existing data
TRUNCATE TABLE enrollment, candidate, fellowship RESTART IDENTITY CASCADE;

-- Insert data into the Fellowship table first
INSERT INTO fellowship (end_date, monthly_value, start_date, name, description) VALUES
                                                                                    ('2025-12-31', 1500.00, '2024-01-01', 'Research Fellow A', 'Supports research in artificial intelligence.'),
                                                                                    ('2025-12-31', 1200.00, '2024-01-01', 'Medical Fellow A', 'Provides support for medical research and training.'),
                                                                                    ('2025-12-31', 1800.00, '2024-01-01', 'Engineering Fellow', 'Focuses on advancements in engineering technologies.'),
                                                                                    ('2025-12-31', 1600.00, '2024-01-01', 'Arts Fellow A', 'Supports research and projects in the arts.'),
                                                                                    ('2025-12-31', 1400.00, '2024-01-01', 'Social Sciences A', 'Provides support for social science research.'),
                                                                                    ('2025-12-31', 2000.00, '2024-01-01', 'Environment Fellow', 'Focuses on environmental research and conservation.'),
                                                                                    ('2025-12-31', 1500.00, '2024-01-01', 'Education Fellow', 'Supports educational research and initiatives.'),
                                                                                    ('2025-12-31', 1700.00, '2024-01-01', 'Economics Fellow', 'Provides support for economics research and development.'),
                                                                                    ('2025-12-31', 1800.00, '2024-01-01', 'Technology Fellow', 'Focuses on technology innovation and research.'),
                                                                                    ('2025-12-31', 1500.00, '2024-01-01', 'Humanities Fellow', 'Supports research in the humanities.'),
                                                                                    ('2025-12-31', 1600.00, '2024-01-01', 'Health Sciences', 'Provides support for health sciences research.'),
                                                                                    ('2025-12-31', 1300.00, '2024-01-01', 'Physics Fellow', 'Focuses on research in physics and related fields.'),
                                                                                    ('2025-12-31', 1500.00, '2024-01-01', 'Chemistry Fellow', 'Supports chemistry research and experiments.'),
                                                                                    ('2025-12-31', 1400.00, '2024-01-01', 'Biology Fellow', 'Provides support for biological research.'),
                                                                                    ('2025-12-31', 1600.00, '2024-01-01', 'Mathematics Fellow', 'Focuses on advancements in mathematics.'),
                                                                                    ('2025-12-31', 1500.00, '2024-01-01', 'Geology Fellow', 'Supports research in geology and earth sciences.'),
                                                                                    ('2025-12-31', 1700.00, '2024-01-01', 'Astronomy Fellow', 'Provides support for astronomical research.'),
                                                                                    ('2025-12-31', 1800.00, '2024-01-01', 'Engineering Fellow B', 'Focuses on advancements in engineering technologies.'),
                                                                                    ('2025-12-31', 1400.00, '2024-01-01', 'Environmental Science', 'Supports research in environmental science.'),
                                                                                    ('2025-12-31', 1500.00, '2024-01-01', 'Public Policy Fellow', 'Provides support for research in public policy.');

-- Insert data into the Candidate table
INSERT INTO candidate (email, ist_id, name) VALUES
                                                ('john.doe@example.com', 'IST00001', 'John Doe'),
                                                ('jane.smith@example.com', 'IST00002', 'Jane Smith'),
                                                ('bob.johnson@example.com', 'IST00003', 'Bob Johnson'),
                                                ('alice.williams@example.com', 'IST00004', 'Alice Williams'),
                                                ('charlie.brown@example.com', 'IST00005', 'Charlie Brown'),
                                                ('david.jones@example.com', 'IST00006', 'David Jones'),
                                                ('emily.davis@example.com', 'IST00007', 'Emily Davis'),
                                                ('frank.miller@example.com', 'IST00008', 'Frank Miller'),
                                                ('grace.lewis@example.com', 'IST00009', 'Grace Lewis'),
                                                ('henry.taylor@example.com', 'IST00010', 'Henry Taylor'),
                                                ('isabella.walker@example.com', 'IST00011', 'Isabella Walker'),
                                                ('jack.hall@example.com', 'IST00012', 'Jack Hall'),
                                                ('karen.martin@example.com', 'IST00013', 'Karen Martin'),
                                                ('luke.thomas@example.com', 'IST00014', 'Luke Thomas'),
                                                ('mia.white@example.com', 'IST00015', 'Mia White'),
                                                ('nathan.harris@example.com', 'IST00016', 'Nathan Harris'),
                                                ('olivia.clark@example.com', 'IST00017', 'Olivia Clark'),
                                                ('peter.garcia@example.com', 'IST00018', 'Peter Garcia'),
                                                ('quinn.anderson@example.com', 'IST00019', 'Quinn Anderson'),
                                                ('rachel.martinez@example.com', 'IST00020', 'Rachel Martinez');

-- Insert data into the Enrollment table
INSERT INTO enrollment (candidate_id, fellowship_id, enrollment_date_time, motivation) VALUES
                                                                                           (1, 1, '2023-11-01 09:00:00', 'Passionate about AI and its applications.'),
                                                                                           (2, 1, '2023-11-02 10:00:00', 'Experienced in AI research and development.'),
                                                                                           (3, 2, '2023-11-03 11:00:00', 'Strong interest in medical research.'),
                                                                                           (4, 3, '2023-11-04 12:00:00', 'Background in engineering and technology.'),
                                                                                           (5, 4, '2023-11-05 13:00:00', 'Creative approach to arts and humanities.'),
                                                                                           (6, 5, '2023-11-06 14:00:00', 'Dedicated to advancing social sciences.'),
                                                                                           (7, 6, '2023-11-07 15:00:00', 'Environmental conservation is my passion.'),
                                                                                           (8, 7, '2023-11-08 16:00:00', 'Focused on improving educational systems.'),
                                                                                           (9, 8, '2023-11-09 17:00:00', 'Researching new economic models.'),
                                                                                           (10, 9, '2023-11-10 18:00:00', 'Advancing technology through innovation.'),
                                                                                           (11, 10, '2023-11-11 19:00:00', 'Exploring new horizons in humanities.'),
                                                                                           (12, 11, '2023-11-12 20:00:00', 'Improving health outcomes through research.'),
                                                                                           (13, 12, '2023-11-13 21:00:00', 'Researching fundamental physics concepts.'),
                                                                                           (14, 13, '2023-11-14 22:00:00', 'Exploring new chemistry techniques.'),
                                                                                           (15, 14, '2023-11-15 23:00:00', 'Researching biological processes.'),
                                                                                           (16, 15, '2023-11-16 09:30:00', 'Advancements in mathematics are key.'),
                                                                                           (17, 16, '2023-11-17 10:30:00', 'Studying geological formations and processes.'),
                                                                                           (18, 17, '2023-11-18 11:30:00', 'Exploring the universe through astronomy.'),
                                                                                           (19, 18, '2023-11-19 12:30:00', 'Engineering advancements are critical.'),
                                                                                           (20, 19, '2023-11-20 13:30:00', 'Researching environmental science for sustainability.');
