package com.bridgeit.utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bridgeit.dao.JwtUserDto;
import com.bridgeit.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JWT {

	final String key = "qazxswed";

	/*public String jwtGenerator(String id) {

		String jwToken = Jwts.builder()
				.setSubject("Registration")
				.setId(id)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();

		return jwToken;
	}
*/
	
	public String jwtGenerator(User user) {
		Date date=new Date();
		date.setHours(20);
		   Claims claims = Jwts.claims().setSubject(user.getUserName());
	        claims.put("userId",user.getUserId() + "");
	        claims.put("role", user.getRole());
	        claims.setExpiration(date);

	        return Jwts.builder()
	                .setClaims(claims)
	                .signWith(SignatureAlgorithm.HS512, key)
	                .compact();
	}	
	
/*	public String jwtGenerator(int id) {
		String uId=Integer.toString(id);
		
		String jwToken = Jwts.builder()
				.setSubject("Registration")
				.setId(uId)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();

		return jwToken;
	}
*/	
	
	
	public String jwtVerify(String jwToken) {
		String status;
		System.out.println("Token "+jwToken);
	       try {
	            Claims body = Jwts.parser()
	                    .setSigningKey("qazxswed")
	                    .parseClaimsJws(jwToken)
	                    .getBody();
	            
	           status=(String) body.get("userId");
	        } catch (JwtException e) {
	            // Simply print the exception and null will be returned for the userDto
	            e.printStackTrace();
	            status=null;
	        }

		return status;
	}

	

	public int jwtVerifyToken(String jwToken) {
		int status;
	      try {
	            Claims body = Jwts.parser()
	                    .setSigningKey("qazxswed")
	                    .parseClaimsJws(jwToken)
	                    .getBody();
	            
	           status= Integer.parseInt((String) body.get("userId"));
	        } catch (JwtException e) {
	            // Simply print the exception and null will be returned for the userDto
	            e.printStackTrace();
	            status=0;
	        }

		return status;
	}

	
}
