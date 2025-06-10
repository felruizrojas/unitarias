//mi programa del main
package com.pruebas.unitarias.service;

import com.pruebas.unitarias.model.Mascota;
import com.pruebas.unitarias.repository.MascotaRepository;
//para las pruebas unitarias
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;
import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class MascotaServiceTest {

    @Mock //maqueta del servicio que va al repositorio, al modelo
    private MascotaRepository mascotaRepository;

    @InjectMocks //este es el servicio que se va a probar
    private MascotaService mascotaService;

    @BeforeEach //este seria como el constructor, se ejecuta antes de cada test
    //inicializa los mocks antes de cada prueba
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /* Test para guardar mascota en la capa servicio */
    @Test
    void testGuardarMascota() {
        Mascota mascota = new Mascota(null, "Rex", "Perro", 5);//lo que voy a enviar para que se guarde
        Mascota mascotaGuardada = new Mascota(1L, "", "Perro", 5);//lo que espero que se guarde, con el id generado
        
        when(mascotaRepository.save(mascota)).thenReturn(mascotaGuardada);//cuando se llame al repositorio con el metodo save, devuelvo la mascota guardada
        //llamo al servicio para guardar la mascota

        Mascota resultado = mascotaService.guardarMascota(mascota);//llamo al servicio para guardar la mascota
        //verifico que el resultado es el esperado
        //el asser funciona con la linea anterior
        assertThat(resultado.getId()).isEqualTo(1L);//verifico que el id de la mascota guardada es el esperado
        assertThat(resultado.getNombre()).isEqualTo("Rex");//verifico que el nombre de la mascota guardada es el esperado
        assertThat(resultado.getTipo()).isNotEqualTo("Gato");//verifico que el tipo de la mascota guardada es el esperado
        verify(mascotaRepository).save(mascota);//verifico que se ha llamado al repositorio con el metodo save y la mascota que le he pasado
    }


 @Test
    void testListarMascotas() {
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        Mascota m2 = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Mascota> resultado = mascotaService.listarMascotas();
        assertThat(resultado).hasSize(2).contains(m1, m2);
        verify(mascotaRepository).findAll();
    }
}