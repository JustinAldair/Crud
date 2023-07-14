const userService = require('../service/service');

function getUserById(req, res) {
  const { id_user } = req.params;
  console.log(id_user);
  userService.getUserById(id_user, (error, results) => {
    if (error) {
      console.error('Error al obtener el usuario: ', error);
      res.status(500).send('Error al obtener el usuario');
    } else if (results.length === 0) {
      res.status(404).send('Usuario no encontrado');
    } else {
      const user = results[0];
      res.json(user);
    }
  });
}


function login(req, res) {
  const { usuario, password } = req.body;

  // Verificar si el usuario y la contraseña coinciden en la base de datos
  userService.login(usuario, password, (error, user) => {
    if (error) {
      console.error('Error al realizar el inicio de sesión: ', error);
      res.status(500).send('Error al realizar el inicio de sesión');
    } else if (!user) {
      res.status(401).send('Credenciales inválidas');
    } else {
      res.send({
        message: 'Inicio de sesión exitoso',
        user: {
          id: user.id,
          name: user.nombre,
          email: user.correo
        }
      });
    }
  });
}

//duplicar
function createUser(req, res) {
  const user = req.body;
  userService.createUser(user, (error, results) => {
      if (error) {
          console.error('Error al crear el usuario', error);
          res.status(500).send('Error al crear el usuario');
      } else {
          res.send('Usuario creado correctamente');
      }
  });
}

function createRoute(req, res) {
  const ruta = req.body;
  userService.createRoute(ruta, (error, result) => {
    if(error){
      res.status(500).send('Error al crear la ruta');
    }else{
      res.status(200).json(ruta)

    }
  })
}

function updateRoute(req, res) {
  const { id } = req.params;
  const ruta = req.body;
  userService.updateRoute(id, ruta, (error, result) => {
    if (error) {
      console.error('Error al actualizar la ruta', error);
      res.status(500).send('Error al actualizar la ruta');
    } else {
      res.status(200).send('Ruta actualizada correctamente');
    }
  });
}

function deleteRoute(req, res) {
  const { id } = req.params;
  userService.deleteRoute(id, (error, result) => {
    if (error) {
      console.error('Error al eliminar la ruta', error);
      res.status(500).send('Error al eliminar la ruta');
    } else {
      res.status(200).send('Ruta eliminada correctamente');
    }
  });
}

function getRouteById(req, res) {
  const { id } = req.params;

  // Lógica para obtener la ruta por su ID desde el servicio
  userService.getRouteById(id, (error, result) => {
    if (error) {
      console.error('Error al obtener la ruta: ', error);
      res.status(500).send('Error al obtener la ruta');
    } else if (!result) {
      res.status(404).send('Ruta no encontrada');
    } else {
      res.json(result);
    }
  });
}



module.exports = {
  getUserById,
  getRouteById,
  login,
  createUser,
  createRoute,
  updateRoute,
  deleteRoute
};