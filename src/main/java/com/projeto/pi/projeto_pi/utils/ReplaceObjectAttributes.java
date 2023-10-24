package com.projeto.pi.projeto_pi.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Altera o valor dos atributos de um objeto existente com os valores de outro
 * objeto, nunca alterando a PK.
 * 
 * @param <T> Tipo do objeto
 * @author Luis Ricardo Alves Santos
 * @since 1.0
 */
public class ReplaceObjectAttributes<T> {

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    T existingItem = null;

    public ReplaceObjectAttributes(T existingItem) {
        this.existingItem = existingItem;
    }

    /**
     * Altera os valores dos atributos de um objeto existente com os valores de
     * outro objeto
     * 
     * @param item Objeto que contém os novos valores a serem alterados
     * @return Objeto existente com os valores alterados
     */
    public T replaceWith(T item) {

        Field[] declaredFields = item.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            boolean isPk = false;
            boolean encode = false;
            field.setAccessible(true);
            try {
                String name = field.getName();

                Object value = field.get(item);

                /*
                 * Validação para evitar a troca da PK,
                 * verificado qual campo possui a annotation
                 */
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    String annotationName = annotation.annotationType().getSimpleName();
                    if (annotationName.equals("Id")) {
                        isPk = true;
                    }
                    if (annotationName.equals("Encode")) {
                        encode = true;
                    }
                }
                if (isPk) {
                    continue;
                }
                if (encode) {
                    value = passwordEncoder.encode(value.toString());
                }

                if (value == null) {
                    continue;
                }

                Field existingField = existingItem.getClass().getDeclaredField(name);
                existingField.setAccessible(true);
                existingField.set(existingItem, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existingItem;

    }
}
