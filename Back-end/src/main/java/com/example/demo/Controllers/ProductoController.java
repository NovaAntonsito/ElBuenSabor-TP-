package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.ProductoDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.ProductoInsumos;
import com.example.demo.Services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/producto")
@Slf4j
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    private final CatergoriaService catergoriaService;

    private final CloudinaryServices cloudServices;

    private final InsumoService insumoService;

    private final ProductoInsumoService productoInsumosService;

    private final ConfigLocalService configService;

    @GetMapping("")
    public ResponseEntity<?> getAllinAlta() throws Exception {
        try {
            List<Producto> prodsInAlta = productoService.getAllNoPage();
            List<ProductoDTO> prodsDto = new ArrayList<>();
            for (Producto p : prodsInAlta) {
                ProductoDTO pDto = new ProductoDTO();
                prodsDto.add(ProductoDTO.toDTO(p));
            }
            return ResponseEntity.status(HttpStatus.OK).body(prodsDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProducto(@PathVariable("id") Long id) throws Exception {
        try {
            Producto productoFound = productoService.findbyID(id);
            ProductoDTO pDto;
            if (productoFound.getPrecioUnitario() == 0 || productoFound.getPrecioUnitario() == null) {
                pDto = ProductoDTO.toDTO(calcularProductoPrecio(productoFound));
            } else {
                pDto = ProductoDTO.toDTO(productoFound);
            }
            return ResponseEntity.status(HttpStatus.OK).body(pDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @PostMapping("")
    public ResponseEntity<?> createProducto(@RequestPart("producto") ProductoDTO productoDTO, @RequestPart(value = "imagen", required = false) MultipartFile file) throws Exception {

        try {
            Producto newProd = productoService.createProduct(productoDTO, file);
            newProd = productoService.saveProduct(newProd);
            return ResponseEntity.status(HttpStatus.OK).body(newProd);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("id") Long ID, @RequestPart("producto") ProductoDTO productoDTO, @RequestPart("file") MultipartFile file) throws Exception {
        try {
            ProductoDTO newProdDTO = new ProductoDTO();
            List<ProductoInsumos> insumoList = new ArrayList<>();
            Categoria cateFound = catergoriaService.findbyID(productoDTO.getProductoCategoria().getId());
            String url = null;

            if (file != null) {
                BufferedImage imgActual = ImageIO.read(file.getInputStream());
                var result = cloudServices.UploadIMG(file);
                url = (String) result.get("url");
            }

            for (var i : productoDTO.getInsumos()) {
                var insumoFound = insumoService.findByID(i.getId());
                if (insumoFound == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("succes", false, "message", "Insumo not found"));
                var productoInsumo = productoInsumosService.save(new ProductoInsumos(insumoFound, i.getCantidad()));
                insumoList.add(productoInsumo);
            }

            Producto updatedProducto = productoService.updateProducto(ID, newProdDTO.toEntity(productoDTO, catergoriaService.findbyID(productoDTO.getProductoCategoria().getId()), insumoList, url));
            return ResponseEntity.status(HttpStatus.OK).body(updatedProducto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("id") Long ID) throws Exception {
        try {
            productoService.deleteSoftProducto(ID);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "success", true,
                    "message", "El objeto se borro correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducto
            (@RequestParam(required = false, value = "idCategoria") Long id,
             @RequestParam(required = false, value = "nombre") String nombre,
             @RequestParam(value = "precioMin") double precioMin,
             @RequestParam(value = "precioMax") double precioMax,
             @RequestParam(value = "descuento") boolean descuento,
             Pageable page) throws Exception {
        try {
            List<Producto> productoPage;
            productoPage = productoService.searchProductsWithFilters(id, nombre, precioMin, precioMax, descuento);

            List<ProductoDTO> prodsDto = new ArrayList<>();
            for (Producto p : productoPage) {
                ProductoDTO pDto = new ProductoDTO();
                if (p.getPrecioUnitario() == 0 || p.getPrecioUnitario() == null) {
                    pDto = ProductoDTO.toDTO(calcularProductoPrecio(p));
                } else {
                    pDto = ProductoDTO.toDTO(p);
                }
                prodsDto.add(pDto);

            }
            return ResponseEntity.status(HttpStatus.OK).body(prodsDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    public Producto calcularProductoPrecio(Producto producto) {
        try {
            Double precio = 0D;
            for (ProductoInsumos insumos : producto.getInsumos()) {
                precio += insumos.getInsumo().getCosto();
            }
            Double valorAgregadoPorCocinar = configService.getPrecioPorTiempo((double) producto.getTiempoCocina());
            precio += valorAgregadoPorCocinar;
            // Supongamos que tienes un valor productoDescuento (long) que contiene el descuento en un rango de 0 a 100.
            long productoDescuento = producto.getDescuento();

            // Dividimos el valor de productoDescuento entre 100 para obtener la fracción.
            double productoDividido = (double) productoDescuento / 100.0;


            // Calculamos el descuento aplicando la fracción productoDividido al precioTotal.
            precio *= (1 - productoDividido);
            producto.setPrecioUnitario(precio);
            producto = productoService.updateProducto(producto.getID(), producto);
            return producto;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterProducts(
            @RequestParam(required = false) Long id, @RequestParam(required = false) String nombre, @RequestParam(required = false) Baja_Alta estado,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page) throws Exception {
        try {
            Page<Producto> rawResults = productoService.filterProducts(id, nombre, estado, page);
            return ResponseEntity.status(HttpStatus.OK).body(rawResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
