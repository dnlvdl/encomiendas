package com.encomiendas.pedidos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encomiendas.pedidos.dto.PedidoDto;
import com.encomiendas.pedidos.exception.RecursoNoEncontradoException;
import com.encomiendas.pedidos.model.Direccion;
import com.encomiendas.pedidos.model.HistorialEstado;
import com.encomiendas.pedidos.model.Pedido;
import com.encomiendas.pedidos.model.Persona;
import com.encomiendas.pedidos.model.Sucursal;
import com.encomiendas.pedidos.repository.DireccionRepository;
import com.encomiendas.pedidos.repository.HistorialEstadoRepository;
import com.encomiendas.pedidos.repository.PedidoRepository;
import com.encomiendas.pedidos.repository.PersonaRepository;
import com.encomiendas.pedidos.repository.SucursalRepository;

@Service
public class PedidoService {

    private static final BigDecimal PESO_MAXIMO = new BigDecimal("50.00");

    private final PedidoRepository pedidoRepository;
    private final PersonaRepository personaRepository;
    private final SucursalRepository sucursalRepository;
    private final DireccionRepository direccionRepository;
    private final HistorialEstadoRepository historialRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         PersonaRepository personaRepository,
                         SucursalRepository sucursalRepository,
                         DireccionRepository direccionRepository,
                         HistorialEstadoRepository historialRepository) {
        this.pedidoRepository = pedidoRepository;
        this.personaRepository = personaRepository;
        this.sucursalRepository = sucursalRepository;
        this.direccionRepository = direccionRepository;
        this.historialRepository = historialRepository;
    }

    @Transactional
    public Pedido crear(PedidoDto.Crear datos) {
        if (datos.pesoKg().compareTo(PESO_MAXIMO) > 0) {
            throw new IllegalArgumentException("El peso no puede superar 50 kg");
        }

        Persona remitente = personaRepository.findById(datos.remitenteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Remitente", datos.remitenteId()));
        Persona destinatario = personaRepository.findById(datos.destinatarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Destinatario", datos.destinatarioId()));
        Sucursal sucursal = sucursalRepository.findById(datos.sucursalId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Sucursal", datos.sucursalId()));
        Direccion direccion = direccionRepository.findById(datos.destinoDireccionId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Dirección de destino", datos.destinoDireccionId()));

        Pedido pedido = new Pedido(
                generarCodigo(),
                remitente,
                destinatario,
                sucursal,
                direccion,
                datos.tamano(),
                datos.pesoKg(),
                datos.esFragil(),
                datos.esRigido(),
                datos.esDelicado(),
                datos.declaracionAceptada());
        pedidoRepository.save(pedido);
        historialRepository.save(new HistorialEstado(pedido, pedido.getEstado(), LocalDateTime.now(), "Pedido creado"));
        return pedido;
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorCodigo(String codigo) {
        return pedidoRepository.findByCodigoSeguimiento(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", codigo));
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorEstado(Pedido.Estado estado) {
        return pedidoRepository.findByEstado(estado);
    }

    @Transactional
    public Pedido cambiarEstado(Long id, PedidoDto.CambioEstado datos) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));

        Pedido.Estado actual = pedido.getEstado();
        Pedido.Estado siguiente = siguienteEstado(actual);
        if (datos.estado() != siguiente) {
            throw new IllegalStateException("Transición no válida: " + actual + " → " + datos.estado()
                    + " (el siguiente estado permitido es " + siguiente + ")");
        }

        pedido.setEstado(datos.estado());
        historialRepository.save(new HistorialEstado(pedido, datos.estado(), LocalDateTime.now(), datos.observacion()));
        return pedido;
    }

    private Pedido.Estado siguienteEstado(Pedido.Estado actual) {
        Pedido.Estado[] estados = Pedido.Estado.values();
        int indice = actual.ordinal() + 1;
        if (indice >= estados.length) {
            throw new IllegalStateException("El pedido ya está en estado final: " + actual);
        }
        return estados[indice];
    }

    private String generarCodigo() {
        return "ENC-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
