package com.javatechie.spring.consul.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Wissen {

	/**
	 * "u1", 100, "login" "u2", 105, "login" "u1", 120, "login" "u2", 170, "login"
	 * "u1", 200, "purchase" "u1", 250, "purchase"
	 * 
	 * 1. u1 --> startPoint 100s nextPoint 120s(after 20s) next 200s () u1_login
	 * 100s u1_login u1_login 120s
	 * 
	 * u1_purchase 200s u1_purchase u1_purchase 250
	 * 
	 */

	public static void main(String[] args) {
		List<User> user = new ArrayList<>();
		user.add(new User("u1", 100, "login"));
		user.add(new User("u2", 105, "login"));
		user.add(new User("u1", 120, "login"));
		user.add(new User("u2", 170, "login"));
		user.add(new User("u1", 200, "purchase"));
		user.add(new User("u1", 250, "purchase"));
		Set<String> result = new HashSet<>();
		Map<Object, List<User>> spcific = user.stream().collect(Collectors.groupingBy(u -> u.userId + "_" + u.action));
		System.out.println(spcific);
		for(Map.Entry<Object, List<User>> entry:spcific.entrySet()) {
			entry.getKey();
			for(int i=1;i<entry.getValue().size();i++) {
				if(entry.getValue().get(i).timestamp - entry.getValue().get(i-1).timestamp <60){
					result.add(entry.getValue().get(i).userId);
					
				}
			}
			
		}
		
		System.out.println(result);
//		System.out.println(result.stream().distinct().collect(Collectors.toList()));
		 User user1  = new User("u1", 100, "login");
		 User user2 = new User("u1", 100, "login");
		 
//		 empid, empname, mid   EmployeeTable   empid = 100
//	select empname from Employee where empid = (select mid from Employee where empid = '100');

	}

}

class User {
	String userId;
	int timestamp;
	String action;

	User(String userId, int timestamp, String action) {
		this.userId = userId;
		this.timestamp = timestamp;
		this.action = action;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", timestamp=" + timestamp + ", action=" + action + "]";
	}

}
