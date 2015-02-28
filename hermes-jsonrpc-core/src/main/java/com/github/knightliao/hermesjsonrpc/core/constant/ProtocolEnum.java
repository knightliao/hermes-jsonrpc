package com.github.knightliao.hermesjsonrpc.core.constant;

/**
 * @author liaoqiqi
 * @version 2014-8-30
 */
public enum ProtocolEnum {

    GSON(0, "application/json-hermes-gson"), PROTOSTUFF(1, "application/json-hermes-protostuff");

    private int type = 0;
    private String modelName = null;

    private ProtocolEnum(int type, String modelName) {
        this.type = type;
        this.modelName = modelName;
    }

    public static ProtocolEnum getByType(int type) {

        if (type == 0) {
            return ProtocolEnum.GSON;
        }

        if (type == 1) {
            return ProtocolEnum.PROTOSTUFF;

        }

        return null;
    }

    public static ProtocolEnum getByName(String name) {

        for (ProtocolEnum protocolEnum : values()) {

            if (protocolEnum.getModelName().equals(name)) {
                return protocolEnum;
            }
        }

        return null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
