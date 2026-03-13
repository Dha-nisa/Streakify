CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE habits (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    target_days_per_week INT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_habits_users
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE habit_logs (
    id BIGSERIAL PRIMARY KEY,
    habit_id BIGINT NOT NULL,
    log_date DATE NOT NULL,
    completed BOOLEAN NOT NULL,
    CONSTRAINT fk_habit_logs_habits
        FOREIGN KEY (habit_id)
        REFERENCES habits(id)
        ON DELETE CASCADE,
    CONSTRAINT uq_habit_logs_habit_date
        UNIQUE (habit_id, log_date)
);