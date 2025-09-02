-- ==============================
-- Tài khoản admin
-- ==============================
INSERT INTO admins (username, password) VALUES
    ('admin', '$2a$10$7n/3m3l4OeWzYNhWjL7SPO/xoaM4m4S0oJ82F0XAdkPZxyRffRBuC');

-- ==============================
-- Người dùng mẫu
-- ==============================
INSERT INTO users (name, birth_date, gender, facebook_id) VALUES
                                                              ('Lan Nguyen', '1990-05-01', 'Female', 'fb123456'),
                                                              ('Minh Tran', '1985-08-20', 'Male', 'fb654321'),
                                                              ('Hoa Pham', '1992-12-15', 'Female', 'fb987654');

-- ==============================
-- Kết quả tarot mẫu
-- ==============================
INSERT INTO tarot_results (user_name, detail_card1, detail_card2, detail_card3, summary) VALUES
                                                                                             ('Lan Nguyen', 'Lá The Fool: Khởi đầu mới', 'Lá The Magician: Sức mạnh', 'Lá The Lovers: Lựa chọn tình cảm', 'Lan sẽ có một cơ hội mới đầy hứng khởi và cần quyết định đúng'),
                                                                                             ('Minh Tran', 'Lá The Emperor: Quyền lực', 'Lá The Tower: Thay đổi đột ngột', 'Lá The Star: Hy vọng', 'Minh sẽ trải qua thử thách nhưng vẫn giữ được hy vọng'),
                                                                                             ('Hoa Pham', 'Lá The Moon: Bí ẩn', 'Lá The Sun: Thành công', 'Lá The Hanged Man: Nhìn nhận lại', 'Hoa sẽ nhận ra điều quan trọng để đạt thành công');
