package org.zeith.libs.configured.io;

import org.zeith.libs.configured.ConfigToken;
import org.zeith.libs.configured.types.ConfigElement;

public interface IElementFactory<T extends ConfigElement<T>>
{
	T create(Runnable onChanged, ConfigToken<T> token, String name);
}