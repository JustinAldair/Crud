const db = require('../config/connection');

function getUserById(id, callback) {
  console.log(id);
  const query = 'SELECT * FROM usuarios WHERE id_usuario = ?';
  db.query(query, [id], callback);
}

function login(usuario, password, callback) {
  const query = 'SELECT * FROM usuarios WHERE usuario = ? AND password = ?';
  db.query(query, [usuario, password], (error, results) => {
    if (error) {
      console.error('Error al realizar el inicio de sesión: ', error);
      callback(error, null);
    } else if (results.length === 0) {
      callback(false, null);
    } else {
      const user = {
        id: results[0].id_usuario,
        nombre: results[0].nombre,
        correo: results[0].correo
      };
      callback(null, user);
    }
  });
}

function createUser(user, callback) {
  const query = 'INSERT INTO usuarios (nombre, usuario, password, correo, telefono, id_nivel, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)';
  const values = [user.nombre, user.usuario, user.password, user.correo, user.telefono, 2, user.tipo_usuario];

  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      // Aquí puedes devolver el ID del usuario recién insertado o cualquier otro valor que desees
      callback(null, result.insertId);
    }
  });
}

//Rutas
function createRoute(ruta, callback) {
  const query = 'INSERT INTO rutas (Seccion, Destino, Dirrecion, Longitud, Latitud, Encargado) VALUES (?, ?, ?, ?, ?, ?)';
const values = [ruta.Seccion, ruta.Destino, ruta.Direccion, parseFloat(ruta.Longitud), parseFloat(ruta.Latitud), ruta.Encargado];


  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      // Aquí puedes devolver el ID del usuario recién insertado o cualquier otro valor que desees
      callback(null, result.id_Destinos);
    }
  });
}

//Actualizar ruta
function updateRoute(id, ruta, callback) {
  const query = 'UPDATE rutas SET Seccion = ?, Destino = ?, Dirrecion = ?, Longitud = ?, Latitud = ?, Encargado = ? WHERE id_Destinos = ?';
  const values = [ruta.Seccion, ruta.Destino, ruta.Direccion, parseFloat(ruta.Longitud), parseFloat(ruta.Latitud), ruta.Encargado, id];

  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      // Aquí puedes devolver cualquier resultado que desees
      callback(null, result);
    }
  });
}


//Eliminar por id:
function deleteRoute(id, callback) {
  const query = 'DELETE FROM rutas WHERE id_Destinos = ?';
  const values = [id];

  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      // Aquí puedes devolver cualquier resultado que desees
      callback(null, result);
    }
  });
}

//Optener
function getRouteById(id, callback) {

  const query = 'SELECT * FROM rutas WHERE id_Destinos = ?';
  const values = [id];

  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      // Aquí puedes devolver el resultado de la consulta
      callback(null, result);
    }
  });
}




module.exports = {
  getUserById,
  login,
  createUser,
  createRoute,
  getRouteById,
  deleteRoute,
  updateRoute,


};
