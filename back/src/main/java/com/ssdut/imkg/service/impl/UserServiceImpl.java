package com.ssdut.imkg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssdut.imkg.config.security.component.UserDetailsServiceImpl;
import com.ssdut.imkg.mapper.RoleMapper;
import com.ssdut.imkg.mapper.UserMapper;
import com.ssdut.imkg.mapper.UserRoleMapper;
import com.ssdut.imkg.pojo.Role;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.pojo.UserRole;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.pojo.pub.UserRequestParam;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssdut.imkg.utils.EncodePasswordUtils;
import com.ssdut.imkg.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EncodePasswordUtils encodePasswordUtils;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    /**
     * 获得所有用户信息分页
     * @param currentPage
     * @param size
     * @param user
     * @return
     */
    @Override
    public RespPageBean getUsers(Integer currentPage, Integer size, UserRequestParam user) {
        //开启分页
        Page<UserRequestParam> page = new Page<>(currentPage,size);
        IPage<UserRequestParam> userIPage = userMapper.getUsers(page,user);
        RespPageBean respPageBean = new RespPageBean(userIPage.getTotal(),userIPage.getRecords());
        return respPageBean;
    }

    /**
     * 根据用户名获得用户信息
     * @param username
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        System.out.println("查询数据库");
        User user = null;
        user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));


        return user;
    }

    /**
     * 根据用户id，获得该用户的角色
     * @param id
     * @return
     */
    @Override
    public List<Role> getUserRoles(Integer id) {
        List<Role> roles = roleMapper.getUserRoles(id);
        return roles;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        //System.out.println("session中的验证码为======"+captcha);
        if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码错误,请重新输入");
        }
        UserDetails userDetails = null;
        userDetails = userDetailsService.loadUserByUsername(username);
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+userDetails);
        //System.out.println("参数pass===="+password);
        //System.out.println("查询到的pass======"+userDetails.getPassword());
        if(null==userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
            return  RespBean.error("用户名或密码不正确!!!，请重新输入");
        }
        if(!userDetails.isEnabled()){
            return  RespBean.error("账号不可用");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        System.out.println("当前用户的权限============"+userDetails.getAuthorities().size());
        return RespBean.success("登陆成功",tokenMap);
    }


    @Override

    /**
     * 获得所有的用户
     */
    public int getAllUsersCount() {
        return userMapper.getAllUsersCount();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public RespBean addUser(UserRequestParam user) {
        int maxId = userMapper.getMaxId();
        user.setId(new Integer(maxId+1));
        user.setEnabled(true);
        user.setUserFace("https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg");
        String pass = encodePasswordUtils.encodePassword(user.getPassword());
        user.setPassword(pass);
        int result = userMapper.addUser(user);
        if(result==1){
            Integer c = new Integer(userRoleMapper.getMaxId()+1);
            Integer roleid = new Integer(901);
            UserRole userRole =  new UserRole(c,user.getId(),roleid);
            System.out.println(userRole);
            int result1 = userRoleMapper.insert(userRole);
            if(result1 == 1){
                return RespBean.success("注册成功",user);
            }
        }
        return RespBean.error("注册失败，请重新尝试");
    }

    /**
     * 用户更新密码
     * @param oldPass
     * @param newPass
     * @param id
     * @return
     */
    @Override
    public RespBean updateUserPassword(String oldPass, String newPass, Integer id) {
        User user = userMapper.selectById(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("数据库查询到的oldPass===="+user.getPassword());
        boolean matches = encoder.matches(oldPass, user.getPassword());
        if(matches){
            user.setPassword(encoder.encode(newPass));
            int result = userMapper.updateById(user);
            if(result == 1){
                return RespBean.success("更新成功");
            }
        }
        return RespBean.error("旧密码输入错误");


    }

    /**
     * 获得所有用户，用于用户数据导出
     * @return
     */
    @Override
    public RespBean getAllUsers() {
        List<UserRequestParam> users =  userMapper.getAllUsers();
        for (UserRequestParam user:users
             ) {
            user.setPassword("******");
        }
        //System.out.println(users);
        return RespBean.success("获得",users);
    }
}
