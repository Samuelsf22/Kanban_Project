use brt2hnkvuzdpzl3xkim9;
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
select * from usuarios;

drop table  if exists categorias;
CREATE TABLE categorias(
    id int auto_increment,
    nombre VARCHAR(255),
    primary key (id)
);

select nombre from categorias;

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

select t.id, t.nombre, e.prioridad, c.nombre, t.fecha_limite
from tareas t
inner join categorias c on c.id = t.categorias_id
inner join etiquetas e on e.id = t.etiqueta_id
where t.estado = 'PENDIENTE';

INSERT INTO tareas (id, nombre, etiqueta_id, categorias_id, fecha_limite, estado)
SELECT ?, ?, e.id, c.id, ?, ?
FROM etiquetas e
INNER JOIN categorias c ON c.nombre = ?
WHERE e.prioridad = ?;

INSERT INTO tareas (id, nombre, etiqueta_id, categorias_id, fecha_limite, estado)
VALUES (?, ?, (SELECT id FROM etiquetas WHERE prioridad = ?), (SELECT id FROM categorias WHERE nombre = ?), ?, ?);

UPDATE tareas
SET nombre = ?, etiqueta_id = (SELECT id FROM etiquetas WHERE prioridad = ?), categorias_id = (SELECT id FROM categorias WHERE nombre = ?), fecha_limite = ?, estado = ?
WHERE id = ?;
UPDATE tareas SET estado = ? WHERE id = ?;

update tareas set nombre = ?, etiqueta_id = ?, categorias_id = ?, fecha_limite = ? where id = ?;

select t.id, t.nombre, e.prioridad, c.nombre, t.fecha_limite
from tareas t
inner join categorias c on c.id = t.categorias_id
inner join etiquetas e on e.id = t.etiqueta_id
where t.estado = 'PENDIENTE';

select * from tareas;
update tareas set estado ='PENDIENTE';

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
INSERT INTO tareaElegida (usuario_id, tarea_id) values (?,?);
delete from tareaElegida where usuario_id = ? and tarea_id = ?;
update etiquetas set estado = ?;
delete from tareaElegida;
SELECT * FROM tareaElegida;

select te.id, t.id, t.nombre, e.prioridad, t.fecha_limite
from tareaElegida te
inner join tareas t on t.id = te.tarea_id
inner join etiquetas e on t.etiqueta_id = e.id
where usuario_id = ? and t.estado = ?;

select te.id, t.nombre, c.nombre
from tareaElegida te
inner join tareas t on t.id = te.tarea_id
inner join categorias c ON t.categorias_id = c.id
where usuario_id = ? and t.estado = 'FINALIZADO';



INSERT INTO usuarios(nombre, apellido, correo, contrasena)
VALUES
('John', 'Doe', 'johndoe@ejemplo.com','contrasena123'),
('Jane', 'Smith', 'janesmith@ejemplo.com','contra123'),
('Mike', 'Johnson', 'mikejohnson@ejemplo.com','seguridad12'),
('Sarah', 'Williams', 'sarahwilliams@ejemplo.com','micontrasena'),
('David', 'Brown', 'davidbrown@ejemplo.com','contrasena456');

INSERT INTO categorias(id, nombre)
VALUES
('categorias1234','Tarea personal'),
('categorias5678','Trabajo'),
('categorias9123','Estudio'),
('categorias9876','Proyectos'),
('categorias5432','Compras');

INSERT INTO etiquetas(id, estado, prioridad)
VALUES
('etiquetas1234','pendiente','alta'),
('etiquetas5678','pendiente','alta'),
('etiquetas9123','pendiente','media'),
('etiquetas9876','pendiente','media'),
('etiquetas5432','pendiente','baja');

INSERT INTO tareas (id, etiqueta_id, categorias_id, fecha_limite)
VALUES
('tareas1234','etiquetas1234','categorias1234','2023-06-30'),
('tareas5678','etiquetas5678','categorias5678','2023-07-15'),
('tareas9123','etiquetas9123','categorias9123','2023-06-25'),
('tareas9876','etiquetas9876','categorias9876','2023-07-05'),
('tareas5432','etiquetas5432','categorias5432','2023-06-28');

SELECT *FROM usuarios;

SELECT* FROM usuarios WHERE usuarios.correo ='johndoe@ejemplo.com' AND usuarios.contrasena='contrasena123';


DROP TABLE usuarios, tareas;