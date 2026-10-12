package com.encomiendas.pedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encomiendas.pedidos.dto.PedidoDto;
import com.encomiendas.pedidos.model.Pedido;
import com.encomiendas.pedidos.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDto.Respuesta> crear(@Valid @RequestBody PedidoDto.Crear datos) {
        Pedido pedido = pedidoService.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoDto.Respuesta.desde(pedido));
    }

    @GetMapping("/seguimiento/{codigo}")
    public PedidoDto.Respuesta seguimiento(@PathVariable String codigo) {
        return PedidoDto.Respuesta.desde(pedidoService.buscarPorCodigo(codigo));
    }

    @GetMapping
    public List<PedidoDto.Respuesta> listarPorEstado(@RequestParam Pedido.Estado estado) {
        return pedidoService.listarPorEstado(estado).stream()
                .map(PedidoDto.Respuesta::desde)
                .toList();
    }

    @PatchMapping("/{id}/estado")
    public PedidoDto.Respuesta cambiarEstado(@PathVariable Long id,
                                             @Valid @RequestBody PedidoDto.CambioEstado datos) {
        return PedidoDto.Respuesta.desde(pedidoService.cambiarEstado(id, datos));
    }
}
