package org.camunda.bpm.extension.swagger.generator.model;

import lombok.SneakyThrows;
import lombok.Value;

import java.lang.reflect.Field;

@Value
public class CamundaRestResource {

  private Class<?> serviceInterfaceClass;
  private Class<?> serviceImplClass;

  public String getSimpleName() {
    return serviceInterfaceClass.getSimpleName();
  }

  public Package getPackage() {
    return serviceInterfaceClass.getPackage();
  }

  public String getPackageName() {
    return getPackage().getName();
  }

  public String getTag() {
    return splitCamelCase(getSimpleName()).split(" ")[0];
  }

  public String getName() {
    String[] n = splitCamelCase(getSimpleName()).split(" ");
    return n[0] + " " + n[2] ;
  }
  
  @SneakyThrows
  public String getPath() {
    Field field = serviceInterfaceClass.getDeclaredField("PATH");
    return field != null ? (String) field.get(null) : "";
  }

  public static String splitCamelCase(String s) {
    return s.replaceAll(
      String.format("%s|%s|%s",
        "(?<=[A-Z])(?=[A-Z][a-z])",
        "(?<=[^A-Z])(?=[A-Z])",
        "(?<=[A-Za-z])(?=[^A-Za-z])"
      ),
      " "
    );
  }

}