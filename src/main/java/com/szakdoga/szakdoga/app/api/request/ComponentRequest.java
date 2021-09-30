package com.szakdoga.szakdoga.app.api.request;

import com.szakdoga.szakdoga.app.repository.entity.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentRequest {
    private Component component;
}
