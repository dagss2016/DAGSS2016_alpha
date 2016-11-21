# Proyecto Java EE DAGSS2016

Aplicación Java EE (Web Profile de Java EE 7) "Receta Electrónica"

## Pasos previos

### Instalación de dependencias previas
* IDE Java Netbeans (version 8.x o superior)
  * Decargar e instalar el paquete "Netbeans Java EE" que incluye el servidor de aplicaciones GlassFish 4.x
  * Pueden usarse otros IDEs (Eclipse) pero será necesario instalar manualmente el servidor GlassFish 4.x 
   y (opcionalmente) configurar la integración del IDE con el servidor
* Herramientas de línea de comandos (no estrictamente necesarias, ya las incluye Netbeans)
  * git (control de versiones)
  * maven (gestión de dependencias + construcción del proyecto)
* Servidor de Base de Datos MySQL 
  * Junto con el conector para JDBC: <http://dev.mysql.com/downloads/connector/j/>
  * El conector JDBC de MySQL debe copiarse en la
    carpeta de instalación del ''dominio'' GlassFish (varía según el tipo de instalación)
  * En una instalación Linux ''de usuario'' (no como root) sería en `$HOME/glassfish-4.x.x/glassfish/domains/domain1/lib/`


### Obtener el código de partida
El código de partida está disponible para cada grupo en su respectivo repositorio GitHub

* Puede descargarse el ZIP con la rama `master` completa en <https://github.com/dagss2016/DAGSS2016_NOMBRE_DE_GRUPO/archive/master.zip>
* Puede clonarse desde línea de comandos
```
git clone https://github.com/dagss2016/DAGSS2016_NOMBRE_DE_GRUPO.git
```


### Crear tablas y carga de entidades/usuarios iniciales

* Crear una base de datos "dagss" y un usuario "dagss/dagss" (si no existe) con privilegios totales sobre dicha base de datos

  ```
  mysql -u root -p    pedirá la contraseña de     administrador de MySQL

  mysql> create database dagss;
  mysql> grant all privileges on dagss.* to   dagss@localhost identified by "dagss";
  ```

* Cargar la definición de tablas y usuarios iniciales 

  * moverse a la carpeta `sql` del proyecto 
  ```
  cd DAGSS2016_NOMBRE_DE_GRUPO
  cd sql
  ```
  
  * cargar la definición de las tablas y usuarios
  ```
  mysql -u dagss -p    (pedirá contraseña)
  
  mysql > use dagss;
  mysql > source creacion-tablas.sql
  mysql > source entidades-iniciales.sql
  ```

### Detalles del proyecto: 

Enunciado en <http://ccia.ei.uvigo.es/docencia/DAGSS/1617/practicas/practicaDAGSS2016/>
