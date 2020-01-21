package com.txx.security.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.txx.security.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * @author labvi
 * @version 1.0.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest,WebAuthenticationDetails> myWebAuthenticationDetailsSource;


    @Autowired
    public CustomerUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/product/**").hasAuthority("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/login.html").permitAll()
                .antMatchers("/captcha.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(myWebAuthenticationDetailsSource) // 应用AuthenticationDetailsSource
                .loginPage("/login.html")
                .loginProcessingUrl("/authentication/form")
                .and()
                .logout()
                .logoutUrl("/myLogout") // 默认/logout
               // .logoutSuccessUrl("/") // 注销成功后重定向的路由
                .invalidateHttpSession(true) // 使该用户的HttpSession失效
                .deleteCookies("cookie","cookie")//注销成功从,删除指定的cookie
                .and()
                .sessionManagement().invalidSessionUrl("/login.html")//会话超时重新定位到登录
//                .sessionManagement().invalidSessionStrategy(new MyInvalidSessionStrategy())
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
        .key("my");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());*/
        //应用自定义的provider
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new MyAuthenticationProvider(userDetailsService,new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Producer captcha(){
        Properties properties = new Properties();
        //图片宽度,高度
        properties.setProperty("kaptcha.image.width","150");
        properties.setProperty("kaptcha.image.height","40");
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string","0123456789");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.border","no");
        properties.setProperty("kaptcha.border.color","105,179,90");
        properties.setProperty("kaptcha.textproducer.font.size","30");
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;

    }
}
