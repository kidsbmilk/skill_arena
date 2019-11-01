package com.zz4955.log4j2filterlogid.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Configuration
@WebFilter(filterName = "MyWebFilter", urlPatterns = "/*")
public class MyWebFilter implements Filter {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String UNIQUE_ID = "traceRootId";

    @Override
    public void destroy() {
        LOGGER.debug("...MyWebFilter destroy...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean bInsertMdc = insertMDC();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();
        LOGGER.debug("...MyWebFilter doFilter...");
        LOGGER.debug("req: " + requestURI);
        try {
            if (requestURI.startsWith("/sub")) {
                RequestDispatcher rd = req.getRequestDispatcher("/reset");
                req.setAttribute("requestURI", requestURI);
                rd.forward(req, resp);
                return;
            }
            chain.doFilter(req, resp);
        } finally {
            if(bInsertMdc) {
                MDC.remove(UNIQUE_ID);
            }
        }
    }

    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID(); // 把这里替换成twitter的snowflake算法就可以了。
        // 详解Twitter开源分布式自增ID算法snowflake，附演算验证过程
        // https://blog.csdn.net/li396864285/article/details/54668031
        String uniqueId = UNIQUE_ID + "-" + uuid.toString().replace("-", "");
        MDC.put(UNIQUE_ID, uniqueId);
        return true;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        LOGGER.info("...MyWebFilter init...");
    }
}
