package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.service.interfaces.IProductoService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    private static final String PATH_FOLDER = "/pedidos/img/productos";

    @Autowired
    private IProductoService productoService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Producto>>> listProductos() {
        return Mono.just(ResponseEntity.ok(productoService.findProductos()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> getProductoById(@PathVariable String id) {
        return productoService.findById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertProduct(@Valid @RequestBody Producto producto) {
        producto.setFechaRegistro(LocalDate.now().toString());
        return productoService.registerProducto(producto)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateProduct(@Valid @RequestBody Producto producto) {
        return productoService.findById(producto.getId())
                .flatMap(res -> productoService.updateProducto(producto))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
        return productoService.findById(id)
                .flatMap(res -> productoService.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<Producto>>> listProductsPageable
            (@RequestParam(value = "size", defaultValue = "10") Integer size,
             @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageRequest = PageRequest.of(page, size);
        return productoService.findPageableProductos(pageRequest)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("subir/{id}")
    public Mono<ResponseEntity<Producto>> subirImagen(@PathVariable String id, @RequestPart FilePart file){

        Cloudinary cloudinary = Singleton.getCloudinary();

        return productoService.findById(id)
                .flatMap(c -> {
                    LOGGER.info("Iniciando proceso de subida de imagen");
                    try {
                        File f = Files.createTempFile("temp", file.filename()).toFile();

                        file.transferTo(f).block(); //se agrego "block", al ser un proceso bloqueante debo esperar que termine antes de usar el archivo

                        Map response= cloudinary.uploader().upload(f, ObjectUtils.asMap("folder", PATH_FOLDER));

                        JSONObject json = new JSONObject(response);
                        LOGGER.info("Response from Cloudinary: {}", json.toString());
                        String url=json.getString("url");

                        c.setUrlFoto(url);
                        return productoService.updateProducto(c).then(Mono.just(ResponseEntity.ok().body(c)));
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    return Mono.just(ResponseEntity.ok().body(c));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
