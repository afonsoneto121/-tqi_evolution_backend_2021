package com.dio.tqi.transfer.stream;

import com.dio.tqi.transfer.mapper.TransactionMapper;
import com.dio.tqi.transfer.model.CacheUser;
import com.dio.tqi.transfer.model.Transaction;
import com.dio.tqi.transfer.service.CacheUserService;
import com.dio.tqi.transfer.service.TransactionService;
import com.example.transfer.schema.TransactionAvro;
import lombok.RequiredArgsConstructor;
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
    private final CacheUserService cacheUserService;
    private final TransactionService transactionService;
    private final TransactionMapper mapper;

    private final StreamBridge streamBridge;

    @Bean
    public Consumer<TransactionAvro> transactionInit() {
        return ((input) -> {
            Optional<CacheUser> user = cacheUserService.findUser(input.getPixKeyReceiver());
            if(user.isPresent()) {
                input.setBankNameReceiver(user.get().getBank());
                Message<TransactionAvro> message = MessageBuilder.withPayload(input).build();
                streamBridge.send("transactionInit-out-0",message);
            } else {
                Message<TransactionAvro> message = MessageBuilder.withPayload(input).build();
                streamBridge.send("userFind-out-0", message);
            }
        });
    }
    @Bean
    public Function<TransactionAvro, TransactionAvro> userFound() {
        return ((input) -> {
            CacheUser user = CacheUser.builder()
                    .bank(input.getBankNameReceiver())
                    .pixKey(input.getPixKeyReceiver())
                    .build();
            cacheUserService.save(user);
            input.setBankNameReceiver(user.getBank());
            return input;
        });
    }

    @Bean
    public Consumer<TransactionAvro> transactionFinished() {
        return (input -> {
            Transaction transaction = mapper.avroToModel(input);
            transactionService.save(transaction);
        });
    }
}
