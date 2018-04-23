/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static java.lang.System.out;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PluginTest {

  @Test
  public void mapPluginShouldInterceptGet() {
    Map map = new HashMap();
    map = (Map) new AlwaysMapPlugin().plugin(map);
//    assertEquals("Always", map.get("Anything"));
    assertEquals("Always", map.get(1));
  }

  @Test
  public void shouldNotInterceptToString() {
    Map map = new HashMap();
    map = (Map) new AlwaysMapPlugin().plugin(map);
    assertFalse("Always".equals(map.toString()));
  }

  @Test
  public void interceptTestInterfaceMethod(){
    TestInterface toBeTest = new TestClass("Have fun");
    assertEquals(TestClassPlugin.INTERCEPT_STR + toBeTest.getStr(),
            ((TestInterface) new TestClassPlugin().plugin(toBeTest)).getStr()
    );
  }

  @Intercepts({
      @Signature(type = Map.class, method = "get", args = {Object.class})})
  public static class AlwaysMapPlugin implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
      return "Always";
    }

    public Object plugin(Object target) {
      return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
  }

  /**
   * create by philo
   * */
  @Intercepts({
          @Signature(type = TestInterface.class, method = "getStr",args = {})
  })
  public static class TestClassPlugin implements Interceptor {

    public static final String INTERCEPT_STR = "addedPrefix_";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
      out.println("intercept:\ninvoke method-\"" + invocation.getMethod() + "\"\ninvoke args-\"" + invocation.getArgs() + "\"");
      return INTERCEPT_STR + invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
      return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
  }

  private interface TestInterface{
    String getStr();
  }

  private class TestClass implements TestInterface{
    private String rawStr;

    public TestClass(String str){this.rawStr = str;}

    @Override
    public String getStr() {
      return rawStr;
    }
  }

}
