CREATE USER AzureDiamond WITH PASSWORD 'hunter2';

CREATE TABLE test (
	bla TEXT,
	blubb TEXT
);

INSERT INTO test (bla, blubb) VALUES ('Hallo', 'Kekse');

GRANT SELECT ON ALL TABLES IN SCHEMA public to AzureDiamond;
