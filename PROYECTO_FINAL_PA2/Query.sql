INSERT INTO DEPARTAMENTOS VALUES(1,'Contabilidad', 'AAAA');
INSERT INTO DEPARTAMENTOS VALUES(2,'Finanzas', 'AAAA');
INSERT INTO DEPARTAMENTOS VALUES(3,'RRHH', 'AAAA');

INSERT INTO PROYECTOS VALUES(1,'1','0805200000632','Lionel Messi','Restructruracion 1','1');
INSERT INTO PROYECTOS VALUES(2,'1','0805200000632','Lionel Messi','Restructruracion 2','2');
INSERT INTO PROYECTOS VALUES(3,'1','0805200000632','Lionel Messi','Restructruracion 3','3');

INSERT INTO ROLES VALUES(1,'Administrador','AAA');
INSERT INTO ROLES VALUES(2,'Consulta','AAA');

INSERT INTO USUARIOS VALUES(1,'Ronaldo','example@gmail.com','0850200005200','Cristiano','C_Ronaldo',1,2);
INSERT INTO USUARIOS VALUES(2,'Mbappe','example@gmail.com','0850200005200','Junior','B_Junior',1,2);
INSERT INTO USUARIOS VALUES(3,'Junior','example@gmail.com','0850200005200','Neymar','J_Neymar',1,2);

INSERT INTO TIPO_COORDINADORES VALUES(1,'Coordinador Profesional', 'AAAA');
INSERT INTO TIPO_COORDINADORES VALUES(2,'Coordinador Tecnico', 'AAAA');
INSERT INTO TIPO_COORDINADORES VALUES(3,'Coordinador General', 'AAAA');

SELECT * FROM DEPARTAMENTOS;

ALTER TABLE proyectos_coordinadores ADD CONSTRAINT FK_PRY_COORD_PROYECTO
      FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTOS (ID_PROYECTO);
      
ALTER TABLE proyectos_coordinadores ADD CONSTRAINT FK_PRY_COORD_TPCOORDINADOR
      FOREIGN KEY (ID_TIPO_COORDINADOR) REFERENCES TIPO_COORDINADORES (ID_TIPO_COORDINADOR);
      
ALTER TABLE proyectos_coordinadores ADD CONSTRAINT FK_PRY_USUARIOS
      FOREIGN KEY (ID_USUARIO) REFERENCES USUARIOS (ID_USUARIO);   
      
---------------------------------------------------------------------------------------------------------------------      
ALTER TABLE proyectos_supervisiones ADD CONSTRAINT FK_PRY_SUPER_PROYECTO
      FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTOS (ID_PROYECTO);
      
ALTER TABLE proyectos_supervisiones ADD CONSTRAINT FK_PRY_SUPER_TPCOORDINADOR
      FOREIGN KEY (ID_TIPO_COORDINADOR) REFERENCES TIPO_COORDINADORES (ID_TIPO_COORDINADOR);
      
ALTER TABLE proyectos_supervisiones ADD CONSTRAINT FK_PRY_SUPER_SUPERVISION
      FOREIGN KEY (ID_SUPERVISION) REFERENCES SUPERVISIONES (ID_SUPERVISION);      
      
      
      
      
      