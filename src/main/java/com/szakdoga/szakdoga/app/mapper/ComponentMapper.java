package com.szakdoga.szakdoga.app.mapper;

import com.szakdoga.szakdoga.app.dto.ComponentDto;
import com.szakdoga.szakdoga.app.repository.entity.Component;

@org.springframework.stereotype.Component
public class ComponentMapper {

    public ComponentDto componentToComponentDto(Component component){
        ComponentDto componentDto = new ComponentDto();

        componentDto.setName(component.getName());

        return componentDto;
    }

    public Component componentDtoToComponent(ComponentDto componentDto){
        Component component = new Component();

        component.setName(componentDto.getName());
        return component;
    }
}
