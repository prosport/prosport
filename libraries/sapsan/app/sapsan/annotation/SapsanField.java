package sapsan.annotation;

import sapsan.common.HtmlInputComponent;
import sapsan.schema.DataTypeGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SapsanField {
    DataTypeGroup dataType() default DataTypeGroup.Unknown;
    HtmlInputComponent inputComponent() default HtmlInputComponent.Unknown;
    int orderInList() default -1;
    int orderInForm() default -1;
}
