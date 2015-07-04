package com.examplecorp.phonecat.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.examplecorp.phonecat.model.User;
import com.examplecorp.phonecat.repository.UserRepository;

@Component
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		return new AppUserDetail(userRepository.findByEmail(userName));
	}

	public class AppUserDetail implements UserDetails {

		private static final long serialVersionUID = -2978519495319796988L;

		public static final String SCOPE_READ = "read";

		public static final String SCOPE_WRITE = "write";

 		public static final String ROLE_USER = "USER";

		private Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
 		
		private User user;

		public AppUserDetail(User user) {
			this.user= user;
			for (String ga : Arrays.asList(ROLE_USER, SCOPE_READ, SCOPE_WRITE)) {
				this.grantedAuthorities.add(new SimpleGrantedAuthority(ga));
			}
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.grantedAuthorities;
		}

		@Override
		public String getPassword() {
			return user.getPassword();
		}

		@Override
		public String getUsername() {
			return user.getEmail();
		}

		@Override
		public boolean isAccountNonExpired() {
			return isEnabled();
		}

		@Override
		public boolean isAccountNonLocked() {
			return isEnabled();
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return isEnabled();
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}
}
