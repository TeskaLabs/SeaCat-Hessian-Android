package org.example.hessian.servlet;

import com.caucho.hessian.server.HessianServlet;
import org.example.hessian.service.HelloService;

public class HelloServlet extends HessianServlet implements HelloService
{
    public String sayHelloTo(String str)
    {
        return "Hello from Server, " + str;
    }
}
