package info;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(CLASS)
@Target({ METHOD, CONSTRUCTOR })
public @interface Do {

	String aufgabe();

	String[] parameter() default { "keine Parameter angegeben" };

	String back() default "kein Wert zurückgegeben";

}
