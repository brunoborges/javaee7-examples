/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface StatisticsEvent {
    
}
