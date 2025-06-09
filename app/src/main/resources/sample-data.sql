-- Insertion de trajets de test
INSERT INTO journeys (id, train_number, departure_station_code, arrival_station_code, departure_time, arrival_time, capacity) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'TGV001', 'PAR', 'LYO', '2024-01-15 08:00:00', '2024-01-15 10:00:00', 50),
('550e8400-e29b-41d4-a716-446655440002', 'TGV002', 'LYO', 'MAR', '2024-01-15 11:00:00', '2024-01-15 12:30:00', 30),
('550e8400-e29b-41d4-a716-446655440003', 'TER003', 'PAR', 'LIL', '2024-01-15 14:00:00', '2024-01-15 15:00:00', 80),
('550e8400-e29b-41d4-a716-446655440004', 'TGV004', 'PAR', 'REN', '2024-01-15 16:00:00', '2024-01-15 18:15:00', 0); 