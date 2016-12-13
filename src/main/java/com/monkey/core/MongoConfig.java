package com.monkey.core;

import com.monkey.utils.EmptyUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by renfei on 16/12/12.
 */
public class MongoConfig {
    private static final String PREFIX = "mongodb://";
    private static final String SLASH = "/";
    private static final String AT = "@";
    private static final String COLON = ":";
    private static final String QUE = "?";
    private static final String AND = "&";

    private String server;
    private String database;
    private String username;
    private String password;

    private Integer maxPoolSize;
    private Integer minPoolSize;
    private Integer maxIdleTimems;
    private Integer maxLifeTimems;
    private Integer waitQueueMultiple;
    private Integer waitQueueTimeoutms;
    private Integer connectTimeoutms;
    private Integer socketTimeoutms;
    private Boolean sslInvalidHostnameAllowed;
    private Boolean ssl;
    private String streamType;
    private String replicaSet;
    private String readConcernLevel;
    private Integer serverSelectionTimeoutms;
    private Integer localThresholdms;
    private Integer heartBeatFrequencyms;

    public String getBuildUri(){
        StringBuilder uri = new StringBuilder(PREFIX).append(username).append(COLON).append(password)
                .append(AT).append(server).append(SLASH).append(database).append(QUE).append("maxpoolsize=").append(maxPoolSize);
        if(!EmptyUtil.isNullOrZero(minPoolSize)){
            uri.append(AND).append("minpoolsize=").append(minPoolSize);
        }
        if(!EmptyUtil.isNullOrZero(maxIdleTimems)){
            uri.append(AND).append("maxidletimems=").append(maxIdleTimems);
        }
        if(!EmptyUtil.isNullOrZero(maxLifeTimems)){
            uri.append(AND).append("maxlifetimems=").append(maxLifeTimems);
        }
        if(!EmptyUtil.isNullOrZero(waitQueueMultiple)){
            uri.append(AND).append("waitqueuemultiple=").append(waitQueueMultiple);
        }
        if(!EmptyUtil.isNullOrZero(waitQueueTimeoutms)){
            uri.append(AND).append("waitqueuetimeoutms=").append(waitQueueTimeoutms);
        }
        if(!EmptyUtil.isNullOrZero(connectTimeoutms)){
            uri.append(AND).append("connecttimeoutms=").append(connectTimeoutms);
        }
        if(!EmptyUtil.isNullOrZero(socketTimeoutms)){
            uri.append(AND).append("sockettimeoutms=").append(socketTimeoutms);
        }
        if(sslInvalidHostnameAllowed != null){
            uri.append(AND).append("sslinvalidhostnameallowed=").append(sslInvalidHostnameAllowed);
        }
        if(ssl != null){
            uri.append(AND).append("ssl=").append(ssl);
        }
        if(StringUtils.isNotBlank(streamType)){
            uri.append(AND).append("minpoolsize=").append(streamType);
        }
        if(StringUtils.isNotBlank(replicaSet)){
            uri.append(AND).append("replicaset=").append(replicaSet);
        }
        if(StringUtils.isNotBlank(readConcernLevel)){
            uri.append(AND).append("readconcernlevel=").append(readConcernLevel);
        }
        if(!EmptyUtil.isNullOrZero(serverSelectionTimeoutms)){
            uri.append(AND).append("serverselectiontimeoutms=").append(serverSelectionTimeoutms);
        }
        if(!EmptyUtil.isNullOrZero(localThresholdms)){
            uri.append(AND).append("localthresholdms=").append(localThresholdms);
        }
        if(!EmptyUtil.isNullOrZero(heartBeatFrequencyms)){
            uri.append(AND).append("heartbeatfrequencyms=").append(heartBeatFrequencyms);
        }
        return uri.toString();
    }

    public String getDatabase() {
        return database;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setMinPoolSize(Integer minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public void setMaxIdleTimems(Integer maxIdleTimems) {
        this.maxIdleTimems = maxIdleTimems;
    }

    public void setMaxLifeTimems(Integer maxLifeTimems) {
        this.maxLifeTimems = maxLifeTimems;
    }

    public void setWaitQueueMultiple(Integer waitQueueMultiple) {
        this.waitQueueMultiple = waitQueueMultiple;
    }

    public void setWaitQueueTimeoutms(Integer waitQueueTimeoutms) {
        this.waitQueueTimeoutms = waitQueueTimeoutms;
    }

    public void setConnectTimeoutms(Integer connectTimeoutms) {
        this.connectTimeoutms = connectTimeoutms;
    }

    public void setSocketTimeoutms(Integer socketTimeoutms) {
        this.socketTimeoutms = socketTimeoutms;
    }

    public void setSslInvalidHostnameAllowed(Boolean sslInvalidHostnameAllowed) {
        this.sslInvalidHostnameAllowed = sslInvalidHostnameAllowed;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public void setReplicaSet(String replicaSet) {
        this.replicaSet = replicaSet;
    }

    public void setReadConcernLevel(String readConcernLevel) {
        this.readConcernLevel = readConcernLevel;
    }

    public void setServerSelectionTimeoutms(Integer serverSelectionTimeoutms) {
        this.serverSelectionTimeoutms = serverSelectionTimeoutms;
    }

    public void setLocalThresholdms(Integer localThresholdms) {
        this.localThresholdms = localThresholdms;
    }

    public void setHeartBeatFrequencyms(Integer heartBeatFrequencyms) {
        this.heartBeatFrequencyms = heartBeatFrequencyms;
    }
}
