package com.github.knightliao.hermesjsonrpc.demo;

/**
 * 
 * @author liaoqiqi
 * @version 2014-8-21
 */
public interface DemoServiceDriver {

    public class Request {

        public Request(int maxValue, int minValue) {
            super();
            this.maxValue = maxValue;
            this.minValue = minValue;
        }

        private int maxValue;

        public int getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(int maxValue) {
            this.maxValue = maxValue;
        }

        public int getMinValue() {
            return minValue;
        }

        public void setMinValue(int minValue) {
            this.minValue = minValue;
        }

        private int minValue;
    }

    public class Response {

        public Response(int value) {
            super();
            this.value = value;
        }

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    Response getRandom(Request seed);
}
