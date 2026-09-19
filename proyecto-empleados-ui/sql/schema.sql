-- Base de datos para el proyecto de gestión de empleados.

CREATE DATABASE IF NOT EXISTS proyecto_empleados;

USE proyecto_empleados;

-- id:
-- INT es suficiente para identificar a los empleados de una empresa pequeña.
-- AUTO_INCREMENT permite que MariaDB asigne el identificador automáticamente.

-- nombre_completo:
-- VARCHAR(100) permite almacenar nombres completos largos sin reservar
-- una cantidad excesiva de caracteres, es más por sio acaso sale un nombre largo y no tener que andar con limitaciones.

-- departamento:
-- VARCHAR(60) porque el departamento es texto libre y no existe
-- una lista cerrada de departamentos.

-- salario:
-- DECIMAL(10,2) permite almacenar cantidades monetarias con centavos
-- sin las imprecisiones propias de FLOAT o DOUBLE, y con 10 decimales para que no sean tan corto o pobre.

-- fecha_contratacion:
-- DATE porque solo necesitamos almacenar la fecha y no una hora.

-- activo:
-- BOOLEAN porque únicamente existen dos estados: activo o inactivo.
-- Entonces un empleado nuevo se registra como activo.

CREATE TABLE empleados (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(100) NOT NULL,
    departamento VARCHAR(60) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    fecha_contratacion DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    CHECK (salario > 0)
);