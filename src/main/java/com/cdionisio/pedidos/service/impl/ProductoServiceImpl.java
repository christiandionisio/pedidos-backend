package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.repo.IProductoRepo;
import com.cdionisio.pedidos.service.interfaces.IProductoService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl extends CrudGenericServiceImpl<Producto> implements IProductoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    Environment environment;

    @Autowired
    private IProductoRepo productoRepo;

    private Cloudinary cloudinary = Singleton.getCloudinary();

    @Override
    public Mono<PageSupport<Producto>> findPageableProductos(Pageable page) {
        LOGGER.info("Obteniendo lista de productos paginado");
        return productoRepo.findAll()
                .collectList()
                .map(list -> new PageSupport<>(
                            list.stream()
                                    .skip((long)page.getPageNumber()*page.getPageSize())
                                    .limit(page.getPageSize())
                                    .collect(Collectors.toList()),
                            page.getPageNumber(),
                            page.getPageSize(),
                            list.size()
                        )
                );
    }

    @Override
    public Mono<PageSupport<Producto>> findPageableProductosByFilters(Pageable page, String nombre, String tipo) {
        LOGGER.info("Obteniendo lista de productos por filtros");
        return productoRepo.findByFieldFilters(nombre, tipo)
                .collectList()
                .map(list -> new PageSupport<>(
                                list.stream()
                                        .skip((long)page.getPageNumber()*page.getPageSize())
                                        .limit(page.getPageSize())
                                        .collect(Collectors.toList()),
                                page.getPageNumber(),
                                page.getPageSize(),
                                list.size()
                        )
                );
    }

    @Override
    public JSONObject uploadImageToCloudinary(FilePart file) throws IOException {
        LOGGER.info("Cargando imagen a Cloudinary");
        File f = Files.createTempFile("temp", file.filename()).toFile();
        //se agrego "block", al ser un proceso bloqueante debo esperar que termine antes de usar el archivo
        file.transferTo(f).block();

        Map<String, Object> response= cloudinary.uploader().upload(f, ObjectUtils.asMap("folder",
                environment.getProperty("cloudinary.path.custom.folder")));

        return new JSONObject(response);
    }

    @Override
    public JSONObject deleteImageToCloudinary(String publicId) throws IOException {
        LOGGER.info("Eliminando imagen de Cloudinary");
        Map<String, Object> deleteParams = ObjectUtils.asMap("invalidate", true );
        Map<String, Object> response = cloudinary.uploader().destroy(publicId, deleteParams );

        return new JSONObject(response);
    }

    @Override
    protected IGenericRepo<Producto> getRepo() {
        return productoRepo;
    }
}
