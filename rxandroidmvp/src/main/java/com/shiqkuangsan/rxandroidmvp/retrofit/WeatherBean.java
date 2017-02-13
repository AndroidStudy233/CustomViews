package com.shiqkuangsan.rxandroidmvp.retrofit;

import java.util.List;

public class WeatherBean {

    /**
     * desc : OK
     * status : 1000
     * data : {"wendu":"31","ganmao":"鍚勯」姘旇薄鏉\u2032欢閫傚疁锛屽彂鐢熸劅鍐掓満鐜囪緝浣庛\u20ac備絾璇烽伩鍏嶉暱鏈熷浜庣┖璋冩埧闂翠腑锛屼互闃叉劅鍐掋\u20ac�","forecast":[{"fengxiang":"鏃犳寔缁鍚�","fengli":"寰绾�","high":"楂樻俯 34鈩�","type":"鏅�","low":"浣庢俯 28鈩�","date":"21鏃ユ槦鏈熶簩"},{"fengxiang":"鏃犳寔缁鍚�","fengli":"寰绾�","high":"楂樻俯 34鈩�","type":"鏅�","low":"浣庢俯 28鈩�","date":"22鏃ユ槦鏈熶笁"},{"fengxiang":"鏃犳寔缁鍚�","fengli":"寰绾�","high":"楂樻俯 34鈩�","type":"鏅�","low":"浣庢俯 28鈩�","date":"23鏃ユ槦鏈熷洓"},{"fengxiang":"鏃犳寔缁鍚�","fengli":"寰绾�","high":"楂樻俯 34鈩�","type":"鏅�","low":"浣庢俯 28鈩�","date":"24鏃ユ槦鏈熶簲"},{"fengxiang":"鏃犳寔缁鍚�","fengli":"寰绾�","high":"楂樻俯 34鈩�","type":"鏅�","low":"浣庢俯 28鈩�","date":"25鏃ユ槦鏈熷叚"}],"yesterday":{"fl":"寰","fx":"鏃犳寔缁鍚�","high":"楂樻俯 33鈩�","type":"澶氫簯","low":"浣庢俯 27鈩�","date":"20鏃ユ槦鏈熶竴"},"aqi":"26","city":"娣卞湷"}
     */

    private String desc;
    private int status;

    private DataBean data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String wendu;
        private String ganmao;
        /**
         * fl : 寰
         * fx : 鏃犳寔缁鍚�
         * high : 楂樻俯 33鈩�
         * type : 澶氫簯
         * low : 浣庢俯 27鈩�
         * date : 20鏃ユ槦鏈熶竴
         */

        private YesterdayBean yesterday;
        private String aqi;
        private String city;
        /**
         * fengxiang : 鏃犳寔缁鍚�
         * fengli : 寰绾�
         * high : 楂樻俯 34鈩�
         * type : 鏅�
         * low : 浣庢俯 28鈩�
         * date : 21鏃ユ槦鏈熶簩
         */

        private List<ForecastBean> forecast;

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            private String fl;
            private String fx;
            private String high;
            private String type;
            private String low;
            private String date;

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        public static class ForecastBean {
            private String fengxiang;
            private String fengli;
            private String high;
            private String type;
            private String low;
            private String date;

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
