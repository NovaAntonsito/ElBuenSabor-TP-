package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
import com.example.demo.Entitys.Carrito;;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.CarritoService;
import com.example.demo.Services.ProductoService;
import com.example.demo.Services.UserService;
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

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {

    private final UserService userService;
    private final CarritoService carritoService;
    private final ProductoService productoService;

    @Value("${front.end.checkout.path}")
    private String frontEndPath;


    @SneakyThrows
    @PostMapping()
    public ResponseEntity<?> pagarPedido (@RequestHeader("Authorization") String token) throws MPException{
        String jwtToken = token.substring(7);
        PreferenceClient client = new PreferenceClient();
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito carritoFound = carritoService.getCarritobyUserID(sub);
            List<PreferenceItemRequest> productosPorComprar = new ArrayList<>();
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(carritoFound.getProductosComprados());
            int precioTotal = 0;
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
            System.out.println("Entre");
        }catch (Exception e){
            System.out.println("No se pudo eliminar la orden");
        }

        return new RedirectView(frontEndPath+"/cart?failure=true");
    }
}
