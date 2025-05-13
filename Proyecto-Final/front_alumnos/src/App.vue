<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
const alumnos = ref([]);
const nuevoAlumno = ref({
  nombre: '',
  numeroControl: '',
  apellidos: '',
  carrera: '',
  semestre: '',
  grupo: '',
  correo: '',
  telefono: '',
  imagenURL: ''
});

const editado = ref(false);


const cargarAlumnos = async () => {
  const response = await axios.get('http://localhost:8081/alumnos/traer-alumnos')
  alumnos.value = response.data;
  console.log(alumnos.value);

}
const AgregarAlumno = async () => {
  if (editado.value) {
    await axios.put(`http://localhost:8081/alumnos/editar-alumno/${nuevoAlumno.value.id}`, nuevoAlumno.value);


  } else {
    await axios.post('http://localhost:8081/alumnos/insertar-alumno', nuevoAlumno.value);
    Swal.fire({
      icon: 'success',
      title: 'Alumno agregado',
      text: 'El alumno ha sido agregado correctamente',
      showConfirmButton: false,
      timer: 1500
    });
  }



  await cargarAlumnos();
  nuevoAlumno.value = {
  nombre: '',
  numeroControl: '',
  apellidos: '',
  carrera: '',
  semestre: '',
  grupo: '',
  correo: '',
  telefono: '',
  imagenURL: ''
  };

}

const EditarAlumno = (alumno) => {
  Object.assign(nuevoAlumno.value, alumno);
  editado.value = true
}

const EliminarAlumno = async (id) => {

  Swal.fire({
    title: '¿Estás seguro?',
    text: "¡No podrás revertir esto!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Sí, eliminarlo!'
  }).then(async (result) => {
    if (result.isConfirmed) {
      await eliminarAlumno(id);
      Swal.fire(
        'Eliminado!',
        'El alumno ha sido eliminado.',
        'success'
      )
    }
  })








  const eliminarAlumno = async (id) => {
    try {

      await axios.delete(`http://localhost:8081/alumnos/eliminar-alumno/${id}`);
      Swal.fire({
        icon: 'success',
        title: 'Alumno eliminado',
        text: 'El alumno ha sido eliminado correctamente',
        showConfirmButton: false,
        timer: 1500
      });
      console.log('Alumno eliminado:', id);
      await cargarAlumnos();
    }
    catch (error) {
      console.error('Error al eliminar el alumno:', error);
      Swal.fire({
        icon: 'error',
        title: 'Error al eliminar el alumno',
        text: 'No se pudo eliminar el alumno.',
      });
    }
  }
}





onMounted(cargarAlumnos);
//modal para confirmar la eliminacion
</script>

<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12 mt-4">

        <div class="card shadow p-4 mb-4">
          <img class="Logo" src="https://www.teahub.io/photos/full/290-2909280_logo-del-tecnologico-nacional-de-mexico.png" alt=""><h2 class="text-center">Formulario de Alumnos <img class="Tec" src="https://www.tlaxiaco.tecnm.mx/wp-content/uploads/2018/10/logotectlaxiaco.png" alt="" >
          </h2>
          <form @submit.prevent="AgregarAlumno">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="numerodecontrol" class="form-label">Numero de control</label>
                <input type="text" class="form-control" id="numerodecontrol" v-model="nuevoAlumno.numeroControl" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" v-model="nuevoAlumno.nombre" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="apellidos" class="form-label">Apellidos</label>
                <input type="text" class="form-control" id="apellidos" v-model="nuevoAlumno.apellidos" required>
              </div>
                <div class="col-md-6 mb-3">
                <label for="correo" class="form-label">Correo</label>
                <input type="text" class="form-control" id="correo" v-model="nuevoAlumno.correo">
              </div>
              <div class="col-md-6 mb-3">
                <label for="carrera" class="form-label">Carrera</label>
                <input type="text" class="form-control" id="carrera" v-model="nuevoAlumno.carrera" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="telefono" class="form-label">Telefono</label>
                <input type="number" class="form-control" id="telefono" v-model="nuevoAlumno.telefono" required>
              </div>
              
              <div class="col-md-6 mb-3">
                <label for="semestre" class="form-label">semestre</label>
                <input type="text" class="form-control" id="semestre" v-model="nuevoAlumno.semestre">
              </div>
                <div class="col-md-6 mb-3">
                <label for="grupo" class="form-label">Grupo</label>
                <input type="text" class="form-control" id="grupo" v-model="nuevoAlumno.grupo">
              </div>
              <div class="col-md-6 mb-3">
                <label for="imagenURL" class="form-label">Imagen URL</label>
                <input type="text" class="form-control" id="imagenURL" v-model="nuevoAlumno.imagenURL">
              </div>
            </div>

            <button type="submit" class="btn btn-primary">
              {{ editado ? 'Actualizar Alumno' : 'Agregar Alumno' }}
            </button>
          </form>




        </div>
      </div>

      <div class="col-md-12">
        <div class="card shadow">
          <div class="card-body">
            <h5 class="card-title mb-3">Tabla de Alumnos</h5>
            <table class="table table-hover align-middle">
              <thead class="table-light">


                <tr>
                  <th scope="col">Id</th>
                  <th scope="col">Numero de control</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                  <th scope="col">Carrera</th>
                  <th scope="col">telefono</th>
                  
                  <th scope="col">Correo</th>
                  <th scope="col">Semestre</th>
                  <th scope="col">Grupo</th>
                  <th scope="col">imagen</th>
                  <th scope="col">Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="alumno in alumnos" :key="alumno.id">
                  <td>{{ alumno.id }}</td>
                  <td>{{ alumno.numeroControl }}</td>
                  <td>{{ alumno.nombre }}</td>
                  <td>{{ alumno.apellidos }}</td>
                  <td>{{ alumno.carrera }}</td>
                  <td>{{ alumno.telefono }}</td>
                  <td>{{ alumno.correo }}</td>
                  <td>{{ alumno.semestre }}</td>
                  <td>{{ alumno.grupo }}</td>
                  <td><img :src="alumno.imagenURL" alt="Imagen de Alumno" width="50"></td>
                  <td>

                    <button @click="EliminarAlumno(alumno.id)" class="btn btn-danger mx-2"><i
                        class="bi bi-trash"></i></button>
                    <button @click="EditarAlumno(alumno)" class="btn btn-warning"><i
                        class="bi bi-pencil-fill"></i></button>

                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>



      </div>
    </div>




  </div>
</template>

<style scoped>
.container{
  background-color: #03045e;
  color:  white;
  height: 93vh;
  margin-top: 30px;
}

.card-body {
  background-color: #3772ff;
  color: white;
}
.Tec{
  border-radius: 50%;
  width: 70px;
  height: 80px;
  position: right;
  margin-left: 10px;
  margin-top: -10px;
  margin-bottom: -10px;
  float: right;
  margin-right: 10px;
}

.Logo{
  width: 90px;
  height: 50px;
  position: left;
  margin-left: 10px;
  margin-top: -5px;
  margin-bottom: -5px;
  float: left;
}


</style>
