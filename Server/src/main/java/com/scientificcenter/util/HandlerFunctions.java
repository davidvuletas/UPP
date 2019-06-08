package com.scientificcenter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.scientificcenter.model.dto.entity.JournalViewDto;
import com.scientificcenter.model.dto.process.FormSubmissionDto;
import com.scientificcenter.model.dto.process.FormVariables;
import com.scientificcenter.model.dto.process.VariableForm;
import com.scientificcenter.model.users.User;
import org.camunda.bpm.engine.rest.dto.identity.UserCredentialsDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.identity.UserProfileDto;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

@Component
public class HandlerFunctions {

    static ObjectMapper oMapper = new ObjectMapper();

    private Map objectToHashmap(Object object) {
        return oMapper.convertValue(object, Map.class);
    }

    public FormVariables mapToFormVariable(Object object) {
        Map map = this.objectToHashmap(object);

        FormVariables formVariables = new FormVariables();
        for (Object label : map.keySet().toArray()) {
            VariableForm variableForm = oMapper.convertValue(map.get(label.toString()), VariableForm.class);
            formVariables.getFormVariables().put(label.toString(), variableForm);
        }
        return formVariables;
    }

    public HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormSubmissionDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }


    public Object mapFormValuesToObject(List<FormSubmissionDto> form, Class className, Object obj) {
        obj = className.cast(obj);
        for (FormSubmissionDto dto : form) {
            try {
                Field field = className.getField(dto.getFieldId());
                if (dto.getFieldId().equals("roles")) {
                    field.set(obj, Collections.singletonList(dto.getFieldValue()));
                } else {
                    field.set(obj, dto.getFieldValue());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return obj;
    }

    public Map<String, Object> createValueInfo(String classType) {
        Map<String, Object> valInfo = new HashMap<>();
        valInfo.put("objectTypeName", classType);
        valInfo.put("serializationDataFormat", "application/json");
        return valInfo;
    }

    public UserDto mapUserToCamundaUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setCredentials(new UserCredentialsDto());
        userDto.setProfile(new UserProfileDto());
        userDto.getCredentials().setPassword(user.getPassword());
        userDto.getProfile().setEmail(user.getEmail());
        userDto.getProfile().setFirstName(user.getName());
        userDto.getProfile().setLastName(user.getLastname());
        userDto.getProfile().setId(user.getEmail().split("@")[0]);
        return userDto;
    }

    public String serialize(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public Object deserialize(String string, Class<?> className) {
        Gson gson = new Gson();
        LinkedTreeMap lk = (LinkedTreeMap) gson.fromJson(string, Object.class);
        if (gson.toJsonTree(lk.get("value")).isJsonArray()) {
            JsonArray value = gson.toJsonTree(lk.get("value")).getAsJsonArray();
            ArrayList list = new ArrayList<>();
            for (JsonElement el : value) {
                JsonObject jsonObject = el.getAsJsonObject();
                list.add(gson.fromJson(jsonObject, className));
            }
            return list;
        }
        JsonObject jsonObject = gson.toJsonTree(lk.get("value")).getAsJsonObject();
        return gson.fromJson(jsonObject, className);
    }
}
