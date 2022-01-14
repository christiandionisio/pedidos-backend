package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.service.interfaces.IProductoService;
import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/productos")
@PreAuthorize("hasRole('ADMIN')")
public class ProductoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService productoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Flux<Producto>>> listProductos() {
        return Mono.just(ResponseEntity.ok(productoService.list()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Producto>> getProductoById(@PathVariable String id) {
        return productoService.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<?>> insertProduct(@Valid @RequestBody Producto producto) {
        producto.setFechaRegistro(LocalDate.now().toString());
        return productoService.insert(producto)
                .flatMap(res -> Mono.just(ResponseEntity.noContent().header("id", res.getId()).build())
                .defaultIfEmpty(ResponseEntity.noContent().build()));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateProduct(@Valid @RequestBody Producto producto) {
        return productoService.getById(producto.getId())
                .flatMap(res -> productoService.update(producto))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
        return productoService.getById(id)
                .flatMap(res -> productoService.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<PageSupport<Producto>>> listProductsPageable
            (@RequestParam(value = "size", defaultValue = "10") Integer size,
             @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageRequest = PageRequest.of(page, size);
        return productoService.findPageableProductos(pageRequest)
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping( value = "subir/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Producto>> subirImagen(@PathVariable String id, @RequestPart("file") FilePart file) {
        return productoService.getById(id)
                .flatMap(c -> {
                    LOGGER.info("Iniciando proceso de subida de imagen");
                    try {
                        JSONObject json = productoService.uploadImageToCloudinary(file);
                        LOGGER.debug("Response from Cloudinary: {}", json.toString());

                        c.setUrlFoto(json.getString("url"));
                        c.setPublicId(json.getString("public_id"));

                        return productoService.update(c).then(Mono.just(ResponseEntity.ok().body(c)));
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    return Mono.just(ResponseEntity.ok().body(c));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping( value = "update-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Producto>> updateImage(@PathVariable String id, @RequestPart("file") FilePart file) {
        return productoService.getById(id)
                .flatMap(c -> {
                    LOGGER.info("Iniciando proceso de update de imagen");
                    try {

                        if (!c.getPublicId().equals("")) {
                            LOGGER.info("Delete image");
                            JSONObject jsonDeleteResponse = productoService.deleteImageToCloudinary(c.getPublicId());
                            if (!jsonDeleteResponse.getString("result").equalsIgnoreCase("ok")) {
                                LOGGER.error("Response from Cloudinary: {}", jsonDeleteResponse.toString());
                                return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(c));
                            }
                        }

                        JSONObject jsonUploadResponse = productoService.uploadImageToCloudinary(file);
                        LOGGER.debug("Response from Cloudinary: {}", jsonUploadResponse.toString());

                        c.setUrlFoto(jsonUploadResponse.getString("url"));
                        c.setPublicId(jsonUploadResponse.getString("public_id"));

                        return productoService.update(c).then(Mono.just(ResponseEntity.ok().body(c)));
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    return Mono.just(ResponseEntity.ok().body(c));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping( value = "eliminar-imagen/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Producto>> eliminarImagen(@PathVariable String id) {
        return productoService.getById(id)
                .flatMap(c -> {
                    LOGGER.info("Iniciando proceso para eliminar imagen...");
                    try {
                        JSONObject json = productoService.deleteImageToCloudinary(c.getPublicId());
                        LOGGER.info("Response from Cloudinary: {}", json.toString());
                        c.setUrlFoto("");
                        c.setPublicId("");

                        return productoService.update(c).then(Mono.just(ResponseEntity.ok().body(c)));
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    return Mono.just(ResponseEntity.ok().body(c));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
