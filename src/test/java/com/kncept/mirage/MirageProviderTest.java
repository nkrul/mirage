package com.kncept.mirage;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.kncept.mirage.util.MirageProvider;

public class MirageProviderTest {
	
	public static Stream<MirageProvider> allTypes() throws IOException {
		return MirageProvider.allTypesAsParameters();
	}
	
	@Test
	public void mirageParamsProviderReturnsStream() {
		Stream<MirageProvider> providers = MirageProvider.allTypesAsParameters();
		assertNotNull(providers);
		assertNotEquals(0L, providers.count());
	}

	@Test
	public void canFindClassFiles() {
		assertNotNull(MirageProvider.getClassFile(getClass()));
	}
	
	@Test
	public void canFindSourceFiles() {
		assertNotNull(MirageProvider.getSourceFile(getClass()));
	}
	
	@ParameterizedTest
	@MethodSource("allTypes")
	public void providersAlwaysProvideMirages(MirageProvider provider) {
		assertNotNull(provider);
		assertNotNull(provider.mirage(getClass()));
	}
}
