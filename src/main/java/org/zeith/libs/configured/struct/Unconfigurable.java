package org.zeith.libs.configured.struct;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unconfigurable
{
}