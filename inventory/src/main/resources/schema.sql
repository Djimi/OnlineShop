    CREATE TABLE IF NOT EXISTS item(
        id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name text NOT NULL,
        description text NOT NULL,
        type text NOT NULL,
        created_at timestamp,
        created_by text,
        updated_at timestamp,
        updated_by text

    );

    CREATE TABLE IF NOT EXISTS country(
        iso text primary key,
        name text NOT NULL
    );

    CREATE TABLE IF NOT EXISTS warehouse(
        id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        country_iso text REFERENCES country(iso) NOT NULL,
        name text NOT NULL,
        address text NOT NULL
    );

    CREATE TABLE IF NOT EXISTS inventory(
        item_id int REFERENCES item(id),
        warehouse_id int REFERENCES warehouse(id) NOT NULL,
        amount int NOT NULL,
        version bigint,
        CONSTRAINT pk PRIMARY KEY (item_id, warehouse_id)
    );
