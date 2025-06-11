package com.pruebas.unitarias.service;

import com.pruebas.unitarias.model.Mascota;
import com.pruebas.unitarias.repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaService mascotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test // TestPost guardarMascota
    void testGuardarMascota() {
        Mascota mascota = new Mascota(null, "Rex", "Perro", 5);
        Mascota mascotaGuardada = new Mascota(1L, "Rex", "Perro", 5);
        when(mascotaRepository.save(mascota)).thenReturn(mascotaGuardada);

        Mascota resultado = mascotaService.guardarMascota(mascota);
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Rex");
        assertThat(resultado.getTipo()).isNotEqualTo("Gato");
        verify(mascotaRepository).save(mascota);
    }

    @Test // TestGet listarMascota
    void testListarMascotas() {
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        Mascota m2 = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Mascota> resultado = mascotaService.listarMascotas();
        assertThat(resultado).hasSize(2).contains(m1, m2);
        verify(mascotaRepository).findAll();
    }

    @Test // TestGet listarMascota por id -- copiada al profe
    void testObtenerMascotaPorId() {
        Mascota buscada = new Mascota(1L, "Rex", "Perro", 5);
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(buscada)); //el optional se importo
        Optional<Mascota> resultado = mascotaService.obtenerMascotaPorId(1L);
        assertThat(resultado.isPresent()).isEqualTo(buscada);
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(mascotaRepository).findById(1L);
    }

    @Test // TestPut actualizarMascota por id
    void testActualizarMascota() {
        Mascota mascotaExistente = new Mascota(1L, "Rex", "Perro", 5);
        Mascota mascotaActualizada = new Mascota(1L, "Rex", "Perro", 6);
        when(mascotaRepository.findById(1L)).thenReturn(java.util.Optional.of(mascotaExistente));
        when(mascotaRepository.save(mascotaActualizada)).thenReturn(mascotaActualizada);

        Mascota resultado = mascotaService.actualizarMascota(1L, mascotaActualizada);
        assertThat(resultado.getEdad()).isEqualTo(6);
        verify(mascotaRepository).findById(1L);
        verify(mascotaRepository).save(mascotaActualizada);
    }

    @Test // TestDelete eliminarMascota por id
    void testEliminarMascota() {
        Mascota mascota = new Mascota(1L, "Rex", "Perro", 5);
        when(mascotaRepository.findById(1L)).thenReturn(java.util.Optional.of(mascota));

        mascotaService.eliminarMascota(1L);
        verify(mascotaRepository).deleteById(1L);
    }
}