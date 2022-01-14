package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.pagination.PageSupport;
import org.cloudinary.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

public interface IProductoService extends ICrudGenericService<Producto>{
    Mono<PageSupport<Producto>> findPageableProductos(Pageable page);
    JSONObject uploadImageToCloudinary(FilePart file) throws IOException;
    JSONObject deleteImageToCloudinary(String publicId) throws IOException;
}
