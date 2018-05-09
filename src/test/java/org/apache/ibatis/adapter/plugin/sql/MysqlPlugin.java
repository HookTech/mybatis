package org.apache.ibatis.adapter.plugin.sql;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import java.util.Properties;

import static java.lang.System.out;

/**
 * plugin intervene the formal process embeded in mysql
 *
 * @author philo
 * @create 2018-05-09 9:57 AM
 **/
public class MysqlPlugin implements Interceptor {

    String prop1;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        out.println("intercept:\ninvoke method-\"" + invocation.getMethod() + "\"\ninvoke args-\"" + invocation.getArgs() + "\"");
        out.println("prop1:\t" + prop1);
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
