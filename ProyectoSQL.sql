drop table  if exists usuarios;
CREATE TABLE usuarios(
    id int AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    primary key (id),
    unique uq_correo(correo)
);
INSERT INTO usuarios(nombre, apellido, correo, contrasena)
VALUES
    ('John', 'Doe', 'johndoe@ejemplo.com','contrasena123'),
    ('Jane', 'Smith', 'janesmith@ejemplo.com','contra123'),
    ('Mike', 'Johnson', 'mikejohnson@ejemplo.com','seguridad12'),
    ('Sarah', 'Williams', 'sarahwilliams@ejemplo.com','micontrasena'),
    ('David', 'Brown', 'davidbrown@ejemplo.com','contrasena456');


drop table  if exists categorias;
CREATE TABLE categorias(
    id int auto_increment,
    nombre VARCHAR(255),
    primary key (id)
);
INSERT INTO categorias (nombre) VALUES
('Académico'),
('Político'),
('Entretenimiento'),
('Deportes'),
('Tecnología'),
('Moda'),
('Salud'),
('Gastronomía'),
('Arte'),
('Ciencia'),
('Automóviles'),
('Viajes'),
('Música'),
('Negocios'),
('Medio ambiente');


drop table  if exists etiquetas;
CREATE TABLE etiquetas(
    id int auto_increment,
    prioridad ENUM('ALTA', 'MEDIA', 'BAJA'),
    primary key (id)
);
select * from etiquetas;

insert into etiquetas (prioridad) VALUES ('BAJA'),
                                         ('MEDIA'),
                                         ('ALTA');

drop table  if exists tareas;
CREATE TABLE tareas
(
    id            VARCHAR(10) PRIMARY KEY,
    nombre        VARCHAR(255),
    etiqueta_id   integer not null ,
    categorias_id integer not null,
    fecha_limite  DATE,
    estado enum('PENDIENTE','ELEGIDO','FINALIZADO'),
    FOREIGN KEY (etiqueta_id) REFERENCES etiquetas (id),
    FOREIGN KEY (categorias_id) REFERENCES categorias (id)
);
drop table  if exists tareaElegida;
create table tareaElegida(
    id int auto_increment not null,
    usuario_id int not null,
    tarea_id varchar(10),
    primary key (id),
    unique u_tarea(tarea_id),
    FOREIGN KEY fk_usuario (usuario_id) REFERENCES usuarios (id),
    FOREIGN KEY fk_tarea   (tarea_id) REFERENCES tareas (id)
);