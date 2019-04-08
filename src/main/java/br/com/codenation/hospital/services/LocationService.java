package br.com.codenation.hospital.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Location;
import br.com.codenation.hospital.domain.LocationCategory;
import br.com.codenation.hospital.dto.HospitalDTO;
import br.com.codenation.hospital.dto.LocationDTO;
import br.com.codenation.hospital.repository.HospitalRepository;
import br.com.codenation.hospital.repository.LocationRepository;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;

@Service
public class LocationService {

	//https://us1.locationiq.com/v1/search.php?key=43b382813d8baa&q=Paulista&format=json
	private final String LocationKey = "43b382813d8baa";
	private final String LocationFormat = "json";
	
	@Autowired
	private  LocationRepository locationRepository;
	
	@Autowired
	private  HospitalRepository hospitalRepository;
	
	@Autowired
	private  HospitalService hospitalService;
	
	public List<LocationDTO> findByNameAndLocationNear(String name, String longitude, String latitude, Double distance) {
		return  convertToDTOs(locationRepository.findByNameAndLocationNear(name, 
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)), 
				new Distance(distance, Metrics.KILOMETERS)));
	}
	
	public List<LocationDTO> findByPositionNear(String longitude, String latitude, Double distance) {
		return  convertToDTOs(locationRepository.findByPositionNear(
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)), 
				new Distance(distance, Metrics.KILOMETERS)));
	}
	
	public List<LocationDTO> findLocationNearHospitalBy(String id) {
		Hospital hospital = hospitalService.findById(id);
		
		String longitude = String.valueOf(hospital.getLocation().getLocation().getX());
		String latitude  = String.valueOf(hospital.getLocation().getLocation().getY());
		Double distance  = 100.0d;
		
		List<Location> locations = locationRepository.findByPositionNear(
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)), 
				new Distance(distance, Metrics.KILOMETERS));
		
		List<Location> filterLocations = locations.stream()
				.filter(f -> f.getReferenceId() != id)
				.collect(Collectors.toList());
				
		return  convertToDTOs(filterLocations);
	}
	
	// Usar para encontrar hospital perto de hospital
	public List<HospitalDTO> findHospitalNearHospitalBy(String id) {
		Hospital hospital = hospitalService.findById(id);
		
		String longitude = String.valueOf(hospital.getLocation().getLocation().getX());
		String latitude  = String.valueOf(hospital.getLocation().getLocation().getY());
		Double distance  = 100.0d;
		
		List<Location> locations = locationRepository.findByPositionNear(
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)), 
				new Distance(distance, Metrics.KILOMETERS));
		
		List<Location> filterLocations = locations.stream()
				.filter(f -> f.getLocationCategory() == LocationCategory.HOSPITAL && f.getName() != hospital.getName())
				.collect(Collectors.toList());
				
		List<HospitalDTO> hospitaisDTO = new ArrayList<HospitalDTO>();
		
		for (Location location : filterLocations) {
			Hospital hosp = hospitalRepository.findByNameLikeIgnoreCase(location.getName())
					.stream()
					.findFirst()
					.orElse(null);
			
			if (hosp != null) {
				hospitaisDTO.add(hospitalService.convertToDTO(hosp));
			}
		}
		
		return  hospitaisDTO;
	}
	
	// Usar para encontrar hospital perto de paciente, enviar endereço do paciente
	public List<HospitalDTO> findHospitalNearLocationBy(String longitude, String latitude, Double distance) {
		distance  = 100.0d;
		
		List<Location> locations = locationRepository.findByPositionNear(
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)), 
				new Distance(distance, Metrics.KILOMETERS));
		
		List<Location> filterLocations = locations.stream()
				.filter(f -> f.getLocationCategory() == LocationCategory.HOSPITAL)
				.collect(Collectors.toList());
				
		List<HospitalDTO> hospitaisDTO = new ArrayList<HospitalDTO>();
		
		for (Location location : filterLocations) {
			Hospital hospital = hospitalRepository.findByNameLikeIgnoreCase(location.getName())
					.stream()
					.findFirst()
					.orElse(null);
			
			if (hospital != null) {
				hospitaisDTO.add(hospitalService.convertToDTO(hospital));
			}
		}
		
		return  hospitaisDTO;
	}
	
	public List<LocationDTO> findAll(){
		return convertToDTOs(locationRepository.findAll());
	}

	public LocationDTO findById(String id) {
		return convertToDTO(findLocationById(id));
    }
		
	public List<LocationDTO> findByNameLikeIgnoreCase(String subject) {
		List<Location> locations = locationRepository.findByNameLikeIgnoreCase(subject);
		return convertToDTOs(locations);
    }

	public LocationDTO insert(LocationDTO locationDTO) {
		Location location = fromDTO(locationDTO);
		return convertToDTO(locationRepository.save(location));
	}
	
	public void delete(String id) {
	    locationRepository.deleteById(id);
	}
	
	public LocationDTO update(String id, LocationDTO locationDTO) {
		Location updateLocation = findLocationById(id);
		Location locationData = fromDTO(locationDTO);
		updateLocation.setName(locationData.getName());
		updateLocation.setLocation(locationData.getLocation());
		updateLocation.setLocationCategory(LocationCategory.valueOf(locationDTO.getCategory()));
		return convertToDTO(locationRepository.save(updateLocation));
	}
	
	private Location findLocationById(String id) {
        Optional<Location> result = locationRepository.findById(id);
		return result.orElseThrow(() -> new ObjectNotFoundException("Location não encontrado! ID: "+ id));
    }
	
	public Location fromDTO(LocationDTO locationDTO) {
		GeoJsonPoint locationPoint = new GeoJsonPoint(
		        Double.valueOf(locationDTO.getLongitude()),
		        Double.valueOf(locationDTO.getLatitude()));

		return new Location(LocationCategory.valueOf(locationDTO.getCategory()), locationDTO.getName(), locationPoint);
	}
	
	private LocationDTO convertToDTO(Location model) {
        return new LocationDTO(model);
    }
	
	private List<LocationDTO> convertToDTOs(List<Location> models) {
        return models.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }	
}
