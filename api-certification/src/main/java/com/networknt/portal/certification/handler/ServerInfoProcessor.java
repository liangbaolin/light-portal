package com.networknt.portal.certification.handler;

import com.networknt.portal.certification.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is a utility class that scans the input server info object and return
 * a list of issues if there is any.
 */
public class ServerInfoProcessor {
    private ServerInfoProcessor() {

    }

    public static List<Issue> process(Map<String, Object> serverInfo) {
        List<Issue> list = new ArrayList<>();
        Map<String, Object> component = (Map<String, Object>)serverInfo.get("component");
        list.addAll(checkComponent(component));

        Map<String, Object> security = (Map<String, Object>)serverInfo.get("security");
        list.addAll(checkSecurity(security));
        return list;

    }

    private static List<Issue> checkComponent(Map<String, Object> component) {
        List<Issue> issues = new ArrayList<>();
        // check if com.networknt.security.JwtVerifyHandler is in the map.
        if(component.get("com.networknt.security.JwtVerifyHandler") == null) {
            Issue issue = new Issue("iss0001");
            issues.add(issue);
        }
        if(component.get("com.networknt.exception.ExceptionHandler") == null) {
            Issue issue = new Issue("iss0005");
            issues.add(issue);
        }
        return issues;
    }

    private static List<Issue> checkSecurity(Map<String, Object> security) {
        List<Issue> issues = new ArrayList<>();
        String serverFingerPrint = (String)security.get("serverFingerPrint");
        if("873b92f50afe99e9c5af38f1c865987ac113191d".equals(serverFingerPrint)) {
            Issue issue = new Issue("iss0003");
            issues.add(issue);
        }

        List<String> oauth2FingerPrints = (List<String>)security.get("oauth2FingerPrints");
        if(oauth2FingerPrints != null) {
            if(oauth2FingerPrints.contains("564aa231f84039ce2b2b886e58f88dcee26fa3e3") ||
               oauth2FingerPrints.contains("0775dcf9193095e791307a115c192cc897753499")) {
                Issue issue = new Issue("iss0004");
                issues.add(issue);
            }
        }
        return issues;
    }

    private Issue checkTlsKeysUpdated() {
        Issue issue = null;

        return issue;
    }
}
