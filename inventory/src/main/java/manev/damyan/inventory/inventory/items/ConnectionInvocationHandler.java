package manev.damyan.inventory.inventory.items;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

@Slf4j
public class ConnectionInvocationHandler implements InvocationHandler {

    private Connection connection;

    public ConnectionInvocationHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.debug("[DPM] Connection trace: " + method.toGenericString());

        Object returnValue = method.invoke(connection, args);
        return returnValue;
    }
}
