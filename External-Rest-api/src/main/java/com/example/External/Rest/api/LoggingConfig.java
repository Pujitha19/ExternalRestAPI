package com.example.External.Rest.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

@Component
@Slf4j
public class LoggingConfig  implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        MyCustomHttpRequestWrapper httpServletRequest = new MyCustomHttpRequestWrapper((HttpServletRequest)servletRequest);
        System.out.println("======================================== Outbound REST REQUEST =================================================================================");
        log.info("Request URI {}"+ httpServletRequest.getRequestURI());
        log.info("Request Method {}", httpServletRequest.getMethod());
        //log.info("Request RequestBody {}", new String(IOUtils.toByteArray(httpServletRequest.getInputStream())));

        log.info("Request RequestBody {}", new String(httpServletRequest.getByteArray()));
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        if (headerNames != null) {
            log.info("Request Headers:: {");
            while (headerNames.hasMoreElements()) {
                String s=headerNames.nextElement();
                log.info(s+"  : "+httpServletRequest.getHeader(s)+"\n");
            }
            log.info("}\n");
        }



       MyCustomHttpResponseWrapper httpservletResponse = new MyCustomHttpResponseWrapper((HttpServletResponse)servletResponse);
        filterChain.doFilter(httpServletRequest,httpservletResponse);

        System.out.println("============================================ Inbound REST RESPONSE ==================================================================================");
        log.info("Response Status {}", httpservletResponse.getStatus());
        log.info("Response Body {}", new String(httpservletResponse.getBaos().toByteArray()));
        Collection<String> headers=httpservletResponse.getHeaderNames();
        Iterator<String> iterator= headers.iterator();
        log.info("Response Headers:: {");
        while (iterator.hasNext()){
            String s=iterator.next();
            log.info(s+"  : "+httpservletResponse.getHeader(s)+"\n");
        }




    }

    @Override
    public void destroy() {

    }

    private class MyCustomHttpRequestWrapper extends HttpServletRequestWrapper{

       private byte[] byteArray;

        public MyCustomHttpRequestWrapper(HttpServletRequest request) {
            super(request);

           try{
               byteArray = IOUtils.toByteArray(request.getInputStream());
           } catch (Exception e) {
               throw new RuntimeException("Issue in reading Stream");
           }
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            DelegatingServletInputStream delegatingServletInputStream;
            return new DelegatingServletInputStream(new ByteArrayInputStream(byteArray));


        }

        public byte[] getByteArray() {
            return byteArray;
        }
    }

    private class MyCustomHttpResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private PrintStream printStream = new PrintStream(baos);

        public ByteArrayOutputStream getBaos() {
            return baos;
        }

        public MyCustomHttpResponseWrapper(HttpServletResponse servletResponse) {
            super(servletResponse);

        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new DelegatingServletOutputStream( new TeeOutputStream(super.getOutputStream(), printStream));
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter( new TeeOutputStream(super.getOutputStream(), printStream));
        }
    }
}
