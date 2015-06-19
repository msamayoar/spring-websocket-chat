package com.tempest.moonlight.server.annotations;

import com.tempest.moonlight.server.exceptions.local.dto.DtoException;
import com.tempest.moonlight.server.services.dto.DtoConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.stereotype.Component;

/**
 * Created by Yurii on 2015-06-18.
 */
@Component
public class EntityDTOAnnotationBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = Logger.getLogger(EntityDTOAnnotationBeanPostProcessor.class.getName());

    @Autowired
    private DtoConverter dtoConverter;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        DTO[] dtos = beanClass.getAnnotationsByType(DTO.class);
        if(dtos.length == 0) {
            return bean;
        } else {
            logger.info("!!! dtos(" + dtos.length + ") found in " + beanClass);
            for (DTO dto : dtos) {
                try {
                    dtoConverter.addDTO(beanClass, dto.type(), dto.dto());
                } catch (DtoException e) {
                    throw new BeanDefinitionValidationException("Can not add DTO for bean of class " + beanClass.getName(), e);
                }
            }
            return bean;
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
