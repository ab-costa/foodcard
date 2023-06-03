package br.com.abcosta.gui;

import br.com.abcosta.coreapi.CreateFoodCartCommand;
import br.com.abcosta.coreapi.DeselectProductCommand;
import br.com.abcosta.coreapi.SelectProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/foodCart")
@RestController
public class FoodOrderingController {
    private final CommandGateway commandGateway;

    public FoodOrderingController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public CompletableFuture<UUID> createFoodCart() {
        return commandGateway.send(new CreateFoodCartCommand(UUID.randomUUID()));
    }

    @PostMapping("/{foodCartId}/select/{productId}/{quantity}")
    public void selectProducts(
            @PathVariable("foodCartId") String foodCartId,
            @PathVariable("productId") String productId,
            @PathVariable("quantity") Integer quantity
    ) {

        commandGateway.send(new SelectProductCommand(
                UUID.fromString(foodCartId),
                UUID.fromString(productId),
                quantity)
        );
    }

    @PostMapping("/{foodCartId}/deselect/{productId}/{quantity}")
    public void deselectProduct(
            @PathVariable("foodCartId") String foodCartId,
            @PathVariable("productId") String productId,
            @PathVariable("quantity") Integer quantity
    ) {
        commandGateway.send(new DeselectProductCommand(
                UUID.fromString(foodCartId),
                UUID.fromString(productId),
                quantity)
        );
    }
}
