package org.apache.ibatis.adapter.plugin.sql;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

import static java.lang.System.out;

/**
 * plugin intervene the formal process embeded in mysql
 *
 * @author philo
 * @create 2018-05-09 9:57 AM
 **/

@Intercepts(
        {
                @Signature(type = Executor.class, method = "commit", args = {boolean.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
public class MysqlPlugin implements Interceptor {

    String prop1;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        out.println("\nintercept:invoke method-" + invocation.getMethod().getName());
        if("commit".equals(invocation.getMethod().getName())){
            out.println("commit\t" + prop1);
        }
        else if("update".equals(invocation.getMethod().getName())){
            out.println("update\t" + prop1);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        parseProp(properties);
    }

    private void parseProp(Properties properties){
        prop1 = properties.getProperty("prop1");
    }
}
