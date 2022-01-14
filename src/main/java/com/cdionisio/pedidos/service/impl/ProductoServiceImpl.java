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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl extends CrudGenericServiceImpl<Producto> implements IProductoService {

    private static final String PATH_FOLDER = "/pedidos/img/productos";

    @Autowired
    private IProductoRepo productoRepo;

    private Cloudinary cloudinary = Singleton.getCloudinary();

    @Override
    public Mono<PageSupport<Producto>> findPageableProductos(Pageable page) {
        return productoRepo.findAll()
                .collectList()
                .map(list -> new PageSupport<>(
                            list.stream()
                                    .skip(Long.valueOf(page.getPageNumber()*page.getPageSize()))
                                    .limit(Long.valueOf(page.getPageSize()))
                                    .collect(Collectors.toList()),
                            page.getPageNumber(),
                            page.getPageSize(),
                            list.size()
                        )
                );
    }

    @Override
    public JSONObject uploadImageToCloudinary(FilePart file) throws IOException {
        File f = Files.createTempFile("temp", file.filename()).toFile();
        file.transferTo(f).block(); //se agrego "block", al ser un proceso bloqueante debo esperar que termine antes de usar el archivo

        Map response= cloudinary.uploader().upload(f, ObjectUtils.asMap("folder", PATH_FOLDER));
        JSONObject jsonResponse = new JSONObject(response);

        return jsonResponse;
    }

    @Override
    public JSONObject deleteImageToCloudinary(String publicId) throws IOException {
        Map deleteParams = ObjectUtils.asMap("invalidate", true );
        Map response = cloudinary.uploader().destroy(publicId, deleteParams );
        JSONObject jsonResponse = new JSONObject(response);

        return jsonResponse;
    }

    @Override
    protected IGenericRepo<Producto> getRepo() {
        return productoRepo;
    }
}
