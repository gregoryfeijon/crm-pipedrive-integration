package br.com.gregoryfeijon.crmpipedriveintegration.security.config;

import java.io.Serializable;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import br.com.gregoryfeijon.crmpipedriveintegration.security.properties.JwtProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 26/05/2021 às 23:51:04
 *
 * @author gregory.feijon
 */

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 124992473757686173L;

	private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
	private final Key SECRET;

	public JwtTokenUtil(JwtProperties jwtProperties) {
		this.SECRET = new SecretKeySpec(jwtProperties.getSignKey().getBytes(), jwtProperties.getKeyType());
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(dateNow());
	}

	public String generateToken(String usuarioLogin, UserDetails userDetails) {
		return doGenerateToken(usuarioLogin, userDetails);
	}

	private String doGenerateToken(String subject, UserDetails userDetails) {
		Claims claims = Jwts.claims().setSubject(subject);
		claims.put("scopes", userDetails.getAuthorities());

		return Jwts.builder().setClaims(claims).setIssuer("Gregory").setIssuedAt(dateNow())
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
				.signWith(SECRET, SignatureAlgorithm.HS256).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Date dateNow() {
		return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	}
}
