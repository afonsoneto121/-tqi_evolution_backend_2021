package com.dio.tqi.apibanco.dto.request;

import com.dio.tqi.apibanco.entity.PixKey;
import com.dio.tqi.apibanco.validator.PixKeyValid;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;

@Data
@Builder
@PixKeyValid
public class PixKeyDTORequest  {
    private @Valid ArrayList<PixKey> keys;
    private @Valid PixKey key;
}
