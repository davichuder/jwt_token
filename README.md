# Refresh Token con Cookies
Este mini proyecto es para aprender a utilizar la funcionalidad de los JSON Web Tokens en una API Rest y mejorar el manejo de Spring Security.

## Introducción

La API tiene como objetivo proporcionar una forma segura y eficiente de autenticación y autorización para aplicaciones web y móviles.
## Características
La API cuenta con las siguientes funcionalidades:

- Registro: Los usuarios pueden registrarse y conceder permisos.
- Logueo: Los usuarios pueden obtener los tokens de autenticación.
- Actualización de tokens: Los JSON Web Tokens se actualizan automáticamente para garantizar la seguridad.
- Deslogueo: Los usuarios pueden cerrar sesión eliminando los tokens.
- Consulta sin registro: Los usuarios pueden realizar consultas sin necesidad de registrarse.
- Consulta con permiso de usuario: Los usuarios registrados pueden realizar consultas con permiso de usuario.
- Consulta con permiso de moderador: Los usuarios registrados con permisos de moderador pueden realizar consultas con permiso de moderador.
- Consulta con permiso de administrador: Los usuarios registrados con permisos de administrador pueden realizar consultas con permiso de administrador.

No se realiza un CRUD debido a que no es el punto principal de la misma.
## Tecnologías

**Backend:** JAVA, Springboot

**Base de Datos:** MySQL

**Editor de Código:** Visual Studio Code

## Referencias de API

#### Registro

```http
  POST /api/auth/signup
```
|Parametro|Tipo|Descripción y extra|
|:-|:-|:-|
|`username`|`string`|**Requerido**|
|`email`|`string`|**Requerido**|
|`password`|`string`|**Requerido**|
|`roles`|`string[]`|**Requerido** Opciones: "admin", "mod", "user"|

#### Logueo
```http
  POST /api/auth/signin
```
|Parametro|Tipo|Descripción y extra|
|:-|:-|:-|
|`username`|`string`|**Requerido**|
|`password`|`string`|**Requerido**|

#### Actualizacion de tokens
```http
  POST /api/auth/refreshtoken
```
|Cookie|Tipo|Ruta|Descripción|
|:-|:-|:-|:-|
|`jwt-refresh`|`string`|`/api/auth/refreshtoken`|**Requerido** JWT Refresh Token|

#### Deslogueo
```http
  POST /api/auth/signout
```

#### Test de roles
```http
  GET /api/test/all
  GET /api/test/user
  GET /api/test/mod
  GET /api/test/admin
```
|Cookie|Tipo|Ruta|Descripción|
|:-|:-|:-|:-|
|`jwt-access`|`string`|`/api`|**Requerido** JWT Access Token|


## Correr localmente

Clonar el proyecto

```bash
git clone https://github.com/davichuder/jwt_token
```

Ejecutar el script de MySQL para la creacion de la base de datos

Ubicado en 
```bash
"script\create_db.sql"
```

Abrir la carpeta del proyecto con VS Code, y abrir al archivo
```bash
"JwtTokenApplication.java"
```
Luego puede clickear sobre "Run" que aparecera sobre el metodo "main"
O dirigirse a la pestaña "Run" y luego clickear sobre "Start Debugging"

Una vez hecho esto ingrese a la siguiente direccion, y pruebe las funcionalidades:
```bash
http://localhost:8081/
```
## Autor
- [David Rolón](https://www.linkedin.com/in/david-emanuel-rolon/)

## Referencias
https://www.bezkoder.com/spring-security-refresh-token/

https://github.com/davichuder/Challenge-4---Foro-Alura

https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html

https://medium.com/@espinozajge/protegiendo-tu-aplicaci%C3%B3n-web-con-spring-security-y-autenticaci%C3%B3n-basada-en-tokens-jwt-1321cbe4c4c3
