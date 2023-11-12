package com.fish.fishNet.Service;

import com.fish.fishNet.Dtos.DefaultResponseDto;
import com.fish.fishNet.Dtos.ServiceResponseDto;
import com.fish.fishNet.Dtos.DtoLogin;
import com.fish.fishNet.Dtos.DtoRegistro;

public interface AuthService {
    ServiceResponseDto<DefaultResponseDto> login(DtoLogin dtoLogin);

    ServiceResponseDto<DefaultResponseDto> registerByRol(DtoRegistro dtoRegistro, String rol);
}
