package com.dio.tqi.apibanco.stream;

import com.dio.tqi.apibanco.exception.NotFound;
import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.service.TransactionService;
import com.dio.tqi.apibanco.service.UserService;
import com.example.transfer.schema.TransactionAvro;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TransactionStream {
    private final TransactionService transactionService;
    private final UserService userService;

    private final StreamBridge streamBridge;

    @Value("${api.bank.name}")
    private String bank;

    @Bean
    public Consumer<TransactionAvro> transactionCreated() {
        return input -> {
            if (input != null && input.getBankNameReceiver().equals(bank)) {
                userService.transferValue(input.getValue(),input.getPixKeyReceiver());
                input.setProcessed(true);
                input.setStatus(true);
                Message<TransactionAvro> message = MessageBuilder.withPayload(input).build();
                streamBridge.send("transactionCreated-out-0",message);
            }
        };
    }

    @Bean
    public Consumer<TransactionAvro> transactionProcess() {
        return input -> {
            if (input != null && input.getBankNameReceiver().equals(bank)) {
                //Call notification microservice
                Message<TransactionAvro> message = MessageBuilder.withPayload(input).build();
                streamBridge.send("transactionProcess-out-0",message);
            }
        };
    }

    @Bean
    public Consumer<TransactionAvro> userFind() {
        return input -> {
            Optional<User> optional = userService.findUserByKey(input.getPixKeyReceiver());
            if(optional.isPresent()) {
                User user = optional.get();
                input.setBankNameReceiver(user.getBank());
                Message<TransactionAvro> message = MessageBuilder.withPayload(input).build();
                streamBridge.send("userFind-out-0",message);
            }
        };
    }
}
