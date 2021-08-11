package com.ltp.web.mapper;

import com.ltp.web.model.dto.RegistrationRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletRequestMapper {

    public static <T> T mapToObject(HttpServletRequest request, Class<T> cl) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String s;
        while((s = request.getReader().readLine()) != null){
            buffer.append(s);
        }

        return JsonMapper.parseToObject(buffer.toString(), cl);
    }

}
