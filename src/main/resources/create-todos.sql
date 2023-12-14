USE todos;

CREATE TABLE todo_list (
    todo_list_id BIGINT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(255) NOT NULL,
    CONSTRAINT PK_todo_list PRIMARY KEY (todo_list_id),
    CONSTRAINT UQ_todo_list_name UNIQUE (name)
);

CREATE TABLE todo_item (
    todo_item_id BIGINT NOT NULL AUTO_INCREMENT,
    todo_list_id BIGINT NOT NULL,
    task NVARCHAR(1024) NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT 0,
    CONSTRAINT PK_todo_item PRIMARY KEY (todo_item_id),
    CONSTRAINT FK_todo_item_todo_list_id
        FOREIGN KEY (todo_list_id) REFERENCES todo_list (todo_list_id)
);

-- Password left blank below, fill in with desired password
CREATE USER IF NOT EXISTS 'todo' IDENTIFIED BY '';
GRANT DELETE, EVENT, EXECUTE, INSERT, SELECT, SHOW VIEW, TRIGGER, UPDATE ON todos.* TO 'todo';
