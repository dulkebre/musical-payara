package com.example.music.service.faker;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.example.music.service.cdi.extension.StartUp;
import com.example.music.service.model.Band;
import com.github.javafaker.Faker;

@ApplicationScoped
@StartUp
public class FakerService {

    private static final Logger LOGGER = Logger.getLogger(FakerService.class.getName());

    @Inject
    private Faker faker;
    
    private List<Band> bestBandsEver;
    private Set<String> namesOfBestBandEver;
    
    @PostConstruct
    public void onStartup() {
    	LOGGER.info("Eger loading on startup");
    	bestBandsEver = produceOneHundredBands();
    	namesOfBestBandEver = extractBandNames(bestBandsEver);
    }

    private Set<String> extractBandNames(List<Band> bestBandsEver) {
		Set<String> result = new HashSet<>();
		for (Band band : bestBandsEver) {
			result.add(band.getBandName());
		}
		return result;
	}

	public List<Band> produceOneHundredBands() {
        List<Band> musicList = new ArrayList<>();
        Set<String> albums;

        for (int i = 0; i < 100; i++) {
            albums = new HashSet<>();
            albums.add(faker.resolve("music.albums"));
            albums.add(faker.resolve("music.albums"));

            musicList.add(Band.of(faker.resolve("music.bands"), faker.music().genre(), albums));
        }
        
        return musicList;
    }

	public List<Band> getBestBandsEver() {
		return bestBandsEver;
	}

	public Set<String> getNamesOfBestBandEver() {
		return namesOfBestBandEver;
	}
}
