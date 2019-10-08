package com.github.xiaoxixi;

public class Constants {

    public static final String ORDER_EXCHANGE = "order_exchange";

    public static final String DLX_ORDER_EXCHANGE = "dlx_order_exchange";

    public static final String CHARSET_UTF_8 = "UTF-8";

    public static class ChannelConstant {

        public static final String ROUTE_KEY = "time.limited.route";

        public static final String DLX_ROUTE_KEY = "time.limited.route.dlx";

        public static final String ARGS_MESSAGE_TTL = "x-message-ttl";

        public static final String ARGS_MESSAGE_DLX = "x-dead-letter-exchange";

        public static final String ARGS_MESSAGE_ELX_ROUTE_KEY = "x-dead-letter-routing-key";

        public static final String DLX_QUEUE_NAME = "dlx_order_queue";

        public static final String QUEUE_NAME = "order_queue";
    }


    private Constants() {}
}
