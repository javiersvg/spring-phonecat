package com.examplecorp.phonecat.controller;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examplecorp.phonecat.model.Phone;
import com.examplecorp.phonecat.repository.PhoneRepository;

@RestController
@RequestMapping("/phone")
public class PhoneController {

	@Autowired
	private PhoneRepository phoneRepository;

	@RequestMapping(method = RequestMethod.POST)
	public Phone savePhone(@RequestBody Phone phone) {
		Phone originalPhone = null;
		if (phone.getId() != null) {
			originalPhone = phoneRepository.findOne(phone.getId());
		}
		if(originalPhone != null) {
			phone.setId(originalPhone.getId());
			phone.setOwnerId(originalPhone.getOwnerId());
			updateWithPostedFields(phone, originalPhone, Phone.class);
		} else {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Assert.notNull(principal);
			phone.setOwnerId(((UserDetails) principal).getUsername());
			phone.setId(phone.getName().replaceAll("[^a-zA-Z0-9]", "-"));
		}
		return phoneRepository.save(phone);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Phone> queryPhone() {
		return phoneRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, params = "phoneId")
	public Phone getPhone(@RequestParam String phoneId) {
		return phoneRepository.findOne(phoneId);
	}

	@SuppressWarnings("unchecked")
	private void updateWithPostedFields(Object resultant, Object merger, Class<?> clazz) {
		try {
			for(PropertyDescriptor propertyDescriptor : 
				Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors()){
			    Object newValue = propertyDescriptor.getReadMethod().invoke(merger);
			    Object originalValue = propertyDescriptor.getReadMethod().invoke(resultant);
			    if(newValue != null) {
			    	if(newValue instanceof String || newValue instanceof Number) {
			    		propertyDescriptor.getWriteMethod().invoke(resultant, newValue);
			    	} else if(newValue instanceof Map) {
			    		((Map<Object,Object>)originalValue).putAll((Map<Object,Object>)newValue);
			    	} else {
			    		updateWithPostedFields(originalValue, newValue, newValue.getClass());
			    	}
			    }
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}