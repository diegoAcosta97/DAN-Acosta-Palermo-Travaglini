package acostapalermotravaglini.lab01.rest;

import acostapalermotravaglini.lab01.dominio.Obra;
import acostapalermotravaglini.lab01.dominio.TipoObra;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/obra")
@Api(value = "ObraRest", description = "Permite gestionar las obras de la empresa")
class ObraRest {

    private static final List<Obra> listaObras = new ArrayList<>();
    private static Integer ID_GEN = 1;

    @GetMapping(path = "/cliente/{id}")
    @ApiOperation(value = "Busca las obras de un cliente")
    public ResponseEntity<List<Obra>> obraPorCliente(@PathVariable Integer id){
        List<Obra> listaO =  listaObras
                .stream()
                .filter(unObra -> unObra.getCliente().getId().equals(id))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaO);
    }

    @GetMapping(path = "/tipo/{id}")
    @ApiOperation(value = "Filtra las obras por tipo")
    public ResponseEntity<List<Obra>> obraPorTipo(@PathVariable Integer id){
        List<Obra> listaO =  listaObras
                .stream()
                .filter(unObra -> unObra.getTipo().getId().equals(id))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaO);
    }

    @GetMapping
    public ResponseEntity<List<Obra>> todos(){
        return ResponseEntity.ok(listaObras);
    }

    @PostMapping
    public ResponseEntity<Obra> crear(@RequestBody Obra nuevo){
        System.out.println(" crear cliente "+nuevo);
        nuevo.setId(ID_GEN++);
        listaObras.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping(path = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Obra> actualizar(@RequestBody Obra nuevo,  @PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaObras.size())
                .filter(i -> listaObras.get(i).getId().equals(id))
                .findFirst();

        if(indexOpt.isPresent()){
            listaObras.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Obra> borrar(@PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaObras.size())
                .filter(i -> listaObras.get(i).getId().equals(id))
                .findFirst();

        if(indexOpt.isPresent()){
            listaObras.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
