package ru.itmo.wp.lesson8;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
@Order(1)
public class StaticFilter extends HttpFilter {

    @Override
    public void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String uri = request.getRequestURI();

        final File resources = new File("D:/Documents/IdeaProjects/Web/lesson8/src/main/resources");

        File file = new File(resources + "/static", uri);

        if (file.isFile()) {
            response.setContentType(getServletContext().getMimeType(file.getCanonicalPath()));
            response.setContentLengthLong(file.length());
            Files.copy(file.toPath(), response.getOutputStream());
        } else {
            chain.doFilter(request, response);
        }
    }
}
