package br.edu.ufape.sgi.comunicacao.controllers;




import br.edu.ufape.sgi.comunicacao.dto.usuario.UsuarioPatchRequest;
import br.edu.ufape.sgi.comunicacao.dto.usuario.UsuarioRequest;
import br.edu.ufape.sgi.comunicacao.dto.usuario.UsuarioResponse;
import br.edu.ufape.sgi.exceptions.notFoundExceptions.UsuarioNotFoundException;
import br.edu.ufape.sgi.fachada.Fachada;

import br.edu.ufape.sgi.models.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> salvar(@Valid @RequestBody UsuarioRequest usuario) {
        Usuario response = fachada.salvarUsuario(usuario.convertToEntity(usuario, modelMapper), usuario.getSenha());
        return new ResponseEntity<>(new UsuarioResponse(response, modelMapper), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable Long id) throws UsuarioNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        Usuario response = fachada.buscarUsuario(id, principal.getSubject());
        return new ResponseEntity<>(new UsuarioResponse(response, modelMapper), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public List<UsuarioResponse> listar() {
        return fachada.listarUsuarios().stream().map(usuario -> new UsuarioResponse(usuario, modelMapper)).toList();
    }

    @GetMapping("/current")
    public ResponseEntity<UsuarioResponse> buscarUsuarioAtual() throws UsuarioNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        Usuario response = fachada.buscarUsuarioPorKcId(principal.getSubject());
        return new ResponseEntity<>(new UsuarioResponse(response, modelMapper), HttpStatus.OK);
    }

    @PatchMapping("/editar")
    public ResponseEntity<UsuarioResponse> atualizar(@Valid @RequestBody UsuarioPatchRequest usuario) throws UsuarioNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        Usuario novoUsuario = usuario.convertToEntity(usuario, modelMapper);
        return new ResponseEntity<>(new UsuarioResponse(fachada.editarUsuario(principal.getSubject(), novoUsuario), modelMapper), HttpStatus.OK);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletar() throws UsuarioNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        fachada.deletarUsuario(principal.getSubject());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
