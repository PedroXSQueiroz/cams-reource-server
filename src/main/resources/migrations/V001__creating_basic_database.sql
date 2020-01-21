CREATE TABLE server(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name NVARCHAR(256) NOT NULL,
	address NVARCHAR(256) NOT NULL,
	node_key NVARCHAR(256) NOT NULL
);

CREATE TABLE client(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name NVARCHAR(256) NOT NULL,
	address NVARCHAR(256) NOT NULL,
	node_key NVARCHAR(256) NOT NULL
);

CREATE TABLE cam(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name NVARCHAR(256) NOT NULL,
	address NVARCHAR(256) NOT NULL,
	node_key NVARCHAR(256) NOT NULL,
	
	id_client INT NOT NULL,
	CONSTRAINT 	FK_Cam_Client
		FOREIGN KEY (id_client)
		REFERENCES client(id)
);