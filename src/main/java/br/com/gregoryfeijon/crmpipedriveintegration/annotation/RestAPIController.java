package br.com.gregoryfeijon.crmpipedriveintegration.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 26/05/2021 às 23:31
 *
 * @author gregory.feijon
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@RequestMapping
public @interface RestAPIController {

    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] value() default "";
}
