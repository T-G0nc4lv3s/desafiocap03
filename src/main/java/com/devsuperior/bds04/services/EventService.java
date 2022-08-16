package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAllPaged(Pageable pageable){
		Page<Event> page = repository.findAll(pageable);
		return page.map(event -> new EventDTO(event));
	}
	
	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event event = new Event();
		dtoToEntity(dto, event);
		event = repository.save(event);
		return new EventDTO(event);
	}

	private void dtoToEntity(EventDTO dto, Event event) {
		City city = new City();
		city.setId(dto.getCityId());
		
		event.setName(dto.getName());
		event.setDate(dto.getDate());
		event.setUrl(dto.getUrl());
		event.setCity(city);
	}
}
