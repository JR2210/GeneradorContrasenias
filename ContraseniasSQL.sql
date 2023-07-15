create schema if not exists contrasenias;

create table if not exists tContraseña(
	nId int auto_increment primary key,
	cContraseña varchar(30),
	cNombre varchar(120)
);

DELIMITER //

CREATE TRIGGER antes_de_insertar_tContraseña
BEFORE INSERT ON tContraseña
FOR EACH ROW
BEGIN
    IF NEW.cContraseña = '' OR NEW.cNombre = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se pueden insertar filas con campos vacíos.';
    END IF;
    
END //

DELIMITER ;