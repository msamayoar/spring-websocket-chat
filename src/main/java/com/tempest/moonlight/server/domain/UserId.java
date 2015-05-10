package com.tempest.moonlight.server.domain;//package com.tempest.moonlight.server.domain;
//
///**
// * Created by Yurii on 4/21/2015.
// */
//public class UserId {
//
//    public static final String FORMAT = "%s@%s";
//
//    private String id;
//    private String domain;
//
//    public UserId(String id, String domain) {
//        this.id = id;
//        this.domain = domain;
//    }
//
//
//    @Override
//    public String toString() {
//        return format(id, domain);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        UserId userId = (UserId) o;
//
//        if (!id.equals(userId.id)) return false;
//        return domain.equals(userId.domain);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id.hashCode();
//        result = 31 * result + domain.hashCode();
//        return result;
//    }
//
//    public static String format(String id, String domain) {
//        return String.format(FORMAT, id, domain);
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public UserId setId(String id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getDomain() {
//        return domain;
//    }
//
//    public UserId setDomain(String domain) {
//        this.domain = domain;
//        return this;
//    }
//}
