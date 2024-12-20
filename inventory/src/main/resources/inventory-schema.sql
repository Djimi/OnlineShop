    CREATE TABLE IF NOT EXISTS item(
        id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name text NOT NULL,
        type text NOT NULL,
        description text NOT NULL
    );

    CREATE TABLE IF NOT EXISTS country(
        iso text primary key,
        name text NOT NULL UNIQUE
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
        amount int NOT NULL CHECK(amount >= 0),
        version int,
        CONSTRAINT pk PRIMARY KEY (item_id, warehouse_id)
    );
