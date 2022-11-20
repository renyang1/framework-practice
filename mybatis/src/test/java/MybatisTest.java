import com.ry.entity.User;
import com.ry.mapper.UserMapper1;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年09月02日 6:54 下午
 */
public class MybatisTest {

    private UserMapper1 userMapper1;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        userMapper1 = sqlSession.getMapper(UserMapper1.class);
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setId(3);
        user.setUsername("ry");
        user.setPassword("root");
        user.setBirthday("1994-01-01");
        userMapper1.addUser(user);
    }

    @Test
    public void findAll() {
        List<User> all = userMapper1.findAll();
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(3);
        user.setUsername("ry_update");
       userMapper1.updateUser(user);
    }

    @Test
    public void selectUserById() {
        User user = userMapper1.findUserById(1);
        System.out.println(user);
    }
}
