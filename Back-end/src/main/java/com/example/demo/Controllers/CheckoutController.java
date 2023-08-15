package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.InsumoCarritoDTO;
import com.example.demo.Controllers.DTOS.PedidoDTO;
import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
import com.example.demo.Entitys.Carrito;;
import com.example.demo.Entitys.Enum.EstadoMP;
import com.example.demo.Entitys.Enum.EstadoPedido;
import com.example.demo.Entitys.Pedido;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.*;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {

    private final UserService userService;
    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final ConfigLocalService configService;
    private final PedidoService pedidoService;
    private final InsumoService insumoService;
    @Value("${front.end.checkout.path}")
    private String frontEndPath;


    @SneakyThrows
    @PostMapping()
    public ResponseEntity<?> pagarPedido (@RequestHeader("Authorization") String token, @RequestBody(required = false) PedidoDTO pedidoDTO) throws MPException{
        String jwtToken = token.substring(7);
        PreferenceClient client = new PreferenceClient();
        try {
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            InsumoCarritoDTO complementosDTO = new InsumoCarritoDTO();
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito carritoFound = carritoService.getCarritobyUserID(sub);
            List<PreferenceItemRequest> productosPorComprar = new ArrayList<>();
            List<PreferenceItemRequest> complementosPorComprar = new ArrayList<>();
            Pedido newPedido = pedidoDTO.toEntity(pedidoDTO, userFound, carritoFound);
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(carritoFound.getProductosComprados());
            List<InsumoCarritoDTO> complementosList = complementosDTO.toDTO(carritoFound.getProductosAdicionales());
            int precioTotal = 0;
            if(pedidoDTO.getEsDelivery()){
                precioTotal += configService.getPrecioPorDelivery();
            }
            for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                precioTotal += productosCarritoDTO.getPrecioTotal();
            }
            for (ProductosCarritoDTO productosCarritoDTO: dtoList) {
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(productosCarritoDTO.getProductoId().toString())
                        .title(productosCarritoDTO.getProducto())
                        .description(productoService.findbyID(productosCarritoDTO.getProductoId()).getDescripcion())
                        .pictureUrl(productoService.findbyID(productosCarritoDTO.getProductoId()).getImgURL())
                        .categoryId(productoService.findbyID(productosCarritoDTO.getProductoId()).getProductoCategoria().getNombre())
                        .quantity(Math.toIntExact(productosCarritoDTO.getCantidad()))
                        .currencyId("ARS")
                        .unitPrice(BigDecimal.valueOf(precioTotal))
                        .build();
                productosPorComprar.add(itemRequest);
            }
            for (InsumoCarritoDTO insumoCarritoDTO: complementosList) {
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(insumoCarritoDTO.getId().toString())
                        .title(insumoCarritoDTO.getNombre())
                        .pictureUrl(insumoService.findByID(insumoCarritoDTO.getId()).getUrlIMG())
                        .categoryId(insumoService.findByID(insumoCarritoDTO.getId()).getCategoria().getNombre())
                        .quantity(Math.toIntExact(insumoCarritoDTO.getCantidad()))
                        .currencyId("ARS")
                        .unitPrice(BigDecimal.valueOf(precioTotal))
                        .build();
            }
            List<PreferenceItemRequest> result = Stream.concat(productosPorComprar.stream(), complementosPorComprar.stream())
                    .distinct()
                    .collect(Collectors.toList());
            List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
            excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());
            PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                    .excludedPaymentTypes(excludedPaymentTypes)
                    .installments(1)
                    .build();
            String urlSuccess = "https://localhost:9000/checkout/success";
            String urlFailure = "https://localhost:9000/checkout/failure";
            PreferenceBackUrlsRequest bu = PreferenceBackUrlsRequest
                    .builder()
                    .success(urlSuccess)
                    .failure(urlFailure)
                    .pending(urlFailure).
                    build();
            PreferenceRequest request = PreferenceRequest.builder()
                    .items(productosPorComprar)
                    .paymentMethods(paymentMethods)
                    .autoReturn("approved")
                    .backUrls(bu).build();


            Preference preference = client.create(request);

            String prefId = preference.getId();
            pedidoService.savePedido(newPedido);
            return ResponseEntity.status(HttpStatus.OK).body("{\"preferenceId\":\""+prefId+"\"}");
        }catch (MPException e){
            log.info("Lo impensable paso "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algo muy malo paso LPM");
        }

    }
    @GetMapping("/success")
    public RedirectView success(
            HttpServletRequest request,
            @RequestParam("collection_id") String collectionId,
            @RequestParam("collection_status") String collectionStatus,
            @RequestParam("external_reference") String externalReference,
            @RequestParam("payment_type") String paymentType,
            @RequestParam("merchant_order_id") String merchantOrderId,
            @RequestParam("preference_id") String preferenceId,
            @RequestParam("site_id") String siteId,
            @RequestParam("processing_mode") String processingMode,
            @RequestParam("merchant_account_id") String merchantAccountId,
            RedirectAttributes attributes)
            throws MPException {

        attributes.addFlashAttribute("genericResponse", true);
        attributes.addFlashAttribute("collection_id", collectionId);
        attributes.addFlashAttribute("collection_status", collectionStatus);
        attributes.addFlashAttribute("external_reference", externalReference);
        attributes.addFlashAttribute("payment_type", paymentType);
        attributes.addFlashAttribute("merchant_order_id", merchantOrderId);
        attributes.addFlashAttribute("preference_id",preferenceId);
        attributes.addFlashAttribute("site_id",siteId);
        attributes.addFlashAttribute("processing_mode",processingMode);
        attributes.addFlashAttribute("merchant_account_id",merchantAccountId);
        try {
            Pedido pedido = pedidoService.getPedido(Long.valueOf(externalReference));
            pedido.setEstadoMP(EstadoMP.APROBADO);
            pedidoService.savePedido(pedido);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return new RedirectView(frontEndPath+externalReference +"?success=true");
    }
    @GetMapping("/failure")
    public RedirectView failure(
            HttpServletRequest request,
            @RequestParam("collection_id") String collectionId,
            @RequestParam("collection_status") String collectionStatus,
            @RequestParam("external_reference") String externalReference,
            @RequestParam("payment_type") String paymentType,
            @RequestParam("merchant_order_id") String merchantOrderId,
            @RequestParam("preference_id") String preferenceId,
            @RequestParam("site_id") String siteId,
            @RequestParam("processing_mode") String processingMode,
            @RequestParam("merchant_account_id") String merchantAccountId,
            RedirectAttributes attributes)
            throws MPException {

        attributes.addFlashAttribute("genericResponse", true);
        attributes.addFlashAttribute("collection_id", collectionId);
        attributes.addFlashAttribute("collection_status", collectionStatus);
        attributes.addFlashAttribute("external_reference", externalReference);
        attributes.addFlashAttribute("payment_type", paymentType);
        attributes.addFlashAttribute("merchant_order_id", merchantOrderId);
        attributes.addFlashAttribute("preference_id",preferenceId);
        attributes.addFlashAttribute("site_id",siteId);
        attributes.addFlashAttribute("processing_mode",processingMode);
        attributes.addFlashAttribute("merchant_account_id",merchantAccountId);

        try {
            Pedido pedido = pedidoService.getPedido(Long.valueOf(externalReference));
            pedido.setEstadoMP(EstadoMP.RECHAZADO);
            pedidoService.savePedido(pedido);
        }catch (Exception e){
            System.out.println("No se pudo eliminar la orden");
        }

        return new RedirectView(frontEndPath+"/cart?failure=true");
    }
}
