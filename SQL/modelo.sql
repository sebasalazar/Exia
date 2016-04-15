BEGIN TRANSACTION;

DROP TABLE IF EXISTS datos CASCADE;
CREATE TABLE datos (
	pk bigserial NOT NULL,
	fecha timestamp DEFAULT NOW(),
	ip varchar(255) NOT NULL,
	codigo int NOT NULL,
	nombre varchar(255) NOT NULL,
	rut int NOT NULL,
	valor numeric NOT NULL DEFAULT '0',
	latitud double precision NOT NULL,
	longitud double precision NOT NULL,
	UNIQUE (codigo),
	PRIMARY KEY (pk)
);
CREATE INDEX datos_rut_idx ON datos(rut);


COMMIT;
