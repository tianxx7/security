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
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * @author labvi
 * @version 1.0.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*@Autowired
    private SpringSessionBackedSessionRegistry sessionRegistry;*/


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
                .sessionManagement()
                .maximumSessions(1)
//                .sessionRegistry(sessionRegistry)//使用session提供的会话注册表
                //会话数达到最大,阻止新会话建立,而不是踢掉旧的会话,带来的问题是,登出后没办法重新登录
                //这是因为spring security是通过监听session的销毁事件来出发会话信息表相关清理工作的
                //但我们并没有注册相关的监听器,导致security 无法正常清理过期或已经注销的会话
                //配置无法工作是因为缺少事件源
                .maxSessionsPreventsLogin(true) // 阻止新会话建立,默认为false,只能等旧会话过期才可以重新登录,超时间内,登出就不能登录了
                .and().invalidSessionUrl("/login.html")//会话超时重新定位到登录
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

    //httpSession的事件监听,改用session提供的会话注册表
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
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
