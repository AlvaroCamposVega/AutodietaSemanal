# autodieta-semanal
Autodieta Semanal Versión 1.0 es una aplicación web que, en su fase actual, te permite tener una dieta nueva aleatoria (cumpliendo una serie de condiciones) cada semana como usuario. Como administrador, te permite administrar las cuentas de usuario normales registradas en el sistema.
## Condiciones de aleatorización de una dieta
Las condiciones que se deben cumplir en la aleatorización de una dieta son las siguientes:
* Una misma dieta no puede aparecer dos días seguidos.
* Las comidas libres solo pueden aparecer los sábados y los domingos.
* Las comidas libres tienen más probabilidades de aparecer en la comida y en la cena.
## Login
La ventana de login es la que se muestra al entrar en la raíz de la aplicación. Dispone de dos campos para iniciar sesión con usuario ya creado, si no dispones de uno, puedes hacer click en el enlace de registro para crear una cuenta nueva.
Si la autenticación falla, se mostrará un mensaje de error y se volverán a pedir los datos.

![Login](/Capturas/login.PNG)

![Login Error](/Capturas/loginError.PNG)

## Registro
La ventana de registro ofrece la posibilidad de registrarse en el sistema y obtener una dieta. Si el nombre escogido ya está en uso por otro usuario se notificará con un mensaje de error.

![registro](/Capturas/registro.PNG)

![registro Error](/Capturas/registroError.PNG)

## Inicio
Si los datos introducidos en login son correctos, la aplicación se encargará de determinar si el usuario tiene privilegio de administrador o de usuario en el sistema y de enviar a su correspondiente sección.
### Usuario
En esta versión, el usuario sólo puede realizar consultar su dieta y las recetas que la componen. Se le mostrará una ventana con su dieta y la fecha en la que expira, además, dispondrá de un enlace en la parte superior derecha para cerrar la sesión. Si el usuario hace click sobre una receta se le redirigirá a una página donde podrá consultar los ingredientes y la descripción de esta.

![Ventana del Usuario](/Capturas/usuario.PNG)

![Receta](/Capturas/receta.PNG)

### Administrador
Un usuario de tipo administrador dispondrá de una ventana con un CRUD de los usuarios registrados en el sistema. Un usuario administrador *NO* dispone de una dieta, ya que su rol es administrar.
Un usuario administrador solo puede administrar usuarios con el rol de usuario, los usuarios administradores no se mostrarán en el listado.
Si el usuario administrador abandona su página de administración (excepto las funciones que esta ofrece) su sesión se cerrará, lo que implicará que este deba de iniciar sesión de nuevo.

![Ventana del administrador](/Capturas/administrador.PNG)

#### Añadir usuario al sistema
En la página del administrador hay un formulario el cual podrá rellenar para dar de alta un usuario nuevo en el sistema de cualquier tipo. Al igual que en el registro, si el nombre ya está cogido se le enviará un mensaje de error y se le marcará el campo de nombre de usuario como erróneo, ocurre igual con el campo de fecha de expiración de la dieta, si se intenta dar de alta un usuario con una fecha *IGUAL* o *INFERIOR* a el día que se quiere dar de alta (hoy), se enviará otro mensaje de error. Para facilitar la selección de la fecha, se pondrá por defecto la fecha de la semana siguiente, que es la que debería de ponerse ya que las dietas caducan cada semana (no es porque no se pueda, este es el objetivo de la aplicación, si pones otra fecha estás ignorando este principio).

![Añadir Error](/Capturas/anyadirError.PNG)

#### Modificar usuario
La ventana de modificar usuario permite modificar cualquier dato del usuario seleccionado, siempre y cuando los datos sean correctos y no existan ya en el sistema (en el caso del nombre).

![Modificar usuario](/Capturas/modificar.PNG)

#### Eliminar usuario
Para eliminar a un usuario no se envía a otra ventana, simplemente se pregunta para confirmar el borrado de este.

![Eliminar usuario](/Capturas/eliminar.PNG)

## Enlaces
* [Entorno de desarrollo](https://github.com/AlvaroCamposVega/autodieta-semanal/tree/desarrollo)
* [Versión 1.1](https://github.com/AlvaroCamposVega/autodieta-semanal/tree/desarrollo-v1.1)
* [Versión 2.0](https://github.com/AlvaroCamposVega/autodieta-semanal/tree/desarrollo-v2.0)
