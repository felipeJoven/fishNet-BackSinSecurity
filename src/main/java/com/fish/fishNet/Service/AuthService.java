package com.fish.fishNet.Service;

import com.fish.fishNet.Dtos.DefaultResponseDto;
import com.fish.fishNet.Dtos.DtoLogin;
import com.fish.fishNet.Dtos.DtoRegistro;
import com.fish.fishNet.Dtos.ServiceResponseDTO;

public interface AuthService {
    ServiceResponseDTO<DefaultResponseDto> login(DtoLogin dtoLogin);

    ServiceResponseDTO<DefaultResponseDto> registerByRol(DtoRegistro dtoRegistro, String rol);
}
