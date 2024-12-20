package manev.damyan.inventory.inventory.items;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.postgresql.jdbc.PgConnection;

import java.lang.reflect.Proxy;
import java.sql.Connection;

//@Component
//@Aspect
@Slf4j
public class DataSourceAspect {

    @Around("target(javax.sql.DataSource)")
    public Object logDataSourceConnectionInfo(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        log.debug("[DPM] Datasource tracker. Method called: " + signature + ". Class intercepted is:" + proceedingJoinPoint.getTarget().getClass().getName());

        Object result = proceedingJoinPoint.proceed();

        if (result instanceof Connection) {

            Object proxiedConnection = Proxy.newProxyInstance(PgConnection.class.getClassLoader(),
                    new Class[] { Connection.class }, new ConnectionInvocationHandler((Connection) result));

            return proxiedConnection;
        }

        log.debug("[DPM] Connection returned in the aspec is: " + result);
        return result;
    }
}
