package me.projects.baldur.betrayal_at_baldurs_gate.classes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassInfoUtilz {

    public static void printClassInfo(Class<?> clazz) {
        List<String> propertiesArr=new ArrayList<String>();

        System.out.println("Class Name: " + clazz.getName());

        // Print fields (properties)
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            String modifierStr = Modifier.toString(modifiers);
            String type = field.getType().getName();
            String name = field.getName();
            propertiesArr.add(modifierStr + " " + type + " " + name);
        }

        // Print methods
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            String modifierStr = Modifier.toString(modifiers);
            String returnType = method.getReturnType().getName();
            String methodName = method.getName();

            //if method isn't lambda expression
            if (!methodName.startsWith("lambda$")) { // save to docs
                propertiesArr.add(modifierStr + " " + returnType + " " + methodName);
            }
        }

        FileUtilz.printToHtmlFile(propertiesArr);
    }

}
