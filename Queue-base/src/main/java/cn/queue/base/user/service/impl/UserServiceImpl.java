package cn.queue.base.user.service.impl;

import cn.queue.base.user.domain.entity.User;
import cn.queue.base.user.mapper.UserMapper;
import cn.queue.base.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 马兰友
 * @Date: 2024/05/20/17:41
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
