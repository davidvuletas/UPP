package com.scientificcenter.model;

public class CamundaConstants {

    public static final String URL_FOR_CAMUNDA_ROOT = "http://localhost:8080/scientific-center/";
    public static final String URL_FOR_MAIN_START_PROCESS = URL_FOR_CAMUNDA_ROOT.concat("process-definition/key/PaperFlow/start");
    public static final String URL_FOR_USER_PROCESS = URL_FOR_CAMUNDA_ROOT.concat("process-definition/key/UserFlow/start");

    public static final String URL_FOR_GET_PROCESS_BY_ID = URL_FOR_CAMUNDA_ROOT.concat("task?processInstanceId=");
    public static final String URL_FOR_TASK = URL_FOR_CAMUNDA_ROOT .concat("task/");
    public static final String URL_FOR_PROCESS_INSTANCE = URL_FOR_CAMUNDA_ROOT.concat("process-instance/");
    public static final String URL_FOR_CREATE_USER = URL_FOR_CAMUNDA_ROOT.concat("user/create");
    public static final String URL_FOR_LOGIN_USER = URL_FOR_CAMUNDA_ROOT.concat("/identity/verify");
    public static final String URL_FOR_ASSIGNE_TASK = URL_FOR_CAMUNDA_ROOT.concat("task?assignee=");
}
