CREATE TABLE tipohabitaciones(
	codigotipo VARCHAR(10) NOT NULL,
	nombretipo VARCHAR(50) NOT NULL,
	descripciontipo VARCHAR(150) NOT NULL
);

ALTER TABLE tipohabitaciones
ADD CONSTRAINT pk_idtipo
PRIMARY KEY(codigotipo);

ALTER TABLE tipohabitaciones
ADD CONSTRAINT uq_nombretipo
UNIQUE(nombretipo);

CREATE TABLE  vendedores(
	dniv INT NOT NULL,
	nombrev VARCHAR(50) NOT NULL,
	direccionv VARCHAR(150) NOT NULL,
	telefonov INT NOT NULL,
	observacionesv VARCHAR(150),
	sueldov FLOAT NOT NULL
);

ALTER TABLE vendedores
ADD CONSTRAINT pk_dniv
PRIMARY KEY(dniv);

CREATE TABLE  clientes(
	dnic INT NOT NULL,
	nombrec VARCHAR(50) NOT NULL,
	fechanac DATE NOT NULL,
	lugarnac VARCHAR(100) NOT NULL,
	sexoc CHAR(1) NOT NULL,
	observacionc VARCHAR(100)
);

ALTER TABLE clientes
ADD CONSTRAINT pk_dnic
PRIMARY KEY(dnic);


CREATE TABLE  habitaciones(
	codigohab VARCHAR(5) NOT NULL,
	numerocamas INT NOT NULL,
	descripcionh VARCHAR(150) NOT NULL,
	precioh FLOAT NOT NULL,
	tipoh VARCHAR(10) NOT NULL,
	observacionesh VARCHAR(150)
);


ALTER TABLE habitaciones
ADD CONSTRAINT pk_codigohab
PRIMARY KEY(codigohab);

ALTER TABLE habitaciones
ADD CONSTRAINT fk_codigohab
FOREIGN KEY(tipoh)
REFERENCES tipohabitaciones(codigotipo);


CREATE TABLE  alquileres(
	codigoalq VARCHAR(10) NOT NULL,
	precioalq FLOAT NOT NULL,
	fechaent DATE NOT NULL,
	fechaSal DATE NOT NULL,
	observaciones VARCHAR(150) ,
	vendedor INT NOT NULL,
	cliente INT NOT NULL,
	habitacion VARCHAR(10) NOT NULL
);


ALTER TABLE alquileres
ADD CONSTRAINT pk_codigoalq
PRIMARY KEY(codigoalq);

ALTER TABLE alquileres
ADD CONSTRAINT fk_vendedor
FOREIGN KEY(vendedor)
REFERENCES vendedores(dniv);


ALTER TABLE alquileres
ADD CONSTRAINT fk_clientes
FOREIGN KEY(cliente)
REFERENCES clientes(dnic);

ALTER TABLE alquileres
ADD CONSTRAINT fk_habitacion
FOREIGN KEY(habitacion)
REFERENCES habitaciones(codigohab);
