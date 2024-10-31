INSERT INTO users (username, hashed_password, type)
VALUES ('admin', '$2a$10$NzIVcH8X7Y4MCohLnquh4.zfYAH.K.tnWi88yRVnQ2MwREuhx7ij2', 'admin')
ON CONFLICT (username) DO NOTHING;
