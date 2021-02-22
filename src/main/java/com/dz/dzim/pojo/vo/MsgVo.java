package com.dz.dzim.pojo.vo;

import java.io.Serializable;

/**
 * @author baohan 消息体
 * @className MsgVo
 * @description TODO
 * @date 2021/2/18 11:20
 */
public class MsgVo implements Serializable {
    /**
     * 消息类型
     */
    private String type;

    /**
     * 服务器时间
     */
    private Long sTime;

    /**
     * 消息流水号
     */
    private long serial;

    /**
     * 消息正文
     */
    private Content content;

    public class Content{
        private String actorId;
        /**
         * 发言人类型
         */
        private String talkerType;
        private String talkerId;
        private String talkerCaption;
        /**
         * 消息类容
         */
        private String word;

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public String getTalkerType() {
            return talkerType;
        }

        public void setTalkerType(String talkerType) {
            this.talkerType = talkerType;
        }

        public String getTalkerId() {
            return talkerId;
        }

        public void setTalkerId(String talkerId) {
            this.talkerId = talkerId;
        }

        public String getTalkerCaption() {
            return talkerCaption;
        }

        public void setTalkerCaption(String talkerCaption) {
            this.talkerCaption = talkerCaption;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public Content(String actorId, String talkerType, String talkerId, String talkerCaption, String word) {
            this.actorId = actorId;
            this.talkerType = talkerType;
            this.talkerId = talkerId;
            this.talkerCaption = talkerCaption;
            this.word = word;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getsTime() {
        return sTime;
    }

    public void setsTime(Long sTime) {
        this.sTime = sTime;
    }

    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public MsgVo() {
    }

    public MsgVo(String type, Long sTime, long serial, Content content) {
        this.type = type;
        this.sTime = sTime;
        this.serial = serial;
        this.content = content;
    }
    public MsgVo(String type, Long sTime, long serial, String actorId, String talkerType, String talkerId, String talkerCaption, String word) {
        this.type = type;
        this.sTime = sTime;
        this.serial = serial;
        Content content =new Content(actorId, talkerType,talkerId, talkerCaption, word);
        this.content = content;
    }
}
