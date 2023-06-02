package br.com.abcosta.command;

import br.com.abcosta.coreapi.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FoodCard {
    private static final Logger logger = LoggerFactory.getLogger(FoodCard.class);

    @TargetAggregateIdentifier
    private UUID foodCardId;

    @CommandHandler
    public FoodCard(CreateFoodCardCommand command) {
        AggregateLifecycle.apply(new FoodCardCreatedEvent(command.getFoodCardId()));
    }

    @CommandHandler
    public void handle(SelectProductCommand command) {
        AggregateLifecycle.apply(new ProductSelectedEvent(foodCardId, command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(DeselectProductCommand command) {
        AggregateLifecycle.apply(new ProductDeselectedEvent(foodCardId, command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        AggregateLifecycle.apply(new OrderConfirmedEvent(foodCardId));
    }

    public FoodCard() {}
}
