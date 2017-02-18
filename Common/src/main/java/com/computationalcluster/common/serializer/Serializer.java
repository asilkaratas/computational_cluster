package com.computationalcluster.common.serializer;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
	OutputStream writeObject(Object object);
	Object readObject(InputStream stream);
}
