package net.tfg.sharedlife.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import net.tfg.sharedlife.dto.AuthUser;

@Component
public class JwtProvider {
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generateToken(Authentication authentication) {
		AuthUser authUser = (AuthUser) authentication.getPrincipal();
		return Jwts.builder().setSubject(authUser.getUsername())
							 .setIssuedAt(new Date())
							 .setExpiration(new Date(new Date().getTime() + expiration * 1000))
							 .signWith(SignatureAlgorithm.HS512, secret)
							 .compact();
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try{
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		}catch(MalformedJwtException e) {
			logger.error("token mal formado");
		}catch(UnsupportedJwtException e) {
			logger.error("token no soportado");
		}catch(ExpiredJwtException e) {
			logger.error("token expirado");
		}catch(IllegalArgumentException e) {
			logger.error("token vacio");
		}catch(SignatureException e) {
			logger.error("fail en la firma");
		}
		return false;
	}
	
}
