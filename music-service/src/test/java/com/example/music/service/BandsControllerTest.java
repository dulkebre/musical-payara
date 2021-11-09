package com.example.music.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.music.service.exception.BandNotFoundException;
import com.example.music.service.faker.FakerService;

@RunWith(MockitoJUnitRunner.class)
public class BandsControllerTest {

	@Test
	public void listBands() {
		// given
		Set<String> testBands = produceTestBands();
		when(fakerService.getNamesOfBestBandEver()).thenReturn(testBands);
		// when
		String actual = sut.listBands();
		// then
		assertTrue(actual.contains(U2));
		assertTrue(actual.contains(PINK_FLOYD));
	}

	@Test(expected = BandNotFoundException.class)
	public void testNameTESTnullBandNameThrowsException() throws Exception {
		sut.listBandAlbums(null);
	}

	@Test(expected = BandNotFoundException.class)
	public void testNameTESTunknownBandNameThrowsException() throws Exception {
		// given
		String unknownBandName = "U3";
		Set<String> testBands = produceTestBands();
		when(fakerService.getNamesOfBestBandEver()).thenReturn(testBands);
		// when
		sut.listBandAlbums(unknownBandName);
	}

	@InjectMocks
	private BandsController sut;

	@Mock
	private FakerService fakerService;

	private final String U2 = "U2";
	private final String PINK_FLOYD = "Pink Floyd";

	private Set<String> produceTestBands() {
		Set<String> mockBands = new HashSet<String>();
		mockBands.add(U2);
		mockBands.add(PINK_FLOYD);
		return mockBands;
	}

}
